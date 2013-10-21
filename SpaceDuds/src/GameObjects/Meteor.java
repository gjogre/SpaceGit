package GameObjects;

import Graphics.Renderer;
import org.jbox2d.common.Vec2;

public class Meteor extends GameObject {
    
    public Meteor(){
      super();  
      meteorShape();
    }
    
    private void meteorShape(){
        super.shapeVecCount = 4;
        super.graphicsVecCount = 6;
        super.shape[0] = new Vec2(-1f,1f);
        super.shape[1] = new Vec2(-1f,-1f);
        super.shape[2] = new Vec2(1f,-1f);
        super.shape[3] = new Vec2(1f,1f);
        super.shape[4] = new Vec2(-1f,1f);
        super.shape[5] = new Vec2(1f,-1f);
    } 
}
