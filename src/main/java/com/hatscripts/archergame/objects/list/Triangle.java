package com.hatscripts.archergame.objects.list;

import com.hatscripts.archergame.objects.AbstractGameObject;
import com.hatscripts.archergame.objects.interfaces.Solid;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends AbstractGameObject implements Solid {
	// TODO: Make collision box triangular
	private final double[] xPoints;
	private final double[] yPoints;

	private Triangle(double x, double y, double width, double height) {
		super(x, y, width, height);
		xPoints = new double[]{x, x + width / 2, x + width};
		yPoints = new double[]{y + height, y, y + height};
	}

	@Override
	public void render(GraphicsContext g) {
		g.setFill(Color.BLACK);
		g.fillPolygon(xPoints, yPoints, 3);
	}
}
