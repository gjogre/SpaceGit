package GameObjects;

import org.jbox2d.common.Vec2;

public class Axle extends GameObject {
    float axleSize;
    
    public Axle(){
        axleSize = 0.2f;
    }
    
    public float getAxleSize(){
        return axleSize;
    }
}
