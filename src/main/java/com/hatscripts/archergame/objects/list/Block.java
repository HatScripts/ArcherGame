package com.hatscripts.archergame.objects.list;

import com.hatscripts.archergame.objects.GameObject;
import com.hatscripts.archergame.objects.ObjectType;

import java.awt.*;
import java.util.LinkedList;

public class Block extends GameObject {

	public Block(float x, float y) {
		super(ObjectType.BLOCK, x, y);
	}

	@Override
	public void tick(LinkedList<GameObject> object) {

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect((int) x, (int) y, width, height);

		Point p = getCenterPoint();
		g.setColor(Color.RED);
		g.drawRect(p.x - 2, p.y - 2, 4, 4);
	}
}
