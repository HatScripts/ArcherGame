package com.hatscripts.archergame.objects.list;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;
import com.hatscripts.archergame.input.MovementKey;
import com.hatscripts.archergame.objects.GameObject;
import com.hatscripts.archergame.objects.ObjectType;
import com.sun.javafx.geom.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends GameObject {
	// TODO: Give the player a bow so that the title "Archer Game" is accurate
	private static final Color COLOR_SKIN = Color.color(0.94, 0.76, 0.68);
	private static final Color COLOR_HAIR = Color.color(0.21, 0.18, 0.14);
	private static final Color COLOR_PUPIL = Color.color(0.29, 0.29, 0.29);
	private static final Color COLOR_SCLERA = Color.WHITE;
	private static final Color COLOR_MOUTH = COLOR_SKIN.interpolate(Color.BLACK, 0.5);
	private static final Color COLOR_TOP = Color.color(0.16, 0.16, 0.16);
	private static final Dimension2D EYE = new Dimension2D(6, 4);
	private static final Dimension2D PUPIL = new Dimension2D(EYE.width / 1.5f, EYE.height);
	private static final Dimension2D MOUTH = new Dimension2D(5, 1);
	private final KeyInput keyInput;
	private final MouseInput mouseInput;

	public Player(double x, double y, KeyInput keyInput, MouseInput mouseInput) {
		super(ObjectType.PLAYER, x, y);
		this.keyInput = keyInput;
		this.mouseInput = mouseInput;
	}

	private static void drawEye(GraphicsContext g, double x, double y, MouseInput mouseInput) {
		// TODO: Add eyelids; blinking, squinting, etc.

		/* Sclera */
		g.setFill(COLOR_SCLERA);
		g.fillRect(x, y, EYE.width, EYE.height);

		/* Pupil */
		// TODO: Make pupil follow cursor vertically as well, not just horizontally.
		g.setFill(COLOR_PUPIL);
		double pupilX = mouseInput.mouseLocation().getX();
		pupilX = Math.max(pupilX, x);
		pupilX = Math.min(pupilX, x + EYE.width - PUPIL.width);
		g.fillRect(pupilX, y, PUPIL.width, PUPIL.height);

		/* Eyebrow */
		g.setFill(COLOR_HAIR);
		g.fillRect(x, y - 2, EYE.width, 2);
	}

	private static void drawMouth(GraphicsContext g, double x, double y) {
		g.setFill(COLOR_MOUTH);
		g.fillRect(x, y, MOUTH.width, MOUTH.height);
		g.fillRect(x + 1, y, MOUTH.width - 2, MOUTH.height);
	}

	@Override
	public void tick(double elapsed) {
		for (MovementKey key : MovementKey.values()) {
			boolean slow = !keyInput.isKeyHeld(key) || keyInput.isKeyHeld(key.opposite());
			double stepSize = slow ? maxSpeed / 50 : maxSpeed / 10;
			xSpeed = alterSpeed(xSpeed, stepSize * key.getXAdjustment(), slow);
			ySpeed = alterSpeed(ySpeed, stepSize * key.getYAdjustment(), slow);
		}
		super.tick(elapsed);
	}

	@Override
	public void render(GraphicsContext g) {
		g.setFill(COLOR_SKIN);
		g.fillRect(x, y, width, width);

		/* Eyes */
		double eyeY = y + (width / 2);
		double leftEyeX = x + 6;
		drawEye(g, leftEyeX, eyeY, mouseInput);
		double rightEyeX = x + 20;
		drawEye(g, rightEyeX, eyeY, mouseInput);

		/* Mouth */
		drawMouth(g, x + (width / 2) - (MOUTH.width / 2), y + (width / 4) * 3 + 1);

		/* Hair */
		g.setFill(COLOR_HAIR);
		double[] xPoints = {x, x, x + 24, x + width, x + width};
		double[] yPoints = {y, y + 20, y + 10, y + 18, y};
		g.fillPolygon(xPoints, yPoints, xPoints.length);

		/* Top */
		g.setFill(COLOR_TOP);
		g.fillRect(x, y + width, width, 20);
	}

	@Override
	public void renderDebug(GraphicsContext g, double elapsed) {
		super.renderDebug(g, elapsed);
		Point2D center = getCenterPoint();
		Point2D mouse = mouseInput.mouseLocation();
		g.strokeLine(center.getX(), center.getY(), mouse.getX(), mouse.getY());
	}
}
