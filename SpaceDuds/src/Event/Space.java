
package Event;

import GameObjects.Planet;
import GameObjects.Planet.Climate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Space {

   
    
    private Random r = new Random();
    private int galaxies = 50;
    private int maxPlanets = 10;
    private int minPlanets = 5;
    private Planet[][] planets;
    public Space(){
        planets = new Planet[galaxies][];
        for(int g = 0; g < galaxies; g++){
            int planetsInGalaxy = r.nextInt(maxPlanets-minPlanets)+minPlanets;
            float distanceToSun = 0f;
            planets[g] = new Planet[planetsInGalaxy];
            for(int p = 0 ; p < planetsInGalaxy; p++){
                if(distanceToSun == 0f){
                    planets[g][p] = new Planet(r.nextFloat()*5,g, Planet.Climate.SUN,p,0f);
                    distanceToSun += r.nextFloat() * 50f+ 10f;
                } else {
                    System.out.println("d: " + distanceToSun);
                    
                    Climate temp;
                    
                    float hotd = 20 * planets[g][0].getSize();
                     float warmd = 50 * planets[g][0].getSize();
                     float tempd = 100 * planets[g][0].getSize();
                     float coldd = 200 *  planets[g][0].getSize();
                    if(distanceToSun < hotd){
                        temp = Climate.HOT;
                    } else if(distanceToSun < hotd){
                        temp = Climate.TEMPERATE;
                    } else {
                        temp = Climate.COLD;
                    }
                    
                    planets[g][p] = new Planet(r.nextFloat()*5+5,g, temp,p,distanceToSun);
                    distanceToSun += r.nextFloat() * 50f+ 10f;
                }
            }
        }
    }
    public ArrayList<Planet> generatePlanetArray(int galaxy){
        ArrayList<Planet> planetArr = new ArrayList<>();
        planetArr.addAll(Arrays.asList(planets[galaxy]));
        return planetArr;
    }
}
