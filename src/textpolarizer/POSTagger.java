/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textpolarizer;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import parsetree.EvaluatedSentence;
import parsetree.TreeBuilder;

/**
 *
 * @author Hecktor
 */
public class POSTagger {

    private TreeBuilder parseTree;
    private Evaluator evaluator;
    private StanfordCoreNLP pipeline;
    private ArrayList<EvaluatedSentence> evaluatedSentences;

    public POSTagger() {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, parse");
        pipeline = new StanfordCoreNLP(props);
        evaluator = new Evaluator();
    }

    public ArrayList<EvaluatedSentence> parse(File input) {
        ArrayList<String> lemmaList;
        String inputText = getTextOutOfFile(input);
        evaluatedSentences = new ArrayList<>();
        EvaluatedSentence evaluatedSentence;

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(inputText);

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            lemmaList = new ArrayList<>();
            Double evalsent = 0.0;
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(PartOfSpeechAnnotation.class);
                // this is the lemma of the word
                String lemma = token.get(LemmaAnnotation.class);

                String realPOS;
                switch (pos) {
                    case "JJ":
                        realPOS = "a";
                        break;
                    default:
                        realPOS = pos.substring(0, 1).toLowerCase();
                }
                //Double evaluation = evaluator.extract(word, realPOS);
                //System.out.println(">"+word+" ? "+pos+" ? "+evaluation);
                Double evaluation2 = evaluator.extractPointsForWord(lemma, realPOS);
                //System.out.println("="+lemma+" ? "+pos+" ? "+evaluation2);


                if (evaluation2 == null) {
                    evaluation2 = 0.0;
                }
                BigDecimal beva = new BigDecimal(evaluation2, new MathContext(2));
                System.out.print(word + "(" + beva + "//" + realPOS + ")" + " ");

                evalsent += evaluation2;

                lemmaList.add(lemma);
            }
            System.out.println();
            System.out.println("=> " + evalsent);

            Tree tree = sentence.get(TreeAnnotation.class);
            
            parseTree = new TreeBuilder(tree.toString(), lemmaList);
            parseTree.buildUpTree(evaluator);
            
            Double sentenceScore = parseTree.evaluateTree();
            System.out.println(sentenceScore);
            BigDecimal sentencScore = new BigDecimal(sentenceScore, new MathContext(2));
            //BigDecimal sentencScore = new BigDecimal(evalsent, new MathContext(2));
            evaluatedSentence = new EvaluatedSentence(sentencScore,sentence.toString());
            evaluatedSentences.add(evaluatedSentence);
        }

        // This is the coreference link graph
        // Each chain stores a set of mentions that link to each other,
        // along with a method for getting the most representative mention
        // Both sentence and token offsets start at 1!
        Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);

        Double eva = evaluator.extractPointsForWord("uncut", "a");
        System.out.println("<>" + eva);
        
        return evaluatedSentences;
    }

    private String getTextOutOfFile(File f) {
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
        } catch (Exception ex) {
        } finally {
            return stringBuilder.toString();
        }
    }
}
