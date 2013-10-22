
package Tools;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;

public class GUIObject {

    protected Vec2 screenCoord;
    protected Vec3 bgColor;
    protected Vec3 lineColor;

    public Vec2 getScreenCoord() {
        return screenCoord;
    }


    protected Vec3 quadColor;
    protected ArrayList<Vec2> lines;
    protected ArrayList<Vec3> quads;
    public GUIObject(float coordX, float coordY){
        screenCoord = new Vec2(coordX,coordY);
        bgColor = new Vec3(1,1,1);
        lineColor = new Vec3(1,1,1);
        quadColor = new Vec3(1,1,1);
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
    public ArrayList<Vec3> getQuads(){
        return quads;
    }
    public Vec3 getLineColor() {
        return lineColor;
    }

    public void setLineColor(float r, float g, float b) {
        this.lineColor = new Vec3(r,g,b);
    }

    public Vec3 getQuadColor() {
        return quadColor;
    }

    public void setQuadColor(float r, float g, float b) {
        this.quadColor = new Vec3(r,g,b);
    }
    public int addLine(float x, float y){
        lines.add(new Vec2(x,y));
        return lines.size();
    }
    
    public void setQuad(float x, float y, float size, int index){
        quads.get(index).set(new Vec3(x,y,size));
    }
    
    public int addQuad(float x, float y, float size){
        quads.add(new Vec3(x,y,size));
        return quads.size();
    }
}
