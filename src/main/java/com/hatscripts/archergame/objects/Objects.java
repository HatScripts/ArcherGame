package com.hatscripts.archergame.objects;

import com.hatscripts.archergame.objects.list.Block;
import com.hatscripts.archergame.objects.list.Player;
import com.hatscripts.archergame.window.Game;

import java.awt.*;
import java.util.LinkedList;

public class Objects {

	public static final LinkedList<GameObject> OBJECTS = new LinkedList<>();

	public void tick() {
		for (GameObject object : OBJECTS) {
			object.tick(OBJECTS);
		}
	}

	public void render(Graphics g) {
		for (GameObject object : OBJECTS) {
			if (object.isOnScreen()) {
				object.render(g);
			}
		}
	}

	public void add(GameObject object) {
		OBJECTS.add(object);
	}

	public void remove(GameObject object) {
		OBJECTS.remove(object);
	}

	public void createLevel() {
		int blockWidth = ObjectType.BLOCK.getWidth();
		int blockHeight = ObjectType.BLOCK.getHeight();

		// Top
		for (int x = 0; x < Game.WIDTH; x += blockWidth) {
			add(new Block(x, 0));
		}
		// Bottom
		for (int x = 0; x < Game.WIDTH; x += blockWidth) {
			add(new Block(x, Game.HEIGHT - blockHeight));
		}
		// Left
		for (int y = blockHeight; y < Game.HEIGHT - blockHeight; y += blockHeight) {
			add(new Block(0, y));
		}
		// Right
		for (int y = blockHeight; y < Game.HEIGHT - blockHeight; y += blockHeight) {
			add(new Block(Game.WIDTH - blockWidth, y));
		}
	}

	public Player getPlayer() {
		for (GameObject object : OBJECTS) {
			if (object.getType() == ObjectType.PLAYER) {
				return (Player) object;
			}
		}
		return null;
	}
}
