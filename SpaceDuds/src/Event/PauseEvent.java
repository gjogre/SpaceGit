
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

    }
}
    
    

