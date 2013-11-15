package Event;

import Graphics.Renderer;
import Physics.Core;
import Tools.Particle;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import static Event.sharedContainer.*;
import java.util.Random;

public class EventMachine {

    private static ArrayList<Event> events;
    public static Core physicsCore;
    public static Renderer renderer;
    public static long eventTimer;
    public static Random r;
    
    public static boolean VSync = true;
    
    public EventMachine() throws LWJGLException{
        r = new Random();
        events = new ArrayList<>();
        
        physicsCore = new Core();
        renderer = new Renderer(false);
        initSound();
        generatePlanets();
        sharedContainer.currentPlanet = space.getPlanetsFromGalaxy(0).get(2);
        eventTimer = 0;
        
    }
    
    public void loop(){
                while(!Display.isCloseRequested()){
                    eventTimer++;
                    Display.sync(60);

                    events.get(events.size()-1).pauseInput();
                    events.get(events.size()-1).update();
                    physicsCore.doStep();
                    physicsCore.destroyBodies();

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
