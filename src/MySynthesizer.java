import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

import com.jsyn.*;
import com.jsyn.devices.javasound.MidiDeviceTools;
import com.jsyn.instruments.DualOscillatorSynthVoice;
import com.jsyn.instruments.SubtractiveSynthVoice;
import com.jsyn.midi.MidiSynthesizer;
import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.swing.EnvelopeEditorPanel;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.Delay;
import com.jsyn.unitgen.EnvelopeAttackDecay;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.FunctionOscillator;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.MixerStereo;
import com.jsyn.unitgen.PhaseShifter;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitVoice;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.util.MultiChannelSynthesizer;
import com.jsyn.util.VoiceAllocator;
import com.jsyn.util.VoiceDescription;
import com.softsynth.shared.time.TimeStamp;

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
public class MySynthesizer extends Circuit implements UnitVoice {
	
	private Synthesizer synth;
	private LineOut lineOut;
	private Oscillator osc1;
	private Oscillator osc2;
	private WaveTableOsc waveOsc;
	private Envelope env1;
	private Envelope env2;
	private EQ eq;
	private MixerStereo mixer;
	private LFO lfo1;
	private Filter filter;
	private Distortion distortion;
	private FunctionOscillator waveTable;
	private final int MAX_VOICES = 8;
	private VoiceAllocator allocator;
	private UnitVoice[] voices;
	private PhaseShifter phase;
	private UnitVoice voice;
	private Preset preset;
	private double freq;
	private double ampl;
	private TimeStamp timeStamp;

	public MySynthesizer() {

		synth = JSyn.createSynthesizer();
		waveOsc = new WaveTableOsc();
		filter = new Filter();
		eq = new EQ();
		synth.add(osc1 = new Oscillator());
		synth.add(osc2 = new Oscillator());
		synth.add(waveOsc = new WaveTableOsc());
		synth.add(lineOut = new LineOut());

		synth.add(lfo1 = new LFO());
		synth.add(distortion = new Distortion());
		synth.add(filter.getHighPass());
		synth.add(filter.getLowPass());
		synth.add(filter.getPassThrough());
		synth.add(mixer = new MixerStereo(4));
		synth.add(waveOsc.getWaveOsc());
		env1 = new Envelope();
		env2 = new Envelope();
		synth.add(env1.getEnvPlayer());
		synth.add(env2.getEnvPlayer());
		synth.add(eq.getHigh());
		synth.add(eq.getLow());
		synth.add(phase = new PhaseShifter());

		osc1.output.connect(mixer.input.getConnectablePart(0));

		osc2.output.connect(mixer.input.getConnectablePart(1));
		waveOsc.getWaveOsc().output.connect(mixer.input.getConnectablePart(2));

		mixer.output.connect(distortion.input);
		mixer.gain.set(0, 1.0);
		mixer.gain.set(1, 0.0);
		mixer.gain.set(2, 0.0);
		env1.getEnvPlayer().output.connect(mixer.amplitude);
		distortion.output.connect(phase.input);
		phase.output.connect(filter.getPassThrough().input);
		filter.getPassThrough().output.connect(filter.getLowPass().input);

		filter.getLowPass().output.connect(eq.getLow());
		filter.getHighPass().output.connect(eq.getLow());
		eq.getLow().output.connect(eq.getHigh());
		eq.getHigh().output.connect(lineOut);
		lineOut.start();
		osc1.noteOff();
		osc2.noteOff();
		waveOsc.getWaveOsc().noteOff();
		waveOsc.amplitude.set(1.0);
		filter.getLowPass().amplitude.set(1);
		preset = new Preset();

		synth.start();
		
	}

	public void playNote(double freq) {

		env1.startEnvelop();
		env2.startEnvelop();
		
		
		if (osc1.getOctave() == 0) {
			osc1.noteOn(freq + osc1.getCent(), 1.0);
		} else if (osc1.getOctave() > 0) {
			if (osc1.getOctave() == 3) {
				osc1.noteOn((freq + osc1.getCent()) * 8, 1.0);
			} else {
				osc1.noteOn((freq + osc1.getCent()) * (2 * osc1.getOctave()), 1.0);
			}
		} else {
			if (osc1.getOctave() == -3) {
				osc1.noteOn(((freq + osc1.getCent()) / 8), 1.0);
			} else {
				osc1.noteOn((freq + osc1.getCent()) / (2 * osc1.getOctave()), 1.0);
			}
		}
		if (osc2.getOctave() == 0) {
			osc2.noteOn(freq + osc2.getCent(), 1.0);
		} else if (osc2.getOctave() > 0) {
			if (osc2.getOctave() == 3) {
				osc2.noteOn((freq + osc2.getCent()) * 8, 1.0);
			} else {
				osc2.noteOn((freq + osc2.getCent()) * (2 * osc2.getOctave()), 1.0);
			}
		} else {
			if (osc2.getOctave() == -3) {
				osc2.noteOn((freq + osc2.getCent()) / (8), 1.0);
			} else {
				osc2.noteOn((freq + osc2.getCent()) / (2 * osc2.getOctave()), 1.0);
			}
		}
		if (waveOsc.getOctave() == 0) {
			waveOsc.getWaveOsc().noteOn(freq + osc2.getCent(), 1.0);
		} else if (waveOsc.getOctave() > 0) {
			if (waveOsc.getOctave() == 3) {
				waveOsc.getWaveOsc().noteOn((freq + osc2.getCent()) * (8), 1.0);
			} else {
				waveOsc.getWaveOsc().noteOn((freq + osc2.getCent()) * (2 * waveOsc.getOctave()), 1.0);
			}
		} else {
			if (waveOsc.getOctave() == -3) {
				waveOsc.getWaveOsc().noteOn((freq + osc2.getCent()) / (8), 1.0);
			} else {
				waveOsc.getWaveOsc().noteOn((freq + osc2.getCent()) / (2 * waveOsc.getOctave()), 1.0);
			}
		}
		lfo1.noteOn(lfo1.getSpeed(), 1.0);

	}

