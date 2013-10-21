package GameObjects;

import java.util.Random;
import org.jbox2d.common.Vec2;

public class Ground extends GameObject{
            Random r = new Random();
        
            boolean isVolcanic = false;
            public Volcano volcano;
            int rdm;
            
    public Ground(float scale, float yStart, float yEnd){
        super();
        super.graphicsVecCount = 6;
        super.shapeVecCount = 4;
        rdm = r.nextInt(2)+1;
        
        if(rdm == 1){
            isVolcanic = true;
            volcano = new Volcano(scale, yStart, yEnd);
        }else{
            isVolcanic = false;
            volcano = null;
        }
        //super.setTexture("basicGround.png");
        super.shape[0] = new Vec2(0,yStart);
        super.shape[1] = new Vec2(0,-6*scale);
        super.shape[2] = new Vec2(1*scale,-6*scale);
        super.shape[3] = new Vec2(1*scale,yEnd);
        super.shape[4] = new Vec2(0,yStart);
        super.shape[5] = new Vec2(1*scale,-6*scale);
    }
    
    public Vec2 returnLast(){
        return shape[3];
    } 
    
    public boolean isVolcanic(){
        return isVolcanic;
    }
}
