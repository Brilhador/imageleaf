/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.measures;

import BRImage.useful.Coordinate;
import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author Anderson
 */
public class Distance {

    //calcula a distancia euclidiana entre dois vetores
    public static Double Euclidian(double[] histgrama1, double[] histograma2) {

        if (histgrama1.length == histograma2.length) {
            Double soma = 0.0;
            for (int i = 1; i < histgrama1.length; i++) {
                soma += Math.pow(Math.abs(histgrama1[i] - histograma2[i]), 2);
            }
            return Math.sqrt(soma);
        } else {
            return null;
        }
    }

    //calcula a distancia euclidiana entre dois vetores
    public static int Euclidian(int[] histgrama1, int[] histograma2) {

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

    public static Double Euclidian(double[] histgrama1, double[] histograma2, int indice) {

        if (histgrama1.length >= indice) {
            Double soma = 0.0;
            for (int i = 0; i < indice; i++) {
                soma += Math.pow(Math.abs(histgrama1[i] - histograma2[i]), 2);
            }
            return Math.sqrt(soma);
        } else {
            return null;
        }
    }

    //distancia de euclidiana entre dois pontos fixos
    public static int Euclidean(Dimension cod1, Dimension cod2) {
        double soma = (Math.pow(Math.abs(cod1.width - cod2.width), 2) + Math.pow(Math.abs(cod1.height - cod2.height), 2));
        return (int) Math.sqrt(soma);
    }
    
     //distancia de euclidiana entre dois pontos fixos
    public static int Euclidean(Coordinate cod1, Coordinate cod2) {
        double soma = (Math.pow(Math.abs(cod1.getX() - cod2.getX()), 2) + Math.pow(Math.abs(cod1.getY() - cod2.getY()), 2));
        return (int) Math.sqrt(soma);
    }

     //distancia de euclidiana entre dois pontos fixos
    public static int Euclidean(Point cod1, Point cod2) {
        double soma = (Math.pow(Math.abs(cod1.getX() - cod2.getX()), 2) + Math.pow(Math.abs(cod1.getY() - cod2.getY()), 2));
        return (int) Math.sqrt(soma);
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

    //distancia de manhattan entre dois pontos fixos
    public static int Manhattan(Dimension cod1, Dimension cod2) {
        return Math.abs(cod1.width - cod2.width) + Math.abs(cod1.height - cod2.height);
    }
    
    //distancia de manhattan entre dois pontos fixos
    public static int Manhattan(Coordinate cod1, Coordinate cod2) {
        return Math.abs(cod1.getX() - cod2.getX()) + Math.abs(cod1.getY() - cod2.getY());
    }

}
