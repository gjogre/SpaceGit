package GameObjects;

import org.jbox2d.common.Vec2;

public class BattleShip extends GameObject {
    public BattleShip(){
        super();
        battleShipShape();
    }
    
    public void battleShipShape(){
        super.shapeVecCount = 3;
        super.graphicsVecCount = 3;
        super.shape[0] = new Vec2(-1f,1f);
        super.shape[1] = new Vec2(-1f,-1f);
        super.shape[2] = new Vec2(1f,0f);
    }
    
    @Override
    protected void takeDamage(float force){
        System.out.println("asd");
    }
}
