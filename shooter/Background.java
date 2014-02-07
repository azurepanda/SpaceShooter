package shooter;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class Background extends Sprite{

	private BufferedImage b1;
	private BufferedImage b2;
	private BufferedImage b3;
	private BufferedImage b4;
	private int[][] background;
	private boolean backgroundCheck;
	private int xn;
	private int yn;
	
	public Background(int key, Layer layer){
		super(key, layer);
		try {
			b1 = ImageIO.read(new File("gfx/star1.png"));
			b2 = ImageIO.read(new File("gfx/star2.png"));
			b3 = ImageIO.read(new File("gfx/star3.png"));
			b4 = ImageIO.read(new File("gfx/star4.png"));
		} catch (IOException e) {
		}
		backgroundCheck = false;
	}
	
	public int getXN(GamePanel gp, SceneManager sm){
		if(sm.getCurrentScene().getClass().equals(GameScene.class)) return (int) (1.5 * Math.ceil((float) (gp.getSize().width / 16)));
		return (int) Math.ceil((float) (gp.getSize().width / 16));
	}
	
	public int getYN(GamePanel gp, SceneManager sm){
		if(sm.getCurrentScene().getClass().equals(GameScene.class)) return (int) (2 * Math.ceil((float) (gp.getSize().height / 16)));
		return (int) Math.ceil((float) (gp.getSize().height / 16));
	}
	
	public int getX(){
		return (xn-1) * 20 + 32; //accounting for overlapping
	}
	
	public int getY(){
		return (yn-1) * 20 + 32; //accounting for overlapping
	}
	
	@Override
	public void paint(GamePanel gp, Graphics2D g2d, SceneManager sm) {
		if(!backgroundCheck){
			xn = getXN(gp, sm);
			yn = getYN(gp, sm);
			background = new int[xn][yn];	
			for (int x = 0; x < xn; x++) {
				for (int y = 0; y < yn; y++) {
					Random rand = new Random();
					int i = rand.nextInt(4);
					background[x][y] = i;
				}
			}
			backgroundCheck = true;
		}if(xn != getXN(gp, sm) || yn != getYN(gp, sm)){
			backgroundCheck = true;
		}
		for (int x = 0; x < background.length; x++) {
			for (int y = 0; y < background[0].length; y++) {	
				int i = background[x][y];
				int xa = x * 20;
				int ya = y * 20;
				boolean range = true;
				if(sm.getCurrentScene().getClass().equals(GameScene.class)){
					Camera c = ((GameScene) sm.getCurrentScene()).getCamera();
					Dimension s = gp.getSize();
					Rectangle r = new Rectangle((int) (-c.getTrans().x - (0.1*s.width)), (int) (-c.getTrans().y - (0.1*s.width)), (int) (1.2*s.width), (int) (1.2*s.height));
					if(!r.contains(new Point(xa, ya))){
						range = false;
					}
				}
				if(range){
					switch (i) {
					case 0:
						g2d.drawImage(b1, xa, ya, null);
						break;
					case 1:
						g2d.drawImage(b2, xa, ya, null);
						break;
					case 2:
						g2d.drawImage(b3, xa, ya, null);
						break;
					case 3:
						g2d.drawImage(b4, xa, ya, null);
						break;
					}
				}
			}
		}
	}

	@Override
	public void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm) {
		
	}
}
