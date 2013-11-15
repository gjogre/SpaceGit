package GameObjects;

import org.jbox2d.common.Vec2;

public class Volcano extends GameObject {
    public Volcano(float s){
        super();
        super.shapeVecCount = 4;
        super.graphicsVecCount = 6;
        
        super.shape[0] = new Vec2(0.25f*(s/4),0.5f*(s/4));
        super.shape[1] = new Vec2(0f,0f);
        super.shape[2] = new Vec2(1f*(s/4),0f*(s/4));
        super.shape[3] = new Vec2(0.75f*(s/4),0.5f*(s/4));
        super.shape[4] = new Vec2(0.25f*(s/4),0.5f*(s/4));
        super.shape[5] = new Vec2(1f*(s/4) ,0f);
        
    }
    
}


