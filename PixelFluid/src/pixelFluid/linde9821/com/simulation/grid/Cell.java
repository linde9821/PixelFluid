package pixelFluid.linde9821.com.simulation.grid;

import pixelFluid.linde9821.com.simulation.particel.Particle;

public class Cell {
	private Particle p;

	public Cell(Particle p) {
		this.p = p;
	}

	public Particle getP() {
		return p;
	}

	public void setP(Particle p) {
		this.p = p;
	}
	
	

}
