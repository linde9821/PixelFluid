package pixelFluid.linde9821.com.simulation;

import java.util.ArrayList;

import pixelFluid.linde9821.com.simulation.distanceField.DistanceField;
import pixelFluid.linde9821.com.simulation.grid.Grid;
import pixelFluid.linde9821.com.simulation.particel.Particle;
import pixelFluid.linde9821.com.simulation.particel.Position;
import pixelFluid.linde9821.com.simulation.particel.Velocity;

public class ParticelManager {
	private SimulationSettings simulationSettings;

	private ArrayList<Particle> particles; // main list of of particles
	private Grid grid; //
	private DistanceField distanceField; //

	private final boolean particleCoordinationCheck = false;

	public ParticelManager() {
		simulationSettings = new SimulationSettings();
		Particle.resetCurrentAmount();
		particles = new ArrayList<Particle>();
		grid = new Grid();
		distanceField = new DistanceField();
	}

	// simulation
	public void update(double timeStep) {
		try {
			applyExternalForce(timeStep);
			applyViscosity(timeStep);
			advanceParticles(timeStep); // look out for rounding
			updateNeighbors();
			doubleDensityRelaxation(timeStep); // NaN Bug
			resolveCollisions();
			updateVelocity(timeStep);
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Exception in der Simulation aufgetreten.
			// Programm muss neugestartet werden");
			// System.exit(0);
		}

		if (particleCoordinationCheck)
			checkCoordinats();

	}

	public void applyExternalForce(double timeStep) {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);

			p.getVel().add(simulationSettings.getGravity());
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
					double q = length / simulationSettings.getRadius();

					double temp = 0.5 * timeStep * (1 - q) * (simulationSettings.getS() * velInward
							+ simulationSettings.getB() * velInward * velInward);

					Vector iVec = Vector.scalarMulti(vpn, temp); //

					p.setVel(new Velocity(Vector.sub(p.getVel(), iVec))); // p.vel = p.vel - I
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

