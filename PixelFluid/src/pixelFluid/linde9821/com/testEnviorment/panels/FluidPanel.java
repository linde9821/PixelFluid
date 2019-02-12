package pixelFluid.linde9821.com.testEnviorment.panels;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import pixelFluid.linde9821.com.simulation.ParticelManager;
import pixelFluid.linde9821.com.simulation.particel.Particle;

public class FluidPanel extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean renderParticel = true;

	private boolean debug = true;
	private FrameCounter frameCounter;
	private int updates;

	ParticelManager pm;

	public FluidPanel(ParticelManager pm) {
		super();
		this.setBackground(Color.black);
		frameCounter = new FrameCounter();
		frameCounter.start();
		this.pm = pm;
	}

	public void addParticles(int amount, int x, int y) {
		
			pm.addParticle(amount, x, y);
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		frameCounter.increaseCounter();

		if (debug)
			drawDebugInfos(g);

		if (renderParticel)
			drawParticel(g);
	}

	private void drawParticel(Graphics g) {
		g.setColor(Color.red);

		for (Particle p : pm.getParticles()) {
			g.drawRect((int) p.getPos().getX(), (int) p.getPos().getY(), 2, 2);
		}
	}

	private void drawDebugInfos(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Size: " + this.getWidth() + " x " + this.getHeight(), 10, 20);
		g.drawString("Framerate: " + frameCounter.getCurrentFramerate() + " " + frameCounter.getStatus(), 10, 30);
		g.drawString("Simulation: " + updates + " / 60", 10, 40);
		g.drawString("Particelcount: " + pm.getCurrentParticelCount() + " / Max: " + pm.getMaxParticles(), 10, 50);
	}

	@Override
	public void run() {
		long lastRun = System.currentTimeMillis();
		long timer = System.currentTimeMillis();

		int internalUpdates = 0;

		while (true) {
			if (System.currentTimeMillis() - lastRun >= pm.getTimeStep()) {
				pm.update(pm.getTimeStep());
				internalUpdates++;
				repaint();
				lastRun = System.currentTimeMillis();
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				updates = internalUpdates;
				internalUpdates = 0;
				timer = System.currentTimeMillis();
			}
		}
	}
}
