package com.hatscripts.archergame.objects.list;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;
import com.hatscripts.archergame.objects.AbstractGameObject;
import com.hatscripts.archergame.objects.ObjectType;
import com.hatscripts.archergame.objects.interfaces.Debuggable;
import com.hatscripts.archergame.utils.Rectangles;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Debug extends AbstractGameObject {
	private final long startTime = LocalTime.now().toNanoOfDay();
	private final SimpleBooleanProperty enabled = new SimpleBooleanProperty();
	private final KeyInput keyInput;
	private final MouseInput mouseInput;
	private final Map<DebugVar, Object> debugVars;
	private Point2D pressed;

	private enum DebugVar {
		RUNTIME("Runtime", "%s"),
		FPS("FPS", "%.0f"),
		KEYS("Keys", "%s"),
		MOUSE("Mouse", "%s");
		private final String title;
		private final String format;

		DebugVar(String title, String format) {
			this.title = title;
			this.format = format;
		}

		String format(Object o) {
			return title + ": " + String.format(format, o);
		}
	}

	protected Debug(double x, double y, double width, double height,
					KeyInput keyInput, MouseInput mouseInput) {
		super(x, y, width, height);
		this.keyInput = keyInput;
		this.mouseInput = mouseInput;
		enabled.bind(keyInput.debugProperty());

		DebugVar[] debugVarArray = DebugVar.values();
		this.debugVars = new HashMap<>(debugVarArray.length);
		for (DebugVar debugVar : debugVarArray) {
			this.debugVars.put(debugVar, null);
		}
		mouseInput.addOnPressed(e -> {
			if (enabled.get()) {
				pressed = mouseInput.location();
			}
		});
		mouseInput.addOnReleased(e -> {
			if (enabled.get() && pressed != null) {
				Rectangle2D rect = Rectangles.pointsToRect(pressed, mouseInput.location());
				switch (e.getButton()) {
					case PRIMARY:
						ObjectType type = e.isControlDown() ? ObjectType.CIRCLE :
								e.isShiftDown() ? ObjectType.TRIANGLE :
										ObjectType.BLOCK;
						objects.add(type, rect);
						break;
					case SECONDARY:
						objects.remove(rect);
						break;
				}
				pressed = null;
			}
		});
	}

	@Override
	public void init() {
		objects.debugProperty().bind(enabled);
	}

	@Override
	public void tick(double elapsed) {
		debugVars.put(DebugVar.RUNTIME, LocalTime.now().minusNanos(startTime));
		debugVars.put(DebugVar.FPS, 1 / elapsed);
		debugVars.put(DebugVar.KEYS, keyInput.toString());
		debugVars.put(DebugVar.MOUSE, mouseInput.toString());
	}

	@Override
	public void render(GraphicsContext g) {

	}

	@Override
	public void renderDebug(GraphicsContext g, double elapsed) {
		g.setFont(Debuggable.DEBUG_FONT);
		g.setTextBaseline(VPos.TOP);
		// TODO: Make FPS display smoother
		String debug = debugVars.entrySet().stream()
				.map(entry -> entry.getKey().format(entry.getValue()))
				.sorted()
				.collect(Collectors.joining("\n"));
		g.fillText(debug, 50, 50);

		if (pressed != null) {
			Point2D mouse = mouseInput.location();
			Rectangle2D rect = Rectangles.pointsToRect(pressed, mouse);
			g.setStroke(Color.WHITE);
			g.strokeRect(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
		}
	}
}
