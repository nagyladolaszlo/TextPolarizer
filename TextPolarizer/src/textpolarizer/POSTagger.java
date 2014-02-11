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
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import parsetree.TreeBuilder;

/**
 *
 * @author Hecktor
 */
public class POSTagger {
    
    private Evaluator evaluator = new Evaluator();

    public POSTagger() {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
        TreeBuilder parseTree;
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        ArrayList<String> lemmaList;
        
        // read some text in the text variable
        String text = "I do not love you. I love wolfes. Sara does not hate me. This movie doesn't care about cleverness, wit or any other kind of intelligent humor. "
                + "Emily got only a few presents for Christmas, beacause she was a bad a girl. The general availability of both cold and warm air in the troposphere makes winter storms in middle latitudes a common occurrence in several regions around the country. " +
/*                + "Scenes like these are hilarious. And while there are somewhat long sequences without any laughs, the laugh-out-loud scenes more than make up for those lacking.\n" +
"\n" +
"The only thing about this film that makes me drop the rating a notch is the execution of the last half hour. Just like the first movie, they set up a ridiculous \"action\" ending, that isn't action at all. Its ending is overlong and goes way too long without any laughs.\n" +
"\n" +
"But that is the only thing I didn't like about the movie. De Niro and Crystal have a real chemistry, even more so here than in the first film. De Niro steals the scenes he is in, and Crystal steals the scenes he is in, and when they are both on screen, you're not sure who to look at.\n" +
"\n" +*/
"I will not win the match tomorrow. I will win the match tomorrow."       ; // Add your text here!

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            lemmaList = new ArrayList<> ();
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
                switch (pos){
                    case "JJ": realPOS = "a"; break;
                    default:
                        realPOS = pos.substring(0, 1).toLowerCase();
                }
                //Double evaluation = evaluator.extract(word, realPOS);
                //System.out.println(">"+word+" ? "+pos+" ? "+evaluation);
                Double evaluation2 = evaluator.extract(lemma, realPOS);
                //System.out.println("="+lemma+" ? "+pos+" ? "+evaluation2);
                
                
                if (evaluation2 == null) evaluation2 = 0.0;
                BigDecimal beva = new BigDecimal(evaluation2, new MathContext(2));
                System.out.print(word + "(" + beva + "//" + realPOS + ")" + " ");
                
                evalsent += evaluation2;
                
                lemmaList.add(lemma);
            }
            System.out.println();
            System.out.println("=> "+evalsent);
            
            Tree tree = sentence.get(TreeAnnotation.class);
            System.out.println("bal");
            tree.pennPrint();
            System.out.println(tree.toString());
            parseTree = new TreeBuilder(tree.toString(), lemmaList);
            parseTree.buildUpTree(evaluator);
            System.out.println("Tree evaluation: ");
            System.out.println(parseTree.evaluateTree());
        }

        // This is the coreference link graph
        // Each chain stores a set of mentions that link to each other,
        // along with a method for getting the most representative mention
        // Both sentence and token offsets start at 1!
        Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
        
        Double eva = evaluator.extract("uncut", "a");
        System.out.println("<>"+eva);
    }
    
    public POSTagger(int i){
        
    }
}
