package com.hatscripts.archergame.input;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyInput extends AbstractAction {

	public static final int[] MOVEMENT_KEYS = {KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S};
	public static final Map<Integer, Boolean> KEYS_HELD;

	static {
		Map<Integer, Boolean> map = new HashMap<>(MOVEMENT_KEYS.length);
		for (int key : MOVEMENT_KEYS) {
			map.put(key, false);
		}
		KEYS_HELD = map;
	}

	private final String command;

	public KeyInput(String command) {
		this.command = command;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (command.equals("Escape")) {
			System.exit(0);
			return;
		}
		int key = (int) command.charAt(0);
		if (KEYS_HELD.containsKey(key)) {
			boolean released = command.endsWith("Released");
			KEYS_HELD.put(key, !released);
		}
	}

	public static boolean isKeyHeld(int key) {
		return KeyInput.KEYS_HELD.get(key);
	}

	public static int getOppositeKey(int key) {
		switch (key) {
			case KeyEvent.VK_A: return KeyEvent.VK_D;
			case KeyEvent.VK_D: return KeyEvent.VK_A;
			case KeyEvent.VK_W: return KeyEvent.VK_S;
			case KeyEvent.VK_S: return KeyEvent.VK_W;
			default:
				throw new IllegalArgumentException("Key is not a valid movement key: " + key);
		}
	}
}