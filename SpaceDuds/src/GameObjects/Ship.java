package GameObjects;

import Event.EventMachine;
import Event.sharedContainer;
import GameObjects.GameObject;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;



public class Ship extends GameObject {
    private final int MAX_ADDONS = 10;

    private ArrayList<Lander> landers;
    private Lander currentLander;
    private int selectedLanderSlot = 0;

    public boolean setLander() {
        try{
        if(landers.get(selectedLanderSlot) != null){
            currentLander = landers.get(selectedLanderSlot);
            landers.remove(landers.get(selectedLanderSlot));
            return true;
        }
        } catch(IndexOutOfBoundsException e){
            return false;
        }
        return false;
    }
    
    public void addLander(Lander l){
        landers.add(l);
    }

    public void addSelectedLanderSlot() {
        if(selectedLanderSlot < landerSlots){
            selectedLanderSlot++;
        } else {
            selectedLanderSlot = 0;
        }
    }
    
    public int getBoostLoad() {
        return boostLoad;
    }
    
    private int boostLoad = 100;
    private int boost = boostLoad;

    public int getBoost() {
        return boost;
    }
    private int boostStrenght = 10;
    private int landerSlots;
    
    //place in local coordinates where the particles comes from
    private Vec2 particleOutputPos; 
    private Vec2 landerOutputPos; 
    public Vec2 getParticleOutputPos() {
        Vec2 temp = new Vec2(particleOutputPos.x * (float)Math.cos(body.getAngle()), particleOutputPos.x  * (float)Math.sin(body.getAngle()));
        return temp;
    }
       public Vec2 getLanderOutputPos() {
        Vec2 temp = new Vec2(landerOutputPos.x * (float)Math.cos(body.getAngle()), landerOutputPos.x  * (float)Math.sin(body.getAngle()));
        return temp;
    }
    public Vec2 posInGalaxy;
    
    public Ship(){
        super();
        landers = new ArrayList<>();
        posInGalaxy = new Vec2(-100f,15f);
        makeNormalShip();
        
        //this.hasHalo = true;
        //this.haloSize = 1.2f;
    }

    private void makeNormalShip(){
        super.shapeVecCount =6;
        super.graphicsVecCount = 9;
        super.shape[0] = new Vec2(-2f,0.5f);
        super.shape[1] = new Vec2(-2f,-0.5f);
        super.shape[2] = new Vec2(0.1f,-0.5f);
        
        super.shape[3] = new Vec2(0.1f,0.5f);
        super.shape[4] = new Vec2(1f,0f);
        super.shape[5] = new Vec2(0.1f,-0.5f);
        
        
        super.shape[6] = new Vec2(-2f,0.5f);
        super.shape[7] = new Vec2(0.1f,-0.5f);
        super.shape[8] = new Vec2(0.1f,0.5f);

      //  super.shape[4] = new Vec2(0.25f,1f);
        particleOutputPos = new Vec2(-2.5f, 0f);
       landerOutputPos = new Vec2(0f,0.5f);
       
       landerSlots = 3;
       landers.add(new Lander(2, 1.5f));
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
    
    public Lander getCurrentLander(){
        return currentLander;
    }
}
