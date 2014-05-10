package manager;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseManager implements MouseListener, MouseWheelListener{

	private boolean[] buttons;
	private int xT;
	private int yT;
	
	public MouseManager(){
		buttons = new boolean[2];
		xT = 0;
		yT = 0;
	}
	
	public void setXT(int x){
		xT = x;
	}
	
	public void setYT(int y){
		yT = y;
	}
	
	public Point getMouseLoc(){
		Point p = MouseInfo.getPointerInfo().getLocation();
		p.translate(-xT, -yT);
		return p;
	}
	
	public boolean mouseDown(int n){//only 0 or 1 for Left and Right
		if(buttons[n]){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			buttons[0] = true;
		}else if(e.getButton() == MouseEvent.BUTTON3){
			buttons[1] = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			buttons[0] = false;
		}else if(e.getButton() == MouseEvent.BUTTON3){
			buttons[1] = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}
}
