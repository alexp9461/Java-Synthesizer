import java.io.File;
import java.time.Instant;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jsyn.unitgen.UnitVoice;
import com.jsyn.util.VoiceAllocator;
import com.softsynth.shared.time.TimeStamp;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Bloom;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
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
public class Controller {

	public MySynthesizer mainSynth;
	TimeStamp timeStamp = new TimeStamp(0.0);
	@FXML
	GridPane backGround;
	@FXML
	ToggleButton distortionToggle, phaseButton;
	@FXML
	JFXComboBox<String> env2Box, LFOConnect;
	@FXML
	Label filterCutoffLabel;
	@FXML
	Spinner<Integer> osc1Octave, osc1Semi, osc2Semi, osc2Octave, waveOsc1Octave;
	@FXML
	JFXSlider osc1Vol, osc2Vol, env1AttackTSlider, env1DecaySlider, env1SustainSlider, env1ReleaseSlider, env2AttackSlider,
			env2DecaySlider, env2SustainSlider, env2ReleaseSlider, cutoffSlider,
			LFO1SpeedSlider, LFO2SpeedSlider, waveOsc1Vol, mainVolSlider, osc1CentSlider, osc2CentSlider, qSlider, eqHighGain,
			eqLowGain, eqHighFreq, eqLowFreq, distortionSlider;
	@FXML
	Polyline filterLine, sawLine1, squareLine1, sawLine2, squareLine2, sawLineLFO, squareLineLFO;
	@FXML
	Pane osc1Pane, osc2Pane, LFOPane1, LFOPane2, filterPane, envPane, envPane2, keyBoardPane, wavePane;

	OscGraph osc1Graph, osc2Graph, LFO1Graph, LFO2Graph;
	FilterGraph filterGraph;
	EnvGraph envGraph, envGraph2;

	private FileChooser waveChooser;



	public void initialize() {

		osc1Graph = new OscGraph();
		osc2Graph = new OscGraph();
		LFO1Graph = new OscGraph();
		envGraph = new EnvGraph();
		envGraph2 = new EnvGraph();
		filterGraph = new FilterGraph();
		osc1Pane.getChildren().add(osc1Graph.getGraph());
		osc2Pane.getChildren().add(osc2Graph.getGraph());
		LFOPane1.getChildren().add(LFO1Graph.getGraph());
		filterPane.getChildren().add(filterGraph.getGraph());
		envPane.getChildren().add(envGraph.getGraph());
		envPane2.getChildren().add(envGraph2.getGraph());
		LFOConnect.getItems().addAll("Oscillators", "Osc 1 Vol", "Osc 2 Vol", "Filter Cutoff");
		env2Box.getItems().addAll("Oscillators", "Osc 1 Vol", "Osc 2 Vol", "Filter Cutoff");

		
		osc1Graph.getSine().setOnAction(e -> {
			changeOsc1("Sine");
		});
		osc1Graph.getSaw().setOnAction(e -> {
			changeOsc1("Saw");
		});
		osc1Graph.getSquare().setOnAction(e -> {
			changeOsc1("Square");
		});
		osc1Graph.getTri().setOnAction(e -> {
			changeOsc1("Triangle");
		});
		osc1Graph.getNoise().setOnAction(e->{
			changeOsc1("Noise");
		});
		osc2Graph.getSine().setOnAction(e -> {
			changeOsc2("Sine");
		});
		osc2Graph.getSaw().setOnAction(e -> {
			changeOsc2("Saw");
		});
		osc2Graph.getSquare().setOnAction(e -> {
			changeOsc2("Square");
		});
		osc2Graph.getTri().setOnAction(e -> {
			changeOsc2("Triangle");
		});
		osc2Graph.getNoise().setOnAction(e->{
			changeOsc2("Noise");
		});
		LFO1Graph.getSaw().setOnAction(e -> {
			LFO1Shape("Saw");
		});
		LFO1Graph.getSine().setOnAction(e -> {
			LFO1Shape("Sine");
		});
		LFO1Graph.getSquare().setOnAction(e -> {
			LFO1Shape("Square");
		});
		filterGraph.getLowPass().setOnAction(e -> {
			changeFilter("Low Pass");
			mainSynth.getFilter().setActiveFilter("Low Pass");
		});
		filterGraph.getHighPass().setOnAction(e -> {
			changeFilter("High Pass");
			mainSynth.getFilter().setActiveFilter("High Pass");
		});
		
		Clip(osc1Pane);
		Clip(osc2Pane);
		Clip(LFOPane1);
		Clip(filterPane);
		Clip(envPane);
		Clip(envPane2);
		
		osc1CentSlider.addEventHandler(MouseEvent.MOUSE_CLICKED, sliderResetHandler);
		osc2CentSlider.addEventHandler(MouseEvent.MOUSE_CLICKED, sliderResetHandler);
		waveChooser = new FileChooser();

	}
	// http://tutorials.jenkov.com/javafx/filechooser.html
	public void waveChooser() {
		File file = waveChooser.showOpenDialog(Main.getScene().getWindow());
		mainSynth.getWaveOsc1().loadSample(file);
	}

