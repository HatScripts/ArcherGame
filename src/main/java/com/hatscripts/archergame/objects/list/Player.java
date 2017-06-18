package com.hatscripts.archergame.objects.list;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;
import com.hatscripts.archergame.input.MovementKey;
import com.hatscripts.archergame.objects.GameObject;
import com.hatscripts.archergame.objects.ObjectType;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Player extends GameObject {
	private static final class Colors {
		private static final Color SKIN = Color.color(0.94, 0.76, 0.68);
		private static final Color MOUTH = SKIN.interpolate(Color.BLACK, 0.5);
		private static final Color HAIR = Color.color(0.21, 0.18, 0.14);
		private static final Color PUPIL = Color.color(0.29, 0.29, 0.29);
		private static final Color SCLERA = Color.WHITE;
		private static final Color TOP = Color.color(0.16, 0.16, 0.16);
		private static final Color BOW = Color.color(0.5, 0.4, 0.3);
		private static final Color BOWSTRING = Color.color(1, 1, 1, 0.75);
	}

	private static final Dimension2D EYE = new Dimension2D(6, 4);
	private static final Dimension2D PUPIL = new Dimension2D(EYE.getWidth() / 1.5, EYE.getHeight());
	private static final Dimension2D MOUTH = new Dimension2D(5, 1);
	private static final double BOW_SIZE = ObjectType.PLAYER.getHeight();
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
		g.setFill(Colors.SCLERA);
		g.fillRect(x, y, EYE.getWidth(), EYE.getHeight());

		/* Pupil */
		// TODO: Make pupil follow cursor vertically as well, not just horizontally.
		g.setFill(Colors.PUPIL);
		double pupilX = mouseInput.location().getX();
		pupilX = Math.max(pupilX, x);
		pupilX = Math.min(pupilX, x + EYE.getWidth() - PUPIL.getWidth());
		g.fillRect(pupilX, y, PUPIL.getWidth(), PUPIL.getHeight());

		/* Eyebrow */
		g.setFill(Colors.HAIR);
		g.fillRect(x, y - 2, EYE.getWidth(), 2);
	}

	private static void drawMouth(GraphicsContext g, double x, double y) {
		g.setFill(Colors.MOUTH);
		g.fillRect(x, y, MOUTH.getWidth(), MOUTH.getHeight());
		g.fillRect(x + 1, y, MOUTH.getWidth() - 2, MOUTH.getHeight());
	}

	private void drawBow(GraphicsContext g) {
		g.save();
		Point2D center = getCenterPoint();
		double angleToMouse = this.angleTo(mouseInput.location());
		double x = center.getX() - BOW_SIZE / 2;
		double y = center.getY() - BOW_SIZE / 2;
		g.setStroke(Colors.BOWSTRING);
		g.setLineWidth(1);
		int arcExtent = 100;
		double startAngle = angleToMouse - arcExtent / 2 + 90;
		g.strokeArc(x, y, BOW_SIZE, BOW_SIZE, startAngle, arcExtent, ArcType.CHORD);
		g.setStroke(Colors.BOW);
		g.setLineWidth(3);
		g.strokeArc(x, y, BOW_SIZE, BOW_SIZE, startAngle, arcExtent, ArcType.OPEN);
		g.restore();
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
		g.setFill(Colors.SKIN);
		g.fillRect(x, y, width, width);

		/* Eyes */
		double eyeY = y + (width / 2);
		double leftEyeX = x + 6;
		drawEye(g, leftEyeX, eyeY, mouseInput);
		double rightEyeX = x + 20;
		drawEye(g, rightEyeX, eyeY, mouseInput);

		/* Mouth */
		drawMouth(g, x + (width / 2) - (MOUTH.getWidth() / 2), y + (width / 4) * 3 + 1);

		/* Hair */
		g.setFill(Colors.HAIR);
		double[] xPoints = {x, x, x + 24, x + width, x + width};
		double[] yPoints = {y, y + 20, y + 10, y + 18, y};
		g.fillPolygon(xPoints, yPoints, xPoints.length);

		/* Top */
		g.setFill(Colors.TOP);
		g.fillRect(x, y + width, width, 20);

		/* Bow */
		drawBow(g);
	}

	@Override
	public void renderDebug(GraphicsContext g, double elapsed) {
		super.renderDebug(g, elapsed);
		Point2D center = getCenterPoint();
		Point2D mouse = mouseInput.location();
		double bowX = center.getX() - BOW_SIZE / 2;
		double bowY = center.getY() - BOW_SIZE / 2;
		g.setStroke(Color.WHITE);
		g.strokeArc(bowX, bowY, BOW_SIZE, BOW_SIZE, 0, 360, ArcType.OPEN);
		g.strokeLine(center.getX(), center.getY(), mouse.getX(), mouse.getY());
		g.fillText(String.format("%fdeg", this.angleTo(mouse)), mouse.getX() + 10, mouse.getY());
	}
}
