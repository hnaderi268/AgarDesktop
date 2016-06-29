import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Map extends JPanel {

	public Map(Field field) {
		this.field = field;
	}

	public synchronized void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 400, 1000);;
		double div = 400.0 / (double)field.getController().getMapWidth();
		if (field.getController().getPlayerM() != null) {
			for (Ball ball : field.getScoreBalls())
				ball.draw(g2, div);
			for (Ball ball : field.getMBalls())
				ball.draw(g2, div);
			for (Ball ball : field.getKBalls())
				ball.draw(g2, div);
		}
	}

	private Field field;

}
