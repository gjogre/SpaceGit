package SpaceView;

import Tools.GUIObject;

public class LandersGUI extends GUIObject {

   
    public LandersGUI(float coordX, float coordY) {
        super(coordX, coordY);
    }
    public void clear(){
        super.quads.clear();
    }
}
