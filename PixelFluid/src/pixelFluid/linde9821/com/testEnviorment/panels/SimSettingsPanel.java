package pixelFluid.linde9821.com.testEnviorment.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pixelFluid.linde9821.com.simulation.ParticelManager;

public class SimSettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * private double radius; // maximum distance particles effect each other
	 * private double collisionRadius; // the distance from a wall that counts as a
	 * collision private double p0; // rest density private double s; // the
	 * viscosit's linear dependence on the velocity (sigma) private double b; // the
	 * viscosit's quadratic dependence on the velocity (beta) private double k; //
	 * stiffness used in DoubleDensityRelaxation private double kNear; //
	 * near-stiffness used in DoubleDensityRelaxation
	 */

	private JLabel radiusLabel;
	private JLabel collisionRadiusLabel;
	private JLabel p0Label;
	private JLabel sLabel;
	private JLabel bLabel;
	private JLabel kLabel;
	private JLabel kNearLabel;
	
	private JTextField radiusTf;
	private JTextField collisionRadiusTf;
	private JTextField p0Tf;
	private JTextField sTf;
	private JTextField bTf;
	private JTextField kTf;
	private JTextField kNearTf;

	private ParticelManager pm;
	
	
	public SimSettingsPanel(ParticelManager pm) {
		super();
		setLayout(null);
		this.pm = pm;

		radiusLabel = new JLabel("Radius (distance particles effect each other): ");
		radiusLabel.setBounds(0, 0, 320, 26);
		add(radiusLabel);
		radiusTf = new JTextField(Double.toString(pm.getRadius()));
		radiusTf.setBounds(320, 0, 40, 26);
		radiusTf.setColumns(10);
		radiusTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pm.setRadius(Double.parseDouble(radiusTf.getText()));
			}
		});
		add(radiusTf);
		
		collisionRadiusLabel = new JLabel("Collision radius (d. from wall that counts as a colli.):");
		collisionRadiusLabel.setBounds(0, 30, 320, 26);
		add(collisionRadiusLabel);
		collisionRadiusTf = new JTextField(Double.toString(pm.getCollisionRadius()));
		collisionRadiusTf.setBounds(320, 30, 40, 26);
		collisionRadiusTf.setColumns(10);
		collisionRadiusTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pm.setCollisionRadius(Double.parseDouble(collisionRadiusTf.getText()));
			}
		});
		add(collisionRadiusTf);
		
		p0Label = new JLabel("rest density: ");
		p0Label.setBounds(1, 60, 320, 26);
		add(p0Label);
		p0Tf = new JTextField(Double.toString(pm.getP0()));
		p0Tf.setBounds(320, 60, 40, 26);
		p0Tf.setColumns(10);
		p0Tf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pm.setP0(Double.parseDouble(p0Tf.getText()));
			}
		});
		add(p0Tf);
		
		
		sLabel = new JLabel("sigma (viscosit's linear dependence on the velocity):");
		sLabel.setBounds(1, 90, 320, 26);
		add(sLabel);
		sTf = new JTextField(Double.toString(pm.getS()));
		sTf.setBounds(320, 90, 40, 26);
		sTf.setColumns(10);
		sTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pm.setS(Double.parseDouble(sTf.getText()));
			}
		});
		add(sTf);
		
		
		bLabel = new JLabel("beta (viscosit's quadratic dependence on the velocity):");
		bLabel.setBounds(0, 120, 320, 26);
		add(bLabel);
		bTf = new JTextField(Double.toString(pm.getB()));
		bTf.setBounds(320, 120, 40, 26);
		bTf.setColumns(10);
		bTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pm.setB(Double.parseDouble(bTf.getText()));
			}
		});
		add(bTf);
		
		kLabel = new JLabel("stiffness: ");
		kLabel.setBounds(0, 150, 320, 26);
		add(kLabel);
		kTf = new JTextField(Double.toString(pm.getK()));
		kTf.setBounds(320, 150, 40, 26);
		kTf.setColumns(10);
		kTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pm.setK(Double.parseDouble(kTf.getText()));
			}
		});
		add(kTf);
		
		kNearLabel = new JLabel("near stiffness:");
		kNearLabel.setBounds(0, 180, 320, 26);
		add(kNearLabel);
		kNearTf = new JTextField(Double.toString(pm.getkNear()));
		kNearTf.setBounds(320, 180, 40, 26);
		kNearTf.setColumns(10);
		kNearTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pm.setkNear(Double.parseDouble(kNearTf.getText()));
			}
		});
		add(kNearTf);
	}

	public JLabel getRadiusLabel() {
		return radiusLabel;
	}

	public JLabel getCollisionRadiusLabel() {
		return collisionRadiusLabel;
	}

	public JLabel getP0Label() {
		return p0Label;
	}

	public JLabel getsLabel() {
		return sLabel;
	}

	public JLabel getbLabel() {
		return bLabel;
	}

	public JLabel getkLabel() {
		return kLabel;
	}

	public JLabel getkNearLabel() {
		return kNearLabel;
	}
	
	

}
