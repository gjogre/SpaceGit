
package Tools;

import Event.EventMachine;
import Physics.Core;
import java.util.ArrayList;
import java.util.Random;
import org.jbox2d.common.Vec3;
import org.jbox2d.dynamics.Body;

public class Particle {

    public static ArrayList<Particle> particles = new ArrayList<>();
    
    public Body body;
    public float radius;
    private int ttl = 0;
    public Vec3 color; 
    
    public Particle(){
        Random r = new Random();
        ttl = r.nextInt(10)+10;
        radius = (r.nextFloat()/2f+0.2f);
        float colorBrightness = r.nextFloat();
        if(colorBrightness < 0.2f){
            colorBrightness = 0.5f;
        }
        color = new Vec3(colorBrightness,colorBrightness,0f);
    }
    public static void addParticle(Particle p){
        particles.add(p);
    }
    
    public boolean subTtl(){
        if(ttl > 0){
            ttl--;
            return false;
        } else {
            
            return true;
        }
        
    }

    
    public static void remove(Particle p){
        EventMachine.physicsCore.removeBody(p.body);
    }

    
}
