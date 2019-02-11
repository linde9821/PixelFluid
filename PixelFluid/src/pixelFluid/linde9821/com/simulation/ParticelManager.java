package pixelFluid.linde9821.com.simulation;

import java.util.ArrayList;

import pixelFluid.linde9821.com.simulation.distanceField.DistanceField;
import pixelFluid.linde9821.com.simulation.grid.Grid;
import pixelFluid.linde9821.com.simulation.particel.Particle;
import pixelFluid.linde9821.com.simulation.particel.Velocity;

public class ParticelManager {

	private final double timeStep;

	private double radius; // maximum distance particles effect each other
	private double collisionRadius; // the distance from a wall that counts as a collision
	private double p0; // rest density
	private double s; // the viscosit's linear dependence on the velocity (sigma)
	private double b; // the viscosit's quadratic dependence on the velocity (beta)
	private double k; // stiffness used in DoubleDensityRelaxation
	private double kNear; // near-stiffness used in DoubleDensityRelaxation
	private Gravity gravity = new Gravity(0, 10); // the global gravity acceleration (in this project used as the only
													// changing extternal force)

	private ArrayList<Particle> particles; // main list of of particles
	private Grid grid; //
	private DistanceField distanceField; //

	public ParticelManager() {
		timeStep = 60;
	}

	// simulation
	public void update(double timeStep) {
		applyExternalForce(timeStep);
		applyViscosity(timeStep);
		advanceParticles(timeStep);
		updateNeighbors();
		doubleDensityRelaxation(timeStep);
		resolveCollisions();
		updateVelocity(timeStep);
	}

	public void applyExternalForce(double timeStep) {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);

			p.getVel().add(gravity);

			// further forces can be added her
		}
	}

	public void applyViscosity(double timeStep) {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);

			for (int j = 0; j < p.getNeighbors().size(); j++) {
				Particle n = p.getNeighbors().get(j);
				Vector vpn = Vector.sub(n.getPos(), p.getPos()); // Vpn = n.pos - p.pos

				double velInward = Vector.scalarProduct(vpn, (Vector.sub(p.getVel(), n.getVel()))); // velInward =
																									// (p.vel - n.vel) *
																									// Vpn
				if (velInward > 0) {
					double length = vpn.length();
					velInward = velInward / length;
					vpn.set(Vector.scalarDiv(vpn, length)); // velInward = velInward / length
					double q = length / radius;

					double temp = 0.5 * timeStep * (1 - q) * (s * velInward + b * velInward * velInward);

					Vector iVec = Vector.scalarMulti(vpn, temp);	//
					
					p.setVel((Velocity) Vector.sub(p.getVel(), iVec));	//p.vel = p.vel - I
				}
			}
		}
	}

	public void advanceParticles(double timeStep) {

	}

	public void updateNeighbors() {

	}

	public void doubleDensityRelaxation(double timeStep) {

	}

	public void resolveCollisions() {

	}

	public void updateVelocity(double timeStep) {

	}

	// getter and setter

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

	public Gravity getGravity() {
		return gravity;
	}

	public void setGravity(Gravity gravity) {
		this.gravity = gravity;
	}

	public ArrayList<Particle> getParticles() {
		return particles;
	}

	public void setParticles(ArrayList<Particle> particles) {
		this.particles = particles;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public DistanceField getDistanceField() {
		return distanceField;
	}

	public void setDistanceField(DistanceField distanceField) {
		this.distanceField = distanceField;
	}

	public double getTimeStep() {
		return timeStep;
	}

}
