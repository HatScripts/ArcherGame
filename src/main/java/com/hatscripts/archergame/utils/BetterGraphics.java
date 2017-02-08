package com.hatscripts.archergame.utils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public final class BetterGraphics {

	public static void drawString(Graphics g, String text, int x, int y) {
		int fontHeight = g.getFontMetrics().getHeight();
		for (String line : text.split("\n")) {
			g.drawString(line, x, y);
			y += fontHeight;
		}
	}

	public static void drawStringCentred(Graphics g, String text, int x, int y) {
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(text, g);

		int textHeight = (int) (rect.getHeight());
		int textWidth = (int) (rect.getWidth());

		x = (x - textWidth) / 2;
		y = (y - textHeight) / 2 + fm.getAscent();

		g.drawString(text, x, y);
	}
}
