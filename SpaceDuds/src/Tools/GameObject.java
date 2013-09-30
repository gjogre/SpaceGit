
package Tools;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class GameObject {

    private final int MAX_VERTICES = 100;
    
    protected Body body;
    protected Vec2[] shape;
    protected int shapeVecCount;
    
    public GameObject(){
        shape = new Vec2[MAX_VERTICES];
        setBasicShape();
        
    }
    
    private void setBasicShape(){
       shapeVecCount = 4;
       shape[0] = new Vec2(-1,1);
       shape[1] = new Vec2(-1,-1);
       shape[2] = new Vec2(1,-1);
       shape[3] = new Vec2(1,1);
        
        
    }
    public Vec2[] getShape(){
        return shape;
    }
    public int getshapeVecCount(){
        return shapeVecCount;
    }
    public Vec2 getLine(int index){
        return shape[index];
    }
    public float getAngle(){
        return body.getAngle();
    }
    public Vec2 getPos(){
        return body.getPosition();
    }
    
    public void applyForce(Vec2 force, Vec2 point){
        body.applyForce(force, point);
    }
    public void applyForce(Vec2 force){
        body.applyForce(force, new Vec2(0f,0f));
    }
    
    public void applyForceForward(float force){
        float x = (float) Math.cos(body.getAngle());
        float y = (float) Math.sin(body.getAngle());
        body.applyForceToCenter(new Vec2(x,y));
    }
    
    public void applyRotation(float rot){
        body.applyTorque(rot);
    }
    
    public void setBody(Body body){
        this.body = body;
    }
    
}
