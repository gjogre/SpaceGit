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
    public void release(){
        Particle.clear();
        renderer.release();
        physicsCore.release();
    }
     
    
    public Event() {
        keyHold = true;
        pressed = false;
    }
    
    private boolean keyHold;
    private boolean pressed;
    public void pauseInput(){
       
          if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            
            if(!keyHold){
                
                 pressed = true;
            
        

            }
        } else{
            keyHold = false;
        }
        if(pressed){
            pushEvent(new PauseEvent());
            keyHold = true;
             pressed = false;
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
    protected void createParticle(float x, float y, float size, float angle, int ttl, float r, float g, float b){
        
            Particle p = new Particle(ttl);
            p.setColor(r, g, b);
            Particle.addParticle(p);
            p.body = physicsCore.addSquareParticle(x, y, 
            size, angle);
        
    }
    
}
     
