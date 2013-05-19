/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anderson
 */
public class ARFF {
    
    private File arquivo = null;
    private BufferedWriter buffer = null;
    
    public void criar (String path, String relation, String[] attribute, String[] data){
        try {
            arquivo = new File(path + "/" + relation + ".arff");
            buffer = new BufferedWriter(new FileWriter(arquivo));
            
        } catch (IOException ex) {
            Logger.getLogger(ARFF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
