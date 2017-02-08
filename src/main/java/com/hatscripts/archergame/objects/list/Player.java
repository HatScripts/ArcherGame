package com.hatscripts.archergame.objects.list;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;
import com.hatscripts.archergame.objects.GameObject;
import com.hatscripts.archergame.objects.ObjectType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Map;

public class Player extends GameObject {
	private static final Color SKIN_COLOR = new Color(240, 194, 174);
	private static final Color HAIR_COLOR = new Color(54, 45, 35);
	private static final Color PUPIL_COLOR = new Color(74, 74, 74);
	private static final Color TOP_COLOR = new Color(42, 42, 42);
	private static final Dimension EYE = new Dimension(6, 4);
	private static final Dimension PUPIL = new Dimension((int) (EYE.width / 1.5), EYE.height);
	private static final Dimension MOUTH = new Dimension(5, 1);

	public Player(float x, float y) {
		super(ObjectType.PLAYER, x, y);
	}

	private enum Looking {
		LEFT, CENTRE, RIGHT;

		public static Looking fromEye(int x) {
			x += (EYE.width / 2);
			int mouseX = MouseInput.getMouseLocation().x;

			if (x > mouseX + 5) {
				return LEFT;
			}
			if (x < mouseX - 5) {
				return RIGHT;
			}
			return CENTRE;
		}
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		for (Map.Entry<Integer, Boolean> entry : KeyInput.KEYS_HELD.entrySet()) {
			int key = entry.getKey();
			boolean slow = !entry.getValue() || KeyInput.isKeyHeld(KeyInput.getOppositeKey(key));
			float stepSize = slow ? 0.1f : 0.3f;
			switch (key) {
				case KeyEvent.VK_A: xSpeed = alterSpeed(xSpeed, -stepSize, slow); break;
				case KeyEvent.VK_D: xSpeed = alterSpeed(xSpeed, +stepSize, slow); break;
				case KeyEvent.VK_W: ySpeed = alterSpeed(ySpeed, -stepSize, slow); break;
				case KeyEvent.VK_S: ySpeed = alterSpeed(ySpeed, +stepSize, slow); break;
			}
		}

		x += xSpeed;
		y += ySpeed;

		collisionCheck();
		capSpeed();
		bringOntoScreen();
	}

	@Override
	public void render(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		int x = (int) this.x;
		int y = (int) this.y;
		/*g.setColor(Color.BLUE);
		g.fillRect((int) x, (int) y, width, height);*/

		g.setColor(SKIN_COLOR);
		//noinspection SuspiciousNameCombination
		g.fillRect(x, y, width, width);

		/* Eyes */
		int leftEyeX = x + 6;
		int rightEyeX = x + 20;
		int eyeY = y + (width / 2);
		drawEye(g, leftEyeX, eyeY, Looking.fromEye(leftEyeX));
		drawEye(g, rightEyeX, eyeY, Looking.fromEye(rightEyeX));

		/* Mouth */
		drawMouth(g, x + (width / 2) - (MOUTH.width / 2), y + (width / 4) * 3 + 1);

		/* Hair */
		int xPoly[] = {x, x, x + 24, x + width, x + width};
		int yPoly[] = {y, y + 20, y + 10, y + 18, y};
		Polygon hairPolygon = new Polygon(xPoly, yPoly, xPoly.length);

		g.setColor(HAIR_COLOR);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g.fillPolygon(hairPolygon);

		/* Top */
		g.setColor(TOP_COLOR);
		g.fillRect(x, y + width, width, 20);

		/* Outline */
		drawInnerShadow(g, x, y, width, width, 0.3f, 3);
		drawInnerShadow(g, x, y + width, width, 20, 0.3f, 3);

		/* Debug *
		final Point p = getCenterPoint();
		g.setColor(Color.RED);
		g.drawRect(p.x - 2, p.y - 2, 4, 4);

		final GameObject nearest = getNearest();
		if (nearest != null) {
			final Point p2 = nearest.getCenterPoint();
			g.drawLine(p.x, p.y, p2.x, p2.y);
		}

		final Point mouse = MouseInput.getMouseLocation();
		if (mouse != null) {
			g.drawLine(p.x, p.y, mouse.x, mouse.y);
		}

		g.setColor(Color.WHITE);

		for (final Bounds bounds : Bounds.values()) {
			final Rectangle r = getBounds(bounds);
			g.drawRect(r.x, r.y, r.width, r.height);
		}

		BetterGraphics.drawString(g, "xSpeed: " + xSpeed
				+ "\nySpeed: " + ySpeed, (int) x, (int) y);
		/* Debug */
	}

	private static void drawInnerShadow(Graphics2D g, int x, int y, int width, int height, float opacity, int size) {
		g.setColor(Color.BLACK);

		float stepSize = opacity / size;

		for (int i = 0; i < size; i++) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (size - i) * stepSize));
			g.drawRect(x + i, y + i, width - (i * 2 + 1), height - (i * 2 + 1));
		}
	}

	private static void drawEye(Graphics2D g, int x, int y, Looking looking) {
		/* Sclera */
		g.setColor(Color.WHITE);
		g.fillRect(x, y, EYE.width, EYE.height);

		/* Pupil */
		g.setColor(PUPIL_COLOR);
		int pupilX = 0;
		switch (looking) {
			case LEFT:
				pupilX = x;
				break;
			case CENTRE:
				pupilX = x + (PUPIL.width / 4);
				break;
			case RIGHT:
				pupilX = x + (PUPIL.width / 2);
				break;
		}
		//final int pupilX = looking == Looking.LEFT ? x : x + (PUPIL.width / 2);
		g.fillRect(pupilX, y, PUPIL.width, PUPIL.height);

		/* Eyebrow */
		g.setColor(HAIR_COLOR);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g.fillRect(x, y - 2, EYE.width, 2);

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	private static void drawMouth(Graphics2D g, int x, int y) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		/* Sclera */
		g.setColor(Color.BLACK);
		g.fillRect(x, y, MOUTH.width, MOUTH.height);
		g.fillRect(x + 1, y, MOUTH.width - 2, MOUTH.height);
	}
}
