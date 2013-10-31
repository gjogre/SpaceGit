package PlanetView;

import Event.Event;
import static Event.EventMachine.*;
import Event.sharedContainer;
import GameObjects.BattleShip;
import GameObjects.Ground;
import GameObjects.Meteor;
import GameObjects.Planet;
import Graphics.SpaceTexture;
import java.util.Random;
import org.jbox2d.common.Vec2;
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
    
    private boolean meteorbool = false;

    @Override
    public void update(){
        input();
        renderer.setCameraTargetPos(ship.getPos().x+cameraPushbackX, ship.getPos().y/*cameraY*/);
        test = r.nextInt(3)+1;
        if(test <= 3 && !meteorbool){
            invokeMeteor();
            meteorbool = true;
        }else if(meteorbool){
            if(eventTimer % 2 == 0) {
            //createParticle(meteor.getPos().x, meteor.getPos().y, r.nextFloat()+0.2f ,(float)(Math.PI/4),30);
            }
            if(meteor.getHitBool()){
                //System.out.println("OSUMA!");
                
            }
            meteor.applyRotation(1f);
            
        }
        meteor.getBody().applyForceToCenter(new Vec2(0,-5f));
        //physicsCore.setGravity(0f, -9.81f); jos jostain syystä haluut experimenttaa gravityllä nii tällä hoituu. muista vaa popeventis sit pistää se takas 0. 
        //jos haluut et osalla on gravity ja osalla ei, pistä niille objekteille applyforcea -gravityn verran updatessa nii se ei enää vaikutakkaa niihin!
        
        //esimerkki miten partikkelia vois käyttää. toi vika luku on time to live. eventTimer on eventin alusta kulunu aika. angle radiaani.
        /*if(eventTimer % 3 == 0) {
            createParticle(50f, 15f, r.nextFloat()+0.2f, r.nextFloat()*3, 30);
        }*/
    }
    
    
    @Override
    protected void init(){
        float startX = 10f;
        float scale = 5f;
        i = 0;
        surface = sharedContainer.currentPlanet.getSurface();
        groundShapeList = new Vec2[surface.groundList.size()+3];
        backwardRun = groundShapeList.length-1;
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
            g.setTexture(groundTexture,anchors);
            g.setBody(physicsCore.addGround(startX,0f,g.getShape(),g.getshapeVecCount()));
            renderer.addObject(g);
            if(g.isVolcanic()){
                g.volcano.setBody(physicsCore.addGround(startX,0f,g.volcano.getShape(),g.volcano.getshapeVecCount()));
                g.volcano.setTransform(startX,g.returnStart(),(float)Math.atan2((g.returnTopRight().y-g.returnTopLeft().y),scale));
                renderer.addObject(g.volcano);
            }
            
            if(i == (surface.groundList.size()/2)-1){
                groundShapeCenter = g.getPos();
            }
            
            if(!firstPiece){
                groundShapeList[0] = new Vec2((surface.groundList.get(i).returnTopLeft().x + startX), surface.groundList.get(i).returnTopLeft().y);
                groundShapeList[1] = new Vec2((surface.groundList.get(i).returnBotLeft().x + startX), surface.groundList.get(i).returnBotLeft().y);
                firstPiece = true;
            }else if(i == surface.groundList.size() - 1){
                groundShapeList[2] = new Vec2((surface.groundList.get(i).returnBotRight().x + startX), surface.groundList.get(i).returnBotRight().y);
                groundShapeList[3] = new Vec2((surface.groundList.get(i).returnTopRight().x + startX), surface.groundList.get(i).returnTopRight().y);
                groundShapeList[4] = new Vec2((surface.groundList.get(i).returnTopLeft().x + startX), surface.groundList.get(i).returnTopLeft().y);
            }else if(backwardRun >= 5){
                groundShapeList[backwardRun] = new Vec2((surface.groundList.get(i).returnTopLeft().x + startX), surface.groundList.get(i).returnTopLeft().y);
                backwardRun--;
            }
            i++;
            startX = startX + scale;
        }
        
        for(i = 0;i<groundShapeList.length;i++){
            groundShapeList[i].x = groundShapeList[i].x-groundShapeCenter.x;
            groundShapeList[i].y = groundShapeList[i].y+groundShapeCenter.y;
            System.out.println(groundShapeList[i].toString());   
        }
        
        ship = new BattleShip();
        ship.setBody(physicsCore.addObject(15f, 10f, ship.getShape(), ship.getshapeVecCount(), 1f,  0.5f, 0.5f));
        physicsCore.addDamageObject(ship);
        renderer.addObject(ship); 
        physicsCore.addDamageObject(ship);
    }
    
    private void invokeMeteor(){
        //meteorChance = r.nextInt(100)+1;
        meteorChance = 0;
        
        float meteorStartX = renderer.getCameraPos().x + 30;
        float meteorStartY = renderer.getCameraPos().y + 30;
        
        if(meteorChance == 0){
            meteor = new Meteor();
            meteor.setBody(physicsCore.addObject(meteorStartX, meteorStartY, meteor.getShape(), meteor.getshapeVecCount(), 1f,  0.5f, 0.1f));
            meteor.setTransform(meteorStartX, meteorStartY,(float)(5*Math.PI)/4);
            physicsCore.addDamageObject(meteor);
            renderer.addObject(meteor);
            for(int impulse = 0; impulse < 30 ;impulse++){
               meteor.applyImulse(50f); 
            }  
            //physicsCore.addDamageObject(meteor);
        }
    }
    
    private void input(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            ship.applyForceForward(5f);
            
        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            if(!typed){
                System.out.println("asd");
                typed = true;
                ship.applyImulse(3f);
            }else{
                typed = false;
            }
        }else if(Keyboard.isKeyDown(Keyboard.KEY_U)){
            meteor.applyForceForward(5f);
            
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            ship.applyRotation(-5f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            ship.applyRotation(5f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_P)){
            popEvent();

        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_B)){
            //createParticle();
        }
    }

    
    
}