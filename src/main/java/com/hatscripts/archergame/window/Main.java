package com.hatscripts.archergame.window;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private Game game;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root);
		KeyInput keyInput = new KeyInput();
		scene.setOnKeyPressed(keyInput);
		scene.setOnKeyReleased(keyInput);
		scene.setOnKeyTyped(keyInput);

		MouseInput mouseInput = new MouseInput();
		scene.setOnMouseMoved(mouseInput);
		scene.setOnMouseClicked(mouseInput);
		scene.setOnMouseEntered(mouseInput);
		scene.setOnMouseExited(mouseInput);
		scene.setOnMousePressed(mouseInput);
		scene.setOnMouseReleased(mouseInput);

		game = new Game(640, 352, keyInput, mouseInput);
		root.getChildren().add(game);

		stage.setTitle("Archer Game");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}

	@Override
	public void stop() throws Exception {
		game.stopAnimationTimer();
	}
}