
package SpaceView;
import GameObjects.Planet;
import Event.Event;
import Event.EventMachine;
import static Event.EventMachine.*;
import Tools.Particle;
import GameObjects.GameObject;
import java.util.ArrayList;
import java.util.Random;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import static Event.EventMachine.*;
import Event.sharedContainer;
import PlanetView.PlanetEvent;
import static Event.sharedContainer.*;
import GameObjects.Moon;
import GameObjects.Roover;
import GameObjects.Wheel;
import Graphics.SpaceTexture;
import ObjectBuilders.RooverBuilder;
import Tools.GUIObject;
import org.jbox2d.collision.Distance;
import org.jbox2d.common.Vec3;
public class SpaceEvent extends Event{
   
    
    private Body debugBody = null;
    private Map map;
    private GUIObject movables;
    private Random r = new Random();
    
    public SpaceEvent(){
        
        
        
    }

    @Override
        protected void init(){
            
            int[] anchors = {
              0,1,2,
              0,0,0
              ,0,2,3
            };
            
             
            ship.setTexture("basicShip.png", 6f, 2f,0f,0f, anchors);
            renderer.addObject(ship);
            physicsCore.addDamageObject(ship);
            
            generatePlanets();
            ship.setBody(physicsCore.addObject(currentPlanet.getPos().x, currentPlanet.getPos().y+currentPlanet.getSize()*2, ship.getShape(), ship.getshapeVecCount(), 0.5f,  0.5f, 0.5f));
            makeMapFrame();
            renderer.addGuiObject(map);
            renderer.addGuiObject(movables);
            
                    
        }
    private void generatePlanets(){

            SpaceTexture solid = new SpaceTexture("lavaLandOutline.png",1f,1f,0f,0f);
            SpaceTexture gas = new SpaceTexture("lavaLandOutline.png",1f,1f,0f,0f);
            SpaceTexture sun = new SpaceTexture("sun.png",1f,1f,0f,0f);
            SpaceTexture moon = new SpaceTexture("moon2asd.png",1f,1f,0f,0f);

             Vec2 point;
            for(Planet p : planets){
                
                
                p.setRoundShape(p.getSize());
               // p.setBody(physicsCore.addPlanet(50f-p.getDistanceToSun(), 0f, p.getSize()));
               point = p.generatePointInGalaxy(new Vec2(0,0));
                p.setBody(physicsCore.addPlanet(point.x, point.y, p.getSize()));
                
                
                
                //p.colorsRGB = new Vec3(1,1,r.nextFloat());
                if(p.getClimate() != Planet.Climate.SUN){
                    if(p.getType() == Planet.Type.SOLID){
                        p.setTexture(solid);
                    } else if(p.getType() == Planet.Type.GAS){
                        p.setTexture(gas);
                        p.alpha = 0.4f;
                    } else if(p.getType() == Planet.Type.MOON){
                        p.setTexture(moon);
                        p.defaultColorsRGB = new Vec3(1f,1f,1f);
                    } 
                }
                else{
                    p.setTexture(sun);
                    p.defaultColorsRGB = new Vec3(1f,1f,0f);
                }
                System.out.println(p.getClimate().toString());
                p.hasHalo = true;
                
                renderer.addObject(p);
                Vec2 moonPoint;
                
                System.out.println("PLANET: " + p.getPos().x + ">" + p.getPos().y );
                for(Moon m : p.moons){
                    m.setRoundShape(m.getSize());
                    moonPoint = m.generatePointInGalaxy(point);
                    m.setBody(physicsCore.addPlanet(moonPoint.x, moonPoint.y, m.getSize()));
                    m.defaultColorsRGB = new Vec3(1f,1f,1f);
                    m.setTexture(moon);
                    renderer.addObject(m);
                    System.out.println("MOON: " + m.getPos().x + ">" + m.getPos().y + "SIZE: " +m.getSize()  );
                   
                }
            }
        gravity = false;
    }

    private boolean landed = false;
    private boolean gravity = false;

    @Override
    public void update(){
        if(!gravity){
            physicsCore.setGravity(0f, 0f);
            gravity = true;
        }
        
        input();
        updateMap();
        renderer.setCameraTargetPos(ship.getPos().x, ship.getPos().y);
        ship.shipUpdate();
        
        if(landed){
            landed = false;
            sharedContainer.playSound(0);
            pushEvent(new PlanetEvent());
        }
    }

    
    private boolean tapped = false;
    private void input(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){

            createParticle(ship.getPos().x +ship.getParticleOutputPos().x,ship.getPos().y +ship.getParticleOutputPos().y, 0.5f,ship.getAngle()+r.nextFloat()-0.5f );
            ship.applyForceForward(3f);

        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            Vec2 brakeForce = ship.getBody().getLinearVelocity();
            brakeForce.x *= -0.1;
            brakeForce.y *= -0.1;
            ship.applyForceForward(brakeForce);
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            ship.applyRotation(-2f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            ship.applyRotation(2f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            
            if(ship.boost()){
                
                createParticle(ship.getPos().x +ship.getParticleOutputPos().x,ship.getPos().y +ship.getParticleOutputPos().y, 0.5f,r.nextFloat()*2*(float)Math.PI,20,r.nextFloat(),r.nextFloat(),r.nextFloat(),2f);
            }
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_E)){
            
            landing();
            
        }
    }
    
    
private void landing(){
    
    for(Planet p : planets) {
        if(physicsCore.getDistance(p.getBody(), ship.getBody()) < p.getSize()*2){
            gravity = false;
            sharedContainer.currentPlanet = p;
            sharedContainer.ship.posInGalaxy = ship.getPos();
            
            
            
            if(ship.setLander()){
                
                LanderParticle lp = new LanderParticle(createParticle(ship.getPos().x +ship.getLanderOutputPos().x,ship.getPos().y +ship.getLanderOutputPos().y, 
                        0.3f,ship.getAngle()+0.5f*(float)Math.PI, 200,0.2f,0.2f,0.2f,1.5f));
                
                physicsCore.addDamageObject(lp);
                
            }
//pushEvent(new PlanetEvent());
            break;
        } 
    }
}

private class LanderParticle extends GameObject {
    
