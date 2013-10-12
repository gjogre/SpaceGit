

package Event;

import SpaceView.Planet;
import SpaceView.Ship;
import SpaceView.Space;
import java.util.ArrayList;

public class sharedContainer {
    public static Space space= new Space();;
    public static Ship ship= new Ship();;
    public static ArrayList<Planet> planets;
    
    
    public static void generatePlanets(){
        
        planets = space.generatePlanetArray(0);
    }

}
