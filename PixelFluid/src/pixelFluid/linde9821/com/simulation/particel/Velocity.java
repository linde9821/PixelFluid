package pixelFluid.linde9821.com.simulation.particel;

import pixelFluid.linde9821.com.simulation.Vector;

public class Velocity extends Vector {

	public Velocity(double x, double y) {
		super(x, y);
	}
	
	public Velocity(Vector v) {
		super(v.getX(), v.getY());
	}

}
