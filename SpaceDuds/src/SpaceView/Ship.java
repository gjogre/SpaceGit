package SpaceView;

import Graphics.Renderer;
import Physics.Core;
import Tools.GameObject;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;



public class Ship extends GameObject {
    private final int MAX_ADDONS = 10;
    
   
    
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
        super.shape[3] = new Vec2(1f,0f);
        super.shape[4] = new Vec2(0.25f,1f);
        
       
        
    }


}
