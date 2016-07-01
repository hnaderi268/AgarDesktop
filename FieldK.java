import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class FieldK extends JPanel {

	public FieldK(Field field) {
		this.field = field;
	}

	public synchronized void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (field.getState() == 2) {
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, field.getController().getWindow().getWidth(),
					field.getController().getWindow().getHeight());
			Font font = new Font("Helvetica", Font.PLAIN, 72);
			g.setFont(font);
			g2.setColor(Color.RED);
			g2.drawString("You're just a loser!", 60, 400);
		} else if (field.getState() == 1) {
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
					(int) -field.getController().getPlayerK().getX() + field.getController().getWindow().getWidth() / 4,
					(int) -field.getController().getPlayerK().getY()
							+ field.getController().getWindow().getHeight() / 2,
					field.getController().getMapWidth(), field.getController().getMapHeight());
			g2.setColor(Color.WHITE);
			g2.fillRect(
					(int) -field.getController().getPlayerK().getX() + field.getController().getWindow().getWidth() / 4
							+ 10,
					(int) -field.getController().getPlayerK().getY() + field.getController().getWindow().getHeight() / 2
							+ 10,
					field.getController().getMapWidth() - 20, field.getController().getMapHeight() - 20);
			g2.setColor(Color.BLACK);
			Font font = new Font("Helvetica", Font.PLAIN, 22);
			g2.setFont(font);
			g2.drawString(""+(int)(Math.pow(field.getController().getPlayerK().getSumSurface(),2)*Math.PI),10,20);
			if (field.getController().getPlayerM() != null) {
				for (Ball ball : field.getScoreBalls())
					ball.draw(g2, field.getController().getPlayerK().getX(), field.getController().getPlayerK().getY());
				for (Ball ball : field.getMBalls())
					ball.draw(g2, field.getController().getPlayerK().getX(), field.getController().getPlayerK().getY(),field.getController().getPlayerM().godPower);
				for (Ball ball : field.getKBalls())
					ball.draw(g2, field.getController().getPlayerK().getX(), field.getController().getPlayerK().getY(),field.getController().getPlayerK().godPower);
			}
		}
	}

	private Field field;
	private static final long serialVersionUID = 1L;
}
