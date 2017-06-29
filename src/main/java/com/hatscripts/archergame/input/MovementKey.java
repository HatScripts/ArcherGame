package com.hatscripts.archergame.input;

import javafx.scene.input.KeyCode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

public enum MovementKey {
	UP('⬆', 0, -1, KeyCode.W, KeyCode.UP),
	DOWN('⬇', 0, 1, KeyCode.S, KeyCode.DOWN),
	LEFT('⬅', -1, 0, KeyCode.A, KeyCode.LEFT),
	RIGHT('➡', 1, 0, KeyCode.D, KeyCode.RIGHT);
	private final char character;
	private final int xAdjustment;
	private final int yAdjustment;
	private final HashSet<KeyCode> codes;

	MovementKey(char character, int xAdjustment, int yAdjustment, KeyCode... codes) {
		this.character = character;
		this.xAdjustment = xAdjustment;
		this.yAdjustment = yAdjustment;
		this.codes = new HashSet<>(Arrays.asList(codes));
	}

	public static Optional<MovementKey> fromKeyCode(KeyCode code) {
		return Arrays.stream(MovementKey.values())
				.filter(key -> key.codes.contains(code))
				.findAny();
	}

	public MovementKey opposite() {
		int ordinal = this.ordinal();
		return MovementKey.values()[ordinal % 2 == 0 ? ordinal + 1 : ordinal - 1];
	}

	public int getXAdjustment() {
		return xAdjustment;
	}

	public int getYAdjustment() {
		return yAdjustment;
	}

	public char toChar() {
		return character;
	}
}
