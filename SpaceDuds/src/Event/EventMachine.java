package Event;

import Graphics.Renderer;
import Physics.Core;
import Tools.Particle;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import static Event.sharedContainer.*;
public class EventMachine {

    private static ArrayList<Event> events;
    public static Core physicsCore;
    public static Renderer renderer;
    public static long eventTimer;
    
    public EventMachine() throws LWJGLException{
        
        events = new ArrayList<>();
        
        physicsCore = new Core();
        renderer = new Renderer();

        generatePlanets();
        sharedContainer.currentPlanet = space.generatePlanetArray(0).get(2);
        eventTimer = 0;
        
    }
    
    public void loop(){
                while(!Display.isCloseRequested()){
                eventTimer++;
                Display.sync(60);
                
                if(!events.get(events.size()-1).pauseInput()) {
                        events.get(events.size()-1).update();
                         physicsCore.doStep();
                    }
                
                renderer.render();
                Display.update();
               
                
            
        }
    }
    public static void pushEvent(Event event){
        if(!events.isEmpty()){
            events.get(events.size()-1).release();
        }
        event.init();
        eventTimer = 0;
        events.add(event);
    }
    
    public static void popEvent(){
        events.get(events.size()-1).release();
        events.remove(events.get(events.size()-1));
        eventTimer = 0;
        events.get(events.size()-1).init();
    }

}
