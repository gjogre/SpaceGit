
package Tools;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class GameObject {

    private final int MAX_VERTICES = 200;
    
    protected Body body;
    protected Vec2[] shape;
    protected int shapeVecCount;
    //vec count for renderer, keep it same as shapeVecCount if object has no 2.5d shape
    protected int graphicsVecCount;

    public Body getBody() {
        return body;
    }
    //no need to touch this, if you apply texture for gameobject, this will be set automatically
    private boolean textured = false;
    
    public GameObject(){
        shape = new Vec2[MAX_VERTICES];
        setBasicShape();
        
    }
    
    private void setBasicShape(){
       shapeVecCount = 4;
       graphicsVecCount = 4;
       shape[0] = new Vec2(-1,1);
       shape[1] = new Vec2(-1,-1);
       shape[2] = new Vec2(1,-1);
       shape[3] = new Vec2(1,1);
        
        
    }
    
    public void setRoundShape(float radius){
        shapeVecCount = 0;//not needed for circle shape
        graphicsVecCount = 34;
        for(int i = 0; i < graphicsVecCount+1; i+= 1){
            float theta = 2.0f * (float)Math.PI * (float)i /(float)graphicsVecCount;
            float x = radius * (float)Math.cos(theta);
            float y = radius * (float)Math.sin(theta);
            shape[i] = new Vec2(x, y);
        }
    }
    public Vec2[] getShape(){
        return shape;
    }
    public int getshapeVecCount(){
        return shapeVecCount;
    }
    public int getGraphicsVecCount(){
        return graphicsVecCount;
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
