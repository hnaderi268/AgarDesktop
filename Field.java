import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Field {

	public Field(Controller controller) {
		this.controller = controller;
		scoreballs = new ArrayList();
		mballs = new ArrayList();
		kballs = new ArrayList();
		makeScoreBalls();
		hitTest();
	}

	public void checkLose() {
		checkLose = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mballs.isEmpty()) {
					WLState = 1;
					controller.getWindow().jmap.setVisible(false);
				} else if (kballs.isEmpty()) {
					WLState = 2;
					controller.getWindow().jmap.setVisible(false);
				}
			}
		});
		checkLose.start();
	}

	private void makeScoreBalls() {
		for (int i = 0; i < controller.getScoreBallsCount(); i++) {
			Ball B = new Ball(Math.random() * controller.getMapWidth(), Math.random() * controller.getMapHeight(),
					10 + Math.random() * 25);
			scoreballs.add(B);
		}
		speed = new Ball(Math.random() * controller.getMapWidth(), Math.random() * controller.getMapHeight(),
				10 + Math.random() * 15, "S");
		god = new Ball(Math.random() * controller.getMapWidth(), Math.random() * controller.getMapHeight(),
				10 + Math.random() * 15, "G");
		gear = new Ball(Math.random() * controller.getMapWidth(), Math.random() * controller.getMapHeight(),
				20 + Math.random() * 45, "Gear");
		scoreballs.add(speed);
		scoreballs.add(god);
		scoreballs.add(gear);
	}

	public synchronized void hitTest() {
		hitTest = new Timer(70, new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				ArrayList<Ball> temp = (ArrayList<Ball>) scoreballs.clone();
				ArrayList<Ball> mtemp = (ArrayList<Ball>) mballs.clone();
				ArrayList<Ball> ktemp = (ArrayList<Ball>) kballs.clone();
				for (Ball sball : temp)
					if (mballs != null)
						for (Ball mball : mtemp)
							if (sball.hit(mball)) {
								if (sball == gear){
									controller.getPlayerM().divide();
								}
								else if (sball == speed) {
									new Thread(new Runnable() {
										public void run() {
											try {
												scoreballs.remove(sball);
												controller.getPlayerM().speedPower = true;
												long past = controller.time;
												while (controller.time - past < 100) {
													System.err.println(controller.time + " " + past);
												}
												controller.getPlayerM().speedPower = false;
												speed = new Ball(Math.random() * controller.getMapWidth(),
														Math.random() * controller.getMapHeight(),
														10 + Math.random() * 15, "S");
												scoreballs.add(speed);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}).start();
								} else if (sball == god) {
									System.out.println("god");
									new Thread(new Runnable() {
										public void run() {
											try {
												scoreballs.remove(sball);
												controller.getPlayerM().godPower = true;
												long past = controller.time;
												while (controller.time - past < 100) {
													System.err.println(controller.time + " " + past);
												}
												controller.getPlayerM().godPower = false;
												god = new Ball(Math.random() * controller.getMapWidth(),
														Math.random() * controller.getMapHeight(),
														10 + Math.random() * 15, "G");
												scoreballs.add(god);
												System.out.println("HI world! this is GOD");
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}).start();
								} else if (sball.getRadius() < mball.getRadius()) {
									mball.setRadius(Math.sqrt((Math.PI * mball.getRadius() * mball.getRadius()
											+ Math.PI * sball.getRadius() * sball.getRadius()) / Math.PI));
									scoreballs.remove(sball);
									Ball B = new Ball(Math.random() * controller.getMapWidth(),
											Math.random() * controller.getMapHeight(), 10 + Math.random() * 25);
									scoreballs.add(B);
								}
							}
				for (Ball sball : temp)
					if (kballs != null)
						for (Ball kball : kballs)
							if (sball.hit(kball))
								if (sball.getRadius() < kball.getRadius()) {
									kball.setRadius(Math.sqrt((Math.PI * kball.getRadius() * kball.getRadius()
											+ Math.PI * sball.getRadius() * sball.getRadius()) / Math.PI));
									scoreballs.remove(sball);
									Ball B = new Ball(Math.random() * controller.getMapWidth(),
											Math.random() * controller.getMapHeight(), 10 + Math.random() * 25);
									scoreballs.add(B);
								}
				if (kballs != null && mballs != null)
					for (Ball mball : mtemp)
						for (Ball kball : ktemp)
							if (mball.hit(kball)) {
								if (mball.getRadius() < kball.getRadius() && !(controller.getPlayerM().godPower)) {
									kball.setRadius(Math.sqrt((Math.PI * kball.getRadius() * kball.getRadius()
											+ Math.PI * mball.getRadius() * mball.getRadius()) / Math.PI));
									mballs.remove(mball);
								} else if (mball.getRadius() > kball.getRadius()
										&& !(controller.getPlayerK().godPower)) {
									mball.setRadius(Math.sqrt((Math.PI * kball.getRadius() * kball.getRadius()
											+ Math.PI * mball.getRadius() * mball.getRadius()) / Math.PI));
									kballs.remove(kball);
								}
							}
			}
		});
		hitTest.start();
	}

	public ArrayList<Ball> getScoreBalls() {
		return scoreballs;
	}

	public ArrayList<Ball> getKBalls() {
		return kballs;
	}

	public ArrayList<Ball> getMBalls() {
		return mballs;
	}

	public Controller getController() {
		return controller;
	}

	public int getState() {
		return WLState;
	}

	Timer checkLose;
	Timer inMap;
	Timer hitTest;
	public Ball gear;
	public Ball speed;
	public Ball god;
	private int WLState = 0;
	private ArrayList<Ball> kballs;
	private ArrayList<Ball> mballs;
	private ArrayList<Ball> scoreballs;
	private Controller controller;
}
