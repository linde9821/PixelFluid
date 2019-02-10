package pixelFluid.linde9821.com.testEnviorment.Panels;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class FluidPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean debug;
	public FrameCounter frameCounter;

	public FluidPanel() {
		super();
		this.setBackground(Color.black);
		debug = true;
		frameCounter = new FrameCounter();
		frameCounter.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		frameCounter.increaseCounter();

		if (debug)
			drawDebugInfos(g);
	}

	private void drawDebugInfos(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Size: " + this.getWidth() + " x " + this.getHeight(), 10, 20);
		g.drawString("Framerate: " + frameCounter.getCurrentFramerate() + " " + frameCounter.getStatus(), 10, 30);
	}
}
