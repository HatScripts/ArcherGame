package com.hatscripts.archergame.objects.list;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;
import com.hatscripts.archergame.objects.AbstractGameObject;
import com.hatscripts.archergame.objects.interfaces.Debuggable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;

public class Debug extends AbstractGameObject {
	private final long startTime = System.nanoTime();
	private final SimpleBooleanProperty enabled = new SimpleBooleanProperty();
	private final SimpleLongProperty runtime = new SimpleLongProperty(null, "Runtime", 0);
	private final SimpleDoubleProperty fps = new SimpleDoubleProperty(null, "FPS", 0);
	private final SimpleStringProperty keys = new SimpleStringProperty(null, "Keys", "");
	private final SimpleStringProperty mouse = new SimpleStringProperty(null, "Mouse", "");
	private final KeyInput keyInput;
	private final MouseInput mouseInput;

	private enum DebugVar {
		RUNTIME("Runtime", "%1$tH:%1$tM:%1$tS"),
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
	}

	@Override
	public void init() {
		objects.debugProperty().bind(enabled);
	}

	@Override
	public void tick(double elapsed) {
		runtime.set((System.nanoTime() - startTime) / 1_000_000);
		fps.set(1 / elapsed);
		keys.set(keyInput.toString());
		mouse.set(mouseInput.toString());
	}

	@Override
	public void render(GraphicsContext g) {

	}

	@Override
	public void renderDebug(GraphicsContext g, double elapsed) {
		g.setFont(Debuggable.DEBUG_FONT);
		g.setTextBaseline(VPos.TOP);
		g.fillText(String.format(
				"%s: %s\n%s: %.0f\n%s: %s\n%s: %s",
				// TODO: Fix runtime showing as 10:MM:SS instead of HH:MM:SS
				runtime.getName(), String.format("%1$tH:%1$tM:%1$tS", runtime.get()),
				// TODO: Make FPS display smoother
				fps.getName(), fps.get(),
				keys.getName(), keys.get(),
				mouse.getName(), mouse.get()
		), 50, 50);
	}
}
