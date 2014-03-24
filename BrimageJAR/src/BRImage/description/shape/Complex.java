/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.description.shape;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author anderson
 */
public class Complex {
    
    private double[] vector;
    
    public Complex(ArrayList<Dimension> lista){
        vector = new double[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            Dimension d = lista.get(i);
            vector[i] = d.width + d.height;
        }
    }

    public double[] getVector() {
        return vector;
    }

    public void setVector(double[] vector) {
        this.vector = vector;
    }
    
}
