import Event.EventMachine;
import Graphics.Renderer;
import PlanetView.PlanetEvent;
import SpaceView.SpaceEvent;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;


public class SpaceDuds{
   //private int WIDTH = (int) Tools.Utils.WIDTH;
  // private int HEIGHT = (int) Tools.Utils.WIDTH;
   

    public static void main(String args[]) throws LWJGLException{
            new SpaceDuds();
    }
    public SpaceDuds() throws LWJGLException{
        
        EventMachine eMachine = new EventMachine();
        eMachine.pushEvent(new SpaceEvent());
        eMachine.loop();
        Display.destroy();
        
    }
    
}
