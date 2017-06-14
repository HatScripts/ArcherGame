package com.hatscripts.archergame.input;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class MouseInput implements EventHandler<MouseEvent> {
	private Point2D mouseLocation = Point2D.ZERO;
	private boolean mouseOutsideWindow;

	/**
	 * Invoked when a specific event of the type for which this handler is
	 * registered happens.
	 *
	 * @param e the event which occurred
	 */
	@Override
	public void handle(MouseEvent e) {
		switch (e.getEventType().getName()) {
			case "MOUSE_CLICKED":
				mouseClicked(e);
				break;
			case "MOUSE_PRESSED":
				mousePressed(e);
				break;
			case "MOUSE_RELEASED":
				mouseReleased(e);
				break;
			case "MOUSE_MOVED":
				mouseMoved(e);
				break;
			case "MOUSE_DRAGGED":
				mouseDragged(e);
				break;
			case "MOUSE_ENTERED":
				mouseEntered(e);
				break;
			case "MOUSE_EXITED":
				mouseExited(e);
				break;
		}
		mouseLocation = new Point2D(e.getX(), e.getY());
	}

	private void mouseClicked(MouseEvent e) {

	}

	private void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed at: " + e.getX() + "," + e.getY());
	}

	private void mouseReleased(MouseEvent e) {
		System.out.println("Mouse released at: " + e.getX() + "," + e.getY());
	}

	private void mouseEntered(MouseEvent e) {
		mouseOutsideWindow = false;
	}

	private void mouseExited(MouseEvent e) {
		mouseOutsideWindow = true;
	}

	private void mouseDragged(MouseEvent e) {
	}

	private void mouseMoved(MouseEvent e) {
	}

	public Point2D mouseLocation() {
		// TODO: Allow mouse location detection when outside window bounds
		return mouseLocation;
	}

	public boolean isMouseOutsideWindow() {
		return mouseOutsideWindow;
	}
}