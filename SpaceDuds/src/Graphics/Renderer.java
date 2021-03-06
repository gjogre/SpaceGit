package Graphics;

import Event.EventMachine;
import Tools.GUIObject;
import GameObjects.GameObject;
import Tools.GUILine;
import Tools.GUIQuad;
import Tools.Particle;
import java.util.ArrayList;
import java.util.Iterator;
import org.jbox2d.common.Vec2;
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
import org.lwjgl.util.glu.*;
import org.newdawn.slick.TrueTypeFont;
public class Renderer {
    
    private int WIDTH = 800, HEIGHT = 800;
    private Vec2 camera = new Vec2(0,0);
    private Vec2 cameraTarget = new Vec2(0,0);
    private ArrayList<GameObject> objectList = new ArrayList<>();
    private ArrayList<GUIObject> GUIList = new ArrayList<>();
    
    private float lerp = 1f;
    
    public void setCameraTargetPos(float x, float y){
        cameraTarget.set(new Vec2(x,y));
    }
    public void setCameraPos(float x, float y){
        camera.set(new Vec2(x,y));
    }
    public Vec2 getCameraPos(){
        return camera;
    }
    public void release() {
        objectList.clear();
        GUIList.clear();
        camera.set(new Vec2(0f,0f));
        cameraTarget.set(new Vec2(0f,0f));
    }

    
    private Display display;
    public Renderer(boolean isApplet)throws LWJGLException{
        if(!isApplet){
            Display.setTitle("SpaceDuds");
            Display.setResizable(false);

            Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));

            Display.setVSyncEnabled(EventMachine.VSync);
            Display.setFullscreen(false);

