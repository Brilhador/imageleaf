/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Anderson
 */
public class Limiar {

    /**
     * ****************************************************************************************************************************************
     */
    /*
     * lIMIARIAZACAO BASICA com LIMIAR passado por parametro
     */
    public static BufferedImage limiarizacao(BufferedImage img, int limiar) {
        //imagem de saida
        BufferedImage imgOut = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int cor;
        // atribuido valores a imagem dos pixels
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                cor = (Color.getColor("red", img.getRGB(x, y)).getRed() + Color.getColor("green", img.getRGB(x, y)).getGreen() + Color.getColor("blue", img.getRGB(x, y)).getBlue()) / 3;
                if (cor >= limiar) {
                    imgOut.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    imgOut.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }
        // retorna a imagem binarizada
        return imgOut;
    }
    // limiarização basica com valor já pré-definido (valores atrivuidos a uma
    // matriz) com liminar por parametro,
    // retornando uma matriz com os valores booleanos true para borda; false
    // para fundo

    public static boolean[][] limiarizacao(int[][] img, int liminar) {
        // cria uma matriz de booleanos com o mesmo tamanho da matriz recebida
        // como parametro
        boolean[][] borda = new boolean[img.length][img[0].length];

        // atribuido caso o pixel seja uma borda recebe true
        // linhas
        for (int x = 0; x < img.length; x++) {
            // colunas
            for (int y = 0; y < img[0].length; y++) {
                if (img[x][y] >= liminar) {
                    borda[x][y] = true;// branco
                } else {
                    borda[x][y] = false;// preto
                }
            }
        }
        // retorna a matriz de boolean
        return borda;
    }

    // limiarização basica com valor já pré-definido (valores atribuidos a uma
    // matriz) com liminar por parametro, que retona uma imagem
    public static BufferedImage limiarizacao(BufferedImage img, int[][] g, int limiar) {
        //Imagem de saida
        BufferedImage imgOut = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        // atribuido valores a imagem dos pixels
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (g[x][y] >= limiar) {
                    imgOut.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    imgOut.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }
        // retorna a imagem binarizada
        return imgOut;
    }

    /**
     * ****************************************************************************************************************************************
     */
    /**
     *
     * Liminar Automático Por Maxima Entropia da imagem
     *
     */
// calcular o liminar pelo maximo de entropia da imagem
    public static int maxentropia(int histograma[], double totalpixels) {
        // variaveis
        double h0, h1, p0, p1, max = 0;
        int t, i, limiar = 0;

        for (t = 0; t < 256; t++) {
            p0 = 0;
            p1 = 0;
            for (i = 0; i <= t; i++) {
                p0 += (histograma[i] / totalpixels);
            }
            for (i = t + 1; i < 256; i++) {
                p1 += (histograma[i] / totalpixels);
            }

            h0 = 0;
            h1 = 0;
            for (i = 0; i <= t; i++) {
                h0 += entropia((histograma[i] / totalpixels) / p0);
            }
            for (i = t + 1; i < 256; i++) {
                h1 += entropia((histograma[i] / totalpixels) / p1);
            }

            if (h0 + h1 > max) {
                max = h0 + h1;
                limiar = t;
            }
        }
        return limiar;
    }

    // calcula a entropia
    private static double entropia(double x) {
        double e;

        if (x > 0) {
            e = -(x * Math.log(x));
        } else {
            e = 0;
        }
        return e;
    }
}
