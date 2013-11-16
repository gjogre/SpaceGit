package GameObjects;

import org.jbox2d.common.Vec2;


public class Lander extends GameObject {
    private float size;
    
    public Lander(float size){
        super();
        rooverShape(size);
        this.size = size;
    }
    
    private void rooverShape(float size){
        
        super.shapeVecCount = 4;
        super.graphicsVecCount = 6;
        
        super.shape[0] = new Vec2(-1*size, 0.5f*size);
        super.shape[1] = new Vec2(-1*size, -0.5f*size);
        super.shape[2] = new Vec2(1*size, -0.5f*size);
        super.shape[3] = new Vec2(0.5f*size, 0.5f*size);
        super.shape[4] = new Vec2(-1*size, 0.5f*size);
        super.shape[5] = new Vec2(1*size, -0.5f*size);
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
    
    public float getMass(){
        return size;
    }
    
}
