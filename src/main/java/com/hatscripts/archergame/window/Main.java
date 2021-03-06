package com.hatscripts.archergame.window;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends Application {
	private Game game;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root);

		boolean debugEnabled = getParameters().getUnnamed().contains("debug");
		KeyInput keyInput = new KeyInput(debugEnabled);
		scene.addEventFilter(KeyEvent.ANY, keyInput);

		MouseInput mouseInput = new MouseInput(stage.xProperty(), stage.yProperty());
		scene.addEventFilter(MouseEvent.ANY, mouseInput);

		double height = 720;
		double width = height * (16d / 9);
		game = new Game(width, height, keyInput, mouseInput);
		root.getChildren().add(game);

		stage.setTitle("Archer Game");
		stage.getIcons().addAll(
				Stream.of(16, 32, 64, 128)
						.map(size -> new Image("logo-" + size + ".png"))
						.collect(Collectors.toSet())
		);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}

	@Override
	public void stop() {
		game.stopAnimationTimer();
	}
}