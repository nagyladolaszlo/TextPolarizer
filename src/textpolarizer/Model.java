/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textpolarizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
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
    private final BigDecimal NEGATIVE = new BigDecimal(-0.2);
    private final BigDecimal POSITIVE = new BigDecimal(+0.2);

    public Model(UserInterface ui) {
        this.ui = ui;
        tagger = new POSTagger();
        //    speaker = new Speaker();
        //    speaker.readText("Your password is wrong.");
        //    speaker.readTextVoice2("Your password is wrong.");

    }

    public void doPolarize() {
        File inputFile = new File(ui.getjTextField1().getText());
        evaluatedSentences = tagger.parse(inputFile);
    }

    public String getTextResult() {
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        //String ls = System.getProperty("line.separator");

        stringBuilder.append("<html>");
     //   stringBuilder.append(ls);

        for (EvaluatedSentence evSe : evaluatedSentences) {
            BigDecimal score = evSe.getScore();
            String style;

            if (score.compareTo(this.NEGATIVE) < 0) {
                style = "<font style=\"BACKGROUND-COLOR: red\">";
            } else {
                if (score.compareTo(this.POSITIVE) > 0) {
                    style = "<font style=\"BACKGROUND-COLOR: green\">";
                } else {
                    style = "<font>";
                }
            }
            line = style + evSe.getSentence() + "</font><br>";
            stringBuilder.append(line);
          //  stringBuilder.append(ls);
        }
        
        stringBuilder.append("</html>");
      //  stringBuilder.append(ls);

        return stringBuilder.toString();
    }

    public void readTextResult() {
    }
}
