import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.util.Random;
import java.util.Vector;

public class StainedGlassFilter implements BufferedImageOp {
	Vector<VoronoiCell> cells;

	public StainedGlassFilter(int imageWidth, int imageHeight,
			int numberOfPivots) {
		cells = new Vector<VoronoiCell>();
		for (int i = 0; i < numberOfPivots; i++) {
			cells.add(new VoronoiCell(new Point(new Random()
					.nextInt(imageWidth), new Random().nextInt(imageHeight))));
		}
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dst) {
		if (dst == null) {
			dst = createCompatibleDestImage(src, null);
		}

		int width = src.getWidth();
		int height = src.getHeight();

		BufferedImage result = src;
		int seedIndex = 0;
		int currentPixelNumber = 0;
		int percentDone = 0;

		for (int y = 0; y < width; y++) {
			for (int x = 0; x < height; x++) {
				Point currentPoint = new Point(y, x);
				double minDistance = Double.MAX_VALUE;

				for (int i = 0; i < cells.size(); i++) {
					double distance = currentPoint.distance(cells.get(i)
							.getSeed());
					if (distance < minDistance) {
						minDistance = distance;
						seedIndex = i;
					}
				}

				cells.get(seedIndex).setRGBPoint(src.getRGB(y, x));
				cells.get(seedIndex).setPoint(currentPoint);

				currentPixelNumber += 1;

				if ((currentPixelNumber * 100) / (height * width) != percentDone) {
					percentDone = (currentPixelNumber * 100) / (height * width);
					System.out
							.println(String.format("%03d%% done!", percentDone));
				}
			}
		}

		for (VoronoiCell voronoiCell : cells) {
			for (Point point : voronoiCell.getPoints()) {
				result.setRGB(point.x, point.y, voronoiCell.getAverageColor());
			}
		}

		return result;
	}
	
	@Override
	public BufferedImage createCompatibleDestImage(BufferedImage src,
			ColorModel destCM) {
		if (destCM == null) {
			destCM = src.getColorModel();
		}

		return new BufferedImage(destCM, destCM.createCompatibleWritableRaster(
				src.getWidth(), src.getHeight()),
				destCM.isAlphaPremultiplied(), null);
	}

	@Override
	public Rectangle2D getBounds2D(BufferedImage src) {
		return new Rectangle(0, 0, src.getWidth(), src.getHeight());
	}

	@Override
	public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
		return (Point2D) srcPt.clone();
	}

	@Override
	public RenderingHints getRenderingHints() {
		return null;
	}
}
