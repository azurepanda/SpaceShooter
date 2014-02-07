package shooter;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;


public class TitleText extends Sprite{

	private Font font1;
	private Font font2;
	private int textFlicker;
	private String text = "Space Gunner";
	private String text2 = "Hit Space to start";
	
	public TitleText(int key, Layer layer){
		super(key, layer);
		textFlicker = 100;
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			font1 = Font.createFont(Font.TRUETYPE_FONT, new File("gfx/BACKTO19.TTF"));
			font1 = font1.deriveFont(Font.PLAIN, 50);
			font2 = font1.deriveFont(Font.PLAIN, 40);
			ge.registerFont(font1);
			ge.registerFont(font2);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(GamePanel gp, Graphics2D g2d, SceneManager sm) {
		int stringx = g2d.getFontMetrics(font1).stringWidth(text);
		int stringy = g2d.getFontMetrics(font1).getHeight() + g2d.getFontMetrics(font1).getAscent();
		g2d.setColor(Color.gray);
		g2d.setColor(Color.yellow);
		g2d.setFont(font1);
		g2d.drawString(text, (gp.getSize().width / 2) - (stringx / 2), (gp.getSize().height / 2) - (stringy / 2));
		stringx = g2d.getFontMetrics(font2).stringWidth(text2);
		stringy = g2d.getFontMetrics(font2).getHeight() + g2d.getFontMetrics(font1).getAscent();
		g2d.setFont(font2);
		if(textFlicker > 35){
			g2d.drawString(text2, (gp.getSize().width / 2) - (stringx / 2), (gp.getSize().height - (stringy * 2)));
		}
		if(textFlicker > 0){
			textFlicker--;
		}else{
			textFlicker = 100;
		}
	}

	@Override
	public void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm) {
		
	}
}
