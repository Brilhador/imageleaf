/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.measures;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author anderson
 */
public class Statistic {

    private double[] vector = null;
    
    public Statistic(double[] vector) {
        this.vector = vector;
    }
    
    public double average() {
        double soma = 0;
        for (int i = 0; i < vector.length; i++) {
            soma += vector[i];
        }
        return soma / vector.length;
    }

    public double variance() {
        double median = average();
        double variance = 0;
        for (int i = 0; i < vector.length; i++) {
            variance += Math.pow((vector[i] - median), 2);
        }
        return variance / (vector.length - 1);
    }
    
    public double averageDeviation() {
        double median = average();
        double variance = 0;
        for (int i = 0; i < vector.length; i++) {
            variance += Math.abs(vector[i] - median);
        }
        return variance / (vector.length - 1);
    }
    
    public double standardDeviation(){
        return Math.sqrt(variance());
    }
    
    public double coefficientVariation(){     
        return standardDeviation() / average();
    }
    
    public double median(){
        ArrayList<Double> dados = new ArrayList<>();
        for (int i = 0; i < vector.length; i++) {
            dados.add(vector[i]);
        }
        
        Collections.sort(dados);
        return dados.get(dados.size() / 2);
    }
    
}
