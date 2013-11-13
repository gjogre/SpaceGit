package GameObjects;

public class Wheel extends GameObject{
    float backWheelSize, frontWheelSize;
    
    public Wheel(){
        backWheelSize = 2f;
        frontWheelSize = 2f;
    }
    
    public float getBackWheelSize(){
        return backWheelSize;
    }
    
    public float getFrontWheelSize(){
        return frontWheelSize;
    }
}
