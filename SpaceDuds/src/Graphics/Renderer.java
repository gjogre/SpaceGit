package Graphics;

import Tools.GameObject;
import Tools.Particle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.*;

public class Renderer {
    
    private int WIDTH = 800, HEIGHT = 800;

    private ArrayList<GameObject> objectList = new ArrayList<>();
    
    public Renderer()throws LWJGLException{
        
        Display.setTitle("SpaceDuds");
        Display.setResizable(false);

        Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));

        Display.setVSyncEnabled(true);
        Display.setFullscreen(false);
        
        Display.create();
       
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        glClearColor(0f, 0f, 0f, 0f);
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        GLU.gluOrtho2D(-25.0f, 25.0f, -25.0f, 25.0f);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        
    }
    
    public void addObject(GameObject obj){
        objectList.add(obj);
    }
    
    public void render(){
         GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
         
         for(GameObject o : objectList){
            GL11.glPushMatrix();
            
               GL11.glTranslatef(o.getPos().x, o.getPos().y, 0f);
               GL11.glRotatef((float)Math.toDegrees(o.getAngle()),0,0,1);
               GL11.glBegin(GL11.GL_LINES);

                for(int i = 0; i < o.getshapeVecCount(); i++){
                    
                    GL11.glVertex2f(o.getLine(i).x, o.getLine(i).y);
                    if(i == o.getshapeVecCount()-1){
                        GL11.glVertex2f(o.getLine(0).x, o.getLine(0).y);
                    } else {
                        GL11.glVertex2f(o.getLine(i+1).x, o.getLine(i+1).y);
                    }
                }

               GL11.glEnd();

            GL11.glPopMatrix();
         }
    }

     /*   private void drawParticles(){
        double angle = 0;
        for(Iterator<Particle> itr = particles.iterator(); itr.hasNext();){
            Particle p = itr.next();
            angle = 0;
                GL11.glPushMatrix();
                GL11.glTranslatef(p.body.getPosition().x, p.body.getPosition().y, 0);
                GL11.glBegin(GL11.GL_POLYGON); 
                for(int i = 0; i < 29; i ++){
                    GL11.glVertex2d(p.radius * Math.cos(i*2*Math.PI / 32)+p.body.getPosition().x,p.radius*Math.sin(i*2*Math.PI / 32)+p.body.getPosition().y );
                }
                GL11.glEnd();
                GL11.glPopMatrix();
            if(p.subTtl()){
                physicsCore.removeBody(p.body);
                itr.remove();
            }
            
        }
    }
    */
}