	EventHandler<KeyEvent> keyPressedHandler = new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent e) {
			if (e.getCode() == KeyCode.A)
				mainSynth.playNote(261.63); // C
			else if (e.getCode() == KeyCode.W)
				mainSynth.playNote(277.18); // C#
			else if (e.getCode() == KeyCode.S)
				mainSynth.playNote(293.66); // D
			else if (e.getCode() == KeyCode.E)
				mainSynth.playNote(311.13); // D#
			else if (e.getCode() == KeyCode.D)
				mainSynth.playNote(329.63); // E
			else if (e.getCode() == KeyCode.F)
				mainSynth.playNote(349.23); // F
			else if (e.getCode() == KeyCode.T)
				mainSynth.playNote(369.99); // F#
			else if (e.getCode() == KeyCode.G)
				mainSynth.playNote(392.00); // G
			else if (e.getCode() == KeyCode.Y)
				mainSynth.playNote(415.30);// g#
			else if (e.getCode() == KeyCode.H)
				mainSynth.playNote(440.00); // A
			else if (e.getCode() == KeyCode.U)
				mainSynth.playNote(466.16); // A#
			else if (e.getCode() == KeyCode.J)
				mainSynth.playNote(493.88); // B
			else if (e.getCode() == KeyCode.K)
				mainSynth.playNote(523.25); // C
			
		
		}
	};

	EventHandler<MouseEvent> sliderResetHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			if(e.getButton() == MouseButton.SECONDARY) {
				((Slider) e.getSource()).setValue(0.5);
		}
		}
	};
	EventHandler<KeyEvent> keyReleasedHandler = new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent e) {
			mainSynth.stopNote();
		}
	};

	public Controller() {
		mainSynth = new MySynthesizer();
	}

	public void changeOsc1(String wave) {
		mainSynth.getOsc1().setActiveWave(wave);
		if (wave.equals("Saw")) {
			osc1Graph.getGraph().getChildren().removeAll(osc1Graph.getGraph().getChildren());
			osc1Graph.getGraph().getChildren().add(osc1Graph.getSawLine());
		} else if (wave.equals("Sine")) {
			osc1Graph.getGraph().getChildren().removeAll(osc1Graph.getGraph().getChildren());
			osc1Graph.getGraph().getChildren().add(osc1Graph.getSineLine());
		} else if (wave.equals("Square")) {
			osc1Graph.getGraph().getChildren().removeAll(osc1Graph.getGraph().getChildren());
			osc1Graph.getGraph().getChildren().add(osc1Graph.getSquareLine());
		} else if(wave.equals("Triangle")) {
			osc1Graph.getGraph().getChildren().removeAll(osc1Graph.getGraph().getChildren());
			osc1Graph.getGraph().getChildren().add(osc1Graph.getTriLine());
		} else if(wave.equals("Noise")) {
			osc1Graph.getGraph().getChildren().removeAll(osc1Graph.getGraph().getChildren());
			osc1Graph.getGraph().getChildren().add(osc1Graph.getNoiseLine());
		}
	}

	public void changeOsc2(String wave) {
		mainSynth.getOsc2().setActiveWave(wave);
		if (wave.equals("Saw")) {
			osc2Graph.getGraph().getChildren().removeAll(osc2Graph.getGraph().getChildren());
			osc2Graph.getGraph().getChildren().add(osc2Graph.getSawLine());
		} else if (wave.equals("Sine")) {
			osc2Graph.getGraph().getChildren().removeAll(osc2Graph.getGraph().getChildren());
			osc2Graph.getGraph().getChildren().add(osc2Graph.getSineLine());
		} else if (wave.equals("Square")) {
			osc2Graph.getGraph().getChildren().removeAll(osc2Graph.getGraph().getChildren());
			osc2Graph.getGraph().getChildren().add(osc2Graph.getSquareLine());
		}else if(wave.equals("Triangle")) {
			osc2Graph.getGraph().getChildren().removeAll(osc2Graph.getGraph().getChildren());
			osc2Graph.getGraph().getChildren().add(osc2Graph.getTriLine());
		} else if(wave.equals("Noise")) {
			osc2Graph.getGraph().getChildren().removeAll(osc2Graph.getGraph().getChildren());
			osc2Graph.getGraph().getChildren().add(osc2Graph.getNoiseLine());
		}
	}

	@FXML
	public void osc1Vol() {
		mainSynth.getMixer().gain.set(0, osc1Vol.getValue());
	}

	@FXML
	public void osc2Vol() {
		mainSynth.getMixer().gain.set(1, osc2Vol.getValue());
	}

	@FXML
	public void waveOsc1Vol() {
		mainSynth.getMixer().gain.set(2, waveOsc1Vol.getValue());
	}

	@FXML
	public void osc1Octave() {
		mainSynth.getOsc1().setOctave(osc1Octave.getValue());
	}

	@FXML
	public void osc2Octave() {
		mainSynth.getOsc2().setOctave(osc2Octave.getValue());
	}

	@FXML
	public void waveOsc1Octave() {
		mainSynth.getWaveOsc1().setOctave(waveOsc1Octave.getValue());
	}
	@FXML
	public void osc1Cent() {
		mainSynth.getOsc1().setCent(osc1CentSlider.getValue());
	}
	@FXML
	public void osc2Cent() {
		mainSynth.getOsc2().setCent(osc2CentSlider.getValue());
	}

	@FXML
	public void env1A() {
		mainSynth.getEnv1().setAttack(env1AttackTSlider.getValue());
		envGraph.setAttack(env1AttackTSlider.getValue() * 20);
		envGraph.drawLine();
	}

	@FXML
	public void env1D() {
		mainSynth.getEnv1().setDecay(env1DecaySlider.getValue());
		envGraph.setDecay(((env1DecaySlider.getValue() * 30) + 30) + envGraph.getAttack());
		envGraph.drawLine();
	}

	@FXML
	public void env1S() {
		mainSynth.getEnv1().setSustain(env1SustainSlider.getValue());
		envGraph.setSustain(Math.abs(80 - (env1SustainSlider.getValue() * 80)));
		envGraph.drawLine();
	}

	@FXML
	public void env1R() {
		mainSynth.getEnv1().setRelease(env1ReleaseSlider.getValue());
		envGraph.setRelease((env1ReleaseSlider.getValue() * 70) + 70);
		envGraph.drawLine();
	}
	
	@FXML
	public void env2A() {
		mainSynth.getEnv2().setAttack(env2AttackSlider.getValue());
		envGraph2.setAttack(env2AttackSlider.getValue() * 20);
		envGraph2.drawLine();
	}

	@FXML
	public void env2D() {
		mainSynth.getEnv2().setDecay(env2DecaySlider.getValue());
		envGraph2.setDecay(((env2DecaySlider.getValue() * 30) + 30) + envGraph2.getAttack());
		envGraph2.drawLine();
	}

	@FXML
	public void env2S() {
		mainSynth.getEnv2().setSustain(env2SustainSlider.getValue());
		envGraph2.setSustain(Math.abs(80 - (env2SustainSlider.getValue() * 80)));
		envGraph2.drawLine();
	}

	@FXML
	public void env2R() {
		mainSynth.getEnv2().setRelease(env2ReleaseSlider.getValue());
		envGraph2.setRelease((env2ReleaseSlider.getValue() * 70) + 70);
		envGraph2.drawLine();
	}
	@FXML
	public void connectEnv2() {
		mainSynth.env2Connection(env2Box.getValue());
	}
	@FXML
	public void loadReece() {
		mainSynth.getPreset().loadPreset(mainSynth.getPreset().getReeceDouble(), mainSynth.getPreset().getReeceString());
	}
	@FXML
	public void loadSub() {
		mainSynth.getPreset().loadPreset(mainSynth.getPreset().getSubDouble(), mainSynth.getPreset().getSubString());
	}
	@FXML
	public void loadLead() {
		mainSynth.getPreset().loadPreset(mainSynth.getPreset().getLeadDouble(), mainSynth.getPreset().getLeadString());
	}
	@FXML
	public void loadWobble() {
		mainSynth.getPreset().loadPreset(mainSynth.getPreset().getWobbleDouble(), mainSynth.getPreset().getWobbleString());
	}
	@FXML
	public void loadHeli() {
		mainSynth.getPreset().loadPreset(mainSynth.getPreset().getHeliDouble(), mainSynth.getPreset().getHeliString());
	}
	@FXML
	public void loadSquares() {
		mainSynth.getPreset().loadPreset(mainSynth.getPreset().getSquaresDouble(), mainSynth.getPreset().getSquaresString());
	}

	@FXML
	public void changeFilter(String type) {
		filterGraph.setCut(cutoffSlider.getValue());
		if (type.equals("High Pass")) {
			mainSynth.getFilter().selectFilter(type);
			filterGraph.setFilterArray(new double[] { 139, 40, (filterGraph.getCut() * 5), 40,
					(filterGraph.getCut() * 5) - filterGraph.getAngle(), 80 });
			filterGraph.setLine(filterGraph.getFilterArray());
			filterGraph.getGraph().getChildren().removeAll(filterGraph.getGraph().getChildren());
			filterGraph.getGraph().getChildren().add(filterGraph.getLine());
			filterGraph.getLine().setStroke(Color.web("#1E90FF"));
			filterGraph.getLine().setStrokeWidth(2);
			filterGraph.getLine().setEffect(new Bloom());
		} else if (type.equals("Low Pass")) {
			mainSynth.getFilter().selectFilter(type);
			filterGraph.setFilterArray(new double[] { 0, 40, (filterGraph.getCut() * 5), 40,
					(filterGraph.getCut() * 5) + filterGraph.getAngle(), 80 });
			filterGraph.getGraph().getChildren().removeAll(filterGraph.getGraph().getChildren());
			filterGraph.setLine(filterGraph.getFilterArray());
			filterGraph.getGraph().getChildren().add(filterGraph.getLine());
			filterGraph.getLine().setStroke(Color.web("#1E90FF"));
			filterGraph.getLine().setStrokeWidth(2);
			filterGraph.getLine().setEffect(new Bloom());
		}

	}

	@FXML
	public void setCutoff() {
		mainSynth.getFilter().setFrequency(cutoffSlider.getValue());
		double cuttofFreq = Math.round(mainSynth.getFilter().getFrequency());
		filterCutoffLabel.setText(Double.toString(cuttofFreq));
		filterGraph.setCut(cutoffSlider.getValue());
		if (mainSynth.getFilter().getActiveFilter().equals("Low Pass")) {
			filterGraph.setFilterArray(new double[] { 0, 40, (filterGraph.getCut() * 5), 40,
					(filterGraph.getCut() * 5) + filterGraph.getAngle(), 80 });
			filterGraph.getGraph().getChildren().removeAll(filterGraph.getGraph().getChildren());
			filterGraph.setLine(filterGraph.getFilterArray());
			filterGraph.getGraph().getChildren().add(filterGraph.getLine());
			filterGraph.getLine().setStroke(Color.web("#1E90FF"));
			filterGraph.getLine().setStrokeWidth(2);
			filterGraph.getLine().setEffect(new Bloom());
		} else if (mainSynth.getFilter().getActiveFilter().equals("High Pass")) {
			filterGraph.setFilterArray(new double[] { 139, 40, (filterGraph.getCut() * 5), 40,
					(filterGraph.getCut() * 5) - filterGraph.getAngle(), 80 });
			filterGraph.getGraph().getChildren().removeAll(filterGraph.getGraph().getChildren());
			filterGraph.setLine(filterGraph.getFilterArray());
			filterGraph.getGraph().getChildren().add(filterGraph.getLine());
			filterGraph.getLine().setStroke(Color.web("#1E90FF"));
			filterGraph.getLine().setStrokeWidth(2);
			filterGraph.getLine().setEffect(new Bloom());
		}
	}

	public void LFO1Shape(String wave) {
		mainSynth.getLFO1().setActiveWave(wave);
		if (wave.equals("Sine")) {
			LFO1Graph.getGraph().getChildren().removeAll(LFO1Graph.getGraph().getChildren());
			LFO1Graph.getGraph().getChildren().add(LFO1Graph.getSineLine());
		} else if (wave.equals("Saw")) {
			LFO1Graph.getGraph().getChildren().removeAll(LFO1Graph.getGraph().getChildren());
			LFO1Graph.getGraph().getChildren().add(LFO1Graph.getSawLine());
		} else if (wave.equals("Square")) {
			LFO1Graph.getGraph().getChildren().removeAll(LFO1Graph.getGraph().getChildren());
			LFO1Graph.getGraph().getChildren().add(LFO1Graph.getSquareLine());
		} else if(wave.equals("Triangle")) {
			LFO1Graph.getGraph().getChildren().removeAll(LFO1Graph.getGraph().getChildren());
			LFO1Graph.getGraph().getChildren().add(LFO1Graph.getTriLine());
		} else if(wave.equals("Noise")) {
			LFO1Graph.getGraph().getChildren().removeAll(LFO1Graph.getGraph().getChildren());
			LFO1Graph.getGraph().getChildren().add(LFO1Graph.getNoiseLine());
		}
	}
	
	public void eqSetLowGain() {
		mainSynth.getEq().setLowGain(eqLowGain.getValue());
	}
	public void eqSetHighGain() {
		mainSynth.getEq().setHighGain(eqHighGain.getValue());
	}
	public void eqSetLowFreq() {
		mainSynth.getEq().setLowFreq(eqLowFreq.getValue());
	}
	public void eqSetHighFreq() {
		mainSynth.getEq().setHighFreq(eqLowFreq.getValue());
	}
//	
//	public void filterSetQ() {
//		mainSynth.getFilter().setQ(qSlider.getValue());
//	}

	public void LFO1SetSpeed() {
		mainSynth.getLFO1().setSpeed(LFO1SpeedSlider.getValue());
	}

	public void ConnectLFO1() {
		mainSynth.connectLFO1(LFOConnect.getValue());
	}

	public MySynthesizer getMainSynth() {
		return this.mainSynth;
	}
	public void distortionToggle() {
		if(mainSynth.getDistortion().isOn() == true) {
			mainSynth.getDistortion().setOn(false);

		} else {
			mainSynth.getDistortion().setOn(true);
		}
	}

	// https://stackoverflow.com/questions/51620537/javafx-partially-hide-image-outside-gridpane
	public static void Clip(Region region) {
		final Rectangle clipPane = new Rectangle();
		region.setClip(clipPane);
		region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
			clipPane.setWidth(newValue.getWidth());
			clipPane.setHeight(newValue.getHeight());
		});
	}

	public void mainVolume() {
		mainSynth.getFilter().getLowPass().amplitude.set(mainVolSlider.getValue());
		mainSynth.getFilter().getHighPass().amplitude.set(mainVolSlider.getValue());

	}
	
}
