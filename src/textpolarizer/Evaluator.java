/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textpolarizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author Hecktor
 */
public class Evaluator {

    private String pathToSWN = "data" + File.separator + "SentiWordNet3.0.0.txt";
    private HashMap<String, Double> _dict;

    public Evaluator() {
        _dict = new HashMap<String, Double>();
        HashMap<String, Vector<Double>> _temp = new HashMap<String, Vector<Double>>();
        try {
            BufferedReader csv = new BufferedReader(new FileReader(pathToSWN));
            //BufferedReader csv = new BufferedReader(new FileReader(Evaluator.class.getResource("/data/SentiWordNet3.0.0.txt").getPath()));
            String line = "";
            while ((line = csv.readLine()) != null) {
                String[] data = line.split("\t");
                Double score = Double.parseDouble(data[2]) - Double.parseDouble(data[3]);
                String[] words = data[4].split(" ");
                for (String w : words) {
                    String[] w_n = w.split("#");
                    w_n[0] += "#" + data[0];
                    int index = Integer.parseInt(w_n[1]) - 1;
                    if (_temp.containsKey(w_n[0])) {
                        Vector<Double> v = _temp.get(w_n[0]);
                        if (index > v.size()) {
                            for (int i = v.size(); i < index; i++) {
                                v.add(0.0);
                            }
                        }
                        v.add(index, score);
                        _temp.put(w_n[0], v);
                    } else {
                        Vector<Double> v = new Vector<Double>();
                        for (int i = 0; i < index; i++) {
                            v.add(0.0);
                        }
                        v.add(index, score);
                        _temp.put(w_n[0], v);
                    }
                }
            }
            Set<String> temp = _temp.keySet();
            for (Iterator<String> iterator = temp.iterator(); iterator.hasNext();) {
                String word = (String) iterator.next();
                Vector<Double> v = _temp.get(word);
                double score = 0.0;
                double sum = 0.0;
                for (int i = 0; i < v.size(); i++) {
                    score += ((double) 1 / (double) (i + 1)) * v.get(i);
                }
                for (int i = 1; i <= v.size(); i++) {
                    sum += (double) 1 / (double) i;
                }
                score /= sum;
                _dict.put(word, score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Double extractPointsForWord(String word, String pos) {
        return _dict.get(word + "#" + pos);
    }
}
