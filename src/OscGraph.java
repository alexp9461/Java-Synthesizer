import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

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
public class OscGraph {
	private Pane graph;
	private ContextMenu menu;
	private MenuItem sine;
	private MenuItem saw;
	private MenuItem square;
	private MenuItem triangle;
	private MenuItem noise;
	private final double[] sawShapeArray = { 0,40, 70,1, 70,80, 139,40 };
	private final double[] squareShapeArray = { 0,1, 70,1, 70,80, 139, 80 };
	private final double[] triangleShapeArray = {0,40, 35,1, 105,80, 139,40};
	private final double[] noiseShapeArray = {0,0, 5,80, 10,0, 11, 80, 20,0, 23,80, 50,0, 60,80, 80,0, 90,80, 100,0, 110,80, 120,0, 139,80};
	private CubicCurve sineLine;
	private Polyline sawLine;
	private Polyline squareLine;
	private Polyline triLine;
	private Polyline noiseLine;
	
	public OscGraph() {
		graph = new Pane();
		menu = new ContextMenu();
		sine = new MenuItem("Sine");
		saw = new MenuItem("Saw");
		square = new MenuItem("Square");	
		triangle = new MenuItem("Triangle");
		noise = new MenuItem("Noise");
		menu.getItems().addAll(sine, saw, square, triangle, noise);
		
		// https://johnloomis.org/ece538/notes/javafx/jfx9be/chap03/DrawingShapes.java.html
		sineLine = new CubicCurve(0.0,40.0, 80.0,-80.0, 80.0,160.0, 140.0,40.0);
		sineLine.setStroke(Color.web("#1E90FF"));
		sineLine.setStrokeWidth(2);
		sineLine.setEffect(new Bloom());
		
		sawLine = new Polyline(sawShapeArray);
		sawLine.setStroke(Color.web("#1E90FF"));
		sawLine.setStrokeWidth(2);
		sawLine.setEffect(new Bloom());
		
		squareLine = new Polyline(squareShapeArray);
		squareLine.setStroke(Color.web("#1E90FF"));
		squareLine.setStrokeWidth(2);
		squareLine.setEffect(new Bloom());
		
		triLine = new Polyline(triangleShapeArray);
		triLine.setStroke(Color.web("#1E90FF"));
		triLine.setStrokeWidth(2);
		triLine.setEffect(new Bloom());
		
		noiseLine = new Polyline(noiseShapeArray);
		noiseLine.setStroke(Color.web("#1E90FF"));
		noiseLine.setStrokeWidth(2);
		noiseLine.setEffect(new Bloom());
		
		graph.getChildren().add(sineLine);
		menu.setStyle("-fx-background-color: #383838");

		
		// https://stackoverflow.com/questions/766956/how-do-i-create-a-right-click-context-menu-in-java-swing
		graph.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				menu.show(graph, event.getScreenX(), event.getScreenY());
				event.consume();
			}
		});
	
	}
	
	public Pane getGraph() {
		return this.graph;
	}
	public MenuItem getSine() {
		return this.sine;
	}
	public MenuItem getSaw() {
		return this.saw;
	}
	public MenuItem getSquare() {
		return this.square;
	}
	public MenuItem getTri() {
		return this.triangle;
	}
	public MenuItem getNoise() {
		return this.noise;
	}
	public Polyline getSawLine() {
		return this.sawLine;
	}
	public CubicCurve getSineLine() {
		return this.sineLine;
	}
	public Polyline getSquareLine() {
		return this.squareLine;
	}
	public Polyline getTriLine() {
		return this.triLine;
	}
	public Polyline getNoiseLine() {
		return this.noiseLine;
	}
	

	
}
