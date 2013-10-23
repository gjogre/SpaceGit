package GameObjects;

import GameObjects.GameObject;
import org.jbox2d.common.Vec2;



public class Ship extends GameObject {
    private final int MAX_ADDONS = 10;
    
    //place in local coordinates where the particles comes from
    private Vec2 particleOutputPos; 
    public Vec2 getParticleOutputPos() {
        Vec2 temp = new Vec2(particleOutputPos.x * (float)Math.cos(body.getAngle()), particleOutputPos.x  * (float)Math.sin(body.getAngle()));
        return temp;
    }
   
    
    public Ship(){
        super();
        makeNormalShip();
    }

    private void makeNormalShip(){
        super.shapeVecCount =6;
        super.graphicsVecCount = 9;
        super.shape[0] = new Vec2(-2f,0.5f);
        super.shape[1] = new Vec2(-2f,-0.5f);
        super.shape[2] = new Vec2(0.1f,-0.5f);
        
        super.shape[3] = new Vec2(0.1f,0.5f);
        super.shape[4] = new Vec2(0.1f,-0.5f);
        super.shape[5] = new Vec2(1f,0f);
        
        super.shape[6] = new Vec2(-2f,0.5f);
        super.shape[7] = new Vec2(0.1f,-0.5f);
        super.shape[8] = new Vec2(0.1f,0.5f);

      //  super.shape[4] = new Vec2(0.25f,1f);
        particleOutputPos = new Vec2(-2.5f, 0f);
       
        
    }


}
