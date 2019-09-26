import java.util.Random;

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
public class Distortion extends UnitFilter {
	
	private boolean on;
	public Distortion() {
		this.setOn(false);
	}

	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
        double[] outputs = output.getValues();
       
        if(isOn()) {
        		for( int i = start; i < limit; i++ ) {
        			 double x = inputs[i];
        			 if(x > 0.4 && x < 0.6) {
        				 x = x * (x * Math.random());
        			 } else {
        				 x = x;
        			 }
        			 outputs[i] = x;
        			 
        }	
        	
        } else {
        		for(int i = start; i<limit; i++) {
        			double x = inputs[i];
        			outputs[i] = x;
        		}
        }
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}


}
