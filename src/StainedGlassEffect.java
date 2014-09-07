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
	String processedFile;

	public StainedGlassEffect(String targetFile, String resultFile,
			int numberOfSeeds) {
		try {
			targetImage = ImageIO.read(new File(targetFile));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		this.processedFile = resultFile;
		stainedGlassFilter = new StainedGlassFilter(targetImage.getWidth(),
				targetImage.getHeight(), numberOfSeeds);
	}

	public void paint(Graphics g) {
		File saveFile = new File(processedFile);
		BufferedImage resultImage = stainedGlassFilter
				.filter(targetImage, null);
		try {
			System.out.println(String.format("Writing to file \"%s\"",
					processedFile));
			ImageIO.write(resultImage, "jpg" ,saveFile);
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

	// public static void main(String[] args) {
	// StainedGlassEffect effect = new StainedGlassEffect(
	// "PJ.jpg", "output.jpg", 5000);
	// effect.paint(null);
	// }

	public static void main(String[] args) {
		if (args.length == 2) {
			StainedGlassEffect effect = new StainedGlassEffect(args[0],
					args[1], 5000);
			effect.paint(null);
		} else if (args.length == 3) {
			StainedGlassEffect effect = new StainedGlassEffect(args[0],
					args[1], Integer.parseInt(args[2]));
			effect.paint(null);
		} else {
			System.out
					.println("Please add arguments as input and output image including the file extensions.");
		}

	}
}
