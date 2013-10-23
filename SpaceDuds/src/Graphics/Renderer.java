package Graphics;

import Tools.GUIObject;
import GameObjects.GameObject;
import Tools.Particle;
import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import org.jbox2d.common.Vec3;
import org.jbox2d.dynamics.Body;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
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
import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.SlickCallable;
public class Renderer {
    
    private int WIDTH = 800, HEIGHT = 800;
    private Vec2 camera = new Vec2(0,0);
    private Vec2 scale = new Vec2(0.05f,0.8f);
    private ArrayList<GameObject> objectList = new ArrayList<>();
    private ArrayList<GUIObject> GUIList = new ArrayList<>();
    public void setCameraPos(float x, float y){
        camera.set(new Vec2(x,y));
    }
    
    public Vec2 getCameraPos(){
        return camera;
    }
    public void scale(float scale){
        this.scale.x += scale;
        this.scale.y += scale;
    }
    public void release() {
        objectList.clear();
        GUIList.clear();
        camera.set(new Vec2(0f,0f));
    }
    
    public Renderer()throws LWJGLException{
        
        Display.setTitle("SpaceDuds");
        Display.setResizable(false);

        Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));

        Display.setVSyncEnabled(true);
        Display.setFullscreen(false);
        
        Display.create();
        
        


        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glEnable( GL_LINE_SMOOTH );
        glEnable(GL_TEXTURE_2D);
      //  GL11.glHint( GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST );
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        glClearColor(0f, 0f, 0f, 0f);
       //glViewport(0, 0, Display.getWidth(), Display.getHeight());
         //GLU.gluOrtho2D(-25.0f, 25.0f, -25.0f, 25.0f);
        glOrtho(-25, 25, -25, 25, -1, 1);
        glMatrixMode(GL_PROJECTION);
        
        glLoadIdentity();
        glMatrixMode(GL_MODELVIEW);
        glLineWidth(2f);
        makeFont();
        
    }
    
    public void addObject(GameObject obj){
        objectList.add(obj);
    }
    public void addGuiObject(GUIObject obj){
        GUIList.add(obj);
    }
    

        
        

    
    
    public void render(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        


        glColor3f(1, 1, 1);
        //moving screen to camera postition
        glPushMatrix();
        glTranslatef(-camera.x, -camera.y, 0.05f);
        glScalef(1f, 1f, 0.05f);
        //glScalef(scale.x, scale.y, 1f);
         

        
         for(GameObject o : objectList){
            glPushMatrix();
                if(o.hasTexture()){
                    glEnable(GL_TEXTURE_2D);
                    o.getTexture().bind();

        
                } else {
                    glDisable(GL_TEXTURE_2D); 
                }
                
               glTranslatef(o.getPos().x, o.getPos().y, 0f);
               glRotatef((float)Math.toDegrees(o.getAngle()),0,0,1);
               
                if(o.hasHalo){
      
                    glBegin(GL_TRIANGLE_FAN);
                        glColor4f(o.colorsRGB.x, o.colorsRGB.y, o.colorsRGB.z, 0.2f);
                        for(int i = 0; i < o.getGraphicsVecCount(); i++){
                            glVertex3f(o.getLine(i).x*2, o.getLine(i).y*2,-0.1f);
                        }
                     glEnd();

               }
               
               if(o.isCircle){
                   glColor4f(o.colorsRGB.x, o.colorsRGB.y, o.colorsRGB.z, o.alpha);
                   //glBegin(GL_TRIANGLE_FAN);
                   glRotatef(90f,1,0,0);
                  glRotatef(-(float)Math.toDegrees(o.getRotation()),0,0,1);
                  // o.getTexture().bind();
                   Sphere s = new Sphere();
                   s.setDrawStyle(GLU.GLU_FILL);
                   s.setTextureFlag(true);
                   s.setNormals(GLU.GLU_SMOOTH);
                   s.draw(o.getSize(), 24, 16);

                   
               } else {
                   glBegin(GL_TRIANGLES);
             
               

               
               glColor4f(o.colorsRGB.x, o.colorsRGB.y, o.colorsRGB.z, o.alpha);
               
               if(o.isCircle){

               }
               int anchors = 0;
               //normal way of drawing
                for(int i = 0; i < o.getGraphicsVecCount(); i++){

                    
                    if(o.hasTexture()){ 
                        if(o.hasAnchors()){ 
                            
                            if(o.getAnchor(i) == 0){
                                glTexCoord2f(0f, 0f);
                            }
                            if(o.getAnchor(i) == 1){
                                glTexCoord2f(0f, 1f);
                            }
                            if(o.getAnchor(i) == 2){
                                glTexCoord2f(1f, 1f);
                            }
                            if(o.getAnchor(i) == 3){
                                glTexCoord2f(1f, 0f);
                            }
                        } else {
                            glTexCoord2f(o.getLine(i).x/o.getSTexture().textureDividerX+o.getSTexture().textureOffsetX,-o.getLine(i).y/o.getSTexture().textureDividerY+o.getSTexture().textureOffsetY);
                        }
                    }

                    glVertex3f(o.getLine(i).x, o.getLine(i).y,0.1f);
                   

                }
                
                if(o.hasTexture()){o.getTexture().release();}
               
               glEnd();
            }

            glPopMatrix();
         }
          glDisable(GL_TEXTURE_2D);
         drawQuadParticles();
              
 //return the camera position
         glPopMatrix();
         
         drawGUI();
    }
    private java.awt.Font awtFont;
    private TrueTypeFont font;
    private void makeFont(){
        awtFont = new  java.awt.Font("Times New Roman",  java.awt.Font.PLAIN, 18);
       
        font = new TrueTypeFont(awtFont, false);
    }
    
    private void drawGUI(){

        for(GUIObject g : GUIList){
            glPushMatrix();
            
            glTranslatef(g.getScreenCoord().x, g.getScreenCoord().y, 0.5f);
            




            
            glColor3f(g.getQuadColor().x, g.getQuadColor().y, g.getQuadColor().z);
            glBegin(GL_QUADS);
                for(Vec3 q : g.getQuads()){
                    glVertex3f(q.x-(q.z/2) , q.y+(q.z/2),0.2f);
                    glVertex3f(q.x-(q.z/2) , q.y-(q.z/2),0.2f);
                    glVertex3f(q.x+(q.z/2) , q.y-(q.z/2),0.2f);
                    glVertex3f(q.x+(q.z/2) , q.y+(q.z/2),0.2f);
                }
            glEnd();
            glColor3f(g.getLineColor().x, g.getLineColor().y, g.getLineColor().z);
            glBegin(GL_LINES);
                for(Vec2 l : g.getLines()){
                    glVertex3f(l.x, l.y,0.2f);
                }
                
                  
          

                
            glEnd();
            glPopMatrix();

        }
            glPushMatrix();
                glTranslatef(-20f, 20f, 0.5f);
                glScalef(0.1f, -0.1f, 0.2f);
                font.drawString(0f, 0f, "Space: Boost", org.newdawn.slick.Color.blue);
                font.drawString(0f, 18f, "e: Land (p: to get off land)", org.newdawn.slick.Color.blue);
                font.drawString(0f, 36f, "wasd: Move", org.newdawn.slick.Color.blue);
            glPopMatrix();
    }
    
    private void drawQuadParticles(){


        for(Iterator<Particle> itr = Particle.particles.iterator(); itr.hasNext();){
            Particle p = itr.next();
            glPushMatrix();
            
                glColor3f(p.color.x, p.color.y, p.color.z);
            
                glTranslatef(p.body.getPosition().x, p.body.getPosition().y, 0);
                glRotatef((float)Math.toDegrees(p.body.getAngle()),0,0,1);
                
                glBegin(GL_QUADS);
                    glVertex3f(- (p.radius/2), (p.radius/2), 0f);
                    glVertex3f( - (p.radius/2), -(p.radius/2), 0f);
                    glVertex3f((p.radius/2), -(p.radius/2), 0f);
                    glVertex3f( (p.radius/2), (p.radius/2), 0f);
                glEnd();
            glPopMatrix();
            
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
                glPushMatrix();
                glTranslatef(p.body.getPosition().x, p.body.getPosition().y, 0);
                glBegin(GL_POLYGON); 
                for(int i = 0; i < 29; i ++){
                    glVertex2d(p.radius * Math.cos(i*2*Math.PI / 32)+p.body.getPosition().x,p.radius*Math.sin(i*2*Math.PI / 32)+p.body.getPosition().y );
                }
                glEnd();
                glPopMatrix();
            if(p.subTtl()){
                physicsCore.removeBody(p.body);
                itr.remove();
            }
            
        }
    }
    */
}
