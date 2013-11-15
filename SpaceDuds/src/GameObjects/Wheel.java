package GameObjects;

import Graphics.SpaceTexture;

public class Wheel extends GameObject{
    float wheelSize;
    private static SpaceTexture wheelText = new SpaceTexture("lavaLandOutline.png", 10f, 10f, 0, 0);
    public Wheel(){
        wheelSize = 0.5f;
        this.setTexture(wheelText);
        
    }
    
    public float getWheelSize(){
        return wheelSize;
    }
    
    public void setWheelSize(float size){
        this.wheelSize = size;
    }
}
