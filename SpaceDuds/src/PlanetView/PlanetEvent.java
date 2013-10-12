package PlanetView;

import Event.Event;
import Event.EventMachine;
import Graphics.Renderer;
import Physics.Core;
import SpaceView.Planet;
import SpaceView.Ship;
import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import static Event.EventMachine.*;
public class PlanetEvent extends Event{
    private Surface surface;
    private BattleShip ship;
    private Ground ground, ground2;
    private float cameraY = 10;
    private float cameraPushbackX = 15; // changing this parameter will make camera go 'n' pixels ahead the ship 
    
    

    @Override
    public void update(){
    input();
    renderer.setCameraPos(ship.getPos().x+cameraPushbackX, cameraY);
    }
    
    @Override
    public void release(){
        renderer.release();
        physicsCore.release();
    
    }
    
    @Override
    protected void init(){
        float startX = -30f;
        float scale = 6f;
        surface = new Surface(scale);
        
        for(Ground g : surface.groundList){
            g.setBody(physicsCore.addGround(startX,0f,g.getShape(),g.getshapeVecCount()));
            renderer.addObject(g);
            startX = startX + scale;
        }
        ship = new BattleShip();
        ship.setBody(physicsCore.addObject(15f, 10f, ship.getShape(), ship.getshapeVecCount(), 1f,  0.5f, 0.5f));
        renderer.addObject(ship);
        
    }
    
    private void input(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            ship.applyForceForward(1f);
            popEvent();
        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            ship.applyImulse(3f);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            ship.applyRotation(-0.5f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            ship.applyRotation(0.5f);
        } else {
            

        }
        
    }
    
}
