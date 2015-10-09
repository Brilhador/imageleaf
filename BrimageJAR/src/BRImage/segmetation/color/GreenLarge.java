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
public class GreenLarge {
    
    public static boolean[][] apply(BufferedImage img){
        
        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();
        
        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);
        
        //matriz de saida
        boolean[][] output = new boolean[largura][altura];
        
        //calculando os valores do CIVE
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                
                if (green > red ) {
                    output[x][y] = true;
                } else {
                    output[x][y] = false;
                }
                
            }
        }

        return output;
    }
    
    public static int[][] getMatrizBin(BufferedImage img){
        
        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();
        
        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);
        
        //matriz de saida
        int[][] output = new int[largura][altura];
        
        //calculando os valores do CIVE
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                
                if (green > red ) {
                    output[x][y] = 1;
                } else {
                    output[x][y] = 0;
                }
                
            }
        }

        return output;
    }
    
    public static boolean[][] getMatrizBool(BufferedImage img){
        
        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();
        
        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);
        
        //matriz de saida
        boolean[][] output = new boolean[largura][altura];
        
        //calculando os valores do CIVE
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                
                if (green > red ) {
                    output[x][y] = true;
                } else {
                    output[x][y] = false;
                }
                
            }
        }

        return output;
    }
    
}
