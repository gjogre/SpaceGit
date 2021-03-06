package PlanetView;

import Event.sharedContainer;
import GameObjects.GameObject;
import GameObjects.Ground;
import GameObjects.Planet;
import GameObjects.Planet.Climate;
import GameObjects.Volcano;
import java.util.ArrayList;
import java.util.Random;
import org.jbox2d.common.Vec2;

public class Surface extends GameObject{
    
    private Climate atmosphere;
    private float temperature;
    Random r = new Random();
    public ArrayList<Ground> groundList;
    public ArrayList<Volcano> volcanoList;
    private int groundPieces = 200; //HOW MANY GROUND OBJECTS MAP HAS
    public Vec2[] groundShapeList = new Vec2[groundPieces+3];
    private boolean firstPiece = true;
    private Vec2 groundShapeCenter;
 
    public Surface(Planet planet){
        super.graphicsVecCount = groundShapeList.length;
        
        atmosphere = planet.getClimate();
        if(atmosphere == Planet.Climate.HOT){
            temperature = (float)r.nextInt(1000)+1000f;
        }
        if(atmosphere == Planet.Climate.WARM){
            temperature = (float)r.nextInt(100)+100f;
        }
        if(atmosphere == Planet.Climate.CHILLY){
            temperature = (float)r.nextInt(100);
        }
        if(atmosphere == Planet.Climate.COLD){
            temperature = (float)r.nextInt(200);
        }
        if(atmosphere == Planet.Climate.FREEZING){
            temperature = -273.15f;
        }
        generateGround(planet.getRoughness()+10);
        super.shape = groundShapeList;
        
    }
    
    private void generateGround(float scale){
        int i;
        int rdm;
        float yAxisStart = 0;
        float lastYAxis = 0;
        
                
        groundList = new ArrayList<>();
        volcanoList = new ArrayList<>();
        
        firstPiece = true;
        for(i = 0;i<groundPieces;i++){
            if(firstPiece){ 
               groundList.add(new Ground(scale, yAxisStart, lastYAxis));
               firstPiece = false;
            }else{
                yAxisStart = groundList.get(groundList.size()-1).returnTopRight().y;
                rdm = r.nextInt(4);
                if(rdm == 0){
                    if(lastYAxis >= (10/*6*scale*/)){
                        lastYAxis = yAxisStart;
                    }else{
                        lastYAxis = yAxisStart + (4/*0.4f*scale*/);
                        groundList.add(new Ground(scale, yAxisStart, lastYAxis));   
                    }
                }else if(rdm == 1){
                    lastYAxis = yAxisStart + (3/*0.3f*scale*/);
                    groundList.add(new Ground(scale, yAxisStart, lastYAxis)); 
                }else if(rdm == 2){
                    if(lastYAxis <= (-1/**scale*/)){
                        lastYAxis = yAxisStart;
                    }else{
                        lastYAxis = yAxisStart - (5/*0.5f*scale*/);
                        groundList.add(new Ground(scale, yAxisStart, lastYAxis));
                    }
                }else if(rdm == 3){
                    if(lastYAxis <= (-1/**scale*/)){
                        lastYAxis = -(8/*0.8f*scale*/);
                    }else{
                        lastYAxis = yAxisStart - (3/*0.3f*scale*/);
                        groundList.add(new Ground(scale, yAxisStart, lastYAxis));
                    }
                }               
            } 
        }
    }
}

