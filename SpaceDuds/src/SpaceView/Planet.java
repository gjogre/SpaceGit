package SpaceView;

import Tools.GameObject;
public class Planet extends GameObject{

    public static enum Climate{
        SUN, HOT, WARM, TEMPERATE, CHILLY, COLD, FREEZING;
    }
    
    private int galaxy;
    private int id;
    private float size;
    private Climate climate;
    private float distanceToSun;
    public Planet( float size, int galaxy, Climate climate, int id, float distanceToSun){
        super();
        this.size = size;
        this.galaxy = galaxy;
        this.climate = climate;
        this.id = id;
        this.distanceToSun = distanceToSun;
    }

    public float getSize(){
        return size;
    }
    public Climate getClimate() {
        return this.climate;
    }
    public float getDistanceToSun(){
        return distanceToSun;
    }

    
}
