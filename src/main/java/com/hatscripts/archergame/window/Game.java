package com.hatscripts.archergame.window;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;
import com.hatscripts.archergame.objects.ObjectType;
import com.hatscripts.archergame.objects.Objects;
import com.hatscripts.archergame.objects.list.Block;
import com.hatscripts.archergame.objects.list.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Game extends Canvas {
	private final SimpleBooleanProperty debug;
	private final GraphicsContext gc;
	private final Objects objects;
	private final AnimationTimer timer;
	private final SimpleDoubleProperty fps = new SimpleDoubleProperty(0, "FPS");
	private final SimpleLongProperty runtime = new SimpleLongProperty(0, "Runtime");

	Game(double width, double height, KeyInput keyInput,
		 MouseInput mouseInput, SimpleBooleanProperty debug) {
		super(width, height);
		this.debug = debug;
		objects = new Objects(debug);
		objects.add(new Player(getWidth() / 2, getHeight() / 2, keyInput, mouseInput));

		double blockWidth = ObjectType.BLOCK.getWidth();
		double blockHeight = ObjectType.BLOCK.getHeight();

		for (double x = 0; x < width; x += blockWidth) {
			objects.add(new Block(x, 0));
			objects.add(new Block(x, height - blockHeight));
		}
		for (double y = blockHeight; y < height - blockHeight; y += blockHeight) {
			objects.add(new Block(0, y));
			objects.add(new Block(width - blockWidth, y));
		}

		this.gc = this.getGraphicsContext2D();
		Game game = this;

		timer = new AnimationTimer() {
			private final long startTime = System.nanoTime();
			private final SimpleLongProperty lastNanoTime = new SimpleLongProperty(startTime);

			@Override
			public void handle(long now) {
				runtime.set((long) ((now - startTime) / 1E6));
				double elapsed = (now - lastNanoTime.get()) / 1E9;
				fps.set(1 / elapsed);
				game.tick(elapsed);
				lastNanoTime.set(now);
			}
		};
		timer.start();
	}

	private void tick(double elapsed) {
		objects.tick(elapsed);
		paint(elapsed);
	}

	private void paint(double elapsed) {
		Platform.runLater(() -> {
			gc.setFill(Color.DARKGRAY);
			gc.fillRect(0, 0, getWidth(), getHeight());

			objects.render(gc, getBoundsInLocal(), elapsed);

			if (debug.get()) {
				gc.setFill(Color.WHITE);
				gc.fillText(String.format("%s: %s\n%s: %.0f",
						// TODO: Fix runtime showing as 10:MM:SS instead of HH:MM:SS
						runtime.getName(), String.format("%1$tH:%1$tM:%1$tS", runtime.get()),
						// TODO: Make FPS display smoother
						fps.getName(), fps.get()), 10, 12);
			}
		});
	}

	void stopAnimationTimer() {
		timer.stop();
	}
}
