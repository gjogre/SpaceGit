package GameObjects;

import Event.EventMachine;
import Event.sharedContainer;
import GameObjects.GameObject;
import org.jbox2d.common.Vec2;



public class Ship extends GameObject {
    private final int MAX_ADDONS = 10;
    
    private int boostLoad = 100;
    private int boost = boostLoad;
    private int boostStrenght = 10;
    
    //place in local coordinates where the particles comes from
    private Vec2 particleOutputPos; 
    public Vec2 getParticleOutputPos() {
        Vec2 temp = new Vec2(particleOutputPos.x * (float)Math.cos(body.getAngle()), particleOutputPos.x  * (float)Math.sin(body.getAngle()));
        return temp;
    }
   
    public Vec2 posInGalaxy;
    
    public Ship(){
        super();
        posInGalaxy = new Vec2(-100f,15f);
        makeNormalShip();
    }

    private void makeNormalShip(){
        super.shapeVecCount =6;
        super.graphicsVecCount = 9;
        super.shape[0] = new Vec2(-2f,0.5f);
        super.shape[1] = new Vec2(-2f,-0.5f);
        super.shape[2] = new Vec2(0.1f,-0.5f);
        
        super.shape[3] = new Vec2(0.1f,0.5f);
        super.shape[4] = new Vec2(0.1f,-0.5f);
        super.shape[5] = new Vec2(1f,0f);
        
        super.shape[6] = new Vec2(-2f,0.5f);
        super.shape[7] = new Vec2(0.1f,-0.5f);
        super.shape[8] = new Vec2(0.1f,0.5f);

      //  super.shape[4] = new Vec2(0.25f,1f);
        particleOutputPos = new Vec2(-2.5f, 0f);
       
        
    }

    public void shipUpdate(){
        if(boost < boostLoad){
            boost++;
            System.out.println(boost);
        }
    }
    
    public boolean boost(){
        if(boost == boostLoad){
            super.applyImulse(1f);
            
            boost = 0;
            
            return true;
        } else if (boost < boostStrenght){
            super.applyImulse(1f);
            return true;
        }
        return false;
    }
}
