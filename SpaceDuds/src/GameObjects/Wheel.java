package GameObjects;

import Graphics.SpaceTexture;

public class Wheel extends GameObject{
    float backWheelSize, frontWheelSize;
    private static SpaceTexture wheelText = new SpaceTexture("lavaLandOutline.png", 10f, 10f, 0, 0);
    public Wheel(){
        backWheelSize = 2f;
        frontWheelSize = 2f;
        this.setTexture(wheelText);
        
    }
    
    public float getBackWheelSize(){
        return backWheelSize;
    }
    
    public float getFrontWheelSize(){
        return frontWheelSize;
    }
}
