package GameObjects;

import org.jbox2d.common.Vec2;


public class Roover extends GameObject {
    
    float backWheelSize, frontWheelSize;
    
    public Roover(){
        super();
        rooverShape();
    }
    
    private void rooverShape(){
        
        super.shapeVecCount = 4;
        super.graphicsVecCount = 6;
        
        super.shape[0] = new Vec2(-4, 2);
        super.shape[1] = new Vec2(-4, -2);
        super.shape[2] = new Vec2(4, -2);
        super.shape[3] = new Vec2(4, 2);
        super.shape[4] = new Vec2(-4, 2);
        super.shape[5] = new Vec2(4, -2);
    }
    
    public Vec2 getBackAxelSpot(){
        return new Vec2(shape[1].x=+0.5f, shape[1].y=+0.5f);
    }
    
    public Vec2 getFrontAxelSpot(){
        return new Vec2(shape[2].x=-0.5f, shape[2].y=+0.5f);
    }
    
}
