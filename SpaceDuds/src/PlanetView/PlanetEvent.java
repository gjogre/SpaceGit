package PlanetView;

import GameObjects.BattleShip;
import GameObjects.Ground;
import Event.Event;
import org.lwjgl.input.Keyboard;
import static Event.EventMachine.*;
import GameObjects.Meteor;
import GameObjects.Volcano;
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
        
        for(Ground g : surface.groundList){
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
            createParticle();
        }
    }

    
    
}
