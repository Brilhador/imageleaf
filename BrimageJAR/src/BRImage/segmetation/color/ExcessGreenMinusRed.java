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
public class ExcessGreenMinusRed {

    public static boolean[][] apply(BufferedImage img) {
        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();

        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);

        //matriz de ExcessGreen
        double[][] exgr = new double[largura][altura];

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

                //Preenchendo a Matriz de Verde Excessivo menos Vermelho Excessivo
                exgr[x][y] = (2 * green - blue - red) - (1.3 * red - green);
                
                if(exgr[x][y] < 0){
                    output[x][y] = false;
                }else{
                    output[x][y] = true;
                } 
            }
        }
   
        return output;
    }
}
