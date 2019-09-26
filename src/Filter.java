import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FilterHighPass;
import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.LineIn;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.UnitFilter;
import com.jsyn.unitgen.UnitGenerator;
import com.sun.xml.internal.ws.wsdl.writer.document.Port;
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
public class Filter{

	private String activeFilter;
	private FilterLowPass lowPass;
	private FilterHighPass highPass;
	private double freq;
	private double q;
	private UnitInputPort input;
	private UnitOutputPort output;
	private PassThrough passThrough;

	public Filter() {
		this.activeFilter = "Low Pass";
		lowPass = new FilterLowPass();
		highPass = new FilterHighPass();
		passThrough = new PassThrough();
	}

	public FilterLowPass getLowPass() {
		return lowPass;
	}

	public FilterHighPass getHighPass() {
		return highPass;
	}

	public UnitInputPort getInput() {
		return this.input;
	}

	public UnitOutputPort getOutput() {
		return this.output;
	}

	public PassThrough getPassThrough() {
		return this.passThrough;
	}

	public void selectFilter(String type) {
		if (type.equals("Low Pass")) {
			Main.getController().mainSynth.setFilterLow();
			this.activeFilter = "Low Pass";
		}
		if (type.equals("High Pass")) {
			Main.getController().mainSynth.setFilterHigh();
			this.activeFilter = "High Pass";
		}
	}

	public void setFrequency(double freq) {
		this.freq = freq * freq * freq;
		lowPass.frequency.set(this.freq);
		highPass.frequency.set(this.freq);
	}
	public void setActiveFilter(String activeFilter) {
		this.activeFilter = activeFilter;
	}

	public double getFrequency() {
		return this.freq;
	}
	public String getActiveFilter() {
		return this.activeFilter;
	}
	public double getQ() {
		return this.q;
	}
//	public void setQ(double q) {
//		this.q = q;
//		lowPass.Q.set(q);
//		highPass.Q.set(q);
//	}

}
