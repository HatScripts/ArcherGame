package com.hatscripts.archergame.objects.list;

import com.hatscripts.archergame.objects.GameObject;
import com.hatscripts.archergame.objects.ObjectType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Block extends GameObject {
	public Block(double x, double y) {
		super(ObjectType.BLOCK, x, y);
	}

	@Override
	public void render(GraphicsContext g) {
		g.setFill(Color.BLACK);
		g.fillRect(x, y, width, height);
	}
}
