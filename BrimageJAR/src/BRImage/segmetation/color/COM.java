/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BRImage.segmetation.color;

import BRImage.description.color.Histogram;
import BRImage.segmetation.Thresholding;
import BRImage.useful.Normalization;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author ANDERSON
 */
public class COM {
    
    public static BufferedImage apply(BufferedImage img) {

        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();

        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);
        
        //
        double[][] com = new double[largura][altura];
        
        double[][] exg = ExcessGreen.getMatriz(img);
        double[][] exrg = ExcessGreenMinusRed.getMatriz(img);
        double[][] cive = CIVE.getMatrizBin(img);
        double[][] veg = Vegetativen.getMatriz(img);
        
        //matriz de saida
        boolean[][] output = new boolean[largura][altura];

        //calculando os valores do CIVE
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {

                //Matriz
                com[x][y] = (exg[x][y] * 0.25) + (exrg[x][y] * 0.3) + (cive[x][y] * 0.33) + (veg[x][y] * 0.12);
            }
        }

        com = Normalization.apply(com);
        
        return index2mono(com);
  }
    
    private static BufferedImage index2mono(double[][] mat) {
        //largura e altura da imagem
        int largura = mat.length;
        int altura = mat[0].length;

        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);

        //matriz auxiliar
        int[][] auxMat = new int[largura][altura];

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {

                auxMat[x][y] = (int)(mat[x][y] * 255);

                Color rgb = new Color(auxMat[x][y], auxMat[x][y], auxMat[x][y]);
                outImage.setRGB(x, y, rgb.getRGB());

            }
        }

        return outImage;
    }
    
}
