import com.jsyn.*;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.UnitVoice;
import com.jsyn.util.PseudoRandom;
import com.jsyn.util.VoiceAllocator;
import com.softsynth.shared.time.TimeStamp;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.EventListener;
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
public class Oscillator extends UnitOscillator {

	private String activeWave;
	private int octave;
	private double cent;

    private PseudoRandom randomNum = new PseudoRandom();
    private UnitVoice voice;

	public Oscillator() {
		this.activeWave = "Sine";
		octave = 0;
			
	}

	public void setActiveWave(String wave) {
		this.activeWave = wave;
	}
	public String getActiveWave() {
		return this.getActiveWave();
	}

	public void setVolume(double volume) {
		this.amplitude.set(volume);
		this.noteOff();
	}
	
	@Override
	public void generate(int freq, int vol) {
		if (activeWave.equals("Sine")) {
			// taken from Jsyn sineOscillator class
			double[] var3 = this.frequency.getValues();
			double[] var4 = this.amplitude.getValues();
			double[] var5 = this.output.getValues();
			double var6 = this.phase.getValue();

			for (int var8 = freq; var8 < vol; ++var8) {
				double var9 = this.convertFrequencyToPhaseIncrement(var3[var8]);
				var6 = this.incrementWrapPhase(var6, var9);
				double var11 = SineOscillator.fastSin(var6);
				var5[var8] = var11 * var4[var8];
			}

			this.phase.setValue(var6);

		}
		if (activeWave.equals("Saw")) {
			// taken from Jsyn SawOscillator class

			double[] var3 = this.frequency.getValues();
			double[] var4 = this.amplitude.getValues();
			double[] var5 = this.output.getValues();
			double var6 = this.phase.getValue();

			for (int var8 = freq; var8 < vol; ++var8) {
				double var9 = this.convertFrequencyToPhaseIncrement(var3[var8]);
				var6 = this.incrementWrapPhase(var6, var9);
				var5[var8] = var6 * var4[var8];
			}

			this.phase.setValue(var6);
		}
		if (activeWave.equals("Square")) {
			// taken from Jsyn SquareOscillator class

			double[] var3 = this.frequency.getValues();
			double[] var4 = this.amplitude.getValues();
			double[] var5 = this.output.getValues();
			double var6 = this.phase.getValue();

			for (int var8 = freq; var8 < vol; ++var8) {
				double var9 = this.convertFrequencyToPhaseIncrement(var3[var8]);
				var6 = this.incrementWrapPhase(var6, var9);
				double var11 = var4[var8];
				var5[var8] = var6 < 0.0D ? -var11 : var11;
			}

			this.phase.setValue(var6);
		}
		
		if(activeWave.equals("Triangle")) {
			// taken from Jsyn TriangleOscillator class

			double[] var3 = this.frequency.getValues();
	        double[] var4 = this.amplitude.getValues();
	        double[] var5 = this.output.getValues();
	        double var6 = this.phase.getValue();


	        for(int var8 = freq; var8 < vol; ++var8) {
	            double var9 = this.convertFrequencyToPhaseIncrement(var3[var8]);
	            var6 = this.incrementWrapPhase(var6, var9);
	            double var11 = var6 >= 0.0D ? 0.5D - var6 : 0.5D + var6;
	            var5[var8] = var11 * 2.0D * var4[var8];
	        }

	        this.phase.setValue(var6);
		}
		if(activeWave.equals("Noise")) {
			// taken from Jsyn NoiseOscillator class
			double[] var3 = this.amplitude.getValues();
	        double[] var4 = this.output.getValues();

	        for(int var5 = freq; var5 < vol; ++var5) {
	            var4[var5] = this.randomNum.nextRandomDouble() * var3[var5];
	        }
		}

	}
	
	public int getOctave() {
		return this.octave;
	}
	
	public void setOctave(int octave) {
		this.octave = octave;
	}
	public void setCent(double cent) {
		this.cent = cent;
	}
	public double getCent() {
		return this.cent;
	}

	@Override
	public UnitOutputPort getOutput() {
		return this.output;
	}
	
	@Override
	public void noteOn(double freq, double ampl, TimeStamp timeStamp) {
		frequency.set(freq, timeStamp);
        amplitude.set(ampl, timeStamp);
	
	}

}