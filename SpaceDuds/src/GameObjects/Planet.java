package GameObjects;

import GameObjects.GameObject;
import PlanetView.Surface;
import java.util.ArrayList;
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
    
    private float distanceToCenter;
    private Vec2 orbitCenter;
    
    private static float lastDistance;
    
    private float roughness = 5f;

    public float getRoughness() {
        return roughness;
    }
    
    private static Random r = new Random();
    public ArrayList<Moon> moons;
    public Planet( float size, int galaxy, Climate climate, int id, float distanceToCenter){
        super();
        
        orbitCenter = new Vec2(0,0);
        
        super.size = size;
        this.galaxy = galaxy;
        this.climate = climate;
        this.id = id;
        this.distanceToCenter = distanceToCenter;
        int rr = r.nextInt(2);
        this.roughness += 0;
        if(distanceToCenter - lastDistance < super.size*2 && distanceToCenter != 0){
            distanceToCenter += super.size*2;

        } 
            switch(rr){
                
                case 0:
                    this.type = Type.GAS;
                    
                    break;
                default:
                    this.type = Type.SOLID;
                    break;
          
     
        }
         System.out.println("Planet type: "+rr);
         generateColor();
        lastDistance = distanceToCenter;

        surface = new Surface(this);
        
        generateMoons();
        
        
    }

    private void generateMoons(){
        moons = new ArrayList<>();
        int m = r.nextInt(3);
        for(int i = 0; i < m; i++){
            moons.add(new Moon(r.nextFloat()*5+this.size*2,r.nextFloat()*3));
        }
    }
    public float getSize(){
        return super.size;
    }
    public Climate getClimate() {
        return this.climate;
    }
    public float getDistanceToCenter(){
        return distanceToCenter;
    }
 public Vec2 generatePointInGalaxy(Vec2 sunPoint){
     float angle = r.nextFloat()*2*(float)Math.PI;
     Vec2 p = new Vec2(sunPoint.x + distanceToCenter * (float)Math.cos(angle),sunPoint.y + distanceToCenter * (float)Math.sin(angle));
     return p;
 }
    private void generateColor(){
        
                super.colorsRGB = new Vec3(r.nextFloat(),r.nextFloat(),r.nextFloat());
               /* int colorStrenght = r.nextInt(3);
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
                    
                }*/
        
    }
}
