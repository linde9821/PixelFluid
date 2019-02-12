package pixelFluid.linde9821.com.simulation.particel;

import pixelFluid.linde9821.com.simulation.Vector;

public class Position extends Vector{	
	public Position(double x, double y) {
		super(x, y);
	}	
	
	public Position(Vector v) {
		super(v.getX(), v.getY());
	}	
}
