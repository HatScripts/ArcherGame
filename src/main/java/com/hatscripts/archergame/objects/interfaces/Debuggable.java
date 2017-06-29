package com.hatscripts.archergame.objects.interfaces;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public interface Debuggable {
	Font DEBUG_FONT = Font.font("monospace");

	default void renderDebugDefault(GraphicsContext g, double elapsed) {
		g.save();
		g.setGlobalAlpha(0.5);
		g.setFont(DEBUG_FONT);
		g.setFill(Color.BLACK);
		g.setStroke(Color.WHITE);
		renderDebug(g, elapsed);
		g.restore();
	}

	void renderDebug(GraphicsContext g, double elapsed);
}
