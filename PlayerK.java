import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class PlayerK extends Player {

	public PlayerK(Controller controller) {
		this.controller = controller;
		maxSpeed = controller.getMaxSpeed();
		x = 3 * controller.getMapWidth() / 4;
		y = controller.getMapHeight() / 2;
		Ball b = new Ball(x, y, 50, "K", controller.iconK);
		controller.getWindow().getField().getKBalls().add(b);
		Ball b2 = new Ball(x + 10, y, 50, "k", controller.iconK);
		controller.getWindow().getField().getKBalls().add(b2);
		ballPlacer();
		ballMerger();
	}

	private void ballMerger() {
		Timer ballMerger = new Timer(2000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int cnt = 0;
				ArrayList<Ball> ktemp = (ArrayList<Ball>) controller.getWindow().getField().getKBalls().clone();
				for (Ball ball1 : ktemp) {
					if (cnt != 0 && ball1.getRadius() > 50) {
						ball1.setRadius(ball1.getRadius() - 1);
						controller.getWindow().getField().getKBalls().get(0)
								.setRadius(controller.getWindow().getField().getKBalls().get(0).getRadius() + 1);
					} else if (cnt != 0 && ball1.getRadius() < 50) {
						controller.getWindow().getField().getKBalls().get(0).setRadius(
								controller.getWindow().getField().getKBalls().get(0).getRadius() + ball1.getRadius());
						controller.getWindow().getField().getKBalls().remove(ball1);
					}
					cnt++;
				}

			}
		});
		ballMerger.start();

	}

	public void ballPlacer() {
		ballPlacer = new Timer(10, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				for (Ball ball1 : controller.getWindow().getField().getKBalls()) {
					for (Ball ball2 : controller.getWindow().getField().getKBalls()) {
						if (ball1.hit(ball2) && ball1 != ball2) {
							ball1.setX(ball1.getX()
									+ (int) ((ball1.getRadius() + ball2.getRadius() - (ball2.getX() - ball1.getX()))
											* .01));
							ball1.setY(ball1.getY()
									+ (int) ((ball1.getRadius() + ball2.getRadius() - (ball2.getY() - ball1.getY()))
											* .01));
						}
					}
					Ball b1 = controller.getWindow().getField().getKBalls().get(0);
					b1.setX(x);
					b1.setY(y);
				}

			}
		});
		ballPlacer.start();
	}

	public void stopMoveL() {
		if (moveL.isRunning())
			moveL.stop();
	}

	public void stopMoveR() {
		if (moveR.isRunning())
			moveR.stop();
	}

	public void stopMoveD() {
		if (moveD.isRunning())
			moveD.stop();
	}

	public void stopMoveU() {
		if (moveU.isRunning())
			moveU.stop();
	}

	public void moveD() {
		moveD = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (y > controller.getMapHeight()) {

				} else {
					double dify = y;
					if (moveL != null && moveR != null && (moveL.isRunning() || moveR.isRunning()))
						y += getMaxSpeed() / getSumSurface() * 0.707;
					else
						y += getMaxSpeed() / getSumSurface();
					for (Ball ball : controller.getWindow().getField().getKBalls())
						ball.setY(ball.getY() + (y - dify));
				}
			}
		});
		moveD.start();
	}

	public void moveU() {
		moveU = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (y < 0) {

				} else {
					double dify = y;
					if (moveL != null && moveR != null && (moveL.isRunning() || moveR.isRunning()))
						y -= getMaxSpeed() / getSumSurface() * 0.707;
					else
						y -= getMaxSpeed() / getSumSurface();
					for (Ball ball : controller.getWindow().getField().getKBalls())
						ball.setY(ball.getY() + (y - dify));
				}
			}
		});
		moveU.start();
	}

	public void moveL() {
		moveL = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (x < 0) {

				} else {
					double difx = x;
					if (moveU != null && moveD != null && (moveU.isRunning() || moveD.isRunning()))
						x -= getMaxSpeed() / getSumSurface() * 0.707;
					else
						x -= getMaxSpeed() / getSumSurface();
					for (Ball ball : controller.getWindow().getField().getKBalls())
						ball.setX(ball.getX() + (x - difx));
				}
			}
		});
		moveL.start();
	}

	public void moveR() {
		moveR = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (x > controller.getMapWidth()) {

				} else {
					double difx = x;
					if (moveU != null && moveD != null && (moveU.isRunning() || moveD.isRunning()))
						x += getMaxSpeed() / getSumSurface() * 0.707;
					else
						x += getMaxSpeed() / getSumSurface();
					for (Ball ball : controller.getWindow().getField().getKBalls())
						ball.setX(ball.getX() + (x - difx));
				}
			}
		});
		moveR.start();
	}

	public double getSumSurface() {
		double sum = 0;
		for (Ball ball : controller.getWindow().getField().getKBalls())
			sum += ball.getRadius();
		// estefade az code zir manaand anche dar tarif prozhe amade kari
		// ahmaghane ast:
		// sum += Math.PI * ball.getRadius() * ball.getRadius();
		return sum;
	}
	
	public double getMaxSpeed() {
		if (speedPower)
			return maxSpeed * 2;
		else
			return maxSpeed;
	}

	public void divide() {
		ArrayList<Ball> ktemp = (ArrayList<Ball>) controller.getWindow().getField().getKBalls().clone();
		for (Ball ball : ktemp) {
			if (ktemp.size() < 4) {
				int rad = (int) (ball.getRadius() / 2);
				if (rad < 50)
					rad = 50;
				controller.getWindow().getField().getKBalls().add(new Ball(x + (int) (Math.random() * 50),
						y + (int) (Math.random() * ball.getRadius() / 2), rad, "k", controller.iconK));
				controller.getWindow().getField().getKBalls().add(new Ball(x + (int) (Math.random() * 50),
						y + (int) (Math.random() * ball.getRadius() / 2), rad, "k", controller.iconK));
				controller.getWindow().getField().getKBalls().remove(ball);
			}
		}
	}
	
	
	private double maxSpeed;
	private Controller controller;
	public static Timer ballPlacer;
	public static Timer moveD;
	public static Timer moveU;
	public static Timer moveL;
	public static Timer moveR;

}
