/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Dimension;

/**
 *
 * @author Anderson
 */
public class Distancia {

    //calcula a distancia euclidiana entre dois vetores
    public static Double Euclidiana(double[] histgrama1, double[] histograma2) {

        if (histgrama1.length == histograma2.length) {
            Double soma = 0.0;
            for (int i = 0; i < histgrama1.length; i++) {
                soma += Math.pow(Math.abs(histgrama1[i] - histograma2[i]), 2);
            }
            return Math.sqrt(soma);
        } else {
            return null;
        }
    }

    //calcula a distancia Manhattan entre dois vetores
    public static Double Manhattan(double[] histgrama1, double[] histograma2) {

        if (histgrama1.length == histograma2.length) {
            Double soma = 0.0;
            for (int i = 0; i < histgrama1.length; i++) {
                soma += Math.abs(histgrama1[i] - histograma2[i]);
            }
            return soma;

        } else {
            return null;
        }
    }
    
     //calcula a distancia euclidiana entre dois vetores
    public static Double Euclidiana(int[] histgrama1, double[] histograma2) {

        if (histgrama1.length == histograma2.length) {
            Double soma = 0.0;
            for (int i = 0; i < histgrama1.length; i++) {
                soma += Math.pow(Math.abs(histgrama1[i] - histograma2[i]), 2);
            }
            return Math.sqrt(soma);
        } else {
            return null;
        }
    }

    //calcula a distancia Manhattan entre dois vetores
    public static Double Manhattan(int[] histgrama1, double[] histograma2) {

        if (histgrama1.length == histograma2.length) {
            Double soma = 0.0;
            for (int i = 0; i < histgrama1.length; i++) {
                soma += Math.abs(histgrama1[i] - histograma2[i]);
            }
            return soma;
        }
        return null;
    }

    public static int Manhattan(Dimension cod1, Dimension cod2) {
        //distancia de manhattan entre dois pontos fixos
        return Math.abs(cod1.width - cod2.width) + Math.abs(cod1.height - cod2.height);
    }
}
