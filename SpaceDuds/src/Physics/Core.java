package Physics;

import GameObjects.GameObject;
import com.sun.org.apache.xalan.internal.xsltc.dom.SingletonIterator;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import org.jbox2d.collision.Distance;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class Core {
    
 
    private float timeStep = 1.0f / 60.f; //advancement of physics at every step call
    private int velIterations = 6; //velocity accuracy, default = 6
    private int posIterations = 3; //position accuracy, default = 3.

    private World world;
    private Random r = new Random();
    
    private DamageSystem damageSystem;
    
    private ArrayList<Body> toBeDestroyed;
    
    private enum Entities {
        SOLID(0x0001) ,
        DYNAMIC(0x0002) ,
        PARTICLE(0x0002),
        SCENERY(0x0004);
        private int code;
 
        private Entities(int c) {
          code = c;
        }

        public int getCode() {
          return code;
        }
    };
    
    private Vec2 gravity = new Vec2(0.0f,0.0f);
    public Core() {
        
       
       world = new World(gravity);
       damageSystem = new DamageSystem();
       world.setContactListener(damageSystem);
      toBeDestroyed = new ArrayList<>();
        
    }
    
    public void setGravity(float x, float y){
        world.setGravity(new Vec2(x,y));
    }
    
    public Body addObject(float x, float y, Vec2[] shapeLines,  int count, float density, float friction, float restitution){
       
       BodyDef b = new BodyDef();
       b.position.set(x,y);
       b.type = BodyType.DYNAMIC;
       
       PolygonShape shape = new PolygonShape();
       shape.set(shapeLines, count);
       
       FixtureDef f = new FixtureDef();
       f.shape = shape;
       //mass density
       f.density = density;
       //kitka
       f.friction = friction;
       //bouncyness
       f.restitution = restitution;
       
       f.filter.categoryBits = (Entities.DYNAMIC.getCode());
       f.filter.maskBits = ( Entities.DYNAMIC.getCode() | Entities.SOLID.getCode());
       Body body = world.createBody(b);
       body.createFixture(f);
       
        
        return body;
        
    }
    public Body addStaticObject(float x, float y, Vec2[] shapeLines,  int count){
       
       BodyDef b = new BodyDef();
       b.position.set(x,y);
       b.type = BodyType.STATIC;
       
       PolygonShape shape = new PolygonShape();
       shape.set(shapeLines, count);
       
       FixtureDef f = new FixtureDef();
       f.shape = shape;
       
       f.filter.categoryBits = (Entities.SOLID.getCode());
       f.filter.maskBits = (Entities.SOLID.getCode() | Entities.DYNAMIC.getCode());
       Body body = world.createBody(b);
       body.createFixture(f);
       
        
        return body;
        
    }
    public float getDistance(Body b1, Body b2){
        float distance=(float)Math.sqrt((b1.getPosition().x-b2.getPosition().x)*(b1.getPosition().x-b2.getPosition().x) + (b1.getPosition().y-b2.getPosition().y)*(b1.getPosition().y-b2.getPosition().y));
        System.out.println(distance);
        return distance;
    
    }
    
   public Body addPlanet(float x, float y, float size){

       BodyDef b = new BodyDef();
       b.position.set(x,y);
       b.type = BodyType.STATIC;
       
       CircleShape shape = new CircleShape();
       //shape.m_p.set(x,y);
       shape.m_radius = size;
       
       FixtureDef f = new FixtureDef();
       f.shape = shape;
       //mass density
       f.density = 0.2f;
       //kitka
       f.friction = 1.0f;
       //bouncyness
       f.restitution = 0.2f;
       
       f.filter.categoryBits = (Entities.SOLID.getCode());
       f.filter.maskBits = (Entities.PARTICLE.getCode());
       
       Body body = world.createBody(b);
       body.createFixture(f);
       
       return body; 
    }
   
   public Body addWheel(float x, float y, float size){
       BodyDef b = new BodyDef();
       b.position.set(x,y);
       b.type = BodyType.DYNAMIC;
       
       CircleShape shape = new CircleShape();
       shape.m_radius = size;
       
       FixtureDef f = new FixtureDef();
       f.shape = shape;
       //mass density
       f.density = 1.0f;
       //kitka
       f.friction = 0.4f;
       //bouncyness
       f.restitution = 0.3f;
       
       f.filter.categoryBits = (Entities.DYNAMIC.getCode());
       f.filter.maskBits = (Entities.DYNAMIC.getCode() | Entities.SOLID.getCode());
       
       
       Body body = world.createBody(b);
       body.createFixture(f);
       
       return body;
   }
    public Body addBlankObject(float x, float y){
       
       BodyDef b = new BodyDef();
       b.position.set(x,y);
       b.type = BodyType.STATIC;
       Body body = world.createBody(b);
        return body; 
   }
   public Body addSquareParticle(float x, float y, float size, float angle, float speed){
       
       BodyDef b = new BodyDef();
       b.position.set(x,y);
       b.type = BodyType.DYNAMIC;
       
       PolygonShape shape = new PolygonShape();
       shape.setAsBox(size, size);
       
       FixtureDef f = new FixtureDef();
       f.shape = shape;
       //mass density
       f.density = 0.2f;
       //kitka
       f.friction = 0.5f;
       //bouncyness
       f.restitution = 0.5f;
       
       f.filter.categoryBits = (Entities.PARTICLE.getCode());
       f.filter.maskBits = (Entities.SOLID.getCode() );
       
       Body body = world.createBody(b);
       body.createFixture(f);
      
        body.applyLinearImpulse(new Vec2(-(float)Math.cos(angle)*size*speed, -(float)Math.sin(angle)*size*speed), new Vec2(0,0));
        return body;
       
       
   }
   public Body addGround(float x, float y,Vec2[] shapeLines,  int count){
       BodyDef b = new BodyDef();
       b.position.set(x,y);
       b.type = BodyType.STATIC;
       
       PolygonShape shape = new PolygonShape();
       shape.set(shapeLines, count);
       
       FixtureDef f = new FixtureDef();
       f.shape = shape;
       //mass density
       f.density = 0.8f;
       //kitka
       f.friction = 1.0f;
       //bouncyness
       f.restitution = 0.2f;
       
       f.filter.categoryBits = (Entities.SOLID.getCode());
       f.filter.maskBits = (Entities.PARTICLE.getCode());
       
       Body body = world.createBody(b);
       body.createFixture(f);
      
        
        return body;
   
   }
    
    public void distanceJoint(Body a, Body b, Vec2 anchorA, Vec2 anchorB){
       DistanceJointDef jointDef = new DistanceJointDef();
       jointDef.initialize(a, b, anchorA, anchorB);
       jointDef.collideConnected = false;
       world.createJoint(jointDef);
    }
    
    public void revoluteJoint(Body a, Body b, Vec2 anchor){
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.initialize(a, b, anchor);
        world.createJoint(jointDef);
    }
    public void addDamageObject(GameObject g){
        damageSystem.addObject(g);
    }

    public void release(){
        setGravity(0f, 0f);
        damageSystem.clearObjects();
        world = new World(gravity);
        world.setContactListener(damageSystem);
    }
    public void removeBody(Body b){
        world.destroyBody(b);
        
    }
    
    public void removeObject(GameObject g){
        toBeDestroyed.add(g.getBody());
        
        //damageSystem.removeObject(g);
    }
    
    public void destroyBodies(){
        for(Body b : toBeDestroyed){
            world.destroyBody(b);
            
        }
        toBeDestroyed.clear();
    }
    
    public void doStep(){
         world.step(timeStep, velIterations, posIterations);
        
    }
    // FOR DEBUG ONLY
    public void printBodyCount(){
        System.out.println(world.getBodyCount());
    }
}
