package com.hatscripts.archergame.objects;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;
import com.hatscripts.archergame.objects.interfaces.GameObject;
import com.hatscripts.archergame.objects.list.*;
import javafx.geometry.Dimension2D;

import java.lang.reflect.Constructor;

public enum ObjectType {
	DEBUG(Debug.class, "Debug", new Dimension2D(0, 0)),
	PLAYER(Player.class, "You", new Dimension2D(32, 52)),
	BLOCK(Block.class, "Block", new Dimension2D(32, 32)),
	CIRCLE(Circle.class, "Circle", new Dimension2D(32, 32)),
	TRIANGLE(Triangle.class, "Triangle", new Dimension2D(32, 32));
	private final Class<? extends GameObject> clazz;
	private final String name;
	private final Dimension2D size;

	ObjectType(Class<? extends GameObject> clazz, String name, Dimension2D size) {
		this.clazz = clazz;
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public Dimension2D getSize() {
		return size;
	}

	public GameObject createObject(double x, double y, double width, double height,
								   KeyInput keyInput, MouseInput mouseInput) {
		@SuppressWarnings("unchecked") Constructor<? extends GameObject> constructor =
				(Constructor<? extends GameObject>) clazz.getDeclaredConstructors()[0];
		if (!constructor.isAccessible()) {
			constructor.setAccessible(true);
		}
		width = width != -1 ? width : size.getWidth();
		height = height != -1 ? height : size.getHeight();
		try {
			switch (constructor.getParameterCount()) {
				case 4:
					return constructor.newInstance(x, y, width, height);
				case 6:
					return constructor.newInstance(x, y, width, height,
							keyInput, mouseInput);
				default:
					throw new AssertionError();
			}
		} catch (ReflectiveOperationException e) {
			throw new AssertionError(e);
		}
	}
}