	public void stopNote() {
		// osc1.noteOff();
		// osc2.noteOff();

		env1.stopEnvelope();
		env2.stopEnvelope();
		lfo1.noteOff();
	}

	// Filter connections
	public void setFilterHigh() {
		if (filter.getLowPass().input.isConnected()) {
			filter.getPassThrough().output.disconnectAll();
		}
		if (lineOut.input.isConnected()) {
			filter.getPassThrough().output.disconnectAll();
		}

		filter.getPassThrough().output.connect(filter.getHighPass().input);
	}

	public void setFilterLow() {
		if (filter.getHighPass().input.isConnected()) {
			filter.getPassThrough().output.disconnectAll();
		}
		if (lineOut.input.isConnected()) {
			filter.getPassThrough().output.disconnectAll();
		}
		filter.getPassThrough().output.connect(filter.getLowPass().input);
	}

	public void setFilterOff() {
		if (filter.getHighPass().input.isConnected()) {
			filter.getPassThrough().output.disconnect(filter.getHighPass().input);
		}
		if (filter.getLowPass().input.isConnected()) {
			filter.getPassThrough().output.disconnect(filter.getLowPass().input);
		}
		filter.getPassThrough().output.connect(lineOut.input);
	}

	// LFO Connections
	public void connectLFO1(String connection) {
		if (lfo1.output.isConnected()) {
			lfo1.output.disconnectAll();
		}
		if (connection.equals("Oscillators")) {
			lfo1.output.connect(osc1.amplitude);
			lfo1.output.connect(osc2.amplitude);
		} else if (connection.equals("Osc 2 Vol")) {
			lfo1.output.connect(osc2.amplitude);
		} else if (connection.equals("Osc 1 Vol")) {
			lfo1.output.connect(osc1.amplitude);
		} else if (connection.equals("Filter Cutoff")) {
			lfo1.output.connect(filter.getHighPass().Q);
			lfo1.output.connect(filter.getLowPass().Q);
		}
	}

	public void env2Connection(String connection) {
		if (env2.getEnvPlayer().output.isConnected()) {
			env2.getEnvPlayer().output.disconnectAll();
		}
		if (connection.equals("Filter Cutoff")) {
			env2.getEnvPlayer().output.connect(filter.getHighPass().Q);
			env2.getEnvPlayer().output.connect(filter.getLowPass().Q);
		} else if (connection.equals("Oscillators")) {
			env2.getEnvPlayer().output.connect(osc1.amplitude);
			env2.getEnvPlayer().output.connect(osc2.amplitude);
		} else if (connection.equals("Osc 1 Vol")) {
			env2.getEnvPlayer().output.disconnectAll();
			env2.getEnvPlayer().output.connect(osc1.amplitude);
		} else if (connection.equals("Osc 2 Vol")) {
			env2.getEnvPlayer().output.disconnectAll();
			env2.getEnvPlayer().output.connect(osc2.amplitude);
		}
	}

	// Getters
	public Envelope getEnv1() {
		return this.env1;
	}

	public Envelope getEnv2() {
		return this.env2;
	}

	public Synthesizer getSynth() {
		return this.synth;
	}

	public Oscillator getOsc1() {
		return this.osc1;
	}

	public Oscillator getOsc2() {
		return this.osc2;
	}

	public WaveTableOsc getWaveOsc1() {
		return this.waveOsc;
	}

	public Distortion getDistortion() {
		return this.distortion;
	}
	public FunctionOscillator getWaveOsc1Function() {
		return this.waveTable;
	}

	public LFO getLFO1() {
		return this.lfo1;
	}

	public LineOut getLineOut() {
		return this.lineOut;
	}

	public MixerStereo getMixer() {
		return this.mixer;
	}

	public Filter getFilter() {
		return this.filter;
	}

	public EQ getEq() {
		return this.eq;
	}

	public PhaseShifter getPhase() {
		return this.phase;
	}

	public Preset getPreset() {
		return this.preset;
	}

	public void stop() {
		synth.stop();
	}

	@Override
	public UnitOutputPort getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void noteOff(TimeStamp arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noteOn(double arg0, double arg1, TimeStamp arg2) {
		playNote(arg1);
	}

}

