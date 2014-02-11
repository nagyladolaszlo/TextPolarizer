/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textpolarizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hecktor
 */
public class Wrapper {

    private FileReader inputReader;
    private BufferedReader reader;
    private FileWriter outputWriter;
    private BufferedWriter writer;
    private static final String parOpenTag = "<par>"; //paragraph
    private static final String parCloseTag = "</par>";

    public void setTextToWrap(File file) {
        try {
            inputReader = new FileReader(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Wrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File getWrappedText() {
        File output;

        output = null;

        return output;
    }

    public void wrap() {
        reader = new BufferedReader(inputReader);
        writer = new BufferedWriter(outputWriter);

        try {
            String line;
            
            writer.write("<body>");
            writer.newLine();
            writer.write(parOpenTag);
            writer.newLine();
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("")){
                    writer.write(parCloseTag);
                    writer.newLine();
                    writer.write(parOpenTag);
                } else
                    writer.write(line);
                
                writer.newLine();
                System.out.println(line);
            }
            
            reader.close();
            writer.write(parCloseTag);
            writer.newLine();
            writer.write("</body>");
            writer.newLine();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Wrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Wrapper() {
    }

    public Wrapper(File inFile) {
        this.setTextToWrap(inFile);
        this.createOutput(null);
    }

    public Wrapper(File inFile, String outFileName) {
        this.setTextToWrap(inFile);
        this.createOutput(outFileName);
    }

    private boolean createOutput(String fileName) {
        boolean state = false;

        if (fileName == null || "".equals(fileName)) {
            fileName = "data" + File.separator + "wrpd.plrz";
        }

        try {
            outputWriter = new FileWriter(fileName);
            state = true;
        } catch (IOException ex) {
            Logger.getLogger(Wrapper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return state;
    }
}
