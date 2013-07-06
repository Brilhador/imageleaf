/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author anderson
 */
public class Medida {

    private double[] vector = null;
    
    public Medida(double[] vector) {
        this.vector = vector;
    }
    
    public double calcMedian() {
        double soma = 0;
        for (int i = 0; i < vector.length; i++) {
            soma += vector[i];
        }
        return soma / vector.length;
    }

    public double calcVariance() {
        double median = calcMedian();
        double variance = 0;
        for (int i = 0; i < vector.length; i++) {
            variance += Math.pow((vector[i] - median), 2);
        }
        return variance / (vector.length - 1);
    }
    
    public double calcMedianDeviation() {
        double median = calcMedian();
        double variance = 0;
        for (int i = 0; i < vector.length; i++) {
            variance += Math.abs(vector[i] - median);
        }
        return variance / (vector.length - 1);
    }
    
    public double calcStandardDeviation(){
        return Math.sqrt(calcVariance());
    }
    
    public double calcCoefficientVariation(){     
        return calcStandardDeviation() / calcMedian();
    }
    
}
