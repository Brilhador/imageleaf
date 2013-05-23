/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anderson
 */
public class ARFF {
    
    private File arquivo = null;
    private BufferedWriter buffer = null;
    private String textBuffer = "";
    
    public void create (String path, String relation, ArrayList<String> attribute, ArrayList<String> classe, ArrayList<String> data){
        try {
            arquivo = new File(path + "/" + relation + ".arff");
            buffer = new BufferedWriter(new FileWriter(arquivo));
            //adiciona a relacao
            //imageCleaf2012
            textBuffer += "@relation " + relation + "\n\n";
            //adiciona os atributos e seus tipos
            //distance0 NUMERIC
            for (String a : attribute) {
                textBuffer += "@attribute " + a + "\n";
            }
            //adicionando as classes existentes
            //acer campestre
            textBuffer += "@attribute class {";
            for (String c : classe) {
                textBuffer += c + ",";
            }
            textBuffer += "}\n\n";
            //adiciona os dados
            //23,43,56,acer campestre
            textBuffer += "@data\n";
            for (String d : data) {
                textBuffer += d + "\n";
            }
            buffer.write(textBuffer);
            buffer.flush();
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(ARFF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
