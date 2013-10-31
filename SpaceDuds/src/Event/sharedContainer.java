

package Event;

import GameObjects.Planet;
import GameObjects.Ship;
import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class sharedContainer {
    public static Space space= new Space();
    public static ArrayList<Planet> planets;
    
    public static Ship ship= new Ship();
    public static Planet currentPlanet;
    private static Audio wavEffect;
    public static void generatePlanets(){
        
        planets = space.getPlanetsFromGalaxy(0);
    }
    public static void initSound(){
        try{
            wavEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sounds/warp.wav"));
        
            System.out.println("audio loaded");
        } catch (IOException e) {
	    e.printStackTrace();
	} 
        
    }
    public static void playSound(int sound){
        
        switch(sound){
            
            default:
                wavEffect.playAsSoundEffect(1.0f, 1.0f, false);
                break;
        }
        
    }
}