				if (Vector.sub(p.getPos(), n.getPos()).length() < simulationSettings.getRadius()) // |p.pos - n.pos| <
																									// radius
					p.getNeighbors().add(n);
			}
		}
	}

	public void doubleDensityRelaxation(double timeStep) {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			double pr = 0;
			double prNear = 0;

			double tempN = 0; // darf niemals 0 werden
			double q;

			for (int j = 0; j < p.getNeighbors().size(); j++) {
				Particle n = p.getNeighbors().get(j);

				tempN = (Vector.sub(p.getPos(), n.getPos())).length();
				q = 1.0 - (tempN / simulationSettings.getRadius());
				pr = pr + (q * q);
				prNear = prNear + (q * q * q);

				n.setTempN(tempN);
			}

			double P = simulationSettings.getK() * (pr - simulationSettings.getP0());
			double PNear = simulationSettings.getkNear() * prNear;

			Vector delta = new Vector(0, 0);
			Vector D = new Vector(0, 0);

			for (int j = 0; j < p.getNeighbors().size(); j++) {
				Particle n = p.getNeighbors().get(j);

				q = 1.0 - (tempN / simulationSettings.getRadius());

				// unsure
				if (tempN == 0)
					tempN = 0.00001;

				Vector vpn = Vector.scalarDiv((Vector.sub(p.getPos(), n.getPos())), tempN);
				n.setVpn(vpn);

				D = Vector.scalarMulti(vpn, (0.5 * (timeStep * timeStep) * (P * q + PNear * (q * q))));

				Vector temp = Vector.add(n.getPos(), D);
				n.setPos(new Position(temp));

				delta = Vector.sub(delta, D);
			}

			// Error Starts beeing neogbour to itselvs
			p.getPos().add(D);
		}
	}

	public void resolveCollisions() {
		double collisionSoftness = 0.5;

		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			int index = distanceField.getIndex(p.getPos());

			if (index != -1) {
				double distance = distanceField.getDistance(index);

				if (distance > -simulationSettings.getCollisionRadius()) {
					Vector normal = distanceField.getNormal(index);
					Vector tangent = perpendicularCCW(normal);

					double temp = 0.0;

					// anpassung
					if (p.getVpn() != null)
						temp = simulationSettings.getTimeStep() * simulationSettings.getFriction()
								* Vector.scalarProduct(p.getVpn(), tangent);
					else
						temp = simulationSettings.getTimeStep() * simulationSettings.getFriction();

					tangent.scalarMulti(temp);

					p.setPos(new Position(Vector.sub(p.getPos(), tangent)));

					temp = collisionSoftness * (distance - simulationSettings.getRadius());

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

	// check
	private Vector perpendicularCCW(Vector v) {
		double x = v.getY();
		double y = -v.getX();
		return new Vector(x, y);
	}

	public void checkCoordinats() {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);

			if (p.getPos().getX() > 1200 + simulationSettings.getCollisionRadius()
					|| p.getPos().getX() < 0 - simulationSettings.getCollisionRadius()
					|| p.getPos().getY() > 800 + simulationSettings.getCollisionRadius()
					|| p.getPos().getY() < 0 - simulationSettings.getCollisionRadius()) {

				p.setPos(p.getPosPrev());

				/*
				 * removeParticel(p); consolLog("Particel " + p.getId() +
				 * " removed [particelCoordinationCheck] (" + p.getPos().getX() + "|" +
				 * p.getPos().getY() + ")");
				 */
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
			addParticle(1, x, y, 0, 0);
			added++;
		}

		consolLog(added + " Particles added at (" + x + "|" + y + ")");
	}

	public void addParticle(int amount, int x, int y, double xv, double yv) {
		int added = 0;

		for (int i = 0; i < amount; i++) {
			if (amount + 1 < simulationSettings.getMaxParticles()) {
				Particle p = new Particle(x, y);
				p.setVel(new Velocity(xv, yv));
				particles.add(p);
				added++;
			} else {
				continue;
			}
		}

		consolLog(added + " Particles added at (" + x + "|" + y + ")");
	}

	// getter and setter

	public int getCurrentParticelCount() {
		return particles.size();
	}

	public int getMaxParticles() {
		return simulationSettings.getMaxParticles();
	}

	public double getRadius() {
		return simulationSettings.getRadius();
	}

	public void setTimeStep(double timeStep) {
		simulationSettings.setTimeStep(timeStep);
	}

	public void setRadius(double radius) {
		simulationSettings.setRadius(radius);
	}

	public double getCollisionRadius() {
		return simulationSettings.getCollisionRadius();
	}

	public void setCollisionRadius(double collisionRadius) {
		simulationSettings.setCollisionRadius(collisionRadius);
	}

	public double getP0() {
		return simulationSettings.getP0();
	}

	public void setP0(double p0) {
		simulationSettings.setP0(p0);
	}

	public double getS() {
		return simulationSettings.getS();
	}

	public void setS(double s) {
		simulationSettings.setS(s);
	}

	public double getB() {
		return simulationSettings.getB();
	}

	public void setB(double b) {
		simulationSettings.setB(b);
	}

	public double getK() {
		return simulationSettings.getK();
	}

	public void setK(double k) {
		simulationSettings.setK(k);
	}

	public double getkNear() {
		return simulationSettings.getkNear();
	}

	public void setkNear(double kNear) {
		simulationSettings.setkNear(kNear);
	}

	public Gravity getGravity() {
		return simulationSettings.getGravity();
	}

	public void setGravity(Gravity gravity) {
		simulationSettings.setGravity(gravity);
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
		return simulationSettings.getTimeStep();
	}

	public double getFriction() {
		return simulationSettings.getFriction();
	}

	public void setFriction(double friction) {
		simulationSettings.setFriction(friction);
	}

	private void consolLog(String str) {
		System.out.println("ParticelManager: " + str);
	}

}
