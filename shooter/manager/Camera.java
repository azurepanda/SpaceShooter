package manager;

import java.awt.Dimension;
import java.awt.Point;

import scene.GameScene;
import sprite.Background;
import sprite.Player;

public class Camera {
	private Player player;
	private int roomMoveX = 100;
	private int roomMoveY = 100;
	private int x;
	private int y;
	private int xT;
	private int yT;
	
	public Camera(Player p){
		this.player = p;
		x = 300;
		y = 300;
		xT = 0;
		yT = 0;
	}
	
	public Point getTrans(){
		return new Point(xT, yT);
	}
	
	public Point getXY(){
		return new Point(x, y);
	}
	
	public void update(GamePanel gp, SceneManager sm){
		Dimension size = gp.getSize();
		roomMoveX = size.width / 3;
		roomMoveY = size.height / 3;
		float pX = player.getX();
		float pY = player.getY();
		x -= player.getXVel();
		y -= player.getYVel();
		if(x < roomMoveX) x = roomMoveX;
		if(y < roomMoveY) y = roomMoveY;
		if(x > size.width - roomMoveX) x = size.width - roomMoveX;
		if(y > size.height - roomMoveY) y = size.height - roomMoveY;
		xT = (int) (x - pX);
		yT = (int) (y - pY);
		if(xT > 0) xT = 0;
		if(yT > 0) yT = 0;
		Background b = ((GameScene) sm.getCurrentScene()).getBackground();
		Dimension s = gp.getSize();
		if(xT < -b.getX() + s.getSize().width) xT = (int) -b.getX() + s.getSize().width;
		if(yT < -b.getY() + s.getSize().height) yT = (int) -b.getY() + s.getSize().height;
	}
}
