import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public abstract class Player {

	protected double x;
	protected double y;
	protected Ball pball;
	public boolean speedPower=false;
	public boolean godPower=false;
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public Ball getPBall() {
		return pball;
	}
}
