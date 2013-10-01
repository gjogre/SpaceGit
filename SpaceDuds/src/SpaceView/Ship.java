package SpaceView;

import Tools.GameObject;
import org.jbox2d.common.Vec2;



public class Ship extends GameObject {

    public Ship(){
        super();
        makeNormalShip();
    }

    private void makeNormalShip(){
        super.shapeVecCount = 5;
        super.graphicsVecCount = 5;
        super.shape[0] = new Vec2(-1,1);
        super.shape[1] = new Vec2(-1,-1);
        super.shape[2] = new Vec2(0.25f,-1);
        super.shape[3] = new Vec2(1,0);
        super.shape[4] = new Vec2(0.25f,1);
        
        
        
    }

}
