package GameObjects;

import static Event.EventMachine.*;
import org.jbox2d.common.Vec2;

public class Moon extends GameObject{

    public float distance;

    public Moon(float distance, float size){
        super();
        this.distance = distance;
        this.size = size;
        
    }
     public Vec2 generatePointInGalaxy(Vec2 centerPoint){
     float angle = r.nextFloat()*2*(float)Math.PI;
     Vec2 p = new Vec2(centerPoint.x + (distance * (float)Math.cos(angle)),centerPoint.y + (distance * (float)Math.sin(angle)));
     return p;
     
 }
    
}
