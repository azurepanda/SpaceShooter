package shooter;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;


public class VisibleAmmoCount extends Sprite{
	
	private AmmoCount ac;
	private final Color botColor = Color.decode("#35D59D");
	private final Color topColor = Color.decode("#00AB6F");
	
	public VisibleAmmoCount(int key, Layer layer, AmmoCount ac){
		super(key, layer);
		this.ac = ac;
	}
	
	public AmmoCount getAmmoCount(){
		return ac;
	}

	@Override
	public void paint(GamePanel gp, Graphics2D g2d, SceneManager sm){
		int pieR = gp.getSize().width / 16;
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.black);
		g2d.drawOval((int) ((gp.getSize().width / 2) - (.5 * pieR)), (int) ((gp.getSize().height / 8) - (.5 * pieR)), pieR, pieR); //draws border
		g2d.setColor(topColor);
		g2d.fillArc((int) ((gp.getSize().width / 2) - (.5 * pieR)), (int) ((gp.getSize().height / 8) - (.5 * pieR)), pieR, pieR, -270, -ac.getPerc());
		g2d.setColor(botColor);
		g2d.fillArc((int) (gp.getSize().width / 2) - (int) (.5 * pieR), (int) ((gp.getSize().height / 8) - (.5 * pieR)), pieR, pieR, -ac.getPerc() - 270, - 360 + ac.getPerc());
	}

	@Override
	public void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm) {

	}
}
