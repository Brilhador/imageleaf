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

                //Preenchendo a Matriz de Verde Excessivo menos Vermelho Excessivo
                exgr[x][y] = (2 * green - blue - red) - (1.3 * red - green);
                
                if(exgr[x][y] < 0){
                    output[x][y] = false;
                }else{
                    output[x][y] = true;
                } 
            }
        }

        //convertendo index para image monocromatica
//        outImage = index2mono(exgr);
//        
//        //matriz de boolean
//        return Thresholding.limiarizacaoBool(outImage, Thresholding.otsuTreshold(Histogram.histogramaGray(outImage), altura * largura));
        
        return output;
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
                
                auxMat[x][y] = (int) mat[x][y] * 255;
                
                //corrigindo a limitação de valores
                if(auxMat[x][y] > 255){
                    auxMat[x][y] = 255;
                }else if(auxMat[x][y] < 0){
                    auxMat[x][y] = 0;
                }
                
                outImage.setRGB(x, y, auxMat[x][y]);
            }
        }
        
        return outImage;
    }
}
