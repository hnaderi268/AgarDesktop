import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
import java.rmi.*;
import java.awt.event.ActionEvent;

public class Controller {

	public Controller(App app,int mapWidth,int mapHeight,int speed,int scoreBallsCount,Image iconK,Image iconM) {
		this.app = app;
		this.mapHeight=mapHeight;
		this.mapWidth=mapWidth;
		this.maxSpeed=speed;
		this.scoreBallsCount=scoreBallsCount;
		this.iconM=iconM;
		this.iconK=iconK;
		app.startPanel.setVisible(false);
		reBuild();
	}

	public void reBuild(){
		window = new Window(this);
		playerk = new PlayerK(this);
		playerm = new PlayerM(this);
		playerk.icon=iconK;
		playerm.icon=iconM;
		window.getFieldM().addMouseMotionListener(window.getFieldM().mouse_move);
		show();
		window.jmap.setVisible(true);
		window.getField().checkLose();
	}
	
	private void show() {
		show = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.getFieldM().repaint();
				window.getFieldK().repaint();
				window.jmap.repaint();
				time++;
				time%=Integer.MAX_VALUE-10;
			}
		});
		show.start();
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double v) {
		maxSpeed = v;
	}

	public PlayerK getPlayerK() {
		return playerk;
	}

	public PlayerM getPlayerM() {
		return playerm;
	}

	public App getApp() {
		return this.app;
	}

	public Window getWindow() {
		return window;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int w) {
		mapWidth = w;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int h) {
		mapHeight = h;
	}

	public int getScoreBallsCount() {
		return scoreBallsCount;
	}

	public Image iconK,iconM;
	public long time=0;
	private Window window;
	private PlayerK playerk;
	private PlayerM playerm;
	private static Timer show;
	private double maxSpeed = 400;
	private int mapWidth = 2000, mapHeight = 1000;
	private int scoreBallsCount = (int)((mapHeight*mapWidth)/40000);
	private App app;
}
