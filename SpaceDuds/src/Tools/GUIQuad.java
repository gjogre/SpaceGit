
package Tools;

import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;

public class GUIQuad {
    public Vec3 quad;
    public Vec3 color;
    public GUIQuad(float x, float y, float z){
             color = new Vec3(1f,1f,1f);
        quad = new Vec3(x,y,z);
    }
    public void setColor(float r, float g, float b){
        color = new Vec3(r,g,b);
    }
}
