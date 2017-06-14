package com.hatscripts.archergame.input;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

public class KeyInput implements EventHandler<KeyEvent> {
	private static final Map<MovementKey, Boolean> KEYS_HELD;
	private final SimpleBooleanProperty debug = new SimpleBooleanProperty(false, "Debug");

	static {
		MovementKey[] values = MovementKey.values();
		Map<MovementKey, Boolean> map = new HashMap<>(values.length);
		for (MovementKey key : values) {
			map.put(key, false);
		}
		KEYS_HELD = map;
	}

	/**
	 * Invoked when a specific event of the type for which this handler is
	 * registered happens.
	 *
	 * @param e the event which occurred
	 */
	@Override
	public void handle(KeyEvent e) {
		KeyCode code = e.getCode();
		if (code == KeyCode.ESCAPE) {
			System.exit(0);
			return;
		}
		EventType<KeyEvent> eventType = e.getEventType();
		if (eventType.equals(KeyEvent.KEY_RELEASED)
				&& code == KeyCode.D && e.isControlDown()) {
			debug.set(!debug.get());
		}
		MovementKey.fromKeyCode(code).ifPresent(key ->
				KEYS_HELD.put(key, !eventType.equals(KeyEvent.KEY_RELEASED)));
	}

	public boolean isKeyHeld(MovementKey key) {
		return KEYS_HELD.get(key);
	}

	public SimpleBooleanProperty debugProperty() {
		return debug;
	}
}