/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Anderson
 */
public class Distancia {
    
    //calcula a distancia euclidiana entre dois vetores
    public static Double Euclidiana(double[] histgrama1, double[] histograma2) {
        int RANGE = 200;
        Double soma = 0.0;
        for (int i = 0; i < RANGE; i++) {
            soma += Math.pow(Math.abs(histgrama1[i] - histograma2[i]), 2);
        }
        return Math.sqrt(soma);
    }
}
