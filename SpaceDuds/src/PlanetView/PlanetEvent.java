package PlanetView;

import Event.Event;
import static Event.EventMachine.*;
import Event.sharedContainer;
import GameObjects.BattleShip;
import ObjectBuilders.RooverBuilder;
import GameObjects.Ground;
import GameObjects.Meteor;
import GameObjects.Planet;
import GameObjects.Roover;
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
    private float cameraPushbackX = 7; // changing this parameter will make camera go 'n' pixels ahead the ship 
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
    
    private boolean meteorbool = false;
    private Roover roover;
    private Wheel backWheel, frontWheel, topWheel;
    private RooverBuilder rooverBuilder;
    
    @Override
    public void update(){
        input();
        renderer.setCameraTargetPos(frontWheel.getPos().x+cameraPushbackX, frontWheel.getPos().y);
        /*test = r.nextInt(3)+1;
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
        physicsCore.setGravity(0f, -9.81f); jos jostain syystä haluut experimenttaa gravityllä nii tällä hoituu. muista vaa popeventis sit pistää se takas 0. 
        //jos haluut et osalla on gravity ja osalla ei, pistä niille objekteille applyforcea -gravityn verran updatessa nii se ei enää vaikutakkaa niihin!
        
        //esimerkki miten partikkelia vois käyttää. toi vika luku on time to live. eventTimer on eventin alusta kulunu aika. angle radiaani.
        /*if(eventTimer % 3 == 0) {
            createParticle(50f, 15f, r.nextFloat()+0.2f, r.nextFloat()*3, 30);
        }*/
        physicsCore.setGravity(0f, -9.81f);
    }
    
    
    @Override
    protected void init(){
        physicsCore.setGravity(0f, -9.81f);
        renderer.setLerp(0.6f);
        renderer.ZOOM_X = 1.6f;
        renderer.ZOOM_Y = 1.6f;
        
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
            shapeArrayBase++;
        }
        
        groundShapeList = new Vec2[surface.groundList.size()+shapeArrayBase];
        backwardRun = groundShapeList.length-1;
        
        System.out.println(groundShapeList.length);
        for(Ground g : surface.groundList){
            //g.defaultColorsRGB = sharedContainer.currentPlanet.defaultColorsRGB;
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
        
        /*ship = new BattleShip();
        ship.setBody(physicsCore.addObject(15f, 10f, ship.getShape(), ship.getshapeVecCount(), 1f,  0.5f, 0.5f));
        physicsCore.addDamageObject(ship);
        renderer.addObject(ship); 
        physicsCore.addDamageObject(ship);*/
        
        roover = new Roover(1f);
        backWheel = new Wheel();
        frontWheel = new Wheel();
        rooverBuilder = new RooverBuilder(roover, backWheel, frontWheel, 7f, 12f);
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
            //backWheel.applyForceForward(5f);
            backWheel.applyRotation(-5f);
            frontWheel.applyRotation(-5f);
            
        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            backWheel.applyRotation(5f);
            frontWheel.applyRotation(5f);
            
            if(!typed){
                System.out.println("asd");
                typed = true;
                //backWheel.applyImulse(3f);
            }else{
                typed = false;
            }
        }else if(Keyboard.isKeyDown(Keyboard.KEY_U)){
            meteor.applyForceForward(5f);
            
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            roover.applyRotation(-5f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            roover.applyRotation(5f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_P)){
            renderer.setLerp(0.1f);
            renderer.ZOOM_X = 1.0f;
            renderer.ZOOM_Y = 1.0f;
            popEvent();

        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_B)){
            //createParticle();
        }
    }

    
    
}