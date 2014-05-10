package sprite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import manager.GamePanel;
import manager.KeyboardManager;
import manager.MouseManager;
import manager.SceneManager;

import framework.AmmoCount;
import framework.Layer;
import framework.Sprite;



public class VisibleAmmoCount extends Sprite{
	
	private AmmoCount acN;
	private AmmoCount acS;
	private AmmoCount acT;
	private final Color botColorN = Color.decode("#35D59D");
	private final Color topColorN = Color.decode("#00AB6F");
	private final Color botColorS = Color.decode("#FF6666");
	private final Color topColorS = Color.decode("#CC3300");
	private final Color botColorT = Color.decode("#FFCC66");
	private final Color topColorT = Color.decode("#FF9900");
	
	public VisibleAmmoCount(int key, Layer layer, AmmoCount acN, AmmoCount acS, AmmoCount acT){
		super(key, layer);
		this.acN = acN;
		this.acS = acS;
		this.acT = acT;
	}
	
	public AmmoCount getAmmoCountN(){
		return acN;
	}
	
	public AmmoCount getAmmoCountS(){
		return acS;
	}
	
	public AmmoCount getAmmoCountT(){
		return acT;
	}

	@Override
	public void paint(GamePanel gp, Graphics2D g2d, SceneManager sm){
		int pieR = gp.getSize().width / 16;
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.black);
		double x1 = ((gp.getSize().width / 2) - (.5 * pieR));
		double y1 = ((gp.getSize().height / 8) - (.5 * pieR));
		
		double enlarge = 1.5;
		double x2 = ((gp.getSize().width / 2) - (.5 * pieR * enlarge));
		double y2 = ((gp.getSize().height / 8) - (.5 * pieR * enlarge));
		g2d.setColor(Color.black);
		g2d.drawArc((int) x2, (int) y2, (int) (pieR*enlarge), (int) (pieR*enlarge), 225, -90);
		g2d.setColor(topColorS);
		g2d.fillArc((int) x2, (int) y2, (int) (pieR*enlarge), (int) (pieR*enlarge), 225, -acS.getPerc()*1/4);
		g2d.setColor(botColorS);
		g2d.fillArc((int) x2, (int) y2, (int) (pieR*enlarge), (int) (pieR*enlarge), -acS.getPerc()*1/4 + 225, - 90 + acS.getPerc()*1/4);
		
		g2d.setColor(Color.black);
		g2d.drawArc((int) x2, (int) y2, (int) (pieR*enlarge), (int) (pieR*enlarge), -45, 90);
		g2d.setColor(topColorT);
		g2d.fillArc((int) x2, (int) y2, (int) (pieR*enlarge), (int) (pieR*enlarge), -45, acT.getPerc()*1/4);
		g2d.setColor(botColorT);
		g2d.fillArc((int) x2, (int) y2, (int) (pieR*enlarge), (int) (pieR*enlarge), acT.getPerc()*1/4 - 45, 90 - acT.getPerc()*1/4);
		
		g2d.setColor(Color.black);
		g2d.drawOval((int) x1, (int) y1, pieR, pieR); //draws border
		g2d.setColor(topColorN);
		g2d.fillArc((int) x1, (int) y1, pieR, pieR, -270, -acN.getPerc());
		g2d.setColor(botColorN);
		g2d.fillArc((int) x1, (int) y1, pieR, pieR, -acN.getPerc() - 270, - 360 + acN.getPerc());
	}

	@Override
	public void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm) {

	}
}
