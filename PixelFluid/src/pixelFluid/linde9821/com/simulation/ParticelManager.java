package pixelFluid.linde9821.com.simulation;

import java.util.ArrayList;

import pixelFluid.linde9821.com.simulation.distanceField.DistanceField;
import pixelFluid.linde9821.com.simulation.grid.Grid;
import pixelFluid.linde9821.com.simulation.particel.Particle;
import pixelFluid.linde9821.com.simulation.particel.Position;
import pixelFluid.linde9821.com.simulation.particel.Velocity;
import sun.text.normalizer.CharTrie.FriendAgent;

public class ParticelManager {

	private double timeStep;
	private final int maxParticles;
	private double radius; // maximum distance particles effect each other
	private double collisionRadius; // the distance from a wall that counts as a collision
	private double p0; // rest density
	private double s; // the viscosit's linear dependence on the velocity (sigma)
	private double b; // the viscosit's quadratic dependence on the velocity (beta)
	private double k; // stiffness used in DoubleDensityRelaxation
	private double kNear; // near-stiffness used in DoubleDensityRelaxation
	private Gravity gravity = new Gravity(0, 10); // the global gravity acceleration (in this project used as the only
													// changing external force)

	private ArrayList<Particle> particles; // main list of of particles
	private Grid grid; //
	private DistanceField distanceField; //

	private final boolean particelCoordinationCheck = true;

	public ParticelManager() {
		timeStep = 0.0166667;
		maxParticles = 3000;

		Particle.resetCurrentAmount();
		particles = new ArrayList<Particle>();
		grid = new Grid();
		distanceField = new DistanceField(grid);
	}

	// simulation
	public void update(double timeStep) {
		System.out.println("start update");
		applyExternalForce(timeStep);
		applyViscosity(timeStep);
		advanceParticles(timeStep);
		updateNeighbors();
		doubleDensityRelaxation(timeStep);
		resolveCollisions();
		updateVelocity(timeStep);
		
		System.out.println("end update");


		if (particelCoordinationCheck)
			checkCoordinats();
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

					Vector iVec = Vector.scalarMulti(vpn, temp); //

					p.setVel((Velocity) Vector.sub(p.getVel(), iVec)); // p.vel = p.vel - I
				}
			}
		}
	}

	public void advanceParticles(double timeStep) {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);

			p.setPosPrev(p.getPos());
			p.setPos(new Position(Vector.scalarMulti(p.getVel(), timeStep))); // p.pos = timeStep * p.vel
			grid.moveParticle(p); // not yet implemented
		}
	}

	public void updateNeighbors() {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);

			p.getNeighbors().clear();

			ArrayList<Particle> possibleNeigbour = grid.possibleNeigbors(p);

			for (int j = 0; j < possibleNeigbour.size(); j++) {
				Particle n = possibleNeigbour.get(j);

				if (Vector.sub(p.getPos(), n.getPos()).length() < radius) // |p.pos - n.pos| < radius
					p.getNeighbors().add(n);
			}
		}
	}

	public void doubleDensityRelaxation(double timeStep) {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			double pr = 0;
			double prNear = 0;

			double tempN = 0;
			double q;

			for (int j = 0; j < p.getNeighbors().size(); j++) {
				Particle n = p.getNeighbors().get(j);

				tempN = (Vector.sub(p.getPos(), n.getPos())).length();
				q = 1.0 - (tempN / radius);
				pr = pr + (q * q);
				prNear = prNear + (q * q * q);
				
				n.setTempN(tempN);
			}

			double P = k * (pr - p0);
			double PNear = kNear * prNear;

			Vector delta = new Vector(0, 0);
			Vector D = new Vector(0, 0);

			for (int j = 0; j < p.getNeighbors().size(); j++) {
				Particle n = p.getNeighbors().get(j);

				q = 1.0 - (tempN / radius);
				Vector vpn = Vector.scalarDiv((Vector.sub(p.getPos(), n.getPos())), tempN);
				n.setVpn(vpn);

				D = Vector.scalarMulti(vpn, (0.5 * (timeStep * timeStep) * (P * q + PNear * (q * q))));

				Vector temp = Vector.add(n.getPos(), D);
				n.setPos((Position) temp);

				delta = Vector.sub(delta, D);
			}

			p.getPos().add(D);
		}
	}

	// currently not working 
	public void resolveCollisions() {
		double friction = 1;
		double collisionSoftness = 0.4;
		
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			int index = distanceField.getIndex(p.getPos());

			if (index != -1) {
				double distance = distanceField.getDistance(index);

				if (distance > -collisionRadius) {
					Vector normal = distanceField.getNormal(index);
					Vector tangent = perpendicularCCW(normal);
					
					double temp = timeStep * friction * Vector.scalarProduct(p.getVpn(), tangent);
					
					tangent.scalarMulti(temp);
					
					p.setPos(new Position(Vector.sub(p.getPos(), tangent)));
					
					temp = collisionSoftness * (distance - radius);
					
					p.setPos(new Position(Position.sub(p.getPos(), (Position.scalarMulti(normal, temp)))));
				}
			}
		}
	}

	public void updateVelocity(double timeStep) {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);

			Vector temp = Vector.scalarDiv(Vector.sub(p.getPos(), p.getPosPrev()), timeStep);
			p.setVel(new Velocity(temp));
		}
	}

	private Vector perpendicularCCW(Vector normal) {
		return normal;
	}
	
	public void checkCoordinats() {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);

			if (p.getPos().getX() > 10000 || p.getPos().getX() < -100 || p.getPos().getY() > 10000
					|| p.getPos().getY() < -100) {
				removeParticel(p);
				consolLog("Particel " + p.getId() + " removed [particelCoordinationCheck] (" + p.getPos().getX() + "|"
						+ p.getPos().getY() + ")");
			}
		}
	}

	public void removeParticel(Particle p) {
		particles.remove(p);
		Particle.removeParticel();
	}

	public void addParticle(int x, int y) {
		addParticle(1, x, y);
	}

	public void addParticle(int amount, int x, int y) {
		int added = 0;

		for (int i = 0; i < amount; i++) {
			if (getCurrentParticelCount() + 1 <= maxParticles) {
				particles.add(new Particle(x, y));
				added++;
			}
		}

		consolLog(added + " Particles added at (" + x + "|" + y + ")");
	}

	// getter and setter

	public int getCurrentParticelCount() {
		return particles.size();
	}

	public int getMaxParticles() {
		return maxParticles;
	}

	public double getRadius() {
		return radius;
	}

	public void setTimeStep(double timeStep) {
		this.timeStep = timeStep;
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

	private void consolLog(String str) {
		System.out.println("ParticelManager: " + str);
	}

}
