package pixelFluid.linde9821.com.testEnviorment.panels;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import pixelFluid.linde9821.com.simulation.Gravity;

public class GravityPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gravity gr;

	public GravityPanel(Gravity g) {
		super();
		gr = g;

		setBackground(Color.green);
	}

	public double getLength() {
		return Math.sqrt((gr.getX() * gr.getX()) + (gr.getY() * gr.getY()));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// draw cordsys
		g.drawLine(5, 105, 205, 105);
		g.drawLine(105, 5, 105, 205);
		g.drawString("x", 200, 115);
		g.drawString("y", 95, 10);

		g.drawString("0", 110, 95);
		g.drawString("50", 155, 95);
		g.drawString("-50", 55, 95);
		g.drawString("100", 185, 95);
		g.drawString("-100", 5, 95);

		g.drawString("50", 110, 55);
		g.drawString("-50", 110, 155);
		g.drawString("100", 110, 10);
		g.drawString("-100", 110, 205);

		// draw vec
		g.setColor(Color.red);
		g.drawLine(105, 105, (int) gr.getX() + 105, (int) gr.getY() + 105);
		g.fillRect((int) gr.getX() + 103, (int) gr.getY() + 103, 5, 5);
	}

	public void setVec(double x, double y) {
		gr.setX(x);
		gr.setY(y);
	}

	public void calcVal(double length) {
		if (gr.getX() == 0 && gr.getY() == 0) {
			gr.setY(length);
		} else {
			double p = length / getLength();
			gr.setX(gr.getX() * p);
			gr.setY(gr.getY() * p);
		}
	}

	public double getXV() {
		return gr.getX();
	}

	public double getYV() {
		return gr.getY();
	}
}
