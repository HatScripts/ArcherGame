package com.hatscripts.archergame.objects.list;

import com.hatscripts.archergame.objects.AbstractGameObject;
import com.hatscripts.archergame.objects.interfaces.Solid;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends AbstractGameObject implements Solid {
	// TODO: Make collision box circular
	private Circle(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	public void render(GraphicsContext g) {
		g.setFill(Color.BLACK);
		g.fillOval(x, y, width, height);
	}
}
