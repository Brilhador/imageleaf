/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.segmetation.color;

import BRImage.description.color.Histogram;
import java.awt.Color;
import java.awt.image.BufferedImage;
import BRImage.segmetation.Thresholding;
import BRImage.useful.Normalization;

/**
 *
 * @author Anderson
 */
public class ExcessGreen {

    public static BufferedImage apply(BufferedImage img) {
        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();

        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);

        //matriz de ExcessGreen
        double[][] exg = new double[largura][altura];

        //matriz de saida
        boolean[][] output = new boolean[largura][altura];

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                double blue = Color.getColor("blue", img.getRGB(x, y)).getBlue();

                //1º normalizando valores em uma scala de 0 e 1
                red = red / 255;
                green = green / 255;
                blue = blue / 255;

                //2º normalização 
                double full = red + green + blue;

                red /= full;
                green /= full;
                blue /= full;

                //Preenchendo a Matriz de Verde Excessivo
                exg[x][y] = (2 * green) - red - blue;
            }
        }

        exg = Normalization.apply(exg);

        return index2mono(exg);
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

                auxMat[x][y] = (int) (mat[x][y] * 255);

                Color rgb = new Color(auxMat[x][y], auxMat[x][y], auxMat[x][y]);
                outImage.setRGB(x, y, rgb.getRGB());

            }
        }

        return outImage;
    }

    public static double[][] getMatriz(BufferedImage img) {
        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();

        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);

        //matriz de ExcessGreen
        double[][] exg = new double[largura][altura];

        //matriz de saida
        boolean[][] output = new boolean[largura][altura];

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                double blue = Color.getColor("blue", img.getRGB(x, y)).getBlue();

                //1º normalizando valores em uma scala de 0 e 1
                red = red / 255;
                green = green / 255;
                blue = blue / 255;

                //2º normalização 
                double full = red + green + blue;

                red /= full;
                green /= full;
                blue /= full;

                //Preenchendo a Matriz de Verde Excessivo
                exg[x][y] = (2 * green) - red - blue;
            }
        }

        return Normalization.apply(exg);
    }
    
    public static int[][] getMatrizInt(BufferedImage img) {
        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();

        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);

        //matriz de ExcessGreen
        double[][] exg = new double[largura][altura];

        //matriz de saida
        boolean[][] output = new boolean[largura][altura];

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                double blue = Color.getColor("blue", img.getRGB(x, y)).getBlue();

                //1º normalizando valores em uma scala de 0 e 1
                red = red / 255;
                green = green / 255;
                blue = blue / 255;

                //2º normalização 
                double full = red + green + blue;

                red /= full;
                green /= full;
                blue /= full;

                //Preenchendo a Matriz de Verde Excessivo
                exg[x][y] = (2 * green) - red - blue;
            }
        }

        return getMonoMatriz(Normalization.apply(exg));
    }
    
    private static int[][] getMonoMatriz(double[][] mat) {
        //largura e altura da imagem
        int largura = mat.length;
        int altura = mat[0].length;

        //matriz auxiliar
        int[][] auxMat = new int[largura][altura];

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {

                auxMat[x][y] = (int) (mat[x][y] * 255);

            }
        }
        
        //retorna a matriz com os valores de 255
        return auxMat;
    }
}
