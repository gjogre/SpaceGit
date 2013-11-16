package GameObjects;

import static Event.EventMachine.physicsCore;
import static Event.EventMachine.renderer;
import org.jbox2d.common.Vec2;


public class Lander extends GameObject {
    private float size;
    public static final int ROOVER = 1, SCOUT = 2, BATTLE_SHIP = 3;
    private Wheel backWheel, frontWheel;
    private int landerType;
    
    public Lander(int landerType, float size){
        super();
        this.size = size;
        if(landerType == ROOVER){
            this.landerType = ROOVER;
            rooverShape(size);
            backWheel = new Wheel();
            frontWheel = new Wheel();
        }else if(landerType == SCOUT){
            this.landerType = SCOUT;
            scoutShape(size);
        }else if(landerType == BATTLE_SHIP){

        }
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
    
    private void scoutShape(float size){
        super.shapeVecCount = 3;
        super.graphicsVecCount = 3;
        super.shape[0] = new Vec2(-1*size, 1f*size);
        super.shape[1] = new Vec2(-1*size, -1f*size);
        super.shape[2] = new Vec2(1*size, -0f);
    }
    
    public Vec2 getBackAxelSpot(){
        return new Vec2(shape[1].x, shape[1].y);
    }
    
    public Vec2 getFrontAxelSpot(){
        return new Vec2(shape[2].x, shape[2].y);
    }
    
    public float getMass(){
        return size;
    }
    
    public Wheel getBackWheel(){
        if(backWheel != null){
            return backWheel;
        }else{
            return null;
        }
    }
    
    public Wheel getFrontWheel(){
        if(backWheel != null){
            return frontWheel;
        }else{
            return null;
        }
    }
    
    public int getLanderType(){
        return landerType;
    }
    
    @Override
    protected void takeDamage(float force){
        System.out.println("asd");
    }
}
