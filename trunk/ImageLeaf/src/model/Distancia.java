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
    public static int Euclidiana(int[] histgrama1, int[] histograma2) {

        if (histgrama1.length == histograma2.length) {
            int soma = 0;
            for (int i = 0; i < histgrama1.length; i++) {
                soma += Math.pow(Math.abs(histgrama1[i] - histograma2[i]), 2);
            }
            return (int) Math.sqrt(soma);
        } else {
            return Integer.MAX_VALUE;
        }
    }

    //calcula a distancia Manhattan entre dois vetores
    public static int Manhattan(int[] histgrama1, int[] histograma2) {

        if (histgrama1.length == histograma2.length) {
            int soma = 0;
            for (int i = 0; i < histgrama1.length; i++) {
                soma += Math.abs(histgrama1[i] - histograma2[i]);
            }
            return soma;
        }
        return Integer.MAX_VALUE;
    }

    public static int Manhattan(Dimension cod1, Dimension cod2) {
        //distancia de manhattan entre dois pontos fixos
        return Math.abs(cod1.width - cod2.width) + Math.abs(cod1.height - cod2.height);
    }
    
    public static int Euclidean(Dimension cod1, Dimension cod2) {
        //distancia de euclidiana entre dois pontos fixos
        double soma = (Math.pow(Math.abs(cod1.width - cod2.width), 2) + Math.pow(Math.abs(cod1.height - cod2.height), 2));
        return (int) Math.sqrt(soma);
    }
}
