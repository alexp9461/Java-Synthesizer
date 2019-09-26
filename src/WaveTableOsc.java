import java.io.File;
import java.io.IOException;

import com.jsyn.data.DoubleTable;
import com.jsyn.data.FloatSample;
import com.jsyn.data.ShortSample;
import com.jsyn.unitgen.FunctionOscillator;
import com.jsyn.util.SampleLoader;
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
public class WaveTableOsc extends FunctionOscillator{
	final static int WAVE_LENGTH = 1;
	private File file;
	private FloatSample sampleFile;
	private DoubleTable myTable;
	private FunctionOscillator myWaveOsc;
	int octave;
			
	public WaveTableOsc() {		
		
		octave = 0;
		// Soundfile from: https://cymatics.fm
		file = new File("Cymatics FM Table 1.wav");
		// http://www.softsynth.com/jsyn/docs/usersguide.php
		try {
			sampleFile = SampleLoader.loadFloatSample(file);
		} catch (IOException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		
		double [] data = new double[WAVE_LENGTH+1];

		for( int i=0; i<data.length; i++ )
		{
			data[i] = (sampleFile.evaluate(i));

		}
		myTable = new DoubleTable(data);
		myWaveOsc = new FunctionOscillator ();
		myWaveOsc.function.set(myTable);

	}
	
	public void loadSample(File file) {
		this.file = file;
		// http://www.softsynth.com/jsyn/docs/usersguide.php

		try {
			sampleFile = SampleLoader.loadFloatSample(file);
		} catch (IOException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}

		double [] data = new double[WAVE_LENGTH+1];

		for( int i=0; i<data.length; i++ )
		{
			data[i] = (sampleFile.evaluate(i));

		}
		myTable.write(data);
		myWaveOsc.function.set(myTable);
	}
	
	public int getOctave() {
		return octave;
	}

	public void setOctave(int octave) {
		this.octave = octave;
	}

	public FunctionOscillator getWaveOsc() {
		return this.myWaveOsc;
	}
	
}
