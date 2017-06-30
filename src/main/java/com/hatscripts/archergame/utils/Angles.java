package com.hatscripts.archergame.utils;

import javafx.geometry.Point2D;

public final class Angles {
	public static double normalise(double angle) {
		return angle + Math.ceil(-angle / 360) * 360;
	}

	public static double angleBetween(double x1, double y1, double x2, double y2) {
		double xDiff = x1 - x2;
		double yDiff = y1 - y2;
		double angle = Math.toDegrees(Math.atan2(yDiff, xDiff));
		return normalise(angle);
	}

	public static double angleBetween(Point2D p1, Point2D p2) {
		return angleBetween(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	public static double average(double a, double b) {
		return average(a, b, 0.5);
	}

	public static double average(double a, double b, double frac) {
		double diff = ((a - b + 180 + 360) % 360) - 180;
		return (360 + b + (diff * (1 - frac))) % 360;
	}
}
