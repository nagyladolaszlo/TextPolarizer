/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsetree;

import java.math.BigDecimal;

/**
 *
 * @author Hecktor
 */
public class EvaluatedSentence {
    private BigDecimal score;
    private String sentence;

    public EvaluatedSentence() {
    }

    public EvaluatedSentence(String sentence) {
        this.sentence = sentence;
    }

    public EvaluatedSentence(BigDecimal score, String sentence) {
        this.score = score;
        this.sentence = sentence;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
