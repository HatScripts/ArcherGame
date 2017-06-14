package com.hatscripts.archergame.objects;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Objects {
	private final LinkedList<GameObject> objectList = new LinkedList<>();
	private final SimpleBooleanProperty debug;

	public Objects(SimpleBooleanProperty debug) {
		this.debug = debug;
	}

	private static Optional<GameObject> nearestTo(GameObject object, Stream<GameObject> stream) {
		return stream.reduce(BinaryOperator.minBy((o1, o2) -> {
			double d1 = object.distanceTo(o1);
			double d2 = object.distanceTo(o2);
			return Double.compare(d1, d2);
		}));
	}

	public void tick(double elapsed) {
		for (GameObject object : objectList) {
			object.tick(elapsed);
		}
	}

	public void render(GraphicsContext gc, Bounds gameBounds, double elapsed) {
		for (GameObject object : objectList) {
			if (object.isInBounds(gameBounds)) {
				object.render(gc);
				if (debug.get()) {
					object.renderDebug(gc, elapsed);
				}
			}
		}
	}

	public void add(GameObject object) {
		object.setObjects(this);
		objectList.add(object);
	}

	public void remove(GameObject object) {
		objectList.remove(object);
	}

	public Optional<GameObject> nearestTo(GameObject object) {
		return nearestTo(object, objectList.stream()
				.filter(other -> other != object));
	}

	public Optional<GameObject> nearestTo(GameObject object, @NotNull ObjectType type) {
		return nearestTo(object,
				objectList.stream().filter(other -> other != object
						&& type == other.getType()));
	}

	List<Rectangle2D> getCollisions(GameObject object) {
		if (!object.isSolid()) {
			throw new IllegalArgumentException("Object must be solid to perform a collision check");
		}
		return objectList.stream()
				.filter(other -> other != object
						&& other.isSolid()
						&& object.getBounds().intersects(other.getBounds()))
				.map(GameObject::getBounds)
				.collect(Collectors.toList());
	}
}
