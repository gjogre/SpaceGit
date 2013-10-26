
package Tools;

import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;

public class GUILine {

    
    public Vec2 line;
    public Vec3 color;
    public GUILine(float x, float y){
        color = new Vec3(1f,1f,1f);
        line = new Vec2(x,y);
    }
    public void setColor(float r, float g, float b){
        color = new Vec3(r,g,b);
    }
}
