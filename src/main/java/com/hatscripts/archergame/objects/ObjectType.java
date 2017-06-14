package com.hatscripts.archergame.objects;

import javafx.geometry.Dimension2D;

public enum ObjectType {
	PLAYER("You", true, new Dimension2D(32, 52), 200),
	BLOCK("Block", true, new Dimension2D(32, 32), 0);
	private final String name;
	private final boolean solid;
	private final double width;
	private final double height;
	private final double maxSpeed;

	ObjectType(String name, boolean solid, Dimension2D dimension, double maxSpeed) {
		this.name = name;
		this.solid = solid;
		this.width = dimension.getWidth();
		this.height = dimension.getHeight();
		this.maxSpeed = maxSpeed;
	}

	public String getName() {
		return name;
	}

	public boolean isSolid() {
		return solid;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}
}
