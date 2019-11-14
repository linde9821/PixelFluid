package pixelFluid.linde9821.com.testEnviorment.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

import pixelFluid.linde9821.com.simulation.ParticelManager;
import pixelFluid.linde9821.com.simulation.Vector;
import pixelFluid.linde9821.com.simulation.particel.Particle;
import pixelFluid.linde9821.com.testEnviorment.Buffer;

public class FluidPanel extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean renderParticel = true;

	private boolean debug = true;
	private FrameCounter frameCounter;
	private int updates;
	private int fps;

	ParticelManager pm;

	public FluidPanel(ParticelManager pm) {
		super();
		this.setBackground(Color.black);
		frameCounter = new FrameCounter();
		frameCounter.start();
		this.pm = pm;
		fps = 60;
	}

	public void addParticles(int amount, int x, int y) {
		pm.addParticle(amount, x, y);
	}

	public void addParticles(int amount, int x, int y, int xv, int yv) {
		pm.addParticle(amount, x, y, xv, yv);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		frameCounter.increaseCounter();

		if (debug)
			drawDebugInfos(g);

		if (renderParticel)
			drawParticel(g);

		// drawVector(g);
	}

	private void drawParticel(Graphics g) {
		g.setColor(Color.blue);

		
		for (Particle p : pm.getParticles()) {
			g.fillArc((int) p.getPos().getX(), (int) p.getPos().getY(), 4, 4, 0, 360);
		}
	}

	@SuppressWarnings("unused")
	private void drawVector(Graphics g) {
		g.setColor(Color.red);

		int index = 0;

		for (int i = 0; i < 800; i++) {
			for (int j = 0; j < 1200; j++) {

				Vector v = pm.getDistanceField().getNormal(index);

				if (v.length() > 0)
					g.drawLine(j, i, j + (int) v.getX() * 10, i + (int) v.getY() * 10);
				// g.fillRect((int) gr.getX() + 103, (int) gr.getY() + 103, 5, 5);
				index++;
			}
		}

	}

	private void drawDebugInfos(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Size: " + this.getWidth() + " x " + this.getHeight(), 10, 20);
		g.drawString("Framerate: " + frameCounter.getCurrentFramerate() + " " + frameCounter.getStatus(), 10, 30);
		g.drawString("Simulation: " + updates + " / 60", 10, 40);
		g.drawString("Particelcount: " + pm.getCurrentParticelCount() + " / Max: " + pm.getMaxParticles(), 10, 50);
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	@Override
	public void run() {
		long lastRun = System.currentTimeMillis();
		long timer = System.currentTimeMillis();

		int internalUpdates = 0;

		while (true) {
			if (System.currentTimeMillis() - lastRun >= 1000 / fps) {
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
