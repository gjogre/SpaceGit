package Graphics;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
public class SpaceTexture {

    private int id;
    private int width;
    private int height;
    private Texture texture;
    
    public Texture getTexture(){
        return texture;
    }
    public SpaceTexture(String name){
        id = 0;
        width = 0;
        height = 0;
        
        try {
            // load texture from PNG file
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("images/"+name));

            System.out.println("Texture loaded: "+texture);
            System.out.println(">> Image width: "+texture.getImageWidth());
            System.out.println(">> Image height: "+texture.getImageHeight());
            System.out.println(">> Texture width: "+texture.getTextureWidth());
            System.out.println(">> Texture height: "+texture.getTextureHeight());
            System.out.println(">> Texture ID: "+texture.getTextureID());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    
}
