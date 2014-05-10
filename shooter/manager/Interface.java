package manager;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;


public class Interface extends JFrame{
		public Interface(){
			this.add(new GamePanel());
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setTitle("Shooter");
			this.setUndecorated(true); 
			this.setResizable(false);
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
		}
	
		public static void main(String args[]){
			EventQueue.invokeLater(new Runnable(){
				public void run(){
					JFrame frame = new Interface();
				}
			});
		}
}
