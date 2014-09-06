import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StainedGlassEffect extends Component {
	private static final long serialVersionUID = 7292337168285997704L;

	BufferedImage targetImage;
	StainedGlassFilter stainedGlassFilter;

	public StainedGlassEffect(String targetFile, String resultFile,
			int numberOfSeeds) {
		try {
			targetImage = ImageIO.read(new File(targetFile));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		stainedGlassFilter = new StainedGlassFilter(targetImage.getWidth(),
				targetImage.getHeight(), numberOfSeeds);
	}

	public void paint(Graphics g) {		
		String format = "jpg";
		File saveFile = new File("output."+format);
		BufferedImage resultImage = stainedGlassFilter.filter(targetImage, null);
		try {
			ImageIO.write(resultImage, format, saveFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Dimension getPreferredSize() {
		if (targetImage == null) {
			return new Dimension(200, 200);
		} else {
			return new Dimension(targetImage.getWidth(null),
					targetImage.getHeight(null));
		}
	}

	public static void main(String[] args) {	
		StainedGlassEffect effect = new StainedGlassEffect("PJ.jpg", null, 10000);
		effect.paint(null);
	}
}
