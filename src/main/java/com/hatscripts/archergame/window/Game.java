package com.hatscripts.archergame.window;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;
import com.hatscripts.archergame.objects.ObjectType;
import com.hatscripts.archergame.objects.Objects;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Game extends Canvas {
	private final GraphicsContext g;
	private final Objects objects;
	private final AnimationTimer timer;

	Game(double width, double height, KeyInput keyInput, MouseInput mouseInput) {
		super(width, height);
		objects = new Objects(keyInput, mouseInput);
		objects.add(ObjectType.DEBUG, 0, 0);
		objects.add(ObjectType.PLAYER, getWidth() / 2, getHeight() / 2);

		{
			double size = 16;
			objects.add(ObjectType.BLOCK, 0, 0, width, size);
			objects.add(ObjectType.BLOCK, 0, height - size, width, size);
			objects.add(ObjectType.BLOCK, 0, 0, size, height);
			objects.add(ObjectType.BLOCK, width - size, 0, size, height);
		}

		this.g = this.getGraphicsContext2D();
		Game game = this;

		timer = new AnimationTimer() {
			private final SimpleLongProperty lastNanoTime = new SimpleLongProperty(System.nanoTime());

			@Override
			public void handle(long now) {
				double elapsed = (now - lastNanoTime.get()) / 1E9;
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
			g.setFill(Color.DARKGRAY);
			g.fillRect(0, 0, getWidth(), getHeight());

			objects.render(g, getBoundsInLocal(), elapsed);
		});
	}

	void stopAnimationTimer() {
		timer.stop();
	}
}
