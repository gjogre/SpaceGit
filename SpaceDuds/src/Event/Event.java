package Event;

import SpaceView.Ship;
import SpaceView.Space;
import Tools.Particle;
import org.lwjgl.input.Keyboard;
import static Event.EventMachine.*;
import static Event.sharedContainer.*;
import org.lwjgl.opengl.Display;
public class Event{


    // declare all physics and renderobjects
    protected void init(){}
    public void update(){}
    public void release(){}
     
    
    public Event() {

    }
    
    private boolean paused = false;
    private void PauseMenu(){
        
        while(paused){

            paused = false;
        }
        System.out.println("FEEE");
    }
    private boolean keyType = false;
    public void pauseInput(){
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            if(!keyType){
               

                paused = !paused;
                
                keyType = true;
                PauseMenu();
            }
           
        } else {
             
            keyType = false;
        }

    }
    
    protected void createParticle(){
        
            Particle p = new Particle();
            Particle.addParticle(p);
            p.body = physicsCore.addSquareParticle(ship.getBody().getPosition().x + ship.getParticleOutputPos().x , ship.getBody().getPosition().y+ ship.getParticleOutputPos().y , 
            p.radius, ship.getAngle());
        
    }
    
}
     
