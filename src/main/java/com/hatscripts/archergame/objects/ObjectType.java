package com.hatscripts.archergame.objects;

import java.awt.*;

public enum ObjectType {
	PLAYER("You", true, new Dimension(32, 52), 5),
	BLOCK("Block", true, new Dimension(32, 32), 0);

	private final String name;
	private final boolean solid;
	private final int width;
	private final int height;
	private final float maxSpeed;

	ObjectType(String name, boolean solid, Dimension dimension, float maxSpeed) {
		this.name = name;
		this.solid = solid;
		this.width = (int) dimension.getWidth();
		this.height = (int) dimension.getHeight();
		this.maxSpeed = maxSpeed;
	}

	public String getName() {
		return name;
	}

	public boolean isSolid() {
		return solid;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}
}
