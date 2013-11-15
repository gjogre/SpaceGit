
package GameObjects;

import Event.EventMachine;
import Graphics.Renderer;
import Graphics.SpaceTexture;
import Physics.Core;
import java.util.Random;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.opengl.Texture;

public class GameObject {

    private final int MAX_VERTICES = 50;
    
    protected Body body;
    protected Vec2[] shape;
    protected int shapeVecCount;
    //vec count for renderer, keep it same as shapeVecCount if object has no 2.5d shape
    protected int graphicsVecCount;

    public boolean isCircle = false;
    public boolean isSphere = false;
    public Vec3 defaultColorsRGB;
    public Vec3 currentColorsRGB;
    public float alpha;
    
    public float haloSize = 2f;
    public boolean hasHalo = false;
    
    // set automatically
    private boolean hasTexture;
    
    //texture anchors if used
    private int[] textAnchors;
    private boolean hasAnchors = false;
    
    private SpaceTexture texture;
    public boolean markForKill = false;
    

    
    public Body getBody() {
        return body;
    }

    
    
    
    public GameObject(){
        shape = new Vec2[MAX_VERTICES];
        setBasicShape();
        hasTexture = false;
        defaultColorsRGB = new Vec3(1,1,1);
        currentColorsRGB = new Vec3(defaultColorsRGB);
        alpha = 1f;
    }

    public void takeHit(float force){
        System.out.println(force + ":" + this.toString());
        currentColorsRGB =  new Vec3(1,0,0);
        takeDamage(force);
        
    }
    
    public void updateGfx(){

            fade();

        
    }
    
    
    //override for damage handling
    protected void takeDamage(float force){
    
    
    }
    
    //override for different fade algorithm
    protected void fade(){
        float speed = 0.1f;
        if(currentColorsRGB.x < defaultColorsRGB.x-speed){
            currentColorsRGB.x+=speed;
        } else if(currentColorsRGB.x > defaultColorsRGB.x+speed){
            currentColorsRGB.x-=speed;
        }
    
        if(currentColorsRGB.y < defaultColorsRGB.y-speed){
            currentColorsRGB.y+=speed;
        } else if(currentColorsRGB.y > defaultColorsRGB.y+speed){
            currentColorsRGB.y-=speed;
        }
        if(currentColorsRGB.z < defaultColorsRGB.z-speed){
            currentColorsRGB.z+=speed;
        } else if(currentColorsRGB.z > defaultColorsRGB.z+speed){
            currentColorsRGB.z-=speed;
        }
        
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
    
    public void setShape(Vec2[] shape){
        
        this.shape = shape;
        this.graphicsVecCount = shape.length;
        
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
    
    public Vec2 getWorldCenter(){
        return body.getWorldCenter();
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
    
    public void kill(){
        markForKill = true;
        EventMachine.renderer.removeObject(this);
        EventMachine.physicsCore.removeObject(this);
        afterKill();
        
    }
    
    protected void afterKill(){
        
    }
}
