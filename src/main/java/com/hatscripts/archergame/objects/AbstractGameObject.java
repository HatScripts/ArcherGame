package com.hatscripts.archergame.objects;

import com.hatscripts.archergame.objects.interfaces.GameObject;
import com.hatscripts.archergame.utils.Angles;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Optional;

public abstract class AbstractGameObject implements GameObject {
	protected final double width;
	protected final double height;
	protected double x;
	protected double y;
	protected Objects objects;

	protected AbstractGameObject(double x, double y,
								 double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void init() {

	}

	@Override
	public void tick(double elapsed) {

	}

	@Override
	public void renderDebug(GraphicsContext g, double elapsed) {
		Point2D center = getCenterPoint();
		g.strokeRect(center.getX() - 2, center.getY() - 2, 4, 4);
		g.strokeRect(x, y, width, height);

		nearestObject().ifPresent(nearest -> {
			g.setStroke(Color.MAGENTA);
			Point2D p2 = nearest.getCenterPoint();
			g.strokeLine(center.getX(), center.getY(), p2.getX(), p2.getY());
		});
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D(x, y, width, height);
	}

	@Override
	public boolean contains(Point2D point) {
		return getBounds().contains(point);
	}

	@Override
	public boolean isInBounds(javafx.geometry.Bounds bounds) {
		return bounds.intersects(bounds);
	}

	@Override
	public Point2D getCenterPoint() {
		return new Point2D(x + (width / 2), y + (height / 2));
	}

	@Override
	public double distanceTo(GameObject other) {
		return distanceTo(other.getCenterPoint());
	}

	@Override
	public double distanceTo(Point2D point) {
		return getCenterPoint().distance(point);
	}

	@Override
	public double angleTo(Point2D point) {
		return Angles.angleBetween(getCenterPoint(), point);
	}

	@Override
	public Optional<GameObject> nearestObject() {
		return objects.nearestTo(this);
	}

	@Override
	public void setObjects(Objects objects) {
		this.objects = objects;
	}
}