/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textpolarizer;

import parsetree.EvaluatedSentence;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Hecktor
 */
public class Model {

    private UserInterface ui;
    private POSTagger tagger;
    private Speaker speaker;
    private ArrayList<EvaluatedSentence> evaluatedSentences;
    private final BigDecimal NEGATIVE = new BigDecimal(-10.0);
    private final BigDecimal POSITIVE = new BigDecimal(+10.0);

    public Model(UserInterface ui) {
        this.ui = ui;
        tagger = new POSTagger();
        speaker = new Speaker();
    }

    public void doPolarize() {
        File inputFile = new File(ui.getjTextField1().getText());
        evaluatedSentences = tagger.parse(inputFile);
    }

    public String getTextResult() {
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<html>");

        for (EvaluatedSentence evSe : evaluatedSentences) {
            BigDecimal score = evSe.getScore();
            String style;

            if (score.compareTo(this.NEGATIVE) < 0) {
                style = "<font style=\"BACKGROUND-COLOR: red\">";
            } else {
                if (score.compareTo(this.POSITIVE) > 0) {
                    style = "<font style=\"BACKGROUND-COLOR: green\">";
                } else {
                    style = "<font style=\"BACKGROUND-COLOR: yellow\">";
                }
            }
            line = style + evSe.getSentence() + "</font><br>";
            stringBuilder.append(line);
        }
        
        stringBuilder.append("</html>");

        return stringBuilder.toString();
    }

    public void readTextResult() {
        BigDecimal score;
        
        for (EvaluatedSentence evSe : evaluatedSentences) {
            score = evSe.getScore();
            if (score.compareTo(this.NEGATIVE) < 0) {
                speaker.readTextNegative(evSe.getSentence());
            } else {
                if (score.compareTo(this.POSITIVE) > 0) {
                    speaker.readTextPositive(evSe.getSentence());
                } else {
                    speaker.readTextNeutral(evSe.getSentence());
                }
            }
        }
    }
}
