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
import edu.wlu.cs.levy.CG.KDTree;
import edu.wlu.cs.levy.CG.KeyDuplicateException;
import edu.wlu.cs.levy.CG.KeySizeException;

public class StainedGlassFilter implements BufferedImageOp {
	private KDTree<VoronoiCell> cells;
	private Vector<double[]> seedsCoordinates;

	public StainedGlassFilter(int imageWidth, int imageHeight, int numberOfSeeds) {
		cells = new KDTree<VoronoiCell>(2);
		seedsCoordinates = new Vector<double[]>();

		for (int i = 0; i < numberOfSeeds; i++) {
			int xCoordinate = 0;
			int yCoordinate = 0;

			// Making sure that all points are generated and they are different
			VoronoiCell amIExist = null;
			do {
				xCoordinate = new Random().nextInt(imageWidth);
				yCoordinate = new Random().nextInt(imageHeight);

				try {
					amIExist = cells.search(new double[] { xCoordinate,
							yCoordinate });
				} catch (KeySizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (amIExist != null);

			try {
				cells.insert(new double[] { xCoordinate, yCoordinate },
						new VoronoiCell(new Point(xCoordinate, yCoordinate)));
				seedsCoordinates.add(new double[] { xCoordinate,
						yCoordinate });
			} catch (KeySizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyDuplicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
		int counter = 0;
		int percentDone = 0;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Point currentPoint = new Point(x, y);

				VoronoiCell nearestCell = null;
				try {
					nearestCell = cells.nearest(new double[] { x, y });
				} catch (KeySizeException e) {
					e.getMessage();
				}

				nearestCell.setBelongingPointsColors(src.getRGB(x, y));
				nearestCell.setBelongingPoint(currentPoint);

				counter++;

				if ((counter * 100) / (height * width) != percentDone) {
					percentDone = (counter * 100) / (height * width);
					System.out.println(String.format(
							"Calculating cells. %d%% done!", percentDone));
				}
			}
		}

		counter = 0;
		percentDone = 0;

		for (double[] seedCoordinates : seedsCoordinates) {
			VoronoiCell cell = null;
			try {
				cell = cells.search(seedCoordinates);
			} catch (KeySizeException e) {
				e.printStackTrace();
			}
			
			int averageColor = cell.getAverageColor();
			for (Point point : cell.getBelongingPoints()) {
				result.setRGB(point.x, point.y, averageColor);
			}
			
			counter++;

			if ((counter * 100) / seedsCoordinates.size() != percentDone) {
				percentDone = (counter * 100) / seedsCoordinates.size();
				System.out.println(String.format(
						"Calculating colors. %d%% done!", percentDone));
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
