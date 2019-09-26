import java.beans.EventHandler;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

import com.jsyn.unitgen.LineOut;
import com.sun.javafx.geom.transform.GeneralTransform3D;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Copyright [2019] [Alex Parker]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
public class Main extends Application {

	private static Controller controller;
	private static Parent root;
	private static Scene scene;

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
		
		root = loader.load();
		controller = loader.getController();

		scene = new Scene(root, 767, 500);
		
		primaryStage.setScene(scene);
		scene.getStylesheets().add("application.css");
		primaryStage.show();
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, controller.keyPressedHandler);
		scene.addEventHandler(KeyEvent.KEY_RELEASED, controller.keyReleasedHandler);
		primaryStage.setResizable(false);
	}
	
	public static Scene getScene() {
		return scene;
	}
	
	public static Controller getController() {
		return controller;
	}
	@Override
	public void stop() {
		controller.mainSynth.stop();
	}

	public static void main(String args[]) {

		launch(args);
	}

}
