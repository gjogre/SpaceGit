package GameObjects;

import Graphics.Renderer;
import java.util.Random;
import org.jbox2d.common.Vec2;

public class Meteor extends GameObject {
    private Random r = new Random();
    
    
    public Meteor(){
      super();  
      super.setCircle(1f, r.nextInt(5)+5);
      //meteorShape();
      
    }
    
    private void meteorShape(){
        super.shapeVecCount = 8;
        super.graphicsVecCount = 9;
        super.shape[0] = new Vec2(-1f,1f);
        super.shape[1] = new Vec2(-1f,-1f);
        super.shape[2] = new Vec2(0f, 0f);
        super.shape[3] = new Vec2(0f,0f);
        super.shape[4] = new Vec2(-0.5f,-1f);
        super.shape[5] = new Vec2(0.4f,-0.9f);
        super.shape[6] = new Vec2(0f,1f);
        super.shape[7] = new Vec2(0.5f,-0.4f);
        super.shape[8] = new Vec2(0.5f,0.5f);
    } 
}
