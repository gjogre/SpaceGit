
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
    private Collection<Planet> planets = new ArrayList<>();
    private Collection<Particle> particles = new ArrayList<>();
    private Body debugBody = null;
    
    private Random r = new Random();
    
    
    public SpaceEvent(Renderer renderer){
        
        
        physicsCore = new Core();
        // this is how you define object
        ship = new Ship();
        ship.setBody(physicsCore.addObject(0, 0, ship.getShape(), ship.getshapeVecCount(), 0.5f, 0.5f, 0.5f));
        renderer.addObject(ship);
        // this is the end of define
        GameObject square = new GameObject();
        square.setRoundShape(2f);
        square.setBody(physicsCore.addPlanet(5, 0,2f));
        renderer.addObject(square);
        
        
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
            Particle p = new Particle();
            p.radius = (r.nextFloat()+0.1f)/5;

            particles.add(p);
            
            ship.applyForceForward(2);

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


    
    private void drawPlanets(){

        double angle = 0;

        
            for(Planet p : planets){
                angle = 0;
                glPushMatrix();
                glTranslatef(p.getPos().x, p.getPos().y, 0);
                glBegin(GL_POLYGON); 
                for(int i = 0; i < 39; i ++){
                    glVertex2d(p.getSize()*Math.cos(i*2*Math.PI / 32)+p.getPos().x,p.getSize()*Math.sin(i*2*Math.PI / 32)+p.getPos().y );
                }
                glEnd();
                glPopMatrix();
            }
        

    }

    
    

}
