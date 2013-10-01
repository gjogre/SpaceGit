
package Tools;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;

public class GUIObject {

    protected Vec2 screenCoord;
    protected Vec3 bgColor;
    protected Vec3 lineColor;


    protected Vec3 quadColor;
    protected ArrayList<Vec2> lines;
    protected ArrayList<Vec2> quads;
    public GUIObject(float coordX, float coordY){
        screenCoord = new Vec2(coordX,coordY);
        bgColor = new Vec3(0,0,0);
        lineColor = new Vec3(0,0,0);
        quadColor = new Vec3(0,0,0);
        lines = new ArrayList<>();
        quads = new ArrayList<>();
    }
    public void setColor(float r, float g, float b){
        bgColor = new Vec3(r,g,b);
    }
    public Vec3 getColor(){
        return bgColor;
    }
    public ArrayList<Vec2> getLines(){
        return lines;
    }
    public ArrayList<Vec2> getQuads(){
        return quads;
    }
    public Vec3 getLineColor() {
        return lineColor;
    }

    public void setLineColor(Vec3 lineColor) {
        this.lineColor = lineColor;
    }

    public Vec3 getQuadColor() {
        return quadColor;
    }

    public void setQuadColor(Vec3 quadColor) {
        this.quadColor = quadColor;
    }
}
