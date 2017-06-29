package com.hatscripts.archergame.objects;

import com.hatscripts.archergame.objects.interfaces.Mobile;
import com.hatscripts.archergame.objects.interfaces.Solid;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.Arrays;
import java.util.Optional;

public abstract class AbstractMobileObject extends AbstractGameObject implements Mobile, Solid {
	protected final double maxSpeed;
	protected double xSpeed;
	protected double ySpeed;

	protected AbstractMobileObject(double x, double y,
								   double width, double height, double maxSpeed) {
		super(x, y, width, height);
		this.maxSpeed = maxSpeed;
	}

	@Override
	public void tick(double elapsed) {
		x += xSpeed * elapsed;
		y += ySpeed * elapsed;

		collisionCheck(elapsed);
		capSpeed();
	}

	@Override
	public void renderDebug(GraphicsContext g, double elapsed) {
		super.renderDebug(g, elapsed);
		for (CollisionChecker collisionChecker : CollisionChecker.values()) {
			Rectangle2D r = getBounds(collisionChecker, elapsed);
			g.setStroke(Color.RED);
			g.strokeRect(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
		}
		g.setTextAlign(TextAlignment.CENTER);
		g.setTextBaseline(VPos.TOP);
		g.fillText("xSpeed: " + xSpeed + "\nySpeed: " + ySpeed, getCenterPoint().getX(), y + height + 10);
	}

	@Override
	public void collisionCheck(double elapsed) {
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

	@Override
	public Rectangle2D getBounds(CollisionChecker collisionChecker, double elapsed) {
		return collisionChecker.rect(x, y, width, height, Math.abs(xSpeed) * elapsed, Math.abs(ySpeed) * elapsed);
	}

	@Override
	public Optional<CollisionChecker> getCollisionLocation(Rectangle2D otherBounds, double elapsed) {
		return Arrays.stream(CollisionChecker.values())
				.filter(checker -> getBounds(checker, elapsed).intersects(otherBounds))
				.findAny();
	}

	@Override
	public void capSpeed() {
		xSpeed = Math.min(xSpeed, maxSpeed);
		xSpeed = Math.max(xSpeed, -maxSpeed);
		ySpeed = Math.min(ySpeed, maxSpeed);
		ySpeed = Math.max(ySpeed, -maxSpeed);
	}

	@Override
	public double getXSpeed() {
		return xSpeed;
	}

	@Override
	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	@Override
	public double getYSpeed() {
		return ySpeed;
	}

	@Override
	public void setYSpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
}
