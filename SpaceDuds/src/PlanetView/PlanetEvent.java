package PlanetView;

import Event.Event;
import static Event.EventMachine.*;
import Event.sharedContainer;
import GameObjects.BattleShip;
import GameObjects.Ground;
import GameObjects.Meteor;
import GameObjects.Planet;
import GameObjects.Lander;
import GameObjects.Wheel;
import Graphics.SpaceTexture;
import java.util.Random;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.lwjgl.input.Keyboard;

public class PlanetEvent extends Event{
    private Surface surface;
    private BattleShip ship;
    private Ground ground;
    private Meteor meteor;
    
    private float cameraY = 10;
    private float cameraPushbackX = 15; // changing this parameter will make camera go 'n' pixels ahead the ship 
    private boolean typed = false;
    
    private Random r = new Random();
    private int meteorChance;
    private int test;
    
    private float meteorStartX;
    private float meteorStartY;
    
    public Vec2[] groundShapeList;
    int i;
    
    private boolean firstPiece = false;
    int backwardRun;
    private Vec2 groundShapeCenter;
    
    private float centerX, centerY;
    private int shapeArrayBase = 5;
    
    private float maxSpeed = 0, maxReverse = 0;
    private boolean lastMeteorDestroyed = true;
    
    @Override
    public void update(){
        input();
        if(sharedContainer.ship.getCurrentLander().getLanderType() == sharedContainer.ship.getCurrentLander().ROOVER){
            renderer.setCameraTargetPos(sharedContainer.ship.getCurrentLander().getBackWheel().getPos().x+cameraPushbackX, sharedContainer.ship.getCurrentLander().getFrontWheel().getPos().y);
        }else if(sharedContainer.ship.getCurrentLander().getLanderType() == sharedContainer.ship.getCurrentLander().SCOUT){
            renderer.setCameraTargetPos(sharedContainer.ship.getCurrentLander().getPos().x+cameraPushbackX, sharedContainer.ship.getCurrentLander().getPos().y);
        }
        test = r.nextInt(3)+1;
        if(test <= 3 && lastMeteorDestroyed){
            invokeMeteor();
            lastMeteorDestroyed = false;
        }else if(!lastMeteorDestroyed){
            if(eventTimer % 2 == 0) {
                createParticle(meteor.getPos().x, meteor.getPos().y, r.nextFloat()+0.2f ,(float)(Math.PI/4),30);
            }
            if(meteor.getHitBool()){
                meteor.kill();
                lastMeteorDestroyed = true;
            }
            meteor.applyRotation(1f);
            
        }
        //meteor.getBody().applyForceToCenter(new Vec2(0,-5f));
        //physicsCore.setGravity(0f, -9.81f); //jos jostain syystä haluut experimenttaa gravityllä nii tällä hoituu. muista vaa popeventis sit pistää se takas 0. 
        //jos haluut et osalla on gravity ja osalla ei, pistä niille objekteille applyforcea -gravityn verran updatessa nii se ei enää vaikutakkaa niihin!
        
        //esimerkki miten partikkelia vois käyttää. toi vika luku on time to live. eventTimer on eventin alusta kulunu aika. angle radiaani.
        /*if(eventTimer % 3 == 0) {
            createParticle(50f, 15f, r.nextFloat()+0.2f, r.nextFloat()*3, 30);
        }*/
    }
    
    
    @Override
    protected void init(){
        renderer.setLerp(0.6f);
        renderer.ZOOM_X = 1.3f;
        renderer.ZOOM_Y = 1.3f;
        
        float startX = -30f;
        i = 0;
        surface = sharedContainer.currentPlanet.getSurface();
        
        SpaceTexture groundTexture;
        if(sharedContainer.currentPlanet.getType() == Planet.Type.MOON){
            groundTexture = new SpaceTexture("moonGround.png", 10f, 10f,0f,0f);
        } else if(sharedContainer.currentPlanet.getType() == Planet.Type.GAS){
            groundTexture = new SpaceTexture("lavaGround.png", 10f, 10f,0f,0f);
        } else {
            groundTexture = new SpaceTexture("lavaGround.png", 1f, 1f,0f,0f);
        }
        int[] anchors = {
          0,1,2,
          3,0,2    
        };
        
        for(Ground g : surface.groundList){
            g.defaultColorsRGB = sharedContainer.currentPlanet.defaultColorsRGB;
            shapeArrayBase++;
        }
        
        groundShapeList = new Vec2[surface.groundList.size()+shapeArrayBase];
        backwardRun = groundShapeList.length-1;
        
        System.out.println(groundShapeList.length);
        for(Ground g : surface.groundList){

            g.setTexture(groundTexture,anchors);
            g.setBody(physicsCore.addGround(startX,0f,g.getShape(),g.getshapeVecCount()));
            renderer.addObject(g);
            /*if(g.isVolcanic()){
                g.volcano.setBody(physicsCore.addGround(startX,0f,g.volcano.getShape(),g.volcano.getshapeVecCount()));
                g.volcano.setTransform(startX,g.returnStart(),(float)Math.atan2((g.returnTopRight().y-g.returnTopLeft().y), sharedContainer.currentPlanet.getRoughness()+10));
                renderer.addObject(g.volcano);
            }*/
            startX = startX + sharedContainer.currentPlanet.getRoughness()+10;
        }
        

        LanderBuilder.Builder(sharedContainer.ship.getCurrentLander(), 7f, 12f);
        
    }
    
