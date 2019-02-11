package pixelFluid.linde9821.com.simulation.particel;

import java.util.ArrayList;

import pixelFluid.linde9821.com.simulation.Gravity;

public class Particle {
	private Position pos; // the particles position
	private Position posPrev; // the particles previous position
	private Velocity vel; // the particles velocity
	private int index; // an index used by the grid class
	private ArrayList<Particle> neighbors; // collection of each particle's list of neighbors

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public Position getPosPrev() {
		return posPrev;
	}

	public void setPosPrev(Position posPrev) {
		this.posPrev = posPrev;
	}

	public Velocity getVel() {
		return vel;
	}

	public void setVel(Velocity vel) {
		this.vel = vel;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Position add(Position old, Gravity g) {
		double x = old.getX() + g.getX();
		double y = old.getY() + g.getY();
		
		return new Position(x, y);
	}

	public ArrayList<Particle> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayList<Particle> neighbors) {
		this.neighbors = neighbors;
	}
	
	

}
