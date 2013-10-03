package Graphics;

import Tools.GUIObject;
import Tools.GameObject;
import Tools.Particle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import org.jbox2d.dynamics.Body;
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
    private Vec2 camera = new Vec2(0,0);
    
    private ArrayList<GameObject> objectList = new ArrayList<>();
    private ArrayList<GUIObject> GUIList = new ArrayList<>();
    public void setCameraPos(float x, float y){
        camera.set(new Vec2(x,y));
    }
    
    
    public Renderer()throws LWJGLException{
        
        Display.setTitle("SpaceDuds");
        Display.setResizable(false);

        Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));

        Display.setVSyncEnabled(true);
        Display.setFullscreen(false);
        
        Display.create();
       
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glEnable( GL11.GL_LINE_SMOOTH );
        GL11.glHint( GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST );
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        glClearColor(0f, 0f, 0f, 0f);
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        GLU.gluOrtho2D(-25.0f, 25.0f, -25.0f, 25.0f);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        GL11.glLineWidth(2f);
        
    }
    
    public void addObject(GameObject obj){
        objectList.add(obj);
    }
    public void addGuiObject(GUIObject obj){
        GUIList.add(obj);
    }
    public void render(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        
  

        GL11.glColor3f(1, 1, 1);
        //moving screen to camera postition
        GL11.glPushMatrix();
        GL11.glTranslatef(-camera.x, -camera.y, 0f);
         for(GameObject o : objectList){
            GL11.glPushMatrix();
            
               GL11.glTranslatef(o.getPos().x, o.getPos().y, 0f);
               GL11.glRotatef((float)Math.toDegrees(o.getAngle()),0,0,1);
               GL11.glBegin(GL11.GL_LINES);

                for(int i = 0; i < o.getGraphicsVecCount(); i++){
                    
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
         drawQuadParticles();
              
 //return the camera position
         GL11.glPopMatrix();
         drawGUI();
    }

    private void drawGUI(){
        for(GUIObject g : GUIList){
            GL11.glPushMatrix();
            
            GL11.glTranslatef(g.getScreenCoord().x, g.getScreenCoord().y, 0f);
            
            
            GL11.glColor3f(g.getQuadColor().x, g.getQuadColor().y, g.getQuadColor().z);
            GL11.glBegin(GL11.GL_QUADS);
                for(Vec3 q : g.getQuads()){
                    GL11.glVertex3f(q.x-(q.z/2) , q.y+(q.z/2),0f);
                    GL11.glVertex3f(q.x-(q.z/2) , q.y-(q.z/2),0f);
                    GL11.glVertex3f(q.x+(q.z/2) , q.y-(q.z/2),0f);
                    GL11.glVertex3f(q.x+(q.z/2) , q.y+(q.z/2),0f);
                }
            GL11.glEnd();
            GL11.glColor3f(g.getLineColor().x, g.getLineColor().y, g.getLineColor().z);
            GL11.glBegin(GL11.GL_LINES);
                for(Vec2 l : g.getLines()){
                    GL11.glVertex2f(l.x, l.y);
                }
            GL11.glEnd();
            GL11.glPopMatrix();
        }
        // ...
    }
    
    private void drawQuadParticles(){
        

        for(Iterator<Particle> itr = Particle.particles.iterator(); itr.hasNext();){
            Particle p = itr.next();
            GL11.glPushMatrix();
            
                GL11.glColor3f(p.color.x, p.color.y, p.color.z);
            
                GL11.glTranslatef(p.body.getPosition().x, p.body.getPosition().y, 0);
                GL11.glRotatef((float)Math.toDegrees(p.body.getAngle()),0,0,1);
                
                GL11.glBegin(GL11.GL_QUADS);
                    GL11.glVertex3f(- (p.radius/2), (p.radius/2), 0f);
                    GL11.glVertex3f( - (p.radius/2), -(p.radius/2), 0f);
                    GL11.glVertex3f((p.radius/2), -(p.radius/2), 0f);
                    GL11.glVertex3f( (p.radius/2), (p.radius/2), 0f);
                GL11.glEnd();
            GL11.glPopMatrix();

            
            if(p.subTtl()){
                Particle.remove(p);
                itr.remove();
            } 
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
