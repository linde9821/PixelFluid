package pixelFluid.linde9821.com.testEnviorment;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class SimulationButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isRunning;

	public SimulationButton() {
		super();
		isRunning = false;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (isRunning) {
			setBackground(Color.green);
			setText("Stop");
		} else {
			setBackground(Color.red);
			setText("Start");
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
