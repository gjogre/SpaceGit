package GameObjects;

import GameObjects.GameObject;
import java.util.Random;
public class Planet extends GameObject{

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
    
    private static Random r = new Random();
    
    public Planet( float size, int galaxy, Climate climate, int id, float distanceToSun){
        super();
        super.size = size;
        this.galaxy = galaxy;
        this.climate = climate;
        this.id = id;
        this.distanceToSun = distanceToSun;
        int rr = r.nextInt(2);
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
        lastDistance = distanceToSun;
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

    
}
