/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BRImage.segmetation.color;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Anderson
 */
public class Vegetativen {
    
    public static BufferedImage apply(BufferedImage img){
        
        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();
        
        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);
        
        //matriz vegetativen
        double[][] veg = new double[largura][altura];
        
        //matriz de saida
        boolean[][] output = new boolean[largura][altura];
        
        //calculando os valores do vegetativen
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                double blue = Color.getColor("blue", img.getRGB(x, y)).getBlue();

                //vegetativen
                veg[x][y] = green / (Math.pow(red, 0.667) * Math.pow(blue, 1 - 0.667));
            }
        }
        
        return index2mono(veg);
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
                
                auxMat[x][y] = (int) mat[x][y];
                
                outImage.setRGB(x, y, auxMat[x][y]);
            }
        }
        
        return outImage;
    }
    
}
