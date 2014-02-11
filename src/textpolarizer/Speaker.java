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
public class Speaker{
    private final String VOICENAME = "kevin16";  //kevin | kevin16 | mbrola_us1
    private Voice voicePositive;
    private Voice voiceNegative;
    private VoiceManager voiceManager;
    
    public Speaker(){
        //System.setProperty( "mbrola.base", "./mbrola" );
        voiceManager = VoiceManager.getInstance();
        voicePositive = voiceManager.getVoice(VOICENAME);
        voiceNegative = voiceManager.getVoice(VOICENAME);
    }
    
    public void readText(String text){
        try{
            float hertz = (float) 100.0;
            voicePositive.allocate();
            voicePositive.setPitch(hertz);
            voicePositive.speak(text);
            voicePositive.deallocate();
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    public void readTextVoice2(String text){
        try{
            float hertz = (float) 400.0;
            float wpm = (float) 300.0;
            float stretch = (float) 1.2;
            /* 1.0 (volume at its loudest level)
             * 100.0 (frequency of the voice)
             * 150.0 (rate of words per minute)
             * 1.0 (stretch of the word in its duration)
             */
            voiceNegative.allocate();
            voiceNegative.setPitch(hertz);
            voiceNegative.setRate(wpm);
            voiceNegative.setDurationStretch(stretch);
            
            
            voiceNegative.speak(text);
            voiceNegative.deallocate();
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
}
