import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class StainedGlassEffect extends Component {
	BufferedImage processedImage;
 
    public StainedGlassEffect() {
       try {
           processedImage = ImageIO.read(new File("input.jpg"));
       } catch (IOException e) {
    	   System.out.println(e.getMessage());
       }
    }
    
    public void paint(Graphics g) {
        g.drawImage(processedImage, 0, 0, null);
    }
 
    public Dimension getPreferredSize() {
        if (processedImage == null) {
             return new Dimension(200,200);
        } else {
           return new Dimension(processedImage.getWidth(null), processedImage.getHeight(null));
       }
    }
    
	private static void DisplayTestImage() {
		JFrame f = new JFrame("Load Image Sample");
        
        f.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        
        f.add(new StainedGlassEffect());
        f.pack();
        f.setVisible(true);		
	}

	public static void main(String[] args) {
		DisplayTestImage();
	}
}
