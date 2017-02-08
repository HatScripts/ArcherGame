package com.hatscripts.archergame.input;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {

	private static Point mouseLocation;
	private static boolean mouseOutsideWindow = true;

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed at: " + e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("Mouse released at: " + e.getPoint());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseOutsideWindow = true;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseLocation = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseLocation = e.getPoint();
		mouseOutsideWindow = false;
	}

	public static Point getMouseLocation() {
		if (mouseOutsideWindow) {
			Point mouse = MouseInfo.getPointerInfo().getLocation();
			Point window = com.hatscripts.archergame.window.Window.getLocation();
			mouseLocation = new Point(mouse.x - window.x, mouse.y - window.y);
		}
		return mouseLocation;
	}
}