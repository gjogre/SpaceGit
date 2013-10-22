package GameObjects;

import org.jbox2d.common.Vec2;

public class Volcano extends GameObject {
    public Volcano(float s){
        super();
        super.shapeVecCount = 4;
        super.graphicsVecCount = 6;
        
        super.shape[0] = new Vec2(0.25f*s,0.5f*s);
        super.shape[1] = new Vec2(0f,0f);
        super.shape[2] = new Vec2(1f*s,0f*s);
        super.shape[3] = new Vec2(0.75f*s,0.5f*s);
        super.shape[4] = new Vec2(0.25f*s,0.5f*s);
        super.shape[5] = new Vec2(1f*s ,0f*s);
        
    }
    
}


