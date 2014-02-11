/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textpolarizer;

import java.io.File;

/**
 *
 * @author Hecktor
 */
public class TextPolarizer {

    public static Wrapper wrapper;
    public static POSTagger tagger;
    public static Speaker speaker;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

         File inputFile = new File("data" + File.separator + "input.txt");
        //wrapper = new Wrapper(inputFile);
        //wrapper.wrap();
        tagger = new POSTagger(); 
    //    speaker = new Speaker();
    //    speaker.readText("Your password is wrong.");
    //    speaker.readTextVoice2("Your password is wrong.");
    }
}
