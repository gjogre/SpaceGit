

package Event;

import GameObjects.Planet;
import GameObjects.Ship;
import java.util.ArrayList;

public class sharedContainer {
    public static Space space= new Space();
    public static ArrayList<Planet> planets;
    
    public static Ship ship= new Ship();
    public static void generatePlanets(){
        
        planets = space.generatePlanetArray(0);
    }

}
