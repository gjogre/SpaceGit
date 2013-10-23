package Event;

import GameObjects.Ship;
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
    
    protected void createParticle(float x, float y, float size, float angle){
        
            Particle p = new Particle();
            Particle.addParticle(p);
            p.body = physicsCore.addSquareParticle(x, y, 
            size, angle);
        
    }
        protected void createParticle(float x, float y, float size, float angle, int ttl){
        
            Particle p = new Particle(ttl);
            Particle.addParticle(p);
            p.body = physicsCore.addSquareParticle(x, y, 
            size, angle);
        
    }
    
}
     
