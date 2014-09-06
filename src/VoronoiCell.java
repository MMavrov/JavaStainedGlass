import java.awt.Point;
import java.util.Vector;

public class VoronoiCell {
	private Point seed;
	private Vector<Point> points;
	private Vector<Integer> RGBpoints;

	public VoronoiCell(Point seed) {
		this.seed = seed;
		points = new Vector<Point>();
		RGBpoints = new Vector<Integer>();
	}

	public Point getSeed() {
		return seed;
	}

	public void setPoint(Point point) {
		points.add(point);
	}

	public void setRGBPoint(Integer point) {
		RGBpoints.add(point);
	}

	public Vector<Point> getPoints() {
		return points;
	}

	public Vector<Integer> getRGBpoints() {
		return RGBpoints;
	}

	public int getAverageColor() {
		int alpha = 0;
		int red = 0;
		int green = 0;
		int blue = 0;

		for (Integer RGBvalue : RGBpoints) {
			alpha += (RGBvalue >> 24) & 0xff;
			red += (RGBvalue >> 16) & 0xff;
			green += (RGBvalue >> 8) & 0xff;
			blue += (RGBvalue >> 16) & 0xff;
		}

		alpha /= RGBpoints.size();
		red /= RGBpoints.size();
		green /= RGBpoints.size();
		blue /= RGBpoints.size();

		return ((alpha & 0x0ff) << 24) | ((red & 0x0ff) << 16)
				| ((green & 0x0ff) << 8) | (blue & 0x0ff);
	}
}
