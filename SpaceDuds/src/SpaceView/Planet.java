package SpaceView;

import java.awt.Graphics2D;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
public class Planet {

    private enum Climate{
        SUN, HOT, WARM, TEMPERATE, CHILLY, COLD, FREEZING;
    }
 
    private float size;
    private Body body;
    
    public Planet( float size){
        this.size = size;
        
    }
    public Vec2 getPos(){
        return body.getPosition();
    }
    public double getSize(){
        return size;
    }
    
    public void draw(){
        
    }

    public void setBody(Body b){
        body = b;
    }
    
}
