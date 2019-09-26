import com.jsyn.Synthesizer;
import com.jsyn.unitgen.UnitVoice;
import com.jsyn.util.Instrument;
import com.jsyn.util.VoiceOperation;
import com.softsynth.shared.time.ScheduledCommand;
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
public class MyVoiceAllocator implements Instrument {
    private int maxVoices;
    private MyVoiceAllocator.VoiceTracker[] trackers;
    private long tick;
    private Synthesizer synthesizer;
    private static final int UNASSIGNED_PRESET = -1;
    private int mPresetIndex = -1;
    private UnitVoice[] voices;

    public MyVoiceAllocator(UnitVoice[] voices) {
    		this.voices = voices;
        this.maxVoices = this.voices.length;
        this.trackers = new MyVoiceAllocator.VoiceTracker[this.maxVoices];

        for(int var2 = 0; var2 < this.maxVoices; ++var2) {
            this.trackers[var2] = new MyVoiceAllocator.VoiceTracker();
            this.trackers[var2].voice = this.voices[var2];
        }
    }
    public UnitVoice[] getVoices() {
    		return this.voices;
    }
    public void setVoices(UnitVoice[] voices) {
    		this.voices = voices;
    		for(int var2 = 0; var2 < this.maxVoices; ++var2) {
    			this.trackers[var2].setVoice(voices[var2]);

            }
    }

    public Synthesizer getSynthesizer() {
        if (this.synthesizer == null) {
            this.synthesizer = this.trackers[0].voice.getUnitGenerator().getSynthesizer();
        }

        return this.synthesizer;
    }

    public int getVoiceCount() {
        return this.maxVoices;
    }

    private MyVoiceAllocator.VoiceTracker findVoice(int var1) {
        MyVoiceAllocator.VoiceTracker[] var2 = this.trackers;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            MyVoiceAllocator.VoiceTracker var5 = var2[var4];
            if (var5.tag == var1) {
                return var5;
            }
        }

        return null;
    }

    private MyVoiceAllocator.VoiceTracker stealVoice() {
        MyVoiceAllocator.VoiceTracker var1 = null;
        MyVoiceAllocator.VoiceTracker var2 = null;
        MyVoiceAllocator.VoiceTracker[] var3 = this.trackers;
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            MyVoiceAllocator.VoiceTracker var6 = var3[var5];
            if (var6.voice == null) {
                return var6;
            }

            if (var1 != null) {
                if (!var6.on && var6.when < var1.when) {
                    var1 = var6;
                }
            } else if (var6.on) {
                if (var2 == null) {
                    var2 = var6;
                } else if (var6.when < var2.when) {
                    var2 = var6;
                }
            } else {
                var1 = var6;
            }
        }

        if (var1 != null) {
            return var1;
        } else {
            return var2;
        }
    }

    protected synchronized UnitVoice allocate(int var1) {
        MyVoiceAllocator.VoiceTracker var2 = this.allocateTracker(var1);
        return var2.voice;
    }

    private MyVoiceAllocator.VoiceTracker allocateTracker(int var1) {
        MyVoiceAllocator.VoiceTracker var2 = this.findVoice(var1);
        if (var2 == null) {
            var2 = this.stealVoice();
        }

        var2.tag = var1;
        var2.when = (long)(this.tick++);
        var2.on = true;
        return var2;
    }

    protected synchronized boolean isOn(int var1) {
        MyVoiceAllocator.VoiceTracker var2 = this.findVoice(var1);
        return var2 != null ? var2.on : false;
    }

    protected synchronized UnitVoice off(int var1) {
        MyVoiceAllocator.VoiceTracker var2 = this.findVoice(var1);
        if (var2 != null) {
            var2.off();
            return var2.voice;
        } else {
            return null;
        }
    }

    public void allNotesOff(TimeStamp var1) {
        this.getSynthesizer().scheduleCommand(var1, new ScheduledCommand() {
            public void run() {
                MyVoiceAllocator.VoiceTracker[] var1 = MyVoiceAllocator.this.trackers;
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    MyVoiceAllocator.VoiceTracker var4 = var1[var3];
                    if (var4.on) {
                        var4.voice.noteOff(MyVoiceAllocator.this.getSynthesizer().createTimeStamp());
                        var4.off();
                    }
                }

            }
        });
    }

    public void noteOn(final int var1, final double var2, final double var4, TimeStamp var6) {
        this.getSynthesizer().scheduleCommand(var6, new ScheduledCommand() {
            public void run() {
                MyVoiceAllocator.VoiceTracker var1x = MyVoiceAllocator.this.allocateTracker(var1);
                if (var1x.presetIndex != MyVoiceAllocator.this.mPresetIndex) {
                    var1x.voice.usePreset(MyVoiceAllocator.this.mPresetIndex);
                    var1x.presetIndex = MyVoiceAllocator.this.mPresetIndex;
                }

                var1x.voice.noteOn(var2, var4, MyVoiceAllocator.this.getSynthesizer().createTimeStamp());
            }
        });
    }

    public void noteOn(final int var1, final double var2, final double var4, final VoiceOperation var6, TimeStamp var7) {
        this.getSynthesizer().scheduleCommand(var7, new ScheduledCommand() {
            public void run() {
                MyVoiceAllocator.VoiceTracker var1x = MyVoiceAllocator.this.allocateTracker(var1);
                var6.operate(var1x.voice);
                var1x.voice.noteOn(var2, var4, MyVoiceAllocator.this.getSynthesizer().createTimeStamp());
            }
        });
    }

    public void noteOff(final int var1, TimeStamp var2) {
        this.getSynthesizer().scheduleCommand(var2, new ScheduledCommand() {
            public void run() {
                MyVoiceAllocator.VoiceTracker var1x = MyVoiceAllocator.this.findVoice(var1);
                if (var1x != null) {
                    var1x.voice.noteOff(MyVoiceAllocator.this.getSynthesizer().createTimeStamp());
                    MyVoiceAllocator.this.off(var1);
                }

            }
        });
    }

    public void setPort(final int var1, final String var2, final double var3, TimeStamp var5) {
        this.getSynthesizer().scheduleCommand(var5, new ScheduledCommand() {
            public void run() {
                MyVoiceAllocator.VoiceTracker var1x = MyVoiceAllocator.this.findVoice(var1);
                if (var1x != null) {
                    var1x.voice.setPort(var2, var3, MyVoiceAllocator.this.getSynthesizer().createTimeStamp());
                }

            }
        });
    }

    public void usePreset(final int var1, TimeStamp var2) {
        this.getSynthesizer().scheduleCommand(var2, new ScheduledCommand() {
            public void run() {
                MyVoiceAllocator.this.mPresetIndex = var1;
            }
        });
    }

    private class VoiceTracker {
        UnitVoice voice;
        int tag;
        int presetIndex;
        long when;
        boolean on;

        private VoiceTracker() {
            this.tag = -1;
            this.presetIndex = -1;
        }

        public void off() {
            this.on = false;
            this.when = (long)(MyVoiceAllocator.this.tick++);
        }
        public void setVoice(UnitVoice voice) {
        		this.voice = voice;
        }
    }
}
