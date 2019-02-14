package pixelFluid.linde9821.com.simulation.distanceField;

import pixelFluid.linde9821.com.simulation.Vector;

public class Cell {
	
	private int index;
	private double distance;
	private Vector normal;

	public Cell(int i) {
		index = i;
		distance = -1;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Vector getNormal() {
		return normal;
	}

	public void setNormal(Vector normal) {
		this.normal = normal;
	}
	
	
	
	
}
