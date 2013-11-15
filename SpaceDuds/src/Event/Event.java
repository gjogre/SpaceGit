package Event;

import GameObjects.Ship;
import Tools.Particle;
import org.lwjgl.input.Keyboard;
import static Event.EventMachine.*;
import static Event.sharedContainer.*;
import org.lwjgl.opengl.Display;
public class Event{

    public static boolean paused = true;
    // declare all physics and renderobjects
    protected void init(){}
    public void update(){}
    public void release(){
        Particle.clear();
        renderer.release();
        physicsCore.release();
    }
     
    
    public Event() {

    }
   
    public void pauseInput(){
        while (Keyboard.next()) {
       if (Keyboard.getEventKeyState()) {
          if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
              paused = !paused;
              if(paused){
                  popEvent();
              }else {
                   pushEvent(new PauseEvent());
              }
           


        }
       }
      }
    }
    
    protected Particle createParticle(float x, float y, float size, float angle){
        
            Particle p = new Particle();
            Particle.addParticle(p);
            p.body = physicsCore.addSquareParticle(x, y, 
            size, angle,5f);
            return p;
        
    }
    protected Particle createParticle(float x, float y, float size, float angle, int ttl){
        
            Particle p = new Particle(ttl);
            Particle.addParticle(p);
            p.body = physicsCore.addSquareParticle(x, y, 
            size, angle,5f);
            return p;
        
    }
    
    protected Particle createParticle(float x, float y, float size, float angle, int ttl, float r, float g, float b, float speed){
        
            Particle p = new Particle(ttl);
            p.setColor(r, g, b);
            Particle.addParticle(p);
            p.body = physicsCore.addSquareParticle(x, y, 
            size, angle,speed);
            return p;
        
    }
    
}
     
