import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class PlayerM extends Player {

	public PlayerM(Controller controller) {
		this.controller = controller;
		maxSpeed = controller.getMaxSpeed();
		x = controller.getMapWidth() / 4;
		y = controller.getMapHeight() / 2;
		Ball b1 = new Ball(x, y, 50, "M",controller.iconM);
		Ball b2 = new Ball(x + 10, y, 50, "m",controller.iconM);
		Ball b3 = new Ball(x - 10, y + 10, 50, "m",controller.iconM);
		controller.getWindow().getField().getMBalls().add(b1);
		controller.getWindow().getField().getMBalls().add(b2);
		controller.getWindow().getField().getMBalls().add(b3);
		move();
		ballMerger();
	}
	
	private void ballMerger() {
		Timer ballMerger = new Timer(2000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int cnt = 0;
				ArrayList<Ball> mtemp = (ArrayList<Ball>) controller.getWindow().getField().getMBalls().clone();
				for (Ball ball1 : mtemp) {
					if (cnt != 0 && ball1.getRadius() > 50) {
						ball1.setRadius(ball1.getRadius() - 1);
						controller.getWindow().getField().getMBalls().get(0)
								.setRadius(controller.getWindow().getField().getMBalls().get(0).getRadius() + 1);
					} else if (cnt != 0 && ball1.getRadius() < 50) {
						controller.getWindow().getField().getMBalls().get(0).setRadius(
								controller.getWindow().getField().getMBalls().get(0).getRadius() + ball1.getRadius());
						controller.getWindow().getField().getMBalls().remove(ball1);
					}
					cnt++;
				}

			}
		});
		ballMerger.start();

	}

	public void move() {
		move = new Timer(10, new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				if ((x > controller.getMapWidth() && desx > 1440 / 4) || (x < 0 && desx < 1440 / 4)
						|| (y > controller.getMapHeight() && desy > 410) || (y < 0 && desy < 410)) {
					;
				} else {

					for (Ball ball1 : controller.getWindow().getField().getMBalls()) {
						for (Ball ball2 : controller.getWindow().getField().getMBalls()) {
							if (ball1.hit(ball2) && ball1 != ball2) {
								ball1.setX(ball1.getX()
										+ (int) ((ball1.getRadius() + ball2.getRadius() - (ball2.getX() - ball1.getX()))
												* .01));
								ball1.setY(ball1.getY()
										+ (int) ((ball1.getRadius() + ball2.getRadius() - (ball2.getY() - ball1.getY()))
												* .01));
							}
						}
						Ball b1 = controller.getWindow().getField().getMBalls().get(0);
						b1.setX(x);
						b1.setY(y);
					}

					double difx = x;
					x += (getMaxSpeed() / getSumSurface()) * (desx - 1440 / 4) / (Math
							.sqrt((desx - 1440 / 4) * (desx - 1440 / 4) + (desy - 1440 / 4) * (desy - 1440 / 4)));
					difx = x - difx;
					double dify = y;
					y += (getMaxSpeed() / getSumSurface()) * (desy - 410)
							/ (Math.sqrt((desx - 410) * (desx - 410) + (desy - 410) * (desy - 410)));
					dify = y - dify;
					for (Ball ball : controller.getWindow().getField().getMBalls()) {
						ball.setX(ball.getX() + difx);
						ball.setY(ball.getY() + dify);
					}
				}
			}
		});
		move.start();
	}

	public void divide() {
		ArrayList<Ball> mtemp = (ArrayList<Ball>) controller.getWindow().getField().getMBalls().clone();
		for (Ball ball : mtemp) {
			if (mtemp.size() < 4) {
				int rad=(int) (ball.getRadius() / 2);
				if(rad<50)
					rad=50;
				controller.getWindow().getField().getMBalls().add(new Ball(x + (int) (Math.random() * 100),
						y + (int) (Math.random() * ball.getRadius() / 2), rad, "m",controller.iconM));
				controller.getWindow().getField().getMBalls().add(new Ball(x - (int) (Math.random() * 100-50),
						y - (int) (Math.random() * ball.getRadius() / 2-50), rad, "m",controller.iconM));
				controller.getWindow().getField().getMBalls().remove(ball);
			}
		}
	}

	public double getMaxSpeed() {
		if (speedPower)
			return maxSpeed * 2;
		else
			return maxSpeed;
	}

	public double getSumSurface() {
		double sum = 0;
		for (Ball ball : controller.getWindow().getField().getMBalls())
			sum += ball.getRadius();
		// estefade az code zir manaand anche dar tarif prozhe amade
		// kari
		// ahmaghane ast:
		// sum += Math.PI * ball.getRadius() * ball.getRadius();
		return sum;
	}

	
	private double maxSpeed;
	public int realdesx, realdesy;
	public int desx, desy;
	private Controller controller;
	public static Timer move;
}
