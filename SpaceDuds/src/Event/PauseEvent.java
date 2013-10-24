
package Event;

import org.lwjgl.input.Keyboard;
import static Event.EventMachine.*;
public class PauseEvent extends Event{

    private boolean keyHold;
    private boolean pressed;
    public PauseEvent(){
        keyHold = true;
        pressed = false;
    }
    @Override
    public void update(){
        System.out.println(keyHold);
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            
            if(!keyHold){
                
                 pressed = true;
            
        

            }
        } else{
            keyHold = false;
        }
        if(pressed){
                popEvent();
        }
    }
    
    
}
