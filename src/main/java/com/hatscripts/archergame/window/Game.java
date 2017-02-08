package com.hatscripts.archergame.window;

import com.hatscripts.archergame.objects.Objects;
import com.hatscripts.archergame.objects.list.Player;
import com.hatscripts.archergame.utils.BetterGraphics;
import com.hatscripts.archergame.utils.Time;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
	private static final String TITLE = "Archer Game";
	public static final int WIDTH = 640;
	public static final int HEIGHT = 352;
	private static final int FPS = 60;
	public static final Rectangle BOUNDS = new Rectangle(0, 0, WIDTH, HEIGHT);
	private boolean running = false;
	private final Objects objects = new Objects();
	private long runtime;
	private int ingameFps;

	Game() {
		objects.add(new Player(WIDTH / 2, HEIGHT / 2));

		//objects.createLevel();
	}

	public static void main(String[] args) {
		new Window(TITLE, WIDTH, HEIGHT);
	}

	synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		long startTime = System.nanoTime();
		long lastTime = startTime;
		int ticks = 0;
		double nsPerTick = 1000000000.0 / FPS;

		while (running) {
			long currentTime = System.nanoTime();
			long diff = currentTime - lastTime;
			if (diff >= nsPerTick) {
				tick();
				render();
				ticks++;
				if (ticks % FPS == 0) {
					runtime = (long) ((currentTime - startTime) / 1000000000.0);
					ingameFps = Math.round(ticks / runtime);
				}
				lastTime = currentTime;
			}
		}
	}

	private void tick() {
		objects.tick();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		g.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		objects.render(g);

		/* Debug */
		g.setColor(Color.WHITE);

		g.drawRect(32, 32, WIDTH - 64, HEIGHT - 64);

		BetterGraphics.drawString(g, "Runtime: " + Time.format(runtime)
				+ "\nFPS: " + ingameFps, 10, 12);
		/* Debug */

		g.dispose();
		bs.show();
	}
}
