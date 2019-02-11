package pixelFluid.linde9821.com.simulation.particel;

public class Particel {
	private Position pos;
	private Position posPrev;
	private double vel;
	private int index;
	
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
	public double getVel() {
		return vel;
	}
	public void setVel(double vel) {
		this.vel = vel;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
}
