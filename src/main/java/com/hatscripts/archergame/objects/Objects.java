package com.hatscripts.archergame.objects;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;
import com.hatscripts.archergame.objects.interfaces.GameObject;
import com.hatscripts.archergame.objects.interfaces.Solid;
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
	private final SimpleBooleanProperty debug = new SimpleBooleanProperty();
	private final KeyInput keyInput;
	private final MouseInput mouseInput;

	public Objects(KeyInput keyInput, MouseInput mouseInput) {
		this.keyInput = keyInput;
		this.mouseInput = mouseInput;
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

	public void render(GraphicsContext g, Bounds gameBounds, double elapsed) {
		for (GameObject object : objectList) {
			if (object.isInBounds(gameBounds)) {
				object.render(g);
				if (debug.get()) {
					object.renderDebugDefault(g, elapsed);
				}
			}
		}
	}

	public GameObject add(ObjectType type, double x, double y) {
		return add(type, x, y, -1, -1);
	}

	public GameObject add(ObjectType type, Rectangle2D rect) {
		return add(type, rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
	}

	public GameObject add(ObjectType type, double x, double y, double width, double height) {
		GameObject object = type.createObject(x, y, width, height, keyInput, mouseInput);
		object.setObjects(this);
		object.init();
		objectList.add(object);
		return object;
	}

	public void remove(GameObject object) {
		objectList.remove(object);
	}

	public void remove(Rectangle2D rect) {
		objectList.stream()
				.filter(object -> object.getBounds().intersects(rect))
				.collect(Collectors.toList())
				.forEach(this::remove);
	}

	public Optional<GameObject> nearestTo(GameObject object) {
		return nearestTo(object, objectList.stream()
				.filter(other -> other != object));
	}

	public Optional<GameObject> nearestTo(GameObject object, Class<? extends GameObject> clazz) {
		return nearestTo(object,
				objectList.stream().filter(other -> other != object
						&& clazz.isInstance(other)));
	}

	List<Rectangle2D> getCollisions(Solid object) {
		return objectList.stream()
				.filter(other -> other != object
						&& other instanceof Solid
						&& object.getBounds().intersects(other.getBounds()))
				.map(GameObject::getBounds)
				.collect(Collectors.toList());
	}

	public SimpleBooleanProperty debugProperty() {
		return debug;
	}
}
