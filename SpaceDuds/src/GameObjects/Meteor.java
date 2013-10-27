package GameObjects;

import Graphics.Renderer;
import java.util.Random;
import org.jbox2d.common.Vec2;

public class Meteor extends GameObject {
    private Random r = new Random();
    
    
    public Meteor(){
      super();  
      meteorShape(2f,4);
      
    }
    
    private void meteorShape(float radius , int smoothness){
        shapeVecCount = 0;//not needed for circle shape
        graphicsVecCount = smoothness;
        float ra = r.nextFloat();
        for(int i = 0; i < graphicsVecCount; i+= 1){
            float theta,x,y;
                theta = 2.0f * (float)Math.PI * (float)i /(float)graphicsVecCount;
                x = radius * (float)Math.cos(theta);
                y = radius * (float)Math.sin(theta);
                
            ra += (r.nextFloat())/3;
           // x = x * ra;
            //y = y * ra;
            shape[i] = new Vec2(x, y);
        }
        isCircle = true;
    } 
}
