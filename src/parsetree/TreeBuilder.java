/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsetree;

import java.util.ArrayList;
import textpolarizer.Evaluator;

/**
 *
 * @author Hecktor
 */
public class TreeBuilder {

    private Node root;
    private String sentence;
    private Double sentenceScore;
    private ArrayList<String> lemmaList;

    public TreeBuilder() {
        this.root = new Node();
        this.sentence = "";
        this.sentenceScore = 0.0;
    }

    public TreeBuilder(String sentence) {
        this.root = new Node();
        this.sentence = sentence;
        this.sentenceScore = 0.0;
    }

    public TreeBuilder(String sentence, ArrayList<String> lemmaList) {
        this.root = new Node();
        this.sentence = sentence;
        this.sentenceScore = 0.0;
        this.lemmaList = lemmaList;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public Double getSentenceScore() {
        return sentenceScore;
    }

    public void setSentenceScore(Double sentenceScore) {
        this.sentenceScore = sentenceScore;
    }

    public ArrayList<String> getLemmaList() {
        return lemmaList;
    }

    public void setLemmaList(ArrayList<String> lemmaList) {
        this.lemmaList = lemmaList;
    }

    public void buildUpTree(Evaluator evaluator) {
        String temp = sentence;
        String pos;
        Node tNode = root;
        int lemmaIndex = 0;

        for (int i = 0; i < temp.length(); i++) {
            int j;
            Double sentiNr;
            switch (temp.charAt(i)) {
                case '(':
                    j = temp.indexOf(" ", i);
                    String vLabel = temp.substring(i + 1, j);
                    Node nNode = new Node(vLabel);
                    nNode.setParent(tNode);
                    tNode.insertChild(nNode);
                    tNode = nNode;
                    i = j;
                    break;
                case ')':
                    tNode = tNode.getParent();
                    break;
                case ' ':
                    break;
                default:
                    j = temp.indexOf(")", i);
                    String vWord = temp.substring(i, j);
                    String vLemma = lemmaList.get(lemmaIndex);
                    String vPOS = tNode.getLabel();

                    switch (vPOS) {
                        case "JJ":
                            pos = "a";
                            break;
                        default:
                            pos = vPOS.substring(0, 1).toLowerCase();
                            break;
                    }

                    sentiNr = evaluator.extractPointsForWord(vLemma, pos);
                    if (sentiNr == null) {
                        sentiNr = 0.0;
                    }
                    sentiNr *= 100.0;

                    tNode.setWord(vWord);
                    tNode.setLemma(vLemma);
                    tNode.setPoint(sentiNr);

                    lemmaIndex++;
                    i = j - 1;
                    break;
            }
        }

        root = root.getChildByIndex(0);
        root.setParent(null);
    }

    public Double evaluateTree() {
        Double score = this.scoreTree(root);

        this.sentenceScore = score;

        return sentenceScore;
    }

    public Double scoreTree(Node tNode) {
        int childNr = tNode.getChildsNumber();
        Double point;
        Double score = 1.0;

        switch (childNr) {
            case 0:
                point = tNode.getPoint();
                tNode.setScore(point);
                return point;
            case 1:
                point = scoreTree(tNode.getChildByIndex(0));
                tNode.setScore(point);
                return point;
            default:
                for (int i = 0; i < childNr; i++) {
                    point = scoreTree(tNode.getChildByIndex(i));
                    if ((point.doubleValue() > -10.0) && (point.doubleValue() < 10.0)) {
                        point = 1.0;
                    }
                    score *= point;
                }
                tNode.setScore(score);
                return score;
        }
    }
}
