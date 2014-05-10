package manager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;
import javax.swing.Timer;

import scene.GameScene;
import framework.Layer;
import framework.Sprite;

public class GamePanel extends JPanel implements ActionListener{

	private KeyboardManager km;
	private MouseManager mm;
	private SceneManager sm;
	private Timer timer;
	private final Layer[] order = {Layer.BACK, Layer.PARTICLE, Layer.PROJECTILE, Layer.TOP, Layer.HUD};
		
	public GamePanel() {
		System.setProperty("sun.java2d.opengl","True");
		mm = new MouseManager();
		km = new KeyboardManager();
		sm = new SceneManager();
		this.addMouseListener(mm);
		this.addMouseWheelListener(mm);
		this.addKeyListener(km);
		this.setFocusable(true);
		timer = new Timer(16, this);
        timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		Graphics2D g2d = (Graphics2D) g;	
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for(Layer l : order){
			AffineTransform oldTransform = g2d.getTransform();
			if(!l.equals(Layer.HUD) && sm.getCurrentScene().getClass().equals(GameScene.class)){
				Point trans = ((GameScene) sm.getCurrentScene()).getCamera().getTrans();
				g2d.translate(trans.x, trans.y);
			}
			for(Sprite s : sm.getCurrentScene().getSpriteList(l).values()){
				s.paint(this, g2d, sm);
			}
			g2d.setTransform(oldTransform);
		}	
	}
	
	public void actionPerformed(ActionEvent e){
		if(sm.getCurrentScene().getClass().equals(GameScene.class)){
        	Point p = ((GameScene) sm.getCurrentScene()).getCamera().getTrans();
        	mm.setXT(p.x);
        	mm.setYT(p.y);
        }
		for(Layer l : order){
			for(Sprite s : sm.getCurrentScene().getSpriteList(l).values()){
				s.update(this, km, mm, sm);
			}
		}
		if(sm.getCurrentScene().getClass().equals(GameScene.class)){
        	((GameScene) sm.getCurrentScene()).updateCamera(this, sm);
        }
		sm.getCurrentScene().update(this, km, sm);
        repaint();     
    }  
}
