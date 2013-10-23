
package SpaceView;
import GameObjects.Planet;
import Event.Event;
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
import Graphics.SpaceTexture;
import org.jbox2d.collision.Distance;
import org.jbox2d.common.Vec3;
public class SpaceEvent extends Event{
   
    
    private Body debugBody = null;
    private Map map;
    private Random r = new Random();
    
    public SpaceEvent(){
        
        
        
    }

    @Override
        protected void init(){


            ship.setBody(physicsCore.addObject(ship.posInGalaxy.x, ship.posInGalaxy.y, ship.getShape(), ship.getshapeVecCount(), 0.5f,  0.5f, 0.5f));
            int[] anchors = {
              0,1,2,
              0,0,0
              ,0,2,3
            };
            
             
            ship.setTexture("basicShip.png", 6f, 2f,0f,0f, anchors);
            ship.isLight = true;
            renderer.addObject(ship);

            generatePlanets();
            
            makeMapFrame();
            renderer.addGuiObject(map);
        }
    private void generatePlanets(){
            SpaceTexture solid = new SpaceTexture("planet.png",1f,1f,0f,0f);
            SpaceTexture gas = new SpaceTexture("gasplanet.png",1f,1f,0f,0f);
            SpaceTexture moon = new SpaceTexture("moon.png",1f,1f,0f,0f);
            for(Planet p : planets){
                
                p.is2d = false;
                
                p.setRoundShape(p.getSize());
                p.setBody(physicsCore.addPlanet(50f-p.getDistanceToSun(), 0f, p.getSize()));
                
                

                
                //p.colorsRGB = new Vec3(1,1,r.nextFloat());
                if(p.getType() == Planet.Type.SOLID){
                    p.setTexture(solid);
                } else if(p.getType() == Planet.Type.GAS){
                    p.setTexture(gas);
                    p.alpha = 0.4f;
                } else if(p.getType() == Planet.Type.MOON){
                    p.setTexture(moon);
                    p.colorsRGB = new Vec3(1f,1f,1f);
                } 
                System.out.println(p.getClimate().toString());
                p.hasHalo = true;
                
                renderer.addObject(p);
                
                if(p.getClimate() == Planet.Climate.SUN){
                    p.isLight = true;
                    p.setTexture(gas);
                }
                
            }
        
    }
    
    @Override
    public void update(){
        input();
        updateMap();
        renderer.setCameraPos(ship.getPos().x, ship.getPos().y);
        ship.shipUpdate();
    }

    
    private boolean tapped = false;
    private void input(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){

            createParticle(ship.getPos().x +ship.getParticleOutputPos().x,ship.getPos().y +ship.getParticleOutputPos().y, 0.5f,ship.getAngle()+r.nextFloat()-0.5f );
            ship.applyForceForward(1f);

        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            ship.applyForce(new Vec2(0,-1));
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            ship.applyRotation(-2f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            ship.applyRotation(2f);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            
            if(ship.boost()){
                
                createParticle(ship.getPos().x +ship.getParticleOutputPos().x,ship.getPos().y +ship.getParticleOutputPos().y, 0.5f,r.nextFloat()*2*(float)Math.PI,20,r.nextFloat(),r.nextFloat(),r.nextFloat());
            }
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_MINUS)){
            renderer.scale(0.1f);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_ADD)){
            renderer.scale(-0.1f);
            pushEvent(new PlanetEvent());
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_E)){
            
            landing();
            
        }
    }
    
private void landing(){
    
    for(Planet p : planets) {

System.out.println("siss" + p.getSize());
        if(physicsCore.getDistance(p.getBody(), ship.getBody()) < p.getSize()*2){
            
            sharedContainer.currentPlanet = p;
            sharedContainer.ship.posInGalaxy = ship.getPos();
            pushEvent(new PlanetEvent());
            break;
        }
        
    }
    
}
    
private void makeMapFrame(){
        map = new Map(-19.0f,-19.0f);
        map.setLineColor(0f,0f,1f);
        map.addLine(5.0f, 5f);
        map.addLine(-5.0f, 5f);
        map.addLine(-5.0f, 5f);
        map.addLine(-5.0f, -5f);
        map.addLine(-5.0f, -5f);
        map.addLine(5.0f, -5f);
        map.addLine(5.0f, -5f);
        map.addLine(5.0f, 5f);
        
        map.setQuadColor(0.6f, 0.1f, 0.6f);
       // map.addQuad(0, 0,10f);

        for(Planet p : planets){
            map.addQuad(p.getPos().x/50f, p.getPos().y/50f, p.getSize()/20f);
        }
        map.player = map.addQuad(ship.getPos().x/50f, ship.getPos().y/50f, 0.2f);
}

private void updateMap(){
    
    map.setQuad(ship.getPos().x/50f, ship.getPos().y/50f, 0.2f,map.player-1);
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