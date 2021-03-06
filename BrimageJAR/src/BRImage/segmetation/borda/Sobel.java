/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.segmetation.borda;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author anderson.brilhador
 */
public class Sobel {
    
    /*
     * Detecção de Bordas
     */
    public static int[][] apply(BufferedImage img) {
        //pesos utilizados para cada pixels
        float[] template = {-1, 0, 1,
            -2, 0, 2,
            -1, 0, 1};

        float[][] GY = new float[img.getWidth()][img.getHeight()];
        float[][] GX = new float[img.getWidth()][img.getHeight()];

        //matriz de saida
        int[][] output = new int[img.getWidth()][img.getHeight()];

        int sum = 0;

        for (int x = 1; x < img.getWidth() - 2; x++) {
            for (int y = 1; y < img.getHeight() - 2; y++) {
                sum = 0;

                for (int x1 = 0; x1 < 3; x1++) {
                    for (int y1 = 0; y1 < 3; y1++) {
                        int x2 = (x - 1 + x1);
                        int y2 = (y - 1 + y1);

                        Color cor = new Color(img.getRGB(x2, y2));
                        float value = ((cor.getRed() + cor.getGreen() + cor.getBlue()) / 3) * (template[y1 * 3 + x1]);
                        sum += value;
                    }
                }
                GY[x][y] = sum;

                sum = 0;
                for (int x1 = 0; x1 < 3; x1++) {
                    for (int y1 = 0; y1 < 3; y1++) {
                        int x2 = (x - 1 + x1);
                        int y2 = (y - 1 + y1);

                        Color cor = new Color(img.getRGB(x2, y2));
                        float value = ((cor.getRed() + cor.getGreen() + cor.getBlue()) / 3) * (template[x1 * 3 + y1]);
                        sum += value;
                    }
                }
                GX[x][y] = sum;

            }
        }
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                output[x][y] = (int) Math.sqrt(Math.pow(GX[x][y], 2) + Math.pow(GY[x][y], 2));
            }
        }

        return output;
    }
    
}
