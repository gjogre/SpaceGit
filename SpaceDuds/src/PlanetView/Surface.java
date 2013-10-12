package PlanetView;

import SpaceView.Planet;
import SpaceView.Planet.Climate;
import java.util.ArrayList;
import java.util.Random;
import org.jbox2d.common.Vec2;

public class Surface {
    
    private Climate atmosphere;
    private float temperature;
    Random r = new Random();
    public ArrayList<Ground> groundList;
    private boolean firstPiece = true;
    
    public Surface(Planet planet){
        atmosphere = planet.getClimate();
        if(atmosphere == Planet.Climate.HOT){
            temperature = (float)r.nextInt(1000)+1000f;
        }
        if(atmosphere == Planet.Climate.WARM){
            temperature = (float)r.nextInt(100)+100f;
        }
        if(atmosphere == Planet.Climate.CHILLY){
            temperature = (float)r.nextInt(-100);
        }
        if(atmosphere == Planet.Climate.COLD){
            temperature = (float)r.nextInt(-200);
        }
        if(atmosphere == Planet.Climate.FREEZING){
            temperature = -273.15f;
        }
    }
    public Surface(float scale) {
        generateGround(scale);
    }
    
    private void generateGround(float scale){
        int i;
        int rdm;
        float yAxisStart = 0;
        float lastYAxis = 0, lastXAxis = 1;
        
        groundList = new ArrayList<Ground>();
        firstPiece = true;
        for(i = 0;i<30;i++){
            if(firstPiece){
               groundList.add(new Ground(0*scale, yAxisStart, (0*scale),(-6*scale), (1*scale),(-6*scale), lastXAxis*scale,lastYAxis));
               firstPiece = false;
            }else{
                yAxisStart = groundList.get(groundList.size()-1).returnLast().y;
                rdm = r.nextInt(4);
                if(rdm == 0){
                    if(lastYAxis >= (2*scale)){
                        lastYAxis = yAxisStart;
                    }else{
                        lastYAxis = yAxisStart + (0.15f*scale);
                        groundList.add(new Ground(0*scale, yAxisStart, (0*scale),(-6*scale), (1*scale),(-6*scale), lastXAxis*scale,lastYAxis));
                    }
                }else if(rdm == 1){
                    lastYAxis = yAxisStart + (0.1f*scale);
                    groundList.add(new Ground(0*scale, yAxisStart, (0*scale),(-6*scale), (1*scale),(-6*scale), lastXAxis*scale,lastYAxis));
                }else if(rdm == 2){
                    if(lastYAxis <= (-1*scale)){
                        lastYAxis = yAxisStart;
                    }else{
                        lastYAxis = yAxisStart - (0.18f*scale);
                        groundList.add(new Ground(0*scale, yAxisStart, (0*scale),(-6*scale), (1*scale),(-6*scale), lastXAxis*scale,lastYAxis));
                    }
                }else if(rdm == 3){
                    if(lastYAxis <= (-1*scale)){
                        lastYAxis = -(0.8f*scale);
                    }else{
                        lastYAxis = yAxisStart - (0.18f*scale);
                        groundList.add(new Ground(0*scale, yAxisStart, (0*scale),(-6*scale), (1*scale),(-6*scale), lastXAxis*scale,lastYAxis));
                    }
                }
                
            }
        }
    }
}

