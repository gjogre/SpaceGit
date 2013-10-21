

package GameObjects;

import GameObjects.GameObject;
import org.jbox2d.common.Vec2;

public class Wing extends GameObject{

    public Wing(){
        super();
        super.shapeVecCount = 8;    
        super.graphicsVecCount = 8;
        Vec2[] addonShape = new Vec2[8];

            
            addonShape[0] = new Vec2(-0.5f,0f);
            addonShape[1] = new Vec2(-0.5f,2f);
            addonShape[2] = new Vec2(-1.5f,2f);
            addonShape[3] = new Vec2(-1.5f,3f);
            addonShape[4] = new Vec2(1.5f,3f);
            addonShape[5] = new Vec2(1.5f,2f);
            addonShape[6] = new Vec2(0.5f,2f);
            addonShape[7] = new Vec2(0.5f,0f);
            super.shape = addonShape;
    }
    
}
