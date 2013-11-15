package GameObjects;

import java.util.Random;
import org.jbox2d.common.Vec2;

public class Ground extends GameObject{
            Random r = new Random();
        
            boolean isVolcanic = false;
            public Volcano volcano;
            int rdm;
            float start, end;
            
            
    public Ground(float scale, float yStart, float yEnd){
        super();
        super.graphicsVecCount = 6;
        super.shapeVecCount = 4;
        rdm = r.nextInt(10)+1;
        
        start = yStart;
        end = yEnd;
        
        super.shape[0] = new Vec2(0,yStart);
        super.shape[1] = new Vec2(0,-24);
        super.shape[2] = new Vec2(1*scale,-24);
        super.shape[3] = new Vec2(1*scale,yEnd);
        super.shape[4] = new Vec2(0,yStart);
        super.shape[5] = new Vec2(1*scale,-24);
        
        if(rdm == 1){
            isVolcanic = true;
            volcano = new Volcano(scale);
        }else{
            isVolcanic = false;
            volcano = null;
        }
        //super.setTexture("basicGround.png");
    }
    
    public float returnStart(){
        return start;
    }
    
    public float returnEnd(){
        return end;
    }
    
    public Vec2 returnTopRight(){
        return shape[3];
    }
    
    public Vec2 returnTopLeft(){
        return shape[0];
    }
    
    public Vec2 returnBotLeft(){
        return shape[1];
    }
    
    public Vec2 returnBotRight(){
        return shape[2];
    }
    
    public boolean isVolcanic(){
        return isVolcanic;
    }
}
