package root.app.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{
	private static boolean[] keys = new boolean[100];
	
	public static boolean isKeyPressed(int keyCode) {
		return keys[keyCode]; 
	}

	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;	
	}

	
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;	
		
	}

	
	public void keyTyped(KeyEvent e) {
		
		
	}

}
