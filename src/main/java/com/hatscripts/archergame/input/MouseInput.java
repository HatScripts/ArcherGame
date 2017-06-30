package com.hatscripts.archergame.input;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MouseInput implements EventHandler<MouseEvent> {
	private Point2D mouseLocation = null;
	private boolean mouseOutsideWindow = true;
	private final Map<EventType<? extends MouseEvent>, ArrayList<EventHandler<MouseEvent>>> mouseEventHandlers;
	private final ReadOnlyDoubleProperty stageX;
	private final ReadOnlyDoubleProperty stageY;

	public MouseInput(ReadOnlyDoubleProperty stageX, ReadOnlyDoubleProperty stageY) {
		this.stageX = stageX;
		this.stageY = stageY;

		mouseEventHandlers = new HashMap<>();
		mouseEventHandlers.put(MouseEvent.MOUSE_PRESSED, new ArrayList<>());
		mouseEventHandlers.put(MouseEvent.MOUSE_RELEASED, new ArrayList<>());
		mouseEventHandlers.put(MouseEvent.MOUSE_CLICKED, new ArrayList<>());
		mouseEventHandlers.put(MouseEvent.MOUSE_ENTERED, new ArrayList<>());
		mouseEventHandlers.put(MouseEvent.MOUSE_EXITED, new ArrayList<>());
		mouseEventHandlers.put(MouseEvent.MOUSE_MOVED, new ArrayList<>());
		mouseEventHandlers.put(MouseEvent.MOUSE_DRAGGED, new ArrayList<>());

		addOnEntered(e -> mouseOutsideWindow = false);
		addOnExited(e -> mouseOutsideWindow = true);
	}

	@Override
	public void handle(MouseEvent e) {
		Optional.ofNullable(mouseEventHandlers.get(e.getEventType()))
				.ifPresent(handlers -> handlers.forEach(handler -> handler.handle(e)));
		mouseLocation = new Point2D(e.getX(), e.getY());
	}

	public void addOnPressed(EventHandler<MouseEvent> e) {
		mouseEventHandlers.get(MouseEvent.MOUSE_PRESSED).add(e);
	}

	public void addOnReleased(EventHandler<MouseEvent> e) {
		mouseEventHandlers.get(MouseEvent.MOUSE_RELEASED).add(e);
	}

	public void addOnClicked(EventHandler<MouseEvent> e) {
		mouseEventHandlers.get(MouseEvent.MOUSE_CLICKED).add(e);
	}

	public void addOnEntered(EventHandler<MouseEvent> e) {
		mouseEventHandlers.get(MouseEvent.MOUSE_ENTERED).add(e);
	}

	public void addOnExited(EventHandler<MouseEvent> e) {
		mouseEventHandlers.get(MouseEvent.MOUSE_EXITED).add(e);
	}

	public void addOnMoved(EventHandler<MouseEvent> e) {
		mouseEventHandlers.get(MouseEvent.MOUSE_MOVED).add(e);
	}

	public void addOnDragged(EventHandler<MouseEvent> e) {
		mouseEventHandlers.get(MouseEvent.MOUSE_DRAGGED).add(e);
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