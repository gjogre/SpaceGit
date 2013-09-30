package Physics;

import java.awt.Point;
import java.util.Random;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;

public class Core {
    

    private float timeStep = 1.0f / 60.f; //advancement of physics at every step call
    private int velIterations = 6; //velocity accuracy, default = 6
    private int posIterations = 3; //position accuracy, default = 3.

    private World world;
    private Random r = new Random();
    
    private enum Entities {
        SOLID(0x0001) ,
        DYNAMIC(0x0002) ,
        PARTICLE(0x0002);
        private int code;
 
        private Entities(int c) {
          code = c;
        }

        public int getCode() {
          return code;
        }
    };
    
    
    public Core() {
        
       Vec2 gravity = new Vec2(0.0f,0f);
       world = new World(gravity);
       
      
        
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
       f.filter.maskBits = (Entities.SOLID.getCode() | Entities.DYNAMIC.getCode());
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
       
       f.filter.categoryBits = (Entities.DYNAMIC.getCode());
       f.filter.maskBits = (Entities.SOLID.getCode() | Entities.DYNAMIC.getCode());
       Body body = world.createBody(b);
       body.createFixture(f);
       
        
        return body;
        
    }
   public Body addPlanet(float x, float y, float size){

       BodyDef b = new BodyDef();
       b.position.set(x,y);
       b.type = BodyType.STATIC;
       
       CircleShape shape = new CircleShape();
       shape.m_p.set(x,y);
       shape.m_radius = size;
       
       FixtureDef f = new FixtureDef();
       f.shape = shape;
       //mass density
       f.density = 1.0f;
       //kitka
       f.friction = 1.0f;
       //bouncyness
       f.restitution = 0.2f;
       
       f.filter.categoryBits = (Entities.SOLID.getCode());
       f.filter.maskBits = (Entities.SOLID.getCode() | Entities.DYNAMIC.getCode() | Entities.PARTICLE.getCode());
       
       Body body = world.createBody(b);
       body.createFixture(f);
      
        
        return body;
        
    }
    
    public Body addFireParticle(float x, float y, float angle, float radius){

       float speed = r.nextFloat()*0.3f+0.2f; 
       //angle vector
        Vec2 vec = new Vec2();
        vec.x = (float)Math.cos(angle);
        vec.y = (float)Math.sin(angle);
       
       BodyDef b = new BodyDef();
       b.position.set(vec.x*-2 +r.nextFloat()-0.5f, vec.y*-2+r.nextFloat()-0.5f);
       b.type = BodyType.DYNAMIC;
       
       CircleShape shape = new CircleShape();
       shape.m_p.set(x,y);
       shape.m_radius = radius;
       
       FixtureDef f = new FixtureDef();
       f.shape = shape;
       //mass density
       f.density = 0.1f;
       //kitka
       f.friction = 1.0f;
       //bouncyness
       f.restitution = 1.0f;
       
       f.filter.categoryBits = (Entities.PARTICLE.getCode());
       f.filter.maskBits = (Entities.SOLID.getCode());
       
       Body body = world.createBody(b);
       body.createFixture(f);

       vec.x *=-speed;
       vec.y *= -speed;

        body.applyForce(vec, body.getWorldCenter());
       return body;
        
        
    }
    
    public void removeBody(Body b){
        world.destroyBody(b);
    }
    
    public void doStep(){
         world.step(timeStep, velIterations, posIterations);
        
    }
    
    
}
