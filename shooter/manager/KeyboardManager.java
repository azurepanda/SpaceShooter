package manager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyboardManager implements KeyListener{
	
	private boolean[] keys;
	
	public KeyboardManager(){
		keys = new boolean[65536];
	}
	
	public boolean KeyDown(int k){
		return keys[k];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
