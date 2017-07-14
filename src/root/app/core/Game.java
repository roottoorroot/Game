package root.app.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import root.app.core.graphics.Renderer;
import root.app.input.InputHandler;

public class Game extends Canvas{
	private static final long serialVersionUID = 1L;
	
	public int width = 300;
	public int height = width / 16 * 9;
	public int scale = 3;
	
	
	private BufferStrategy bs;
	private int x = 0;
	private int y = 0;
	private String title = "Game";
	private Graphics g;
	private Thread thread;
	private boolean running;
	private JFrame frame = new JFrame(title);
	private Renderer renderer;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //Create image;
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData(); //Work with created image;
	
	
	
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		renderer = new Renderer(width, height, pixels);
		frame.addKeyListener(new InputHandler());
		
	}
	
	private void init() {
		//frame = new JFrame(name);
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void render() {
			if (bs == null) {
				createBufferStrategy(3);
				bs = getBufferStrategy();
			}
			renderer.clear();
			renderer.render(x, y);
			g = bs.getDrawGraphics();
			//g.setColor(Color.BLUE);
			g.drawImage(image, 0, 0, getWidth(), getHeight(),null);
			//g.fillRect(0, 0, getWidth(), getHeight());
			g.dispose();
			bufferSwap();
		
		}
	
	private void bufferSwap() {
		bs.show();
		
	}

	public synchronized void start() {
		running = true;
		init();
		
		thread = new Thread(() -> {
			long jvmLastTime = System.nanoTime();//Start time
			long timer = System.currentTimeMillis(); 
			double jvpPartTime = 1_000_000_000.0 / 60.0; //60 fps;
			double delta = 0;
			int updates = 0;
			int frames = 0;
			while(running) {
				long jvmNow = System.nanoTime(); //Now time
				delta += (jvmNow - jvmLastTime);
				jvmLastTime = jvmNow;
				if (delta >= jvpPartTime) {
					update();
					updates++;
					delta = 0;
				}
				render();
				frames++;
				if (System.currentTimeMillis() - timer > 1000) {
					timer +=1000;
					frame.setTitle( title + " | " + updates + ", " + "Frames: " + frames);
					updates = 0;
					frames = 0;
				}
				//update();
			}
		});
		
		thread.start();
	}
	
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void update() {
		if (InputHandler.isKeyPressed(KeyEvent.VK_UP)) y--;
		if (InputHandler.isKeyPressed(KeyEvent.VK_DOWN)) y++;
		if (InputHandler.isKeyPressed(KeyEvent.VK_RIGHT)) x++;
		if (InputHandler.isKeyPressed(KeyEvent.VK_LEFT)) x--;
		
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
		
	}
}
