/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BRImage.process.morphology;

import java.util.Arrays;

/**
 *
 * @author ANDERSON
 */
public class Dilation {
    
    public static boolean[][] simple(boolean[][] img, boolean[][] kernel, int iteration){
        
        boolean[][] imgOut = img;

        int largura = img.length;
        int altura = img[0].length;

        for (int indice = 0; indice < iteration; indice++) {          
           boolean[][] imgIteration = clone(imgOut);
           
            //perrcorrer a imagem;
            for (int i = 0; i < largura; i++) {
                for (int j = 0; j < altura; j++) {
                    if (imgIteration[i][j] == true) {
                        dilApply(imgOut, kernel, i, j);
                    }
                }
            }
        }
        return imgOut;
    }
    
    private static void dilApply(boolean[][] imgOut, boolean[][] kernel, int i, int j) {

        int largura = i - (int) (kernel.length / 2);
        int altura = j - (int) (kernel[0].length / 2);
        
        for (int k = 0; k < kernel.length; k++) {
            for (int l = 0; l < kernel[0].length; l++) {
                if (kernel[k][l] == true) {
                    if (inBounds(imgOut, largura + k, altura + l)) {
                        imgOut[largura + k][altura + l] = true;
                    }
                }

            }
        }
    }

    private static boolean inBounds(boolean[][] img, int i, int j) {
        return ((i > 0 && i < img.length) && (j > 0 && j < img[0].length));
    }
    
    private static boolean[][] clone(boolean[][] img){
        boolean[][] imgOut = new boolean[img.length][img[0].length];
        
        for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[0].length; j++) {
                imgOut[i][j] =  img[i][j];
            }
        }
        
        return imgOut;
    }
    
}
