package com.hatscripts.archergame.objects.interfaces;

import javafx.geometry.Rectangle2D;

import java.util.Optional;

public interface Mobile extends GameObject {
	enum CollisionChecker {
		TOP {
			@Override
			public Rectangle2D rect(double x, double y, double w, double h, double xs, double ys) {
				return new Rectangle2D(x + HALF_WIDTH + xs, y - ys,
						w - WIDTH - xs * 2, ys + HALF_WIDTH);
			}
		}, BOTTOM {
			@Override
			public Rectangle2D rect(double x, double y, double w, double h, double xs, double ys) {
				return new Rectangle2D(x + HALF_WIDTH + xs, y + h - HALF_WIDTH,
						w - WIDTH - xs * 2, ys + HALF_WIDTH);
			}
		}, LEFT {
			@Override
			public Rectangle2D rect(double x, double y, double w, double h, double xs, double ys) {
				return new Rectangle2D(x - xs, y + HALF_WIDTH + ys,
						xs + HALF_WIDTH, h - WIDTH - ys * 2);
			}
		}, RIGHT {
			@Override
			public Rectangle2D rect(double x, double y, double w, double h, double xs, double ys) {
				return new Rectangle2D(x + w - HALF_WIDTH, y + HALF_WIDTH + ys,
						xs + HALF_WIDTH, h - WIDTH - ys * 2);
			}
		};
		private static final int WIDTH = 10;
		private static final int HALF_WIDTH = WIDTH / 2;

		public abstract Rectangle2D rect(double x, double y, double w, double h, double xs, double ys);
	}

	static double alterSpeed(double speed, double amount, boolean toStop) {
		if (toStop) {
			amount = Math.abs(amount);
			if (speed > 0) {
				return Math.max(speed - amount, 0);
			} else {
				return Math.min(speed + amount, 0);
			}
		} else {
			return speed + amount;
		}
	}

	void collisionCheck(double elapsed);

	Rectangle2D getBounds(CollisionChecker collisionChecker, double elapsed);

	Optional<CollisionChecker> getCollisionLocation(Rectangle2D otherBounds, double elapsed);

	void capSpeed();

	double getXSpeed();

	void setXSpeed(double xSpeed);

	double getYSpeed();

	void setYSpeed(double ySpeed);
}
