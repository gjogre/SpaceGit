package PlanetView;

import Tools.GameObject;
import org.jbox2d.common.Vec2;

public class BattleShip extends GameObject {
    public BattleShip(){
        super();
        battleShipShape();
    }
    
    public void battleShipShape(){
        super.shapeVecCount = 4;
        super.graphicsVecCount = 4;
        super.shape[0] = new Vec2(0f,0f);
        super.shape[1] = new Vec2(0f,-1f);
        super.shape[2] = new Vec2(1f,-1f);
        super.shape[3] = new Vec2(1f,0f);
    }
}
