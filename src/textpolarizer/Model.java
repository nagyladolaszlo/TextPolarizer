/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textpolarizer;

import java.io.File;
import java.util.ArrayList;
import parsetree.EvaluatedSentence;

/**
 *
 * @author Hecktor
 */
public class Model {

    private UserInterface ui;
    private POSTagger tagger;
    private Speaker speaker;
    private ArrayList<EvaluatedSentence> evaluatedSentences;

    public Model(UserInterface ui) {
        this.ui = ui;
        tagger = new POSTagger();
        //    speaker = new Speaker();
        //    speaker.readText("Your password is wrong.");
        //    speaker.readTextVoice2("Your password is wrong.");

    }
    
    public void doPolarize(){
        File inputFile = new File(ui.getjTextField1().getText());
        evaluatedSentences = tagger.parse(inputFile);
        
        System.out.println("DONE");
    }
}
