package PlanetView;

import Tools.GameObject;
import java.util.Random;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;

public class Ground extends GameObject{
    
    public Ground(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4){
        super();
        super.graphicsVecCount = 4;
        super.shapeVecCount = 4;
        super.shape[0] = new Vec2(x1,y1);
        super.shape[1] = new Vec2(x2,y2);
        super.shape[2] = new Vec2(x3,y3);
        super.shape[3] = new Vec2(x4,y4);
    }
    
    public Vec2 returnLast(){
        return shape[3];
    }
    
}
