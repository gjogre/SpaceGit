package GameObjects;

import Graphics.Renderer;
import Graphics.SpaceTexture;
import java.util.Random;
import org.jbox2d.common.Vec2;

public class Meteor extends GameObject {
    private Random r = new Random();
    private static SpaceTexture meteorTexture = new SpaceTexture("lavaLandOutline.png",10f,10f,0,0);
    private boolean hit;
    
    public Meteor(){
      super();  
      meteorShape(r.nextFloat()*2+0.5f,r.nextInt(4)+5);
      this.setTexture(meteorTexture);
      this.hasHalo = true;
      this.haloSize = 1.4f;
      hit = false;
      
    }
    
    private void meteorShape(float radius , int smoothness){
        shapeVecCount = smoothness;
        graphicsVecCount = smoothness;
        float ra = r.nextFloat();
        for(int i = 0; i < graphicsVecCount; i+= 1){
            float theta,x,y;
                theta = 2.0f * (float)Math.PI * (float)i /(float)graphicsVecCount;
                x = radius * (float)Math.cos(theta);
                y = radius * (float)Math.sin(theta);
                
            ra += (r.nextFloat())/3;
            x = x * ra;
            y = y * ra;
            shape[i] = new Vec2(x, y);
        }
        isCircle = true;
    } 
    
    public boolean getHitBool(){
        return hit;
    }
    
    @Override
    protected void takeDamage(float force){
        hit = true;
    }
}
