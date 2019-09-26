import com.jsyn.*;
import com.jsyn.unitgen.FilterAllPass;
import com.jsyn.unitgen.FilterBandStop;
import com.jsyn.unitgen.FilterBiquad;
import com.jsyn.unitgen.FilterBiquadShelf;
import com.jsyn.unitgen.FilterHighShelf;
import com.jsyn.unitgen.FilterLowShelf;
import com.jsyn.unitgen.FilterOnePole;
import com.jsyn.unitgen.FilterPeakingEQ;
import com.jsyn.unitgen.UnitFilter;

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
public class EQ extends UnitFilter{
	private FilterHighShelf eqHigh;;
	private FilterLowShelf eqLow;
	
	public EQ() {
		eqHigh = new FilterHighShelf();
		eqLow = new FilterLowShelf();
	}
	
	public void setHighFreq( double freq) {
		this.eqHigh.frequency.set(freq);
	}
	public void setLowFreq(double freq) {
		this.eqLow.frequency.set(freq);
	}
	public void setHighGain(double gain) {
		this.eqHigh.gain.set(gain);
	}
	public void setLowGain(double gain) {
		this.eqLow.gain.set(gain);
	}
	public FilterLowShelf getLow() {
		return this.eqLow;
	}
	public FilterHighShelf getHigh() {
		return this.eqHigh;
	}

	@Override
	public void generate(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}