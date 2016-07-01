import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class FieldM extends JPanel {
	public ArrayList<Ball> balls;
	public Field field;

	public FieldM(Field field) {
		this.field = field;
		MouseMotionListener mouse_move = new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				if (Math.abs(
						field.getController().getPlayerM().getX() - field.getController().getWindow().getWidth() / 4
								+ e.getX() - field.getController().getPlayerM().x) > 1
						|| Math.abs(field.getController().getPlayerM().getY()
								- field.getController().getWindow().getHeight() / 2 + e.getY()
								- field.getController().getPlayerM().y) > 1) {
					field.getController().getPlayerM().realdesx = (int) (field.getController().getPlayerM().getX()
							- field.getController().getWindow().getWidth() / 4 + e.getX());
					field.getController().getPlayerM().realdesy = (int) (field.getController().getPlayerM().getY()
							- field.getController().getWindow().getHeight() / 2 + e.getY());
					field.getController().getPlayerM().desx = e.getX();
					field.getController().getPlayerM().desy = e.getY();
				}
			}

			public void mouseDragged(MouseEvent e) {
			}
		};

		addMouseMotionListener(mouse_move);
	}

	//
	public synchronized void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (field.getState() == 1) {
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, field.getController().getWindow().getWidth(),
					field.getController().getWindow().getHeight());
			Font font = new Font("Helvetica", Font.PLAIN, 72);
			g.setFont(font);
			g2.setColor(Color.RED);
			g2.drawString("You're just a loser!", 60, 400);
		} else if (field.getState() == 2) {
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, field.getController().getWindow().getWidth(),
					field.getController().getWindow().getHeight());
			Font font = new Font("Helvetica", Font.PLAIN, 72);
			g.setFont(font);
			g2.setColor(Color.GREEN);
			g2.drawString("You're the Best!", 100, 400);
		} else {
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, field.getController().getWindow().getWidth(),
					field.getController().getWindow().getHeight());
			g2.setColor(Color.BLACK);
			g2.fillRect(
					(int) -field.getController().getPlayerM().getX() + field.getController().getWindow().getWidth() / 4,
					(int) -field.getController().getPlayerM().getY()
							+ field.getController().getWindow().getHeight() / 2,
					field.getController().getMapWidth(), field.getController().getMapHeight());
			g2.setColor(Color.WHITE);
			g2.fillRect(
					(int) -field.getController().getPlayerM().getX() + field.getController().getWindow().getWidth() / 4
							+ 10,
					(int) -field.getController().getPlayerM().getY() + field.getController().getWindow().getHeight() / 2
							+ 10,
					field.getController().getMapWidth() - 20, field.getController().getMapHeight() - 20);
			g2.setColor(Color.BLACK);
			Font font = new Font("Helvetica", Font.PLAIN, 22);
			g2.setFont(font);
			g2.drawString("" + (int) (Math.pow(field.getController().getPlayerM().getSumSurface(), 2) * Math.PI), 10,
					20);
			if (field.getController().getPlayerM() != null) {
				ArrayList<Ball> temp = (ArrayList<Ball>) field.getScoreBalls().clone();
				for (Ball ball : temp)
					ball.draw(g2, field.getController().getPlayerM().getX(), field.getController().getPlayerM().getY());
				for (Ball ball : field.getMBalls())
					ball.draw(g2, field.getController().getPlayerM().getX(), field.getController().getPlayerM().getY(),field.getController().getPlayerM().godPower);
				for (Ball ball : field.getKBalls())
					ball.draw(g2, field.getController().getPlayerM().getX(), field.getController().getPlayerM().getY(),field.getController().getPlayerK().godPower);
			}
		}
	}

	// private Field field;
	public MouseMotionListener mouse_move;
	private static final long serialVersionUID = 1L;
}
