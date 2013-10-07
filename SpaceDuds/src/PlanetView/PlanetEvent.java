package PlanetView;

import Graphics.Renderer;
import Physics.Core;
import SpaceView.Planet;
import SpaceView.Ship;
import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class PlanetEvent {
    private Surface surface;
    private Core physicsCore;
    private BattleShip ship;
    private Ground ground, ground2;
    private float cameraY = 10;
    private float cameraPushbackX = 15; // changing this parameter will make camera go 'n' pixels ahead the ship 
    
    public PlanetEvent(Renderer renderer){
        float startX = -30f;
        float scale = 6f;
        surface = new Surface(scale);
        physicsCore = new Core();
        
        for(Ground g : surface.groundList){
            g.setBody(physicsCore.addGround(startX,0f,g.getShape(),g.getshapeVecCount()));
            renderer.addObject(g);
            startX = startX + scale;
        }
        ship = new BattleShip();
        ship.setBody(physicsCore.addObject(15f, 10f, ship.getShape(), ship.getshapeVecCount(), 1f,  0.5f, 0.5f));
        renderer.addObject(ship);
        
        while(!Display.isCloseRequested()){
            
            input();
            Display.sync(60);
            renderer.setCameraPos(ship.getPos().x+cameraPushbackX, cameraY);
            renderer.render();
            Display.update();
            physicsCore.doStep();  
        } 
    }
    private void input(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            ship.applyForceForward(1f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            ship.applyLinearImpulse(3f,0f);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            ship.applyRotation(-0.5f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            ship.applyRotation(0.5f);
        } else {
            

        }
        
    }
    
}
