package pixelFluid.linde9821.com.simulation;

public class SimulationSettings {
	private final int maxParticles; // maximum amount of particles

	private double timeStep;
	private double radius; // maximum distance particles effect each other
	private double collisionRadius; // the distance from a wall that counts as a collision
	private double p0; // rest density
	private double s; // the viscosit's linear dependence on the velocity (sigma)
	private double b; // the viscosit's quadratic dependence on the velocity (beta)
	private double k; // stiffness used in DoubleDensityRelaxation
	private double kNear; // near-stiffness used in DoubleDensityRelaxation
	private double friction; // friction used in resolveCollision
	private Gravity gravity; // the global gravity acceleration (in this project used as the only changing
								// external force)

	// std
	public SimulationSettings() {
		maxParticles = 50000;
		timeStep = 0.0166667;
		radius = 5;
		collisionRadius = 3;
		p0 = 10;
		s = 0.3;
		b = 0.5;
		k = 2;
		kNear = 0.4;
		friction = 0.6;
		gravity = new Gravity(0, 9.81);
	}

	/*
	 * public SimulationSettings(String str) {
	 * 
	 * }
	 */

	public void reloadSettings(String str) {
		try {
				
		}catch (Exception e) {
			
		}
	}

	public void saveSettings(String str) {

	}

	public double getTimeStep() {
		return timeStep;
	}

	public void setTimeStep(double timeStep) {
		this.timeStep = timeStep;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getCollisionRadius() {
		return collisionRadius;
	}

	public void setCollisionRadius(double collisionRadius) {
		this.collisionRadius = collisionRadius;
	}

	public double getP0() {
		return p0;
	}

	public void setP0(double p0) {
		this.p0 = p0;
	}

	public double getS() {
		return s;
	}

	public void setS(double s) {
		this.s = s;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public double getkNear() {
		return kNear;
	}

	public void setkNear(double kNear) {
		this.kNear = kNear;
	}

	public double getFriction() {
		return friction;
	}

	public void setFriction(double friction) {
		this.friction = friction;
	}

	public Gravity getGravity() {
		return gravity;
	}

	public void setGravity(Gravity gravity) {
		this.gravity = gravity;
	}

	public int getMaxParticles() {
		return maxParticles;
	}
}
