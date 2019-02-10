package pixelFluid.linde9821.com.testEnviorment.Panels;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GravityPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x, y;

	public GravityPanel(int x, int y) {
		super();
		this.x = x;
		this.y = y;

		setBackground(Color.green);
	}

	public double getLength() {
		return Math.sqrt((x * x) + (y * y));
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
		g.drawLine(105, 105, x + 105, y + 105);
	}

	public void setVec(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void calcVal(double length) {
		double p = length / getLength();
		x *= p;
		y *= p;
	}

	public int getXV() {
		return x;
	}

	public int getYV() {
		return y;
	}
}
