import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

class startWindow extends JFrame {

	private JLabel labelScoreballs = new JLabel("Enter scoreballs count: ");
	private JLabel labelSpeed = new JLabel("Enter Max Speed: ");
	private JLabel labelMapW = new JLabel("Please enter your costum map width: ");
	private JLabel labelMapH = new JLabel("Please enter your costum map height: ");
	private JTextField textScoreballs = new JTextField(20);
	private JTextField textSpeed = new JTextField(20);
	private JTextField textMapW = new JTextField(20);
	private JTextField textMapH = new JTextField(20);

	private JButton picKButton = new JButton("Pic Keyboard");
	private JButton picMButton = new JButton("Pic Mouse");
	private JButton playButton = new JButton("Play!");
	private App app;

	private Image iconK;
	private Image iconM;
	
	public startWindow(App app) {
		this.app = app;

		this.setTitle("Start Game");
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		JPanel newPanel = new JPanel(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		// add components to the panel
		constraints.gridx = 0;
		constraints.gridy = 0;
		newPanel.add(labelScoreballs, constraints);

		constraints.gridx = 1;
		newPanel.add(textScoreballs, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		newPanel.add(labelSpeed, constraints);

		constraints.gridx = 1;
		newPanel.add(textSpeed, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		newPanel.add(labelMapW, constraints);

		constraints.gridx = 1;
		newPanel.add(textMapW, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		newPanel.add(labelMapH, constraints);

		constraints.gridx = 1;
		newPanel.add(textMapH, constraints);

		constraints.gridx = 0;
		constraints.gridy = 4;
		newPanel.add(picKButton, constraints);

		constraints.gridx = 1;
		newPanel.add(picMButton, constraints);

		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		newPanel.add(playButton, constraints);

		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textMapW.getText().equals(""))
					textMapW.setText("1000");
				if (textMapH.getText().equals(""))
					textMapH.setText("500");
				if (textScoreballs.getText().equals(""))
					textScoreballs.setText("0");
				if (textSpeed.getText().equals(""))
					textSpeed.setText("400");
				int scoreballs = Integer.parseInt(textScoreballs.getText());
				int mapw = Integer.parseInt(textMapW.getText());
				int maph = Integer.parseInt(textMapH.getText());
				int speed = Integer.parseInt(textSpeed.getText());
				if (mapw < 1000 || mapw > 4000)
					mapw = 2000;
				if (maph < 500 || maph > 2000)
					maph = 1000;
				if (scoreballs == 0)
					scoreballs = (int) ((maph * mapw) / 40000);

				app.controller = new Controller(app, mapw, maph, speed, scoreballs,iconK,iconM);
			}
		});

		picMButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("/Users/ho3in/Desktop/Avatar"));
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println(selectedFile.getAbsolutePath());
					iconM=new ImageIcon(selectedFile.getAbsolutePath()).getImage();
				}
			}
		});
		
		picKButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("/Users/ho3in/Desktop/Avatar"));
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println(selectedFile.getName());
					iconK=new ImageIcon(selectedFile.getAbsolutePath()).getImage();
				}
			}
		});

		// set border for the panel
		newPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Game Config"));

		// add the panel to this frame
		add(newPanel);

		pack();
		setLocationRelativeTo(null);

		this.setVisible(true);
	}
}