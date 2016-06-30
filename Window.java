import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JWindow;

public class Window extends JFrame {

	public Window(Controller controller) {
		this.controller = controller;
		this.setTitle("ZZagar");
		this.setSize(1440, 825);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		content = this.getContentPane();
		this.setFocusable(true);

		// create a menu bar
		this.setJMenuBar(menubar);

		// create menus
		JMenu filemenu = new JMenu("File");
		JMenu editmenu = new JMenu("Preferences");
		JMenu windowmenu = new JMenu("Window");
		JMenu helpmenu = new JMenu("Help");

		// create menu items
		JMenuItem newitem = new JMenuItem("New Game");
		newitem.setActionCommand("New");

		JMenuItem openitem = new JMenuItem("Open");
		openitem.setActionCommand("Open");

		JMenuItem closeitem = new JMenuItem("Close");
		closeitem.setActionCommand("Close");

		JMenuItem saveitem = new JMenuItem("Save");
		saveitem.setActionCommand("Save");

		JMenuItem saveasitem = new JMenuItem("Save as...");
		saveasitem.setActionCommand("Save as");

		JMenuItem printitem = new JMenuItem("Print");
		printitem.setActionCommand("Print");

		JMenuItem speeditem = new JMenuItem("Max Speed");
		speeditem.setActionCommand("Speed");

		JMenuItem countitem = new JMenuItem("Score Balls Count");
		countitem.setActionCommand("Count");

		JMenuItem mapitem = new JMenuItem("Map Size");
		mapitem.setActionCommand("Map");

		JMenuItem gearitem = new JMenuItem("Gear Size");
		gearitem.setActionCommand("Gear");

		JMenuItem helpitem = new JMenuItem("Help");
		helpitem.setActionCommand("Help");

		JMenuItem aboutitem = new JMenuItem("About");
		aboutitem.setActionCommand("About");

		MenuItemListener menuItemListener = new MenuItemListener();

		newitem.addActionListener(menuItemListener);
		openitem.addActionListener(menuItemListener);
		saveitem.addActionListener(menuItemListener);
		closeitem.addActionListener(menuItemListener);
		saveitem.addActionListener(menuItemListener);
		saveasitem.addActionListener(menuItemListener);
		printitem.addActionListener(menuItemListener);
		speeditem.addActionListener(menuItemListener);
		countitem.addActionListener(menuItemListener);
		mapitem.addActionListener(menuItemListener);
		gearitem.addActionListener(menuItemListener);
		helpitem.addActionListener(menuItemListener);
		aboutitem.addActionListener(menuItemListener);

		// add menu items to menus
		filemenu.add(newitem);
		filemenu.add(openitem);
		filemenu.add(closeitem);
		filemenu.addSeparator();
		filemenu.add(saveitem);
		filemenu.add(saveasitem);
		filemenu.addSeparator();
		filemenu.add(printitem);
		editmenu.add(speeditem);
		editmenu.add(countitem);
		editmenu.add(mapitem);
		editmenu.add(gearitem);
		helpmenu.add(helpitem);
		helpmenu.add(aboutitem);

		// add menu to menubar
		menubar.add(filemenu);
		menubar.add(editmenu);
		menubar.add(windowmenu);
		menubar.add(helpmenu);

		// Fields & map
		field = new Field(controller);
		GridLayout layout = new GridLayout(0, 2);
		layout.setHgap(10);
		layout.setVgap(10);
		content.setLayout(layout);

		fieldm = new FieldM(field);
		content.add(fieldm);
		fieldm.requestFocus();

		fieldk = new FieldK(field);
		content.add(fieldk);
		fieldm.requestFocus();

		jmap = new JWindow();
		jmap.setSize(400, (int) ((400.0 / (double) controller.getMapWidth()) * controller.getMapHeight()));
		map = new Map(field);
		jmap.setLocation(520, 840-(int) ((400.0 / (double) controller.getMapWidth()) * controller.getMapHeight()));
		jmap.setLayout(new GridLayout(0, 1));
		jmap.setFocusable(false);
		jmap.setAlwaysOnTop (true);
		jmap.add(map);

		// key listeners
		kcontrol = new KeyListener() {

			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {
				switch (e.getKeyChar()) {
				case 'w':
					//System.out.println("Here");
					controller.getPlayerK().stopMoveU();
					break;
				case 's':
					controller.getPlayerK().stopMoveD();
					break;
				case 'a':
					controller.getPlayerK().stopMoveL();
					break;
				case 'd':
					controller.getPlayerK().stopMoveR();
					break;
				case 'f':
					controller.getPlayerK().divide();
					break;
				case 'm':
					controller.getPlayerM().divide();
					break;
				}
			}

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case 'w':
					controller.getPlayerK().moveU();
					break;
				case 's':
					controller.getPlayerK().moveD();
					break;
				case 'a':
					controller.getPlayerK().moveL();
					break;
				case 'd':
					controller.getPlayerK().moveR();
					break;
				}
			}
		};
		this.addKeyListener(kcontrol);

		this.setVisible(true);

		// play sound
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("chill.wav"));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.out.println("play sound error: " + e.getMessage() + " for ");
				}
			}
		}).start();


	}

	public Field getField() {
		return field;
	}

	public FieldM getFieldM() {
		return fieldm;
	}

	public FieldK getFieldK() {
		return fieldk;
	}

	class MenuItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Speed":
				String speed = JOptionPane.showInputDialog("Please enter your costum Max Speed: ");
				controller.setMaxSpeed(Integer.parseInt(speed));
				break;
			case "Map":
				String map = JOptionPane.showInputDialog(
						"Please enter your costum map size \n(two integers with a space between them, 2000<width,height<10000): ");
				int i = 0, w = 0, h = 0;
				while (map.charAt(i) != ' ') {
					w = w * 10 + map.charAt(i) - '0';
					i++;
				}
				i++;
				while (i < map.length()) {
					h = h * 10 + map.charAt(i) - '0';
					i++;
				}
				controller.setMapWidth(w);
				controller.setMapHeight(h);
			case "Close":
				;
			case "New":
//				controller.getApp().getController()=new Controller(controller.getApp());
//				controller.reBuild();
			}
		}
	}

	public JWindow jmap;
	private Map map;
	public KeyListener kcontrol;
	private Controller controller;
	private Container content;
	private Field field;
	private FieldM fieldm;
	private FieldK fieldk;
	private JMenuBar menubar = new JMenuBar();
}