            Display.create();
        
        }


        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glEnable( GL_LINE_SMOOTH );
        glEnable(GL_TEXTURE_2D);
      //  GL11.glHint( GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST );
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST_MIPMAP_NEAREST);
        glClearColor(0f, 0f, 0f, 0f);
       //glViewport(0, 0, Display.getWidth(), Display.getHeight());
         //GLU.gluOrtho2D(-25.0f, 25.0f, -25.0f, 25.0f);
        glOrtho(-25, 25, -25, 25, -1, 1);
        glMatrixMode(GL_PROJECTION);
        
        glLoadIdentity();
        glMatrixMode(GL_MODELVIEW);
        glLineWidth(2f);
        makeFont();
       // GL30.glGenerateMipmap(GL_TEXTURE_2D);
    }
    
    public void addObject(GameObject obj){
        objectList.add(obj);
    }
    public void addGuiObject(GUIObject obj){
        GUIList.add(obj);
    }
    

        
        

    


    public static float ZOOM_X = 1.0f;
    public static float ZOOM_Y = 1.0f;
    

    public void render(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        camera.x += (cameraTarget.x - camera.x)* lerp;
        camera.y += (cameraTarget.y - camera.y)* lerp;
        
        glColor3f(1, 1, 1);
        //moving screen to camera postition
        glPushMatrix();
        glScalef(ZOOM_X, ZOOM_Y, 0.05f);
        glTranslatef(-camera.x, -camera.y, 0.05f);
        
        //glScalef(scale.x, scale.y, 1f);
         

        
         for(GameObject o : objectList){
             o.updateGfx();
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
                   drawHalo(o);
               }
               glColor4f(o.currentColorsRGB.x, o.currentColorsRGB.y, o.currentColorsRGB.z, o.alpha);
               if(o.isSphere){
                   drawSphere(o);                  
               } else if(o.isCircle){
                   drawCircle(o);
               } else {
                   drawStandardObject(o);
               }

            glPopMatrix();
         }
          glDisable(GL_TEXTURE_2D);
         drawQuadParticles();
              
 //return the camera position
         glPopMatrix();
         
         drawGUI();
    }

    private void drawStandardObject(GameObject o) {
        glBegin(GL_TRIANGLES);
        
        //glColor4f(o.colorsRGB.x, o.colorsRGB.y, o.colorsRGB.z, o.alpha);
        
        
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

    private void drawCircle(GameObject o) {
        glBegin(GL_TRIANGLE_FAN);
        if(o.hasTexture()){glTexCoord2f(0.5f/o.getSTexture().textureDividerX+o.getSTexture().textureOffsetX, 0.5f/o.getSTexture().textureDividerY+o.getSTexture().textureOffsetY);}
        glVertex3f(0,0,0.2f);
        
        for(int i = 0; i < o.getGraphicsVecCount(); i++){
            if(o.hasTexture()){glTexCoord2f(o.getLine(i).x/o.getSTexture().textureDividerX+o.getSTexture().textureOffsetX, o.getLine(i).y/o.getSTexture().textureDividerY+o.getSTexture().textureOffsetY);}
            glVertex3f(o.getLine(i).x, o.getLine(i).y,0.2f);
        }
        if(o.hasTexture()){glTexCoord2f(o.getLine(0).x/o.getSTexture().textureDividerX+o.getSTexture().textureOffsetX, o.getLine(0).y/o.getSTexture().textureDividerY+o.getSTexture().textureOffsetY);}
        glVertex3f(o.getLine(0).x, o.getLine(0).y,0.2f);
        glEnd();
    }

    private void drawSphere(GameObject o) {
        //glBegin(GL_TRIANGLE_FAN);
        glRotatef(90f,1,0,0);
        glRotatef(-(float)Math.toDegrees(o.getRotation()),0,0,1);
        // o.getTexture().bind();
        Sphere s = new Sphere();
        s.setDrawStyle(GLU.GLU_FILL);
        s.setTextureFlag(true);
        s.setNormals(GLU.GLU_SMOOTH);
        s.draw(o.getSize(), 24, 16);
    }

    private void drawHalo(GameObject o) {
        glBegin(GL_TRIANGLE_FAN);
        glColor4f(o.defaultColorsRGB.x,o.defaultColorsRGB.y, o.defaultColorsRGB.z, 0.2f);
        for(int i = 0; i < o.getGraphicsVecCount(); i++){
            
            glVertex3f(o.getLine(i).x*o.haloSize, o.getLine(i).y*o.haloSize,-0.1f);
            
        }
        glEnd();
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
            


            glBegin(GL_QUADS);
                for(GUIQuad q : g.getQuads()){
                    glColor3f(q.color.x, q.color.y, q.color.z);
                    glVertex3f(q.quad.x-(q.quad.z/2) , q.quad.y+(q.quad.z/2),0.2f);
                    glVertex3f(q.quad.x-(q.quad.z/2) , q.quad.y-(q.quad.z/2),0.2f);
                    glVertex3f(q.quad.x+(q.quad.z/2) , q.quad.y-(q.quad.z/2),0.2f);
                    glVertex3f(q.quad.x+(q.quad.z/2) , q.quad.y+(q.quad.z/2),0.2f);
                }
            glEnd();
           
            glBegin(GL_LINES);
                for(GUILine l : g.getLines()){
                    glColor3f(l.color.x, l.color.y, l.color.z);
                    glVertex3f(l.line.x, l.line.y,0.2f);
                }
                
                  
          

                
            glEnd();
            glPopMatrix();

        }
            glPushMatrix();
                glTranslatef(-20f, 20f, 0.5f);
                glScalef(0.1f, -0.1f, 0.2f);
                font.drawString(0f, 0f, "Space: Boost", org.newdawn.slick.Color.blue);
                font.drawString(0f, 18f, "e: Send lander (if it misses planet its gone)", org.newdawn.slick.Color.blue);
                font.drawString(0f, 36f, "p: get off planet", org.newdawn.slick.Color.blue);
                font.drawString(0f, 54f, "wasd: Move", org.newdawn.slick.Color.blue);
                font.drawString(0f, 72f, "esc: pause and restart scene", org.newdawn.slick.Color.blue);
            glPopMatrix();
            glPushMatrix();
                glTranslatef(-24f, -8f, 0.5f);
                glScalef(0.1f, -0.1f, 0.2f);
                font.drawString(0f, 0f, "Landers", org.newdawn.slick.Color.blue);
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
    
    public void removeObject(GameObject g){
        if(objectList.contains(g)){
            objectList.remove(g);
        }
    }
    
    public void setLerp(float lerp){
        this.lerp = lerp;
    }
}
