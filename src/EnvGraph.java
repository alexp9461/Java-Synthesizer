import javafx.scene.effect.Bloom;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

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
public class EnvGraph {
	private Pane graph;
	private Polyline line;
	private double attack =1;
	private double decay = 30;
	private double sustain = 0;
	private double release = 70;
	private double[] envShapeArray = {0,80, attack,1, decay, Math.abs(1 - sustain) ,release,80 };

	public EnvGraph() {
		graph = new Pane();
		line = new Polyline(envShapeArray);
		graph.getChildren().add(line);
		// https://www.programcreek.com/java-api-examples/?class=javafx.scene.shape.Polyline&method=setStroke
		line.setStroke(Color.web("#1E90FF"));
		line.setStrokeWidth(2);
		line.setEffect(new Bloom());
		
	}
	
	public void drawLine() {
		setEnvShapeArray(new double[] {0,80, attack,1, decay, Math.abs(1 - sustain),release,80});
		
		graph.getChildren().removeAll(graph.getChildren());
		this.line = new Polyline(envShapeArray);
		graph.getChildren().add(line);
		// https://www.programcreek.com/java-api-examples/?class=javafx.scene.shape.Polyline&method=setStroke
		line.setStroke(Color.web("#1E90FF"));
		line.setStrokeWidth(2);
		line.setEffect(new Bloom());
	}
	
	public Pane getGraph() {
		return this.graph;
	}
	public Polyline getLine() {
		return this.line;
	}

	public double[] getEnvShapeArray() {
		return envShapeArray;
	}

	public void setEnvShapeArray(double[] envShapeArray) {
		this.envShapeArray = envShapeArray;
	}

	public double getAttack() {
		return attack;
	}

	public void setAttack(double attack) {
		this.attack = attack;
	}

	public double getDecay() {
		return decay;
	}

	public void setDecay(double decay) {
		this.decay = decay;
	}

	public double getSustain() {
		return sustain;
	}

	public void setSustain(double sustain) {
		this.sustain = sustain;
	}

	public double getRelease() {
		return release;
	}

	public void setRelease(double release) {
		this.release = release;
	}

	public void setGraph(Pane graph) {
		this.graph = graph;
	}

	public void setLine(Polyline line) {
		this.line = line;
	}
	
}
