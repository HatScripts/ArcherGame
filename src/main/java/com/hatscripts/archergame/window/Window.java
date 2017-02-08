package com.hatscripts.archergame.window;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public final class Window {

	private static Game game = null;

	public Window(String title, int width, int height) {
		game = new Game();
		game.setSize(new Dimension(width, height));

		JFrame frame = new JFrame(title);
		frame.add(game);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);

		/* Mouse input */
		MouseInput mouseInput = new MouseInput();
		game.addMouseListener(mouseInput);
		game.addMouseMotionListener(mouseInput);

		/* Keyboard input */
		JRootPane pane = frame.getRootPane();
		InputMap im = pane.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = pane.getActionMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
		am.put("Escape", new KeyInput("Escape"));
		for (int key : KeyInput.MOVEMENT_KEYS) {
			for (int i = 0; i < 2; i++) {
				boolean released = i == 1;
				String id = (char) key + (released ? "Released" : "");
				im.put(KeyStroke.getKeyStroke(key, 0, released), id);
				am.put(id, new KeyInput(id));
			}
		}

		frame.setVisible(true);
		game.start();
	}

	public static Point getLocation() {
		return game.getLocationOnScreen();
	}
}