import java.util.ArrayList;

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
public class Preset {

	private ArrayList<Double> reeceDouble;
	private ArrayList<String> reeceString;
	
	private ArrayList<Double> subDouble;
	private ArrayList<String> subString;
	
	private ArrayList<Double> leadDouble;
	private ArrayList<String> leadString;
	
	private ArrayList<Double> wobbleDouble;
	private ArrayList<String> wobbleString;
	
	private ArrayList<Double> heliDouble;
	private ArrayList<String> heliString;
	
	private ArrayList<Double> squaresDouble;
	private ArrayList<String> squaresString;


	public Preset() {
		reeceDouble = new ArrayList<Double>();
		reeceString = new ArrayList<String>();
		subDouble = new ArrayList<Double>();
		subString = new ArrayList<String>();
		leadDouble = new ArrayList<Double>();
		leadString = new ArrayList<String>();
		wobbleDouble = new ArrayList<Double>();
		wobbleString = new ArrayList<String>();
		heliDouble = new ArrayList<Double>();
		heliString = new ArrayList<String>();
		squaresDouble = new ArrayList<Double>();
		squaresString = new ArrayList<String>();

		reeceString.add(0, "Saw");
		reeceString.add(1, "Saw");
		reeceString.add(2, "");
		reeceString.add(3, "Low Pass");
		reeceString.add(4, "");
		reeceString.add(5, "Sine");
		
		reeceDouble.add(0, 2.0);		// osc1Fine
		reeceDouble.add(1, 1.0);		//osc1Vol
		reeceDouble.add(2, -2.0);	//osc1oct
		reeceDouble.add(3, -2.0);	//osc2Fine
		reeceDouble.add(4, 1.0);		//osc2Vol
		reeceDouble.add(5, -2.0);	//osc2oct
		reeceDouble.add(6, 0.0);		//waveVol
		reeceDouble.add(7, 0.0);		//	waveOsc
		reeceDouble.add(8, 0.001);	//env1A
		reeceDouble.add(9, 0.0);		//env1D
		reeceDouble.add(10, 1.0);	//env1S
		reeceDouble.add(11, 0.001);	//env1R
		reeceDouble.add(12, 0.0);	//env2A
		reeceDouble.add(13, 0.0);	//env2D
		reeceDouble.add(14, 0.0);	//env2S
		reeceDouble.add(15, 0.0);	//env2R
		reeceDouble.add(16, 0.0);	//lfoSpeed
		reeceDouble.add(17, 13.0);	//filterCutoff
		reeceDouble.add(18, 1.0);	//eqlowGain 
		reeceDouble.add(19, 1.0);	//eqlowFreq 
		reeceDouble.add(20, 1.0);	//eqhighGain
		reeceDouble.add(21, 1.0);	//eqhighFreq
		reeceDouble.add(22, 1.0);	// mainVol

		subString.add(0, "Sine");
		subString.add(1, "Sine");
		subString.add(2, "");
		subString.add(3, "Low Pass");
		subString.add(4, "");
		subString.add(5, "Sine");
		
		subDouble.add(0, 1.0);		// osc1Fine
		subDouble.add(1, 1.0);		//osc1Vol
		subDouble.add(2, -2.0);	//osc1oct
		subDouble.add(3, 1.0);	//osc2Fine
		subDouble.add(4, 0.0);		//osc2Vol
		subDouble.add(5, -2.0);	//osc2oct
		subDouble.add(6, 0.0);		//waveVol
		subDouble.add(7, 0.0);		//	waveOsc
		subDouble.add(8, 0.001);	//env1A
		subDouble.add(9, 0.0);		//env1D
		subDouble.add(10, 1.0);	//env1S
		subDouble.add(11, 0.001);	//env1R
		subDouble.add(12, 0.0);	//env2A
		subDouble.add(13, 0.0);	//env2D
		subDouble.add(14, 0.0);	//env2S
		subDouble.add(15, 0.0);	//env2R
		subDouble.add(16, 0.0);	//lfoSpeed
		subDouble.add(17, 15.0);	//filterCutoff
		subDouble.add(18, 1.0);	//eqlowGain 
		subDouble.add(19, 1.0);	//eqlowFreq 
		subDouble.add(20, 1.0);	//eqhighGain
		subDouble.add(21, 1.0);	//eqhighFreq
		subDouble.add(22, 1.0);	// mainVol
		
		leadString.add(0, "Saw");
		leadString.add(1, "Square");
		leadString.add(2, "");
		leadString.add(3, "High Pass");
		leadString.add(4, "");
		leadString.add(5, "Sine");
		
		leadDouble.add(0, 1.0);		// osc1Fine
		leadDouble.add(1, 1.0);		//osc1Vol
		leadDouble.add(2, 1.0);	//osc1oct
		leadDouble.add(3, 1.0);	//osc2Fine
		leadDouble.add(4, 0.0);		//osc2Vol
		leadDouble.add(5, 0.0);	//osc2oct
		leadDouble.add(6, 0.0);		//waveVol
		leadDouble.add(7, 0.0);		//	waveOsc
		leadDouble.add(8, 0.001);	//env1A
		leadDouble.add(9, 0.0);		//env1D
		leadDouble.add(10, 1.0);	//env1S
		leadDouble.add(11, 0.001);	//env1R
		leadDouble.add(12, 0.0);	//env2A
		leadDouble.add(13, 0.0);	//env2D
		leadDouble.add(14, 0.0);	//env2S
		leadDouble.add(15, 0.0);	//env2R
		leadDouble.add(16, 0.0);	//lfoSpeed
		leadDouble.add(17, 3.0);	//filterCutoff
		leadDouble.add(18, 1.0);	//eqlowGain 
		leadDouble.add(19, 1.0);	//eqlowFreq 
		leadDouble.add(20, 1.0);	//eqhighGain
		leadDouble.add(21, 1.0);	//eqhighFreq
		leadDouble.add(22, 1.0);	// mainVol
		
		wobbleString.add(0, "Saw"); // osc1
		wobbleString.add(1, "Square"); // osc2
		wobbleString.add(2, ""); // env
		wobbleString.add(3, "Low Pass"); // LFO
		wobbleString.add(4, "Filter Cutoff"); // filter type
		wobbleString.add(5, "Sine"); // lfo shape
		
		wobbleDouble.add(0, 1.0);		// osc1Fine
		wobbleDouble.add(1, 1.0);		//osc1Vol
		wobbleDouble.add(2, -2.0);	//osc1oct
		wobbleDouble.add(3, 1.0);	//osc2Fine
		wobbleDouble.add(4, 2.0);		//osc2Vol
		wobbleDouble.add(5, -2.0);	//osc2oct
		wobbleDouble.add(6, 0.5);		//waveVol
		wobbleDouble.add(7, -1.0);		//	waveOsc
		wobbleDouble.add(8, 0.001);	//env1A
		wobbleDouble.add(9, 0.0);		//env1D
		wobbleDouble.add(10, 1.0);	//env1S
		wobbleDouble.add(11, 0.001);	//env1R
		wobbleDouble.add(12, 0.0);	//env2A
		wobbleDouble.add(13, 0.0);	//env2D
		wobbleDouble.add(14, 0.0);	//env2S
		wobbleDouble.add(15, 0.0);	//env2R
		wobbleDouble.add(16, 2.0);	//lfoSpeed
		wobbleDouble.add(17, 13.0);	//filterCutoff
		wobbleDouble.add(18, 1.0);	//eqlowGain 
		wobbleDouble.add(19, 1.0);	//eqlowFreq 
		wobbleDouble.add(20, 1.0);	//eqhighGain
		wobbleDouble.add(21, 1.0);	//eqhighFreq
		wobbleDouble.add(22, 1.0);	// mainVol


		
		heliString.add(0, "Noise"); // osc1
		heliString.add(1, "Sine"); // osc2
		heliString.add(2, ""); // env
		heliString.add(3, "Low Pass"); // filter type
		heliString.add(4, "Filter Cutoff"); // lfo
		heliString.add(5, "Sine"); // lfo shape
		
		heliDouble.add(0, 1.0);		// osc1Fine
		heliDouble.add(1, 1.0);		//osc1Vol
		heliDouble.add(2, 0.0);	//osc1oct
		heliDouble.add(3, 1.0);	//osc2Fine
		heliDouble.add(4, 1.0);		//osc2Vol
		heliDouble.add(5, -2.0);	//osc2oct
		heliDouble.add(6, 0.0);		//waveVol
		heliDouble.add(7, 0.0);		//	waveOsc
		heliDouble.add(8, 0.001);	//env1A
		heliDouble.add(9, 0.0);		//env1D
		heliDouble.add(10, 1.0);	//env1S
		heliDouble.add(11, 0.001);	//env1R
		heliDouble.add(12, 0.0);	//env2A
		heliDouble.add(13, 0.0);	//env2D
		heliDouble.add(14, 0.0);	//env2S
		heliDouble.add(15, 0.0);	//env2R
		heliDouble.add(16, 10.0);	//lfoSpeed
		heliDouble.add(17, 10.0);	//filterCutoff
		heliDouble.add(18, 1.0);	//eqlowGain 
		heliDouble.add(19, 1.0);	//eqlowFreq 
		heliDouble.add(20, 1.0);	//eqhighGain
		heliDouble.add(21, 1.0);	//eqhighFreq
		heliDouble.add(22, 1.0);	// mainVol
		
		squaresString.add(0, "Square");
		squaresString.add(1, "Square");
		squaresString.add(2, "");
		squaresString.add(3, "Low Pass");
		squaresString.add(4, "");
		squaresString.add(5, "Sine");
		
		squaresDouble.add(0, 2.0);		// osc1Fine
		squaresDouble.add(1, 1.0);		//osc1Vol
		squaresDouble.add(2, 0.0);	//osc1oct
		squaresDouble.add(3, -2.0);	//osc2Fine
		squaresDouble.add(4, 1.0);		//osc2Vol
		squaresDouble.add(5, 0.0);	//osc2oct
		squaresDouble.add(6, 0.0);		//waveVol
		squaresDouble.add(7, 0.0);		//	waveOsc
		squaresDouble.add(8, 0.001);	//env1A
		squaresDouble.add(9, 0.0);		//env1D
		squaresDouble.add(10, 1.0);	//env1S
		squaresDouble.add(11, 0.001);	//env1R
		squaresDouble.add(12, 0.0);	//env2A
		squaresDouble.add(13, 0.0);	//env2D
		squaresDouble.add(14, 0.0);	//env2S
		squaresDouble.add(15, 0.0);	//env2R
		squaresDouble.add(16, 10.0);	//lfoSpeed
		squaresDouble.add(17, 10.0);	//filterCutoff
		squaresDouble.add(18, 1.0);	//eqlowGain 
		squaresDouble.add(19, 1.0);	//eqlowFreq 
		squaresDouble.add(20, 1.0);	//eqhighGain
		squaresDouble.add(21, 1.0);	//eqhighFreq
		squaresDouble.add(22, 1.0);	// mainVol
	}
	
	public void loadPreset(ArrayList<Double> doubleParameters, ArrayList<String> stringParameters) {

		Main.getController().changeOsc1(stringParameters.get(0));
		Main.getController().changeOsc2(stringParameters.get(1));
		Main.getController().env2Box.setValue(stringParameters.get(2));
		Main.getController().connectEnv2();
		Main.getController().changeFilter(stringParameters.get(3));
		Main.getController().LFOConnect.setValue(stringParameters.get(4));
		Main.getController().LFO1Shape(stringParameters.get(5));
		
		Main.getController().osc1CentSlider.setValue(doubleParameters.get(0));
		Main.getController().osc1Cent();
		Main.getController().osc1Vol.setValue(doubleParameters.get(1));
		Main.getController().osc1Vol();
		Main.getController().osc1Octave.getValueFactory().setValue(doubleParameters.get(2).intValue());
		Main.getController().osc1Octave();
		Main.getController().osc2CentSlider.setValue(doubleParameters.get(3));
		Main.getController().osc2Cent();
		Main.getController().osc2Vol.setValue(doubleParameters.get(4));
		Main.getController().osc2Vol();
		Main.getController().osc2Octave.getValueFactory().setValue(doubleParameters.get(5).intValue());
		Main.getController().osc2Octave();
		Main.getController().waveOsc1Vol.setValue(doubleParameters.get(6));
		Main.getController().waveOsc1Vol();
		Main.getController().waveOsc1Octave.getValueFactory().setValue(doubleParameters.get(7).intValue());
		Main.getController().waveOsc1Octave();
		Main.getController().env1AttackTSlider.setValue(doubleParameters.get(8));
		Main.getController().env1A();
		Main.getController().env1DecaySlider.setValue(doubleParameters.get(9));
		Main.getController().env1D();
		Main.getController().env1SustainSlider.setValue(doubleParameters.get(10));
		Main.getController().env1S();
		Main.getController().env1ReleaseSlider.setValue(doubleParameters.get(11));
		Main.getController().env1R();
		Main.getController().env2AttackSlider.setValue(doubleParameters.get(12));
		Main.getController().env2A();
		Main.getController().env2DecaySlider.setValue(doubleParameters.get(13));
		Main.getController().env2D();
		Main.getController().env2SustainSlider.setValue(doubleParameters.get(14));
		Main.getController().env2S();
		Main.getController().env2ReleaseSlider.setValue(doubleParameters.get(15));
		Main.getController().env2R();
		Main.getController().LFO1SpeedSlider.setValue(doubleParameters.get(16));
		Main.getController().LFO1SetSpeed();
		Main.getController().cutoffSlider.setValue(doubleParameters.get(17));
		Main.getController().setCutoff();
		Main.getController().eqLowGain.setValue(doubleParameters.get(18));
		Main.getController().eqSetLowGain();
		Main.getController().eqLowFreq.setValue(doubleParameters.get(19));
		Main.getController().eqSetLowFreq();
		Main.getController().eqHighGain.setValue(doubleParameters.get(20));
		Main.getController().eqSetHighGain();
		Main.getController().eqHighFreq.setValue(doubleParameters.get(21));
		Main.getController().eqSetHighFreq();
		Main.getController().mainVolSlider.setValue(doubleParameters.get(22));
		Main.getController().mainVolume();

	}
	
	public Preset createReecePreset() {
		return null;
		
	}
	public ArrayList<Double> getReeceDouble() {
		return this.reeceDouble;
	}
	public ArrayList<String> getReeceString() {
		return this.reeceString;
	}
	public ArrayList<Double> getSubDouble() {
		return this.subDouble;
	}
	public ArrayList<String> getSubString() {
		return this.subString;
	}
	public ArrayList<Double> getLeadDouble(){
		return this.leadDouble;
	}
	public ArrayList<String> getLeadString(){
		return this.leadString;
	}
	public ArrayList<Double> getWobbleDouble(){
		return this.wobbleDouble;
	}
	public ArrayList<String> getWobbleString(){
		return this.wobbleString;
	}
	public ArrayList<Double> getHeliDouble(){
		return this.heliDouble;
	}
	public ArrayList<String> getHeliString(){
		return this.heliString;
	}
	public ArrayList<Double> getSquaresDouble(){
		return this.squaresDouble;
	}
	public ArrayList<String> getSquaresString(){
		return this.squaresString;
	}
	
}

/* 0: osc1Fine  1: osc1Vol  2: osc1oct 
 * 3: osc2Fine  4: osc2Vol  5: osc2oct
 * 6: waveVol  7: waveOsc
 * 8: env1A  9:env1D  10:env1S  11: env1R
 * 12: env2A  13: env2D  14: env2S  15: env2R
 * 16: lfoSpeed
 * 17: filterCutoff
 * 18: eqlowGain 19: eqlowFreq  20: eqhighGain 21: eqhighFreq
 * 22: mainVol
 * */




