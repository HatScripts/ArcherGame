package com.hatscripts.archergame.objects;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.Arrays;
import java.util.Optional;

public abstract class GameObject {
	protected final double width;
	protected final double height;
	protected final double maxSpeed;
	private final boolean solid;
	private final ObjectType type;
	protected double x;
	protected double y;
	protected double xSpeed;
	protected double ySpeed;
	private Objects objects;

	protected GameObject(ObjectType type, double x, double y) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.solid = type.isSolid();
		this.width = type.getWidth();
		this.height = type.getHeight();
		this.maxSpeed = type.getMaxSpeed();
	}

	protected enum CollisionChecker {
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

	protected static double alterSpeed(double current, double amount, boolean toStop) {
		if (toStop) {
			amount = Math.abs(amount);
			if (current > 0) {
				return current <= amount ? 0 : current - amount;
			} else {
				return current >= -amount ? 0 : current + amount;
			}
		} else {
			return current + amount;
		}
	}

	public void tick(double elapsed) {
		x += xSpeed * elapsed;
		y += ySpeed * elapsed;

		collisionCheck(elapsed);
		capSpeed();
	}

	public abstract void render(GraphicsContext g);

	public void renderDebug(GraphicsContext g, double elapsed) {
		g.save();
		Point2D center = getCenterPoint();
		g.setStroke(Color.RED);
		g.strokeRect(center.getX() - 2, center.getY() - 2, 4, 4);

		Optional<GameObject> optionalNearest = objects.nearestTo(this);
		optionalNearest.ifPresent(nearest -> {
			Point2D p2 = nearest.getCenterPoint();
			g.strokeLine(center.getX(), center.getY(), p2.getX(), p2.getY());
		});

		if (maxSpeed > 0) {
			g.setFill(Color.WHITE);
			g.setStroke(Color.WHITE);
			for (CollisionChecker collisionChecker : CollisionChecker.values()) {
				Rectangle2D r = getBounds(collisionChecker, elapsed);
				g.strokeRect(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
			}
			g.setTextAlign(TextAlignment.CENTER);
			g.setTextBaseline(VPos.TOP);
			g.fillText("xSpeed: " + xSpeed + "\nySpeed: " + ySpeed, center.getX(), y + height + 10);
		}
		g.restore();
	}

	public ObjectType getType() {
		return type;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	public double getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}

	public boolean isSolid() {
		return solid;
	}

	public Rectangle2D getBounds() {
		return new Rectangle2D(x, y, width, height);
	}

	private Rectangle2D getBounds(CollisionChecker collisionChecker, double elapsed) {
		return collisionChecker.rect(x, y, width, height, Math.abs(xSpeed) * elapsed, Math.abs(ySpeed) * elapsed);
	}

	public boolean contains(Point2D point) {
		return getBounds().contains(point);
	}

	public boolean isInBounds(javafx.geometry.Bounds bounds) {
		return bounds.intersects(bounds);
	}

	protected Point2D getCenterPoint() {
		return new Point2D(x + (width / 2), y + (height / 2));
	}

	public double distanceTo(GameObject other) {
		return distanceTo(other.getCenterPoint());
	}

	private double distanceTo(Point2D point) {
		return getCenterPoint().distance(point);
	}

	public double angleTo(Point2D point) {
		Point2D center = getCenterPoint();
		Point2D diff = center.subtract(point);
		double angle = Math.toDegrees(Math.atan2(diff.getY(), diff.getX()));
		angle += Math.ceil(-angle / 360) * 360;
		return angle;
	}

	private void collisionCheck(double elapsed) {
		// TODO: Improve this method. The CollisionChecker enum is probably unnecessary.
		for (Rectangle2D collision : objects.getCollisions(this)) {
			getCollisionLocation(collision, elapsed).ifPresent(collisionLoc -> {
				switch (collisionLoc) {
					case TOP:
						ySpeed = 0;
						y = collision.getMaxY();
						break;
					case BOTTOM:
						ySpeed = 0;
						y = collision.getMinY() - height;
						break;
					case LEFT:
						xSpeed = 0;
						x = collision.getMaxX();
						break;
					case RIGHT:
						xSpeed = 0;
						x = collision.getMinX() - width;
						break;
				}
			});
		}
	}

	private Optional<CollisionChecker> getCollisionLocation(Rectangle2D otherBounds, double elapsed) {
		return Arrays.stream(CollisionChecker.values())
				.filter(checker -> getBounds(checker, elapsed).intersects(otherBounds))
				.findAny();
	}

	private void capSpeed() {
		xSpeed = Math.min(xSpeed, maxSpeed);
		xSpeed = Math.max(xSpeed, -maxSpeed);
		ySpeed = Math.min(ySpeed, maxSpeed);
		ySpeed = Math.max(ySpeed, -maxSpeed);
	}

	void setObjects(Objects objects) {
		this.objects = objects;
	}
}