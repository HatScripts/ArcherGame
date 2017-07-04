package com.hatscripts.archergame.utils;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public final class Rectangles {
	public static Rectangle2D pointsToRect(Point2D p1, Point2D p2) {
		return pointsToRect(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	private static Rectangle2D pointsToRect(double x1, double y1, double x2, double y2) {
		double x = Math.min(x1, x2);
		double y = Math.min(y1, y2);
		double w = Math.max(x1, x2) - x;
		double h = Math.max(y1, y2) - y;
		return new Rectangle2D(x, y, w, h);
	}
}
