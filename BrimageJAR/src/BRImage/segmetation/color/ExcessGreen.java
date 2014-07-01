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

/**
 *
 * @author Anderson
 */
public class ExcessGreen {

    public static boolean[][] apply(BufferedImage img) {
        //largura e altura da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();
        
        //Imagem de saida
        BufferedImage outImage = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);
        
        //matriz de ExcessGreen
        double[][] exg = new double[largura][altura];
        
        //histograma da matriz de ExcessGreen
        int histograma[] = new int[256];
        //inicialização do histograma
        for (int i = 0; i < 256; i++) {
            histograma[i] = 0;
        }

        //matriz de saida
        boolean[][] output = new boolean[largura][altura];

        //valores máximos dos canais RGB
        int redMax = 0;
        int greenMax = 0;
        int blueMax = 0;

        //encontrando o valor maximo dentro da imagem
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                double blue = Color.getColor("blue", img.getRGB(x, y)).getBlue();

                if (red > redMax) {
                    redMax = (int) red;
                }
                if (green > greenMax) {
                    greenMax = (int) green;
                }
                if (blue > blueMax) {
                    blueMax = (int) blue;
                }
            }
        }

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                double red = Color.getColor("red", img.getRGB(x, y)).getRed();
                double green = Color.getColor("green", img.getRGB(x, y)).getGreen();
                double blue = Color.getColor("blue", img.getRGB(x, y)).getBlue();

                //1º normalizando valores em uma scala de 0 e 1
                red = red / redMax;
                green = green / greenMax;
                blue = blue / blueMax;

                //2º normalização 
                double full = red + green + blue;
                
                red /= full;
                green /= full;
                blue /= full;
                
                //Preenchendo a Matriz de Verde Excessivo
                exg[x][y] = (2 * green - blue - red) * 255;
                
                //corrigindo a limitação de valores
                if(exg[x][y] > 255){
                    exg[x][y] = 255;
                }else if(exg[x][y] < 0){
                    exg[x][y] = 0;
                }
                
                outImage.setRGB(x, y, (int) exg[x][y]);
            }
        }
         
        //matriz de boolean
        return Thresholding.limiarizacaoBool(outImage, Thresholding.otsuTreshold(Histogram.histogramaGray(outImage), altura * largura));
    }

    
    
}
