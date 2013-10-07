package SpaceView;

import Graphics.Renderer;
import Physics.Core;
import Tools.GameObject;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;



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
        super.shapeVecCount = 5;
        super.graphicsVecCount = 5;
        super.shape[0] = new Vec2(-4f,1);
        super.shape[1] = new Vec2(-4f,-1);
        super.shape[2] = new Vec2(0.25f,-1f);
        super.shape[3] = new Vec2(2f,0f);
        super.shape[4] = new Vec2(0.25f,1f);
        particleOutputPos = new Vec2(-5f, 0f);
       
        
    }


}
