import com.jsyn.data.SegmentedEnvelope;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.UnitFilter;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.VariableRateMonoReader;
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
public class Envelope {

	private VariableRateMonoReader envPlayer;
	private SegmentedEnvelope envData;
	private double attack = 0.01;
	private double sustain = 1.0;
	private double decay = 0.0;
	private double release = 0.01;
	private double[] data;

	public VariableRateMonoReader getEnvPlayer() {
		return envPlayer;
	}

	public SegmentedEnvelope getSegEnv() {
		return envData;
	}

	public Envelope() {
		// http://www.softsynth.com/jsyn/docs/usersguide.php
		data = new double[] { this.attack, 1.0,
				this.decay, this.sustain, 
				0.0, this.sustain,
				0.0, this.sustain,
				this.release, 0.0 };
		envData = new SegmentedEnvelope(data);
		envPlayer = new VariableRateMonoReader();
		envPlayer.rate.set(0.7);
	}
	
	public void startEnvelop() {
		http://www.softsynth.com/jsyn/docs/usersguide.php
		this.envPlayer.dataQueue.clear();
		this.envPlayer.dataQueue.queue(this.envData, 0, 3);
		this.envPlayer.dataQueue.queueLoop(this.envData, 1, 2);
		envPlayer.start();
	}
	
	public void stopEnvelope() {
		this.envPlayer.dataQueue.queue(this.envData, 3, 2);

	}

	public void setAttack(double attack) {
		this.attack = attack;
		this.data[0] = this.attack;
		envData.write(data);
	}

	public void setDecay(double decay) {
		this.decay = decay;
		this.data[2] = this.decay;
		envData.write(data);
	}

	public void setSustain(double sustain) {
		this.sustain = sustain;
		this.data[3] = this.sustain;
		this.data[5] = this.sustain;
		this.data[7] = this.sustain;

		envData.write(data);
	}

	public void setRelease(double release) {
		this.release = release;
		this.data[8] = this.release;
		envData.write(data);
	}

}
