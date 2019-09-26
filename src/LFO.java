import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.UnitOscillator;

import javafx.scene.media.EqualizerBand;

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
public class LFO extends UnitOscillator{
	
	private String activeWave;
	private double speed;
	
	public LFO() {
		this.activeWave = "Sine";
		this.speed = 10;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public void setActiveWave(String wave) {
		this.activeWave = wave;
	}

	@Override
	public void generate(int freq, int vol) {
		if (activeWave.equals("Sine")) {
			// Taken from JSyn SineOscillator Class

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
			// Taken from JSyn SawtoothOscillator Class

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
		// Taken from JSyn SquareOscillator Class

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
	
	}
}