    private void invokeMeteor(){
        
        float meteorStartX = renderer.getCameraPos().x + 30;
        float meteorStartY = renderer.getCameraPos().y + 30;

        meteor = new Meteor();
        meteor.setBody(physicsCore.addObject(meteorStartX, meteorStartY, meteor.getShape(), meteor.getshapeVecCount(), 1f,  0.5f, 0.1f));
        meteor.setTransform(meteorStartX, meteorStartY,(float)(5*Math.PI)/4);
        physicsCore.addDamageObject(meteor);
        renderer.addObject(meteor);
        for(int impulse = 0; impulse < 30 ;impulse++){
           meteor.applyImulse(50f); 
        }
    }
    
    private void input(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            //backWheel.applyForceForward(5f);
            if(sharedContainer.ship.getCurrentLander().getLanderType() == sharedContainer.ship.getCurrentLander().ROOVER){
                
                    sharedContainer.ship.getCurrentLander().getBackWheel().applyRotation(-50f);
                    sharedContainer.ship.getCurrentLander().getFrontWheel().applyRotation(-50f);
                 
            }else if(sharedContainer.ship.getCurrentLander().getLanderType() == sharedContainer.ship.getCurrentLander().SCOUT){
                sharedContainer.ship.getCurrentLander().applyForceForward(5f);
            }
            
        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            if(sharedContainer.ship.getCurrentLander().getLanderType() == sharedContainer.ship.getCurrentLander().ROOVER){
                sharedContainer.ship.getCurrentLander().getBackWheel().applyRotation(20f);
                sharedContainer.ship.getCurrentLander().getFrontWheel().applyRotation(20f);
            }else if(sharedContainer.ship.getCurrentLander().getLanderType() == sharedContainer.ship.getCurrentLander().SCOUT){
                if(!typed){
                    System.out.println("asd");
                    typed = true;
                    sharedContainer.ship.getCurrentLander().applyImulse(3f);
                }else{
                    typed = false;
                }
            }
            
           
        }else if(Keyboard.isKeyDown(Keyboard.KEY_U)){
            meteor.applyForceForward(5f);
            
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            sharedContainer.ship.getCurrentLander().applyRotation(-20f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            sharedContainer.ship.getCurrentLander().applyRotation(20f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_P)){
            renderer.setLerp(0.1f);
            renderer.ZOOM_X = 1.0f;
            renderer.ZOOM_Y = 1.0f;
            sharedContainer.ship.addLander(sharedContainer.ship.getCurrentLander());
            popEvent();

        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_B)){
            //createParticle();
        }
    }
    
    
    
    
}