/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SpaceView;

import java.util.Random;

public class Space {

    private Random r = new Random();
    private int galaxies = 50;
    private int maxPlanets = 10;
    
    private Planet[][] planets;
    public Space(){
        planets = new Planet[galaxies][];
        for(int g = 0; g < galaxies; g++){
            int planetsInGalaxy = r.nextInt(maxPlanets);
            float distanceToSun = 0f;
            
            for(int p = 0 ; p < planetsInGalaxy; p++){
                if(distanceToSun == 0f){
                    planets[g][p] = new Planet(r.nextFloat()*5,g, Planet.Climate.SUN,p,0f);
                    distanceToSun += r.nextFloat() * 5+ 3f;
                } else {
                    
                    planets[g][p] = new Planet(r.nextFloat()*5,g, Planet.Climate.TEMPERATE,p,distanceToSun);
                    distanceToSun += r.nextFloat() * 5+ 3f;
                }
            }
        }
    }
    
}
