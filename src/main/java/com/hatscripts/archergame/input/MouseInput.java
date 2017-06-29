package com.hatscripts.archergame.input;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class MouseInput implements EventHandler<MouseEvent> {
	private Point2D mouseLocation = null;
	private boolean mouseOutsideWindow = true;
	private final Map<EventType<? extends MouseEvent>, Consumer<MouseEvent>> mouseEventMethods;
	private final ReadOnlyDoubleProperty stageX;
	private final ReadOnlyDoubleProperty stageY;

	public MouseInput(ReadOnlyDoubleProperty stageX, ReadOnlyDoubleProperty stageY) {
		this.stageX = stageX;
		this.stageY = stageY;

		Map<EventType<? extends MouseEvent>, Consumer<MouseEvent>> map = new HashMap<>();
		map.put(MouseEvent.MOUSE_PRESSED, this::pressed);
		map.put(MouseEvent.MOUSE_RELEASED, this::released);
		map.put(MouseEvent.MOUSE_CLICKED, this::clicked);
		map.put(MouseEvent.MOUSE_ENTERED, this::entered);
		map.put(MouseEvent.MOUSE_EXITED, this::exited);
		map.put(MouseEvent.MOUSE_MOVED, this::moved);
		map.put(MouseEvent.MOUSE_DRAGGED, this::dragged);
		mouseEventMethods = Collections.unmodifiableMap(map);
	}

	@Override
	public void handle(MouseEvent e) {
		Optional.ofNullable(mouseEventMethods.get(e.getEventType()))
				.ifPresent(consumer -> consumer.accept(e));
		mouseLocation = new Point2D(e.getX(), e.getY());
	}

	private void pressed(MouseEvent e) {
		System.out.println("Mouse pressed at: " + e.getX() + "," + e.getY());
	}

	private void released(MouseEvent e) {
		System.out.println("Mouse released at: " + e.getX() + "," + e.getY());
	}

	private void clicked(MouseEvent e) {
	}

	private void entered(MouseEvent e) {
		mouseOutsideWindow = false;
	}

	private void exited(MouseEvent e) {
		mouseOutsideWindow = true;
	}

	private void moved(MouseEvent e) {
	}

	private void dragged(MouseEvent e) {
	}

	public Point2D location() {
		if (mouseOutsideWindow) {
			// TODO: Find a non-AWT way to get mouse location when it's outside window
			Point mouse = MouseInfo.getPointerInfo().getLocation();
			return new Point2D(mouse.x - stageX.get(), mouse.y - stageY.get());
		}
		return mouseLocation;
	}

	@Override
	public String toString() {
		Point2D location = location();
		return String.format("%.0f,%.0f", location.getX(), location.getY());
	}
}