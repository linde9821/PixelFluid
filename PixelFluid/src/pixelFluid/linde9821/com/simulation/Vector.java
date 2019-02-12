package pixelFluid.linde9821.com.simulation;

public class Vector {
	private double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vector a) {
		x += a.getX();
		y += a.getY();
	}

	public void sub(Vector a) {
		x -= a.getX();
		y -= a.getY();
	}

	public static Vector add(Vector a, Vector b) {
		return new Vector(a.getX() + b.getX(), a.getY() + b.getY());
	}

	public static Vector sub(Vector a, Vector b) {
		return new Vector(a.getX() - b.getX(), a.getY() - b.getY());
	}

	public static double scalarProduct(Vector a, Vector b) {
		return a.x * b.x + a.y * b.y;
	}

	public double scalarProduct(Vector a) {
		return a.x * x + a.y * y;
	}

	public static Vector scalarDiv(Vector a, double t) {
		double x = a.x / t;
		double y = a.y / t;

		return new Vector(x, y);
	}

	public void scalarDiv(double t) {
		x = x / t;
		y = y / t;
	}

	public static Vector scalarMulti(Vector v, double scalar) {
		return new Vector(v.x * scalar, v.y * scalar);
	}

	public void scalarMulti(double scalar) {
		x *= scalar;
		y *= scalar;
	}

	public double length() {
		return Math.sqrt((x * x) + (y * y));
	}

	// getter and setter

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void set(Vector a) {
		x = a.x;
		y = a.y;
	}

}
