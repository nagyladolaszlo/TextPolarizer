/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsetree;

import java.util.ArrayList;

/**
 *
 * @author Hecktor
 */
public class Node {
    private String label;
    private String word;
    private String lemma;
    private Double point; //represents the point of the world
    private Double score; //represents the obtained points inlcuding this node
    private ArrayList<Node> childs;
    private Node parent;
    
    public Node (){
        this.label = "";
        this.word = "";
        this.lemma = "";
        this.point = 0.0;
        this.childs = new ArrayList<> ();
        this.parent = null;
    }
    
    public Node (String label) {
        this.label = label;
        
        this.word = "";
        this.lemma = "";
        this.point = 0.0;
        this.childs = new ArrayList<> ();
        this.parent = null;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public Double getScore() {
        return score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public ArrayList<Node> getChilds() {
        return childs;
    }
    
    public void setChilds(ArrayList<Node> childs) {
        this.childs = childs;
    }
    
    public int getChildsNumber() {
        return childs.size();
    }
    
    public Node getChildByIndex(int i) {
        return childs.get(i);
    }
    
    public void insertChild(Node node) {
        childs.add(node);
    }
    
}
