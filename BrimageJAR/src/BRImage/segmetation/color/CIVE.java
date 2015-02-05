/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.segmetation.color;

import BRImage.description.color.Histogram;
import BRImage.segmetation.Thresholding;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Anderson
 */
public class CIVE {

    public static boolean[][] apply(BufferedImage img) {

        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();

        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);

        //matriz CIVE
        double[][] cive = new double[largura][altura];

        //matriz de saida
        boolean[][] output = new boolean[largura][altura];

        //calculando os valores do CIVE
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                double blue = Color.getColor("blue", img.getRGB(x, y)).getBlue();

                //CIVE
                cive[x][y] = 0.441 * red - 0.811 * green + 0.385 * blue + 18.78745;
            }
        }

        outImage = index2mono(cive);

        return Thresholding.limiarizacaoBoolInv(outImage, Thresholding.otsuTreshold(Histogram.histogramaGray(outImage), altura * largura));
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

                //                corrigindo a limitação de valores
                if (auxMat[x][y] > 255) {
                    auxMat[x][y] = 255;
                } else if (auxMat[x][y] < 0) {
                    auxMat[x][y] = 0;
                }

                Color rgb = new Color(auxMat[x][y], auxMat[x][y], auxMat[x][y]);
                outImage.setRGB(x, y, rgb.getRGB());

            }
        }

        return outImage;
    }

}
