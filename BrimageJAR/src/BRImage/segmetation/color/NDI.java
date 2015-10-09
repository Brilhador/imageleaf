/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.segmetation.color;

import BRImage.useful.Normalization;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author ANDERSON
 */
public class NDI {
    
    public static BufferedImage apply(BufferedImage img) {

        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();

        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);

        //matriz CIVE
        double[][] ndi = new double[largura][altura];

        //matriz de saida
        boolean[][] output = new boolean[largura][altura];

        //calculando os valores do CIVE
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                double blue = Color.getColor("blue", img.getRGB(x, y)).getBlue();

                //NDI
                ndi[x][y] = (green - red) / (green + red);
            }
        }

        ndi = Normalization.apply(ndi);

        return index2mono(ndi);
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

    public static double[][] getMatrizBin(BufferedImage img) {

        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();

        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);

        //matriz NDI
        double[][] ndi = new double[largura][altura];

        //matriz de saida
        boolean[][] output = new boolean[largura][altura];

        //calculando os valores do CIVE
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                double blue = Color.getColor("blue", img.getRGB(x, y)).getBlue();

                //NDI
                ndi[x][y] = (green - red) / (green + red);
            }
        }

        return Normalization.apply(ndi);
    }
    
    public static int[][] getMatrizInt(BufferedImage img) {

        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();

        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);

        //matriz NDI
        double[][] ndi = new double[largura][altura];

        //matriz de saida
        boolean[][] output = new boolean[largura][altura];

        //calculando os valores do CIVE
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                double blue = Color.getColor("blue", img.getRGB(x, y)).getBlue();

                //NDI
                ndi[x][y] = (green - red) / (green + red);
            }
        }

        return getMonoMatriz(Normalization.apply(ndi));
    }
}
