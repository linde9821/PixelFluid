package pixelFluid.linde9821.com.testEnviorment;

import java.awt.EventQueue;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pixelFluid.linde9821.com.simulation.ParticelManager;
import pixelFluid.linde9821.com.testEnviorment.panels.FluidPanel;
import pixelFluid.linde9821.com.testEnviorment.panels.GravityPanel;
import pixelFluid.linde9821.com.testEnviorment.panels.SimSettingsPanel;

public class VirtualFrame extends JFrame {

    /**
     * Frame
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField gravityTf;
    private JLabel lblXvel;
    private JLabel lblYvel;
    private JTextField tFXvel;
    private JTextField tFYvel;
    private JCheckBox chckbxGravity;
    private JTextField tFSpawnAmount;
    private GravityPanel gravityPanel;
    private SimulationButton startStopButton;

    private boolean mouseDown = false;
    private boolean isRunning = false;

    private MouseEvent meP;

    /**
     * Simulation
     */
    private ParticelManager pm;
    private Thread simThread;
    private FluidPanel fluidPanel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                VirtualFrame frame = new VirtualFrame();

                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler aufgetreten, Programm muss neugestartet werden.");
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public VirtualFrame() {
        pm = new ParticelManager();

        // frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1800, 850);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        setResizable(false);

        fluidPanel = new FluidPanel(pm);
        fluidPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Integer.parseInt(tFSpawnAmount.getText()) == 1)
                    fluidPanel.addParticles(Integer.parseInt(tFSpawnAmount.getText()), e.getX(), e.getY());
                else {
                    fluidPanel.addParticles(Integer.parseInt(tFSpawnAmount.getText()), e.getX(), e.getY(), 1, 0);
                }

                fluidPanel.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = true;
                    meP = e;
                    initThread();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = false;
                    meP = null;
                }
            }
        });
        fluidPanel.setLocation(0, 0);
        fluidPanel.setSize(1200, 800);
        fluidPanel.setVisible(true);
        contentPane.setLayout(null);
        contentPane.add(fluidPanel);

        simThread = new Thread(fluidPanel, "Simulation-Thread");

        gravityPanel = new GravityPanel(pm.getGravity());
        gravityPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mE) {
                gravityPanel.setVec(mE.getX() - 100, mE.getY() - 100);
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
                chckbxGravity.setSelected(true);
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
                gravityPanel.setVec(Double.parseDouble(tFXvel.getText()), Double.parseDouble(tFYvel.getText()));
                gravityPanel.repaint();
                gravityTf.setText(Double.toString(gravityPanel.getLength()));
            }
        });
        tFXvel.setBounds(1320, 338, 150, 20);
        contentPane.add(tFXvel);
        tFXvel.setText(Double.toString(gravityPanel.getXV()));
        tFXvel.setColumns(10);

        tFYvel = new JTextField();
        tFYvel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gravityPanel.setVec(Double.parseDouble(tFXvel.getText()), Double.parseDouble(tFYvel.getText()));
                gravityPanel.repaint();
                gravityTf.setText(Double.toString(gravityPanel.getLength()));
            }
        });
        tFYvel.setText(Double.toString(gravityPanel.getYV()));
        tFYvel.setBounds(1320, 388, 150, 20);
        contentPane.add(tFYvel);
        tFYvel.setColumns(10);

        chckbxGravity = new JCheckBox("Gravity");
        chckbxGravity.setSelected(true);
        chckbxGravity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chckbxGravity.isSelected()) {
                    tFXvel.setText("0.0");
                    tFYvel.setText("1.0");
                    gravityPanel.setVec(0, 10);
                    gravityPanel.repaint();
                    gravityTf.setText(Double.toString(gravityPanel.getLength()));
                } else {
                    tFXvel.setText("0.0");
                    tFYvel.setText("0.0");
                    gravityPanel.setVec(0, 0);
                    gravityPanel.repaint();
                    gravityTf.setText(Double.toString(gravityPanel.getLength()));
                }
            }
        });
        chckbxGravity.setBounds(1222, 17, 179, 20);
        contentPane.add(chckbxGravity);

        JLabel lblSpawnamount = new JLabel("spawnamount:");
        lblSpawnamount.setBounds(1222, 435, 91, 14);
        contentPane.add(lblSpawnamount);

        tFSpawnAmount = new JTextField();
        tFSpawnAmount.setText("1");
        tFSpawnAmount.setBounds(1320, 431, 80, 20);
        contentPane.add(tFSpawnAmount);
        tFSpawnAmount.setColumns(10);

        // simulation

        startStopButton = new SimulationButton();
        startStopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (startStopButton.isRunning())
                    simThread.interrupt();
                else
                    simThread.start();

                startStopButton.setRunning(!startStopButton.isRunning());

                fluidPanel.repaint();
            }
        });
        startStopButton.setText("Start");
        startStopButton.setBounds(1320, 461, 87, 20);
        contentPane.add(startStopButton);

        JButton btnLoad = new JButton("Load");
        btnLoad.setBounds(1320, 15, 97, 25);
        contentPane.add(btnLoad);

        JButton btnReset = new JButton("reset");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pm.getParticles().clear();
            }
        });
        btnReset.setBounds(1318, 492, 89, 23);
        contentPane.add(btnReset);

        JTextField fps = new JTextField();
        fps.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fluidPanel.setFps(Integer.parseInt(fps.getText()));
            }
        });
        fps.setText("60");
        fps.setBounds(1320, 550, 150, 20);
        contentPane.add(fps);
        fps.setColumns(10);

        SimSettingsPanel simSettingsPanel = new SimSettingsPanel(pm);
        simSettingsPanel.setLocation(1500, 110);
        simSettingsPanel.setSize(500, 500);
        simSettingsPanel.setVisible(true);
        contentPane.add(simSettingsPanel);
		/*

		for (int i = 799; i > 700; i = i - 2) {
			for (int j = 0; j < 1200; j = j + 2) {
				fluidPanel.addParticles(1, j, i);
			}
		}

		 * 
		 * for (int i = 0; i < 8; i++) { for (int j = 0; j < 12; j++) {
		 * 
		 * for (int k = 0; k < 10; k++) { for (int l = 0; l < 10; l++) {
		 * fluidPanel.addParticles(1, (j + 1) * 100 + l, (i + 1) * 100 + k); } }
		 * 
		 * } }
		 */
    }

    private synchronized boolean checkAndMark() {
        if (isRunning)
            return false;
        isRunning = true;
        return true;
    }

    private void initThread() {
        if (checkAndMark()) {
            new Thread() {
                public void run() {
                    do {
                        Point p = MouseInfo.getPointerInfo().getLocation();

                        pm.addParticle(Integer.parseInt(tFSpawnAmount.getText()), (int) p.getX() - 100, (int) p.getY() - 100, 100, -40);

                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } while (mouseDown);
                    isRunning = false;
                }
            }.start();
        }
    }
}