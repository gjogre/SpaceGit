package GameObjects;

import GameObjects.GameObject;
import PlanetView.Surface;
import java.util.Random;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
public class Planet extends GameObject{

    private Surface surface;

    public Surface getSurface() {
        return surface;
    }
    
    public static enum Climate{
        SUN, HOT, WARM, TEMPERATE, CHILLY, COLD, FREEZING;

    }
    public static enum Type {
        GAS, SOLID, MOON;
    }

    public Type getType() {
        return type;
    }
    private int galaxy;
    private int id;
    
    private Climate climate;
    private Type type;
    
    private float distanceToSun;
    private static float lastDistance;
    
    private float roughness = 5f;

    public float getRoughness() {
        return roughness;
    }
    
    private static Random r = new Random();
    
    public Planet( float size, int galaxy, Climate climate, int id, float distanceToSun){
        super();
        super.size = size;
        this.galaxy = galaxy;
        this.climate = climate;
        this.id = id;
        this.distanceToSun = distanceToSun;
        int rr = r.nextInt(2);
        this.roughness += 0;
        if(distanceToSun - lastDistance < super.size*2 && distanceToSun != 0){
            super.size = super.size / 3;
            this.type = Type.MOON;
        } else {
            switch(rr){
                
                case 0:
                    this.type = Type.GAS;
                    
                    break;
                default:
                    this.type = Type.SOLID;
                    break;
            }
     
        }
         System.out.println("Planet type: "+rr);
         generateColor();
        lastDistance = distanceToSun;
        surface = new Surface(this);
        
        
    }


    public float getSize(){
        return super.size;
    }
    public Climate getClimate() {
        return this.climate;
    }
    public float getDistanceToSun(){
        return distanceToSun;
    }
 public Vec2 generatePointInGalaxy(Vec2 sunPoint){
     float angle = r.nextFloat()*2*(float)Math.PI;
     Vec2 p = new Vec2(sunPoint.x + distanceToSun * (float)Math.cos(angle),sunPoint.y + distanceToSun * (float)Math.sin(angle));
     return p;
 }
    private void generateColor(){
        
                super.colorsRGB = new Vec3(r.nextFloat(),r.nextFloat(),r.nextFloat());
                int colorStrenght = r.nextInt(3);
                switch(colorStrenght){
                    
                    case 0:
                        super.colorsRGB.x = 1f;
                        break;
                    case 1:
                        super.colorsRGB.y = 1f;
                        break;
                    case 2:
                        super.colorsRGB.z = 1f;
                        break;
                    
                }
        
    }
}
