
package SpaceView;
import Event.Event;
import static Event.EventMachine.*;
import Tools.Particle;
import Tools.GameObject;
import java.util.ArrayList;
import java.util.Random;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import static Event.EventMachine.*;
import PlanetView.PlanetEvent;
import static Event.sharedContainer.*;
public class SpaceEvent extends Event{
   
    
    private Body debugBody = null;
    private Map map;
    private Random r = new Random();
    
    public SpaceEvent(){
        
        
        
    }

    @Override
        protected void init(){


            ship.setBody(physicsCore.addObject(2f, 10f, ship.getShape(), ship.getshapeVecCount(), 0.1f,  0.5f, 0.5f));
            ship.setTexture("basicShip.png");
            renderer.addObject(ship);

            GameObject testObject = new GameObject();
            testObject.setBody(physicsCore.addObject(4f, 10f, testObject.getShape(), testObject.getshapeVecCount(), 0.1f,  0.5f, 0.5f));
            renderer.addObject(testObject);
            // this is the end of define


            for(Planet p : planets){
                p.setRoundShape(p.getSize());
                p.setBody(physicsCore.addPlanet(50f-p.getDistanceToSun(), r.nextFloat()*50-100, p.getSize()));
                renderer.addObject(p);
            }
            makeMapFrame();
            renderer.addGuiObject(map);
        }
    
    
    @Override
    public void update(){
        input();
        updateMap();
        renderer.setCameraPos(ship.getPos().x, ship.getPos().y);
    }
    @Override
    public void release(){
        renderer.release();
        physicsCore.release();
    }
    
    private void input(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){

            //createParticle();
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
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            pushEvent(new PlanetEvent());
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
        
        map.setQuadColor(0.6f, 0.1f, 0.6f);
       // map.addQuad(0, 0,10f);
        System.out.println(planets.size());
        for(Planet p : planets){
            map.addQuad(p.getPos().x/50f, p.getPos().y/50f, p.getSize()/10);
        }
        map.player = map.addQuad(ship.getPos().x/50f, ship.getPos().y/50f, 0.2f);
}

private void updateMap(){
    
    map.setQuad(ship.getPos().x/50f, ship.getPos().y/50f, 0.2f,map.player-1);
    
}

}
        
        //testing addon, 2 wings
        /*Wing wing = new Wing();
        wing.setBody(physicsCore.addObject(ship.getPos().x-2, ship.getPos().y+1, wing.getShape(), wing.getshapeVecCount(), 0.1f,  0.5f, 0.5f));
        physicsCore.distanceJoint(ship.getBody(), wing.getBody(), 
                ship.getBody().getWorldCenter(), wing.getBody().getWorldCenter());
        renderer.addObject(wing);
        
        Wing wing2 = new Wing();
        wing2.setBody(physicsCore.addObject(ship.getPos().x-2, ship.getPos().y-1, wing2.getShape(), wing2.getshapeVecCount(), 0.1f,  0.5f, 0.5f));
        wing2.getBody().setTransform(wing2.getBody().getPosition(), 3.14159265f);
        physicsCore.distanceJoint(ship.getBody(), wing2.getBody(), 
                ship.getBody().getWorldCenter(), wing2.getBody().getWorldCenter());
        renderer.addObject(wing2);*/
        // /testing addon