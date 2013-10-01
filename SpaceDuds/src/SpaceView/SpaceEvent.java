
package SpaceView;
import Tools.Particle;
import Graphics.Renderer;
import Physics.Core;
import Tools.GameObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;


public class SpaceEvent{
   
    private Ship ship;
    private Core physicsCore;
    private Body debugBody = null;
    private Map map;
    private Random r = new Random();
    
    
    public SpaceEvent(Renderer renderer){
        
        Space space = new Space();
        physicsCore = new Core();
        // this is how you define object
        ship = new Ship();
        ship.setBody(physicsCore.addObject(2f, 10f, ship.getShape(), ship.getshapeVecCount(), 0.1f,  0.5f, 0.5f));
        renderer.addObject(ship);
        // this is the end of define
        
        //testing addon, 2 wings
        Wing wing = new Wing();
        wing.setBody(physicsCore.addObject(ship.getPos().x-2, ship.getPos().y+1, wing.getShape(), wing.getshapeVecCount(), 0.1f,  0.5f, 0.5f));
        physicsCore.distanceJoint(ship.getBody(), wing.getBody(), 
                ship.getBody().getWorldCenter(), wing.getBody().getWorldCenter());
        renderer.addObject(wing);
        
        Wing wing2 = new Wing();
        wing2.setBody(physicsCore.addObject(ship.getPos().x-2, ship.getPos().y-1, wing2.getShape(), wing2.getshapeVecCount(), 0.1f,  0.5f, 0.5f));
        wing2.getBody().setTransform(wing2.getBody().getPosition(), 3.14159265f);
        physicsCore.distanceJoint(ship.getBody(), wing2.getBody(), 
                ship.getBody().getWorldCenter(), wing2.getBody().getWorldCenter());
        renderer.addObject(wing2);
        // /testing addon
        
        makeMapFrame();
        
        ArrayList<Planet> planets = space.generatePlanetArray(0);
        for(Planet p : planets){
            p.setRoundShape(p.getSize());
            p.setBody(physicsCore.addPlanet(50f-p.getDistanceToSun(), 0, p.getSize()));
            renderer.addObject(p);
        }

        renderer.addGuiObject(map);
        while(!Display.isCloseRequested()){
            
            input();
            Display.sync(60);
            renderer.setCameraPos(ship.getPos().x, ship.getPos().y);
            renderer.render();
            Display.update();
            physicsCore.doStep();
            
            
        }
        
    }

    private void input(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            //Particle p = new Particle();
           // p.radius = (r.nextFloat()+0.1f)/5;

            //particles.add(p);
            
            ship.applyForceForward(2f);

        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            ship.applyForce(new Vec2(0,-1));
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            ship.applyRotation(-2f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            ship.applyRotation(2f);
        } else {
            

        }
        
    }
private void makeMapFrame(){
        map = new Map(-19.0f,-19.0f);
        map.setLineColor(0f,0f,1f);
        map.addLine(5.0f, 5f);
        map.addLine(-5.0f, 5f);
        map.addLine(-5.0f, 5f);
        map.addLine(-5.0f, -5f);
        map.addLine(-5.0f, -5f);
        map.addLine(5.0f, -5f);
        map.addLine(5.0f, -5f);
        map.addLine(5.0f, 5f);
        
        map.setQuadColor(0.1f, 0.1f, 0.1f);
        map.addQuad(0, 0,10f);
}

}
