package sprite;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;

import manager.GamePanel;
import manager.KeyboardManager;
import manager.MouseManager;
import manager.SceneManager;
import framework.Damage;
import framework.DamageType;
import framework.Layer;
import framework.Sprite;

public class DamageText extends Sprite{
	
	private final int colorDamageMax = 400;
	private Color color;
	private Ship ship;
	private int damage;
	private double yVel = 10;
	private double endYVel = 3;
	private double acel = 0.92;
	private int y = 45;
	private int x = 53;
	private boolean crit = false;
	
	public DamageText(int key, Layer layer, Ship ship, Damage d){
		super(key, layer);
		this.ship = ship;
		this.damage = d.getAmount();
		if(d.getDamageType() == DamageType.CRIT){
			color = Color.MAGENTA;
			acel = 0.96;
			crit = true;
		}else if(d.getDamageType() == DamageType.BULLET){
			double power = (double) (damage - 100) / colorDamageMax;
			if(power > 1) power = 1;
			if(power < 0) power = 0;
			power = 1 - power;
			color = new Color(255, (int) (power * 255), 0);
		}else if(d.getDamageType() == DamageType.DOT){
			color = new Color(0, 255, 0);
		}
	}

	@Override
	public void paint(GamePanel gp, Graphics2D g2d, SceneManager sm) {
		Point p = ship.getXY();
		g2d.setColor(color);
		Composite comp = g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (1 - endYVel / yVel)));
		if(crit){
			g2d.setFont(sm.getFontManager().getCritFont());
		}else{
			g2d.setFont(sm.getFontManager().getDamageFont());
		}
		g2d.drawString(damage+"", p.x + x, p.y - y);
		g2d.setComposite(comp);
	}

	@Override
	public void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm) {
		yVel *= acel;
		if(yVel < endYVel) this.remove(sm);
		y += yVel;
	}
}
