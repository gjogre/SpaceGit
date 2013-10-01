package Tools;

import org.lwjgl.opengl.GL11;

public class Texture {

    private int textureID;
    private int textureWidth;
    private int textureHeight;
    
    public Texture(){
        textureID = 0;
        textureWidth = 0;
        textureHeight = 0;
    }
    
    public boolean loadTextureFromPixels(int pixels, int width, int height){
        
        textureWidth = width;
        textureHeight = height;
        //GL11.glGenTextures(textureID); 
       return true;
    }
    
}
