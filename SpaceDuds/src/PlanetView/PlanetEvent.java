package PlanetView;

import GameObjects.BattleShip;
import GameObjects.Ground;
import Event.Event;
import org.lwjgl.input.Keyboard;
import static Event.EventMachine.*;
import GameObjects.Meteor;
import GameObjects.Volcano;
import Graphics.SpaceTexture;
import java.util.Random;

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
    
    private boolean meteorbool = false;

    @Override
    public void update(){
        input();
        renderer.setCameraPos(ship.getPos().x+cameraPushbackX, ship.getPos().y/*cameraY*/);
        test = r.nextInt(1000)+1;
        if(test <= 3 && !meteorbool){
            invokeMeteor();
            meteorbool = true;
        }
        //physicsCore.setGravity(0f, -9.81f); jos jostain syystä haluut experimenttaa gravityllä nii tällä hoituu. muista vaa popeventis sit pistää se takas 0. 
        //jos haluut et osalla on gravity ja osalla ei, pistä niille objekteille applyforcea -gravityn verran updatessa nii se ei enää vaikutakkaa niihin!
        
        //esimerkki miten partikkelia vois käyttää. toi vika luku on time to live. eventTimer on eventin alusta kulunu aika. angle radiaani.
        if(eventTimer % 3 == 0) {
            createParticle(50f, 15f, r.nextFloat()+0.2f, r.nextFloat()*3, 30);
        }
    }
    
    @Override
    public void release(){
        renderer.release();
        physicsCore.release();
    }
    
    @Override
    protected void init(){
        float startX = 10f;
        float scale = 5f;
        surface = new Surface(scale);
        SpaceTexture ground = new SpaceTexture("basicGround.png", 20f, 50f,0f,1.1f);
        for(Ground g : surface.groundList){
            g.setTexture(ground);
            g.setBody(physicsCore.addGround(startX,0f,g.getShape(),g.getshapeVecCount()));
            renderer.addObject(g);
            if(g.isVolcanic()){
                g.volcano.setBody(physicsCore.addGround(startX,0f,g.volcano.getShape(),g.volcano.getshapeVecCount()));
                g.volcano.setTransform(startX,g.returnStart(),(float)Math.atan2((g.returnLast().y-g.returnFirst().y),scale));
                renderer.addObject(g.volcano);
            }
            startX = startX + scale;
        }
        
        ship = new BattleShip();
        ship.setBody(physicsCore.addObject(15f, 10f, ship.getShape(), ship.getshapeVecCount(), 1f,  0.5f, 0.5f));
        renderer.addObject(ship);  
    }
    
    private void invokeMeteor(){
        meteorChance = r.nextInt(100)+1;
        meteorChance = meteorChance%3;
        
        float meteorStartX = renderer.getCameraPos().x + 30;
        float meteorStartY = renderer.getCameraPos().y + 30;
        if(meteorChance == 0){
            meteor = new Meteor();
            meteor.setBody(physicsCore.addObject(meteorStartX, meteorStartY, meteor.getShape(), meteor.getshapeVecCount(), 1f,  0.5f, 0.5f));
            renderer.addObject(meteor);
            meteor.applyImulse(1f);
            meteor.applyForceForward(1f);
            
        }
    }
    
    private void input(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            ship.applyForceForward(1f);
            
        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            if(!typed){
                System.out.println("asd");
                typed = true;
                ship.applyImulse(3f);
            }else{
                typed = false;
            }
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            ship.applyRotation(-0.5f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            ship.applyRotation(0.5f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_P)){
            popEvent();

        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_B)){
            //createParticle();
        }
    }

    
    
}