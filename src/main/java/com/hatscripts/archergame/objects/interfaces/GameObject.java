package com.hatscripts.archergame.objects.interfaces;

import com.hatscripts.archergame.objects.Objects;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;

public interface GameObject extends Debuggable {
	void init();

	void tick(double elapsed);

	void render(GraphicsContext g);

	double getX();

	void setX(double x);

	double getY();

	void setY(double y);

	Rectangle2D getBounds();

	boolean contains(Point2D point);

	boolean isInBounds(Bounds bounds);

	Point2D getCenterPoint();

	double distanceTo(GameObject other);

	double distanceTo(Point2D point);

	double angleTo(Point2D point);

	Optional<GameObject> nearestObject();

	void setObjects(Objects objects);
}
