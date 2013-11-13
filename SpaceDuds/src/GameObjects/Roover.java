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
        
        super.shape[0] = new Vec2(-3, 1.5f);
        super.shape[1] = new Vec2(-3, -1.5f);
        super.shape[2] = new Vec2(3, -1.5f);
        super.shape[3] = new Vec2(1.5f, 1.5f);
        super.shape[4] = new Vec2(-3, 1.5f);
        super.shape[5] = new Vec2(3, -1.5f);
    }
    
    public Vec2 getBackAxelSpot(){
        return new Vec2(shape[1].x, shape[1].y);
    }
    
    public Vec2 getFrontAxelSpot(){
        return new Vec2(shape[2].x, shape[2].y);
    }
    
    @Override
    protected void takeDamage(float force){
        System.out.println("asd");
    }
    
}
