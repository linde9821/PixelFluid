package pixelFluid.linde9821.com.testEnviorment;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pixelFluid.linde9821.com.testEnviorment.Panels.FluidPanel;
import pixelFluid.linde9821.com.testEnviorment.Panels.GravityPanel;

public class VirtualFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField gravityTf;
	private JLabel lblXvel;
	private JLabel lblYvel;
	private JTextField tFXvel;
	private JTextField tFYvel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VirtualFrame frame = new VirtualFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VirtualFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 851);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		FluidPanel fluidPanel = new FluidPanel();
		fluidPanel.setLocation(0, 0);
		fluidPanel.setSize(1200, 800);
		fluidPanel.setVisible(true);
		contentPane.setLayout(null);
		contentPane.add(fluidPanel);

		JCheckBox chckbxGravity = new JCheckBox("Gravity");
		chckbxGravity.setSelected(true);
		chckbxGravity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		chckbxGravity.setBounds(1222, 17, 179, 35);
		contentPane.add(chckbxGravity);

		GravityPanel gravityPanel = new GravityPanel(0, 10);
		gravityPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mE) {
				gravityPanel.setVec((int) (mE.getX() - 100), (int) (mE.getY() - 100));
				gravityPanel.repaint();
				gravityTf.setText(Double.toString(gravityPanel.getLength()));
				tFXvel.setText(Double.toString(gravityPanel.getXV()));
				tFYvel.setText(Double.toString(gravityPanel.getYV()));
			}
		});
		gravityPanel.setLocation(1222, 110);
		gravityPanel.setSize(210, 210);
		gravityPanel.setVisible(true);
		contentPane.add(gravityPanel);

		gravityTf = new JTextField();
		gravityTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aE) {
				gravityPanel.calcVal(Double.parseDouble(gravityTf.getText()));
				gravityPanel.repaint();
				tFXvel.setText(Double.toString(gravityPanel.getXV()));
				tFYvel.setText(Double.toString(gravityPanel.getYV()));
				gravityTf.setText(Double.toString(gravityPanel.getLength()));
			}
		});
		gravityTf.setBounds(1221, 69, 186, 32);
		contentPane.add(gravityTf);
		gravityTf.setColumns(10);
		gravityTf.setText(Double.toString(gravityPanel.getLength()));

		lblXvel = new JLabel("x-Vel: ");
		lblXvel.setBounds(1221, 341, 92, 26);
		contentPane.add(lblXvel);

		lblYvel = new JLabel("y-Vel: ");
		lblYvel.setBounds(1221, 381, 92, 26);
		contentPane.add(lblYvel);

		tFXvel = new JTextField();
		tFXvel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gravityPanel.setVec((int) Double.parseDouble(tFXvel.getText()), (int) Double.parseDouble(tFYvel.getText()));
				gravityPanel.repaint();
				gravityTf.setText(Double.toString(gravityPanel.getLength()));
			}
		});
		tFXvel.setBounds(1287, 341, 92, 32);
		contentPane.add(tFXvel);
		tFXvel.setText(Double.toString(gravityPanel.getXV()));
		tFXvel.setColumns(10);

		tFYvel = new JTextField();
		tFYvel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gravityPanel.setVec((int) Double.parseDouble(tFXvel.getText()), (int) Double.parseDouble(tFYvel.getText()));
				gravityPanel.repaint();
				gravityTf.setText(Double.toString(gravityPanel.getLength()));
			}
		});
		tFYvel.setText(Double.toString(gravityPanel.getYV()));
		tFYvel.setBounds(1287, 381, 92, 32);
		contentPane.add(tFYvel);
		tFYvel.setColumns(10);
	}
}
