import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

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

	public StainedGlassEffect() {
		try {
			targetImage = ImageIO.read(new File("input.jpg"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(targetImage, stainedGlassFilter, 0, 0);
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
		JFrame f = new JFrame("Beware! Stained Glass Effect");

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		f.add(new StainedGlassEffect("PJ.jpg", null, 5000));
		f.pack();
		f.setVisible(true);
	}
}
