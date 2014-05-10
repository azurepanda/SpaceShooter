package manager;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class FontManager {
	
	private Font damageFont;
	private Font critFont;
	
	public FontManager(){
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			damageFont = Font.createFont(Font.TRUETYPE_FONT, new File("gfx/AlteHaasGroteskBold.TTF")).deriveFont(Font.PLAIN, 15);
			critFont = Font.createFont(Font.TRUETYPE_FONT, new File("gfx/AlteHaasGroteskBold.TTF")).deriveFont(Font.PLAIN, 18);
			ge.registerFont(damageFont);
			ge.registerFont(critFont);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Font getDamageFont(){
		return damageFont;
	}
	
	public Font getCritFont(){
		return critFont;
	}
}
