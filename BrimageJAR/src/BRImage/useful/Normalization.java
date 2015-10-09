/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.useful;

/**
 *
 * @author ANDERSON
 */
public class Normalization {

    //normalização de histograma por scala
    //normalization by scaling between 0 and 1
    //normalização linear
    public static double[][] apply(double[][] matriz) {

        //Armazena a frequencia max e min do histograma
        double max = matriz[0][0];
        double min = matriz[0][0];

        double[][] matAux = new double[matriz.length][matriz[0].length];

        //identifica a frequencia maxima e minima existente no histograma
        for (int x = 0; x < matriz.length; x++) {
            for (int y = 0; y < matriz[0].length; y++) {
                if (matriz[x][y] > max && matriz[x][y] != Double.POSITIVE_INFINITY) {
                    max = matriz[x][y];
                } else if (matriz[x][y] < min && matriz[x][y] != Double.NEGATIVE_INFINITY) {
                    min = matriz[x][y];
                }
            }
        }
        //calcula os valores do no histograma        
        for (int x = 0; x < matriz.length; x++) {
            for (int y = 0; y < matriz[0].length; y++) {
                matAux[x][y] = (matriz[x][y] - min) / (max - min);
                if(matAux[x][y] > 1){
                    matAux[x][y] = 1;
                }else if(matAux[x][y] < 0){
                    matAux[x][y] = 0;
                }
            }
        }

        //retorna a matriz 
        return matAux;
    }
}
