package ObjectBuilders;

import GameObjects.Roover;
import GameObjects.Wheel;
import static Event.EventMachine.physicsCore;
import static Event.EventMachine.renderer;

public class RooverBuilder {
    public RooverBuilder(Roover roover, Wheel backWheel, Wheel frontWheel, float x, float y){
        int i;
        float size, wheelSize;
        size = roover.getMass() / 0.5f;
        
        if(size == 2.0f){
            
        }else{
            size -=2;
            wheelSize = backWheel.getWheelSize();
            for(i = 0;i<size;i++){
                wheelSize += 0.22f;
            }
            backWheel.setWheelSize(wheelSize);
            frontWheel.setWheelSize(wheelSize);
        }
        
        roover.setBody(physicsCore.addObject(x/*+0.75f*/, y/*+0.6f*/, roover.getShape(), roover.getshapeVecCount(), 1f, 1f, 1f));
        physicsCore.addDamageObject(roover);
        backWheel.setCircle(backWheel.getWheelSize(), 12);
        backWheel.setBody(physicsCore.addWheel(roover.getBackAxelSpot().x+x, roover.getBackAxelSpot().y+y, backWheel.getWheelSize()));
        frontWheel.setCircle(frontWheel.getWheelSize(), 12);
        frontWheel.setBody(physicsCore.addWheel(roover.getFrontAxelSpot().x+x/*x+1.5f*/, roover.getFrontAxelSpot().y+y, frontWheel.getWheelSize()));
        
        physicsCore.distanceJoint(roover.getBody(), backWheel.getBody(), roover.getBackAxelSpot(), backWheel.getPos());
        physicsCore.distanceJoint(roover.getBody(), frontWheel.getBody(), roover.getFrontAxelSpot(), frontWheel.getPos());
        physicsCore.distanceJoint(roover.getBody(), backWheel.getBody(), roover.getPos(), backWheel.getPos());
        physicsCore.distanceJoint(roover.getBody(), frontWheel.getBody(), roover.getPos(), frontWheel.getPos());
        physicsCore.distanceJoint(backWheel.getBody(), frontWheel.getBody(), backWheel.getPos(), frontWheel.getPos());
        
        renderer.addObject(roover);
        renderer.addObject(backWheel);
        renderer.addObject(frontWheel);
    }
}
