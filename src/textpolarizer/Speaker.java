/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textpolarizer;

import com.sun.speech.freetts.*;

/**
 *
 * @author Hecktor
 */
public class Speaker {

    private final String VOICENAME = "kevin16";  //kevin | kevin16 | mbrola_us1
    private Voice voicePositive;
    private Voice voiceNegative;
    private Voice voiceNeutral;
    private VoiceManager voiceManager;

    public Speaker() {
        //System.setProperty( "mbrola.base", "./mbrola" );
        voiceManager = VoiceManager.getInstance();
        voicePositive = voiceManager.getVoice(VOICENAME);
        voiceNegative = voiceManager.getVoice(VOICENAME);
        voiceNeutral = voiceManager.getVoice(VOICENAME);

        /* 1.0 (volume at its loudest level)
         * 100.0 (frequency of the voice)
         * 150.0 (rate of words per minute)
         * 1.0 (stretch of the word in its duration)
         */

        //POSITIVE
        float hertz = (float) 300.0;
        float wpm = (float) 300.0;
        float stretch = (float) 1.2;
        voiceNegative.setPitch(hertz);
        voiceNegative.setRate(wpm);
        voiceNegative.setDurationStretch(stretch);

        //NEGATIVE
        hertz = (float) 100.0;
        voicePositive.setPitch(hertz);

        //NEUTRAL
        hertz = (float) 150.0;
        wpm = (float) 150.0;
        stretch = (float) 1.0;
        voiceNeutral.setPitch(hertz);
        voiceNeutral.setRate(wpm);
        voiceNeutral.setDurationStretch(stretch);
    }

    public void readTextNegative(String text) {
        try {
            voicePositive.allocate();
            voicePositive.speak(text);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void readTextPositive(String text) {
        try {
            voiceNegative.allocate();
            voiceNegative.speak(text);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void readTextNeutral(String text) {
        try {
            voiceNeutral.allocate();
            voiceNeutral.speak(text);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
