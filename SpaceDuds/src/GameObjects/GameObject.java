
package GameObjects;

import Graphics.SpaceTexture;
import java.util.Random;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.opengl.Texture;

public class GameObject {

    private final int MAX_VERTICES = 200;
    
    protected Body body;
    protected Vec2[] shape;
    protected int shapeVecCount;
    //vec count for renderer, keep it same as shapeVecCount if object has no 2.5d shape
    protected int graphicsVecCount;

    //no need to touch this, if you apply texture for gameobject, this will be set automatically
    private boolean hasTexture;
    private int[] textAnchors;
    private boolean hasAnchors = false;
    private SpaceTexture texture;
    
    
    public boolean isCircle = false;
    public boolean isSphere = false;
    public Vec3 colorsRGB;
    public float alpha;
    
    public boolean isLight = false;
    public boolean is2d = true;
    
    public float haloSize = 2f;
    public boolean hasHalo = false;
    
    public Body getBody() {
        return body;
    }

    
    
    
    public GameObject(){
        shape = new Vec2[MAX_VERTICES];
        setBasicShape();
        hasTexture = false;
        colorsRGB = new Vec3(1,1,1);
        alpha = 1f;
    }

    public void takeHit(float force){
        System.out.println(force);
    }
    
    
    public void setTexture(String filename, float dividerx, float dividery, float offsetX, float offsetY, int anchors[]){
        texture = new SpaceTexture(filename,dividerx,dividery, offsetX, offsetY);

        textAnchors = anchors;
        hasAnchors = true;
        hasTexture = true;
    }
    
    public void setTexture(SpaceTexture file){
        texture = file;
        hasTexture = true;
    }
    public void setTexture(SpaceTexture file, int[] anchors){
        texture = file;
        textAnchors = anchors;
        hasAnchors = true;
        hasTexture = true;
    }
    
    public boolean hasAnchors(){
        return hasAnchors;
    }
    public int getAnchor(int i){
        return textAnchors[i];
    }

    public boolean hasTexture(){
        return hasTexture;
    }
    public Texture getTexture(){
        return texture.getTexture();
    }
    public SpaceTexture getSTexture(){
        return texture;
    }

    
    
    private void setBasicShape(){
       shapeVecCount = 4;
       graphicsVecCount = 4;
       shape[0] = new Vec2(-1,1);
       shape[1] = new Vec2(-1,-1);
       shape[2] = new Vec2(1,-1);
       shape[3] = new Vec2(1,1);
        
        
    }
    //for planets for now
    protected float size;
    public float getSize(){
        return size;
    }
    private float rotation = 0;
    private Random r = new Random();
    private float rotateSpeed = r.nextFloat()/100;
    public float getRotation(){
        rotation += rotateSpeed;
        return rotation;
    }
    
    ////////////////
    public void setRoundShape(float radius){
        shapeVecCount = 0;//not needed for circle shape
        graphicsVecCount = 34;
        for(int i = 0; i < graphicsVecCount+1; i+= 1){
            float theta = 2.0f * (float)Math.PI * (float)i /(float)graphicsVecCount;
            float x = radius * (float)Math.cos(theta);
            float y = radius * (float)Math.sin(theta);
            shape[i] = new Vec2(x, y);
        }
        isSphere = true;
    }
    public void setCircle(float radius, int smoothness){
        shapeVecCount = 0;//not needed for circle shape
        graphicsVecCount = smoothness;
        float ra = r.nextFloat();
        for(int i = 0; i < graphicsVecCount; i+= 1){
            float theta,x,y;
                theta = 2.0f * (float)Math.PI * (float)i /(float)graphicsVecCount;
                x = radius * (float)Math.cos(theta);
                y = radius * (float)Math.sin(theta);
            ra += (r.nextFloat())/3;
           // x = x * ra;
            //y = y * ra;
            shape[i] = new Vec2(x, y);
        }
        isCircle = true;
    }

    
    public void setTransform(float x, float y, float angle){
        body.setTransform(new Vec2(x,y), angle);
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
        body.applyForce(force, body.getWorldCenter());
    }
       public void applyImulse(float force){
        float x = (float) Math.cos(body.getAngle());
        float y = (float) Math.sin(body.getAngle());
        body.applyLinearImpulse(new Vec2(x,y), body.getWorldCenter());
    }
    public void applyForceForward(float force){
        float x = (float) Math.cos(body.getAngle()) * force;
        float y = (float) Math.sin(body.getAngle()) * force;
        body.applyForceToCenter(new Vec2(x,y));
    }
    public void applyForceForward(Vec2 force){
        float x = (float) Math.cos(body.getAngle()) * force.x;
        float y = (float) Math.sin(body.getAngle()) * force.y;
        body.applyForceToCenter(new Vec2(x,y));
    }
    public void applyRotation(float rot){
        body.applyTorque(rot);
    }
    
    public void setBody(Body body){
        this.body = body;
    }
    
}