    public Particle p;
    
    public LanderParticle(Particle p){
        this.p = p;
        body = p.body;
    }
    
    @Override
    protected void takeDamage(float force){
        landed = true;

    }
    
    
}

    private int boostMeter;
private void makeMapFrame(){
        map = new Map(-19.0f,-19.0f);
       
        map.addLine(5.0f, 5f);
         map.setLineColor(0f,0f,1f);
        map.addLine(-5.0f, 5f);
         map.setLineColor(0f,0f,1f);
        map.addLine(-5.0f, 5f);
         map.setLineColor(0f,0f,1f);
        map.addLine(-5.0f, -5f);
         map.setLineColor(0f,0f,1f);
        map.addLine(-5.0f, -5f);
         map.setLineColor(0f,0f,1f);
        map.addLine(5.0f, -5f);
         map.setLineColor(0f,0f,1f);
        map.addLine(5.0f, -5f);
         map.setLineColor(0f,0f,1f);
        map.addLine(5.0f, 5f);
        map.setLineColor(0f,0f,1f);

        for(Planet p : planets){
            for(float i = 0f; i < 2*Math.PI; i+=0.2f) {
                map.addLine(p.getDistanceToCenter() * (float)Math.cos(i) / 50f, p.getDistanceToCenter() * (float)Math.sin(i) / 50f);
                map.setLineColor(0.2f, 0.2f, 0.2f);
            }

            map.addQuad(p.getPos().x/50f, p.getPos().y/50f, p.getSize()/20f);
            map.setQuadColor(0.6f, 0.1f, 0.6f);
            for(Moon m : p.moons){
                map.addQuad(m.getPos().x/50f, m.getPos().y/50f, m.getSize()/20f);
                map.setQuadColor(0.7f, 0.7f, 0.7f);
            }
             
        }
        movables = new GUIObject(-19.0f, -19.0f);
        movables.addQuad(0f, 0f, 0.5f);
        movables.setQuadColor(1f, 1f, 0f);
        
        boostMeter =movables.addQuad(0f, 0f, 0.5f);
        
        map.player = movables.addQuad(ship.getPos().x/50f, ship.getPos().y/50f, 0.2f);
        movables.setQuadColor(0f, 1f, 0f);
        movables.addLine(6f, -5f);
        boostMeter = movables.addLine(6f, ship.getBoost()/(25f));
        movables.setLineColor(1f, 1f, 0f);
}
private boolean blink;
private void updateMap(){
    
    if(EventMachine.eventTimer % 60 == 0) {
        if(blink) {
            movables.setQuadColor(1f, 1f, 0f);
        } else {
            movables.setQuadColor(0f, 1f, 0f);
        }
        blink = !blink;
    }
    movables.setQuad(ship.getPos().x/50f, ship.getPos().y/50f, 0.2f,map.player-1);
    if(ship.getBoost() == ship.getBoostLoad()){
        movables.setLineColor(0f, 1f, 0f);
    }else{
        movables.setLineColor(1f, 1f, 1f);
    }
    movables.setLine(6f, ship.getBoost()/(10f)-5f, 0.2f, boostMeter-1);
    
}

}
        
        //testing addon, 2 wings
        /*Wing wing = new Wing();
        wing.setBody(physicsCore.addObject(ship.getPos().x-2, ship.getPos().y+1, wing.getShape(), wing.getshapeVecCount(), 0.1f,  0.5f, 0.5f));
        physicsCore.distanceJoint(ship.getBody(), wing.getBody(), 
                ship.getBody().getWorldCenter(), wing.getBody().getWorldCenter());
        renderer.addObject(wing);
        
        Wing wing2 = new Wing();
        wing2.setBody(physicsCore.addObject(ship.getPos().x-2, ship.getPos().y-1, wing2.getShape(), wing2.getshapeVecCount(), 0.1f,  0.5f, 0.5f));
        wing2.getBody().setTransform(wing2.getBody().getPosition(), 3.14159265f);
        physicsCore.distanceJoint(ship.getBody(), wing2.getBody(), 
                ship.getBody().getWorldCenter(), wing2.getBody().getWorldCenter());
        renderer.addObject(wing2);*/
        // /testing addon