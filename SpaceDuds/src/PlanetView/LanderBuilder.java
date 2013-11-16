package PlanetView;

import static Event.EventMachine.physicsCore;
import static Event.EventMachine.renderer;
import GameObjects.Lander;

public class LanderBuilder {
    
    public static void Builder(Lander lander, float x, float y){
        
        if(lander.getLanderType() == lander.ROOVER){
            int i;
            float size, wheelSize;
            size = lander.getMass() / 0.5f;

            if(size == 2.0f){

            }else{
                size -=2;
                wheelSize = 0.5f;
                for(i = 0;i<size;i++){
                    wheelSize += 0.22f;
                }
                lander.getBackWheel().setWheelSize(wheelSize);
                lander.getFrontWheel().setWheelSize(wheelSize);
            }

            lander.setBody(physicsCore.addObject(x, y, lander.getShape(), lander.getshapeVecCount(), 1f, 1f, 1f));
            physicsCore.addDamageObject(lander);
            lander.getBackWheel().setCircle(lander.getBackWheel().getWheelSize(), 12);
            lander.getBackWheel().setBody(physicsCore.addWheel(lander.getBackAxelSpot().x+x, lander.getBackAxelSpot().y+y, lander.getBackWheel().getWheelSize()));
            lander.getFrontWheel().setCircle(lander.getFrontWheel().getWheelSize(), 12);
            lander.getFrontWheel().setBody(physicsCore.addWheel(lander.getFrontAxelSpot().x+x/*x+1.5f*/, lander.getFrontAxelSpot().y+y, lander.getFrontWheel().getWheelSize()));

            physicsCore.distanceJoint(lander.getBody(), lander.getBackWheel().getBody(), lander.getBackAxelSpot(), lander.getBackWheel().getPos());
            physicsCore.distanceJoint(lander.getBody(), lander.getFrontWheel().getBody(), lander.getFrontAxelSpot(), lander.getFrontWheel().getPos());
            physicsCore.distanceJoint(lander.getBody(), lander.getBackWheel().getBody(), lander.getPos(), lander.getBackWheel().getPos());
            physicsCore.distanceJoint(lander.getBody(), lander.getFrontWheel().getBody(), lander.getPos(), lander.getFrontWheel().getPos());
            physicsCore.distanceJoint(lander.getBackWheel().getBody(), lander.getFrontWheel().getBody(), lander.getBackWheel().getPos(), lander.getFrontWheel().getPos());

            renderer.addObject(lander);
            renderer.addObject(lander.getBackWheel());
            renderer.addObject(lander.getFrontWheel());
            physicsCore.setGravity(0f, -9.81f);
            
        }else if(lander.getLanderType() == lander.SCOUT){
            
            lander.setBody(physicsCore.addObject(x, y, lander.getShape(), lander.getshapeVecCount(), 0.5f, 0.5f, 0.5f));
            physicsCore.addDamageObject(lander);
            renderer.addObject(lander);
            physicsCore.setGravity(0f, 0f);
        }
    }
}
