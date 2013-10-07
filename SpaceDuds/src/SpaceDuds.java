import Graphics.Renderer;
import PlanetView.PlanetEvent;
import SpaceView.SpaceEvent;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;


public class SpaceDuds{
   //private int WIDTH = (int) Tools.Utils.WIDTH;
  // private int HEIGHT = (int) Tools.Utils.WIDTH;
   
   private boolean VSync = false;
   
    public static void main(String args[]) throws LWJGLException{
        
        
        new SpaceDuds();
        
        
    }
    public SpaceDuds() throws LWJGLException{
        Renderer renderer = new Renderer();
        
        new PlanetEvent(renderer);

        Display.destroy();
        
    }
    
}
