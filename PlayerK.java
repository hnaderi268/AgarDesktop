import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PlayerK extends Player {

	public PlayerK(Controller controller) {
		this.controller = controller;
		x = 3 * controller.getMapWidth() / 4;
		y = controller.getMapHeight() / 2;
		Ball b = new Ball(x, y, 50, "K");
		controller.getWindow().getField().getKBalls().add(b);
		Ball b2 = new Ball(x + 10, y, 50, "k");
		controller.getWindow().getField().getKBalls().add(b2);
		ballPlacer();
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
						y += controller.getMaxSpeed() / getSumSurface() * 0.707;
					else
						y += controller.getMaxSpeed() / getSumSurface();
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
						y -= controller.getMaxSpeed() / getSumSurface() * 0.707;
					else
						y -= controller.getMaxSpeed() / getSumSurface();
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
						x -= controller.getMaxSpeed() / getSumSurface() * 0.707;
					else
						x -= controller.getMaxSpeed() / getSumSurface();
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
						x += controller.getMaxSpeed() / getSumSurface() * 0.707;
					else
						x += controller.getMaxSpeed() / getSumSurface();
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

	private Controller controller;
	public static Timer ballPlacer;
	public static Timer moveD;
	public static Timer moveU;
	public static Timer moveL;
	public static Timer moveR;
}
