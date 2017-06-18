package com.hatscripts.archergame.window;

import com.hatscripts.archergame.input.KeyInput;
import com.hatscripts.archergame.input.MouseInput;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {
	private Game game;

	@Override
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root);

		SimpleBooleanProperty debug = new SimpleBooleanProperty(
				getParameters().getUnnamed().contains("debug"));

		KeyInput keyInput = new KeyInput(debug);
		scene.addEventFilter(KeyEvent.ANY, keyInput);

		MouseInput mouseInput = new MouseInput(stage.xProperty(), stage.yProperty());
		scene.addEventFilter(MouseEvent.ANY, mouseInput);

		double height = 480;
		game = new Game(height * (16d / 9), height, keyInput, mouseInput, debug);
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