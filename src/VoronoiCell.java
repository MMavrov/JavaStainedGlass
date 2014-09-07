import java.awt.Point;
import java.util.Vector;

public class VoronoiCell {
	private Point seed;
	private Vector<Point> belongingPoints;
	private Vector<Integer> belongingPointsColors;
	
	public VoronoiCell(){
	}
	
	public VoronoiCell(Point seed) {
		this.seed = seed;
		belongingPoints = new Vector<Point>();
		belongingPointsColors = new Vector<Integer>();
	}

	public Point getSeed() {
		return seed;
	}

	public void setBelongingPoint(Point point) {
		belongingPoints.add(point);
	}

	public void setBelongingPointsColors(Integer point) {
		belongingPointsColors.add(point);
	}

	public Vector<Point> getBelongingPoints() {
		return belongingPoints;
	}

	public Vector<Integer> getBelongingPointsColors() {
		return belongingPointsColors;
	}

	public int getAverageColor() {
		int alpha = 0;
		int red = 0;
		int green = 0;
		int blue = 0;

		for (Integer RGBvalue : belongingPointsColors) {
			alpha += (RGBvalue >> 24) & 0xff;
			red += (RGBvalue >> 16) & 0xff;
			green += (RGBvalue >> 8) & 0xff;
			blue += RGBvalue & 0xff;
		}

		red /= belongingPointsColors.size();
		alpha /= belongingPointsColors.size();
		green /= belongingPointsColors.size();
		blue /= belongingPointsColors.size();

		return ((alpha & 0x0ff) << 24) | ((red & 0x0ff) << 16)
				| ((green & 0x0ff) << 8) | (blue & 0x0ff);
	}
}
