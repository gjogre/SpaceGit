package Event;

import Graphics.Renderer;
import Physics.Core;
import SpaceView.Space;
import Tools.Particle;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import static Event.sharedContainer.*;
public class EventMachine {

    private static ArrayList<Event> events;
    public static Core physicsCore;
    public static Renderer renderer;

    
    public EventMachine() throws LWJGLException{
        
        events = new ArrayList<>();
        Particle.bindCore(physicsCore);
        
        physicsCore = new Core();
        renderer = new Renderer();

        generatePlanets();

        
    }
    
    public void loop(){
                while(!Display.isCloseRequested()){
                events.get(events.size()-1).update();
                events.get(events.size()-1).pauseInput();
                Display.sync(60);
                renderer.render();
                Display.update();
                physicsCore.doStep();
                
            
        }
    }
    public static void pushEvent(Event event){
        if(!events.isEmpty()){
            events.get(events.size()-1).release();
        }
        event.init();

        events.add(event);
    }
    
    public static void popEvent(){
        events.get(events.size()-1).release();
        events.remove(events.get(events.size()-1));
        events.get(events.size()-1).init();
    }

}
