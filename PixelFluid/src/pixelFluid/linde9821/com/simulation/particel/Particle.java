package pixelFluid.linde9821.com.simulation.particel;

import java.util.ArrayList;

import pixelFluid.linde9821.com.simulation.Gravity;
import pixelFluid.linde9821.com.simulation.Vector;

public class Particle {
	private Position pos; // the particles position
	private Position posPrev; // the particles previous position
	private Velocity vel; // the particles velocity
	private int id; 
	private int index; // an id used by the grid class
	private ArrayList<Particle> neighbors; // collection of each particle's list of neighbors
	
	private double tempN;
	private Vector vpn;
	
	private static int currentAmount;

	public Particle(Position p) {
		this((int) p.getX(),(int) p.getY());
	}
	
	public Particle(int x, int y) {
		pos = new Position(x, y);
		posPrev = new Position(x, y);
		vel = new Velocity(0, 0);
		neighbors = new ArrayList<Particle>();
		id = currentAmount;		// unsure about the usage of id
		currentAmount ++;
	}
	
	public static void removeParticel() {
		currentAmount--;	
	}
	
	public static void resetCurrentAmount() {
		currentAmount = 0;
	}
	
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

	public int getId() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
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

	public static int getCurrentAmount() {
		return currentAmount;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getTempN() {
		return tempN;
	}

	public void setTempN(double tempN) {
		this.tempN = tempN;
	}

	public Vector getVpn() {
		return vpn;
	}

	public void setVpn(Vector vpn) {
		this.vpn = vpn;
	}	
	
	
}
