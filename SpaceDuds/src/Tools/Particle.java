/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools;

import java.util.Random;
import org.jbox2d.dynamics.Body;

public class Particle {

    public Body body;
    public float radius;
    private int ttl = 0;
    
    public Particle(){
        Random r = new Random();
        ttl = r.nextInt(50)+50;
    }
    
    public boolean subTtl(){
        if(ttl > 0){
            ttl--;
            return false;
        } else {
            return true;
        }
        
    }
    
}
