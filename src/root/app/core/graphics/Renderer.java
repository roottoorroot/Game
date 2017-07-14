package root.app.core.graphics;

import java.util.Random;

public class Renderer {
	private int width, height;
	private int[] pixels;
	private int tileIndex;
	private static final int MAP_SIZE = 64;
	private static final int MAP_SIZE_MASK = MAP_SIZE - 1;
	private int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	private static final Random random = new Random();
	
	
	public Renderer(int width, int height, int[] pixels) {
		this.width = width;
		this.height = height;
		this.pixels = pixels;
		fill();
	}
	
	private void fill() {
		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xfffffff);	
		}
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0; //Black color 0x000000; 
		}
	}
	
	
	public void render(int xOffset, int yOffset) {
		for (int y = 0; y < height; y++) {
			int yy = y + yOffset;
			for (int x = 0; x < width; x++) {
				int xx = x + xOffset;
				tileIndex = ((yy >> 4) & MAP_SIZE_MASK) + (((xx >> 4) & MAP_SIZE_MASK) * MAP_SIZE);
				pixels[x + (y * width)] = tiles[tileIndex];
				//pixels[50 + 70 * width] = 0x004FF;
			}
		}
	}
}
