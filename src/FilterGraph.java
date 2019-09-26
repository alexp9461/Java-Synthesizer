import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
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
public class FilterGraph {
	private Pane graph;
	private double cut = 80;
	private double angle = 100;
	private Polyline line;
	private ContextMenu menu;
	private MenuItem lowPass;
	private MenuItem highPass;
	private double[] filterArray ;
	
	public FilterGraph() {
		graph = new Pane();
		menu = new ContextMenu();
		highPass = new MenuItem("High Pass");
		lowPass = new MenuItem("Low Pass");
		menu.getItems().addAll(highPass, lowPass);
		menu.setStyle("-fx-background-color: #383838");
		
		this.cut = 80;
		this.angle = 20;
		filterArray = new double[] {0,40, cut, 40, cut+angle,80};

		line = new Polyline(filterArray);
		// https://www.programcreek.com/java-api-examples/?class=javafx.scene.shape.Polyline&method=setStroke
		line.setStroke(Color.web("#1E90FF")); 
		line.setStrokeWidth(2);
		line.setEffect(new Bloom());
		graph.getChildren().add(line);
		
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
	public Polyline getLine() {
		return this.line;
	}
	public void setLine(double [] line) {
		this.line = new Polyline(this.filterArray);
	}
	public MenuItem getHighPass() {
		return this.highPass;
	}
	public MenuItem getLowPass() {
		return this.lowPass;
	}
	public void setFilterArray(double[] arr) {
		this.filterArray = arr;
	}
	public double[] getFilterArray() {
		return this.filterArray;
	}
	public double getCut() {
		return this.cut;
	}
	public void setCut(double cut) {
		this.cut = cut;
	}
	public double getAngle() {
		return this.angle;
		
	}

}
