/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.description.color;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Anderson
 */
public class Histogram {

    /**
     * ****************************************************************************************************************************************
     */
    /**
     *
     * Histogramas Por tom de cinza e os 3 canais RGB
     *
     *
     */
    public static int[] histogramaRed(BufferedImage img) {
        // Histogramas
        int red[] = new int[256];

        // Inicialização dos vetores
        for (int i = 0; i <= 255; i++) {
            red[i] = 0;
        }

        // pegando os valores RGB
        int largura = img.getWidth();
        int altura = img.getHeight();

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {

                red[Color.getColor("red", img.getRGB(x, y)).getRed()]++;
            }
        }
        return red;
    }

    public static int[] histogramaRed(BufferedImage img, boolean[][] imgBool) {
        // Histogramas
        int red[] = new int[256];

        // Inicialização dos vetores
        for (int i = 0; i <= 255; i++) {
            red[i] = 0;
        }

        // pegando os valores RGB
        int largura = img.getWidth();
        int altura = img.getHeight();

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                if (imgBool[x][y]) {
                    red[Color.getColor("red", img.getRGB(x, y)).getRed()]++;
                }
            }
        }
        return red;
    }

    public static int[] histogramaGreen(BufferedImage img) {
        // Histogramas
        int green[] = new int[256];

        // Inicialização dos vetores
        for (int i = 0; i <= 255; i++) {
            green[i] = 0;
        }

        // pegando os valores RGB
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                green[Color.getColor("green", img.getRGB(x, y)).getGreen()]++;
            }
        }
        return green;
    }

    public static int[] histogramaGreen(BufferedImage img, boolean[][] imgBool) {
        // Histogramas
        int green[] = new int[256];

        // Inicialização dos vetores
        for (int i = 0; i <= 255; i++) {
            green[i] = 0;
        }

        // pegando os valores RGB
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (imgBool[x][y]) {
                    green[Color.getColor("green", img.getRGB(x, y)).getGreen()]++;
                }
            }
        }
        return green;
    }

    public static int[] histogramaBlue(BufferedImage img) {
        // Histogramas
        int blue[] = new int[256];

        // Inicialização dos vetores
        for (int i = 0; i <= 255; i++) {
            blue[i] = 0;
        }

        // pegando os valores RGB
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                blue[Color.getColor("blue", img.getRGB(x, y)).getBlue()]++;
            }
        }
        return blue;
    }

    public static int[] histogramaBlue(BufferedImage img, boolean[][] imgBool) {
        // Histogramas
        int blue[] = new int[256];

        // Inicialização dos vetores
        for (int i = 0; i <= 255; i++) {
            blue[i] = 0;
        }

        // pegando os valores RGB
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (imgBool[x][y]) {
                    blue[Color.getColor("blue", img.getRGB(x, y)).getBlue()]++;
                }
            }
        }
        return blue;
    }

    public static int[] histogramaGray(BufferedImage img) {
        // Histogramas
        int h[] = new int[256];

        // Inicialização dos vetores
        for (int i = 0; i <= 255; i++) {
            h[i] = 0;
        }

        // Recebe a media das cores Red, Green e Blue que é o tom de cinza do
        // pixel
        int media;

        // pegando os valores RGB
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                media = (Color.getColor("red", img.getRGB(x, y)).getRed() + Color.getColor("green", img.getRGB(x, y)).getGreen() + Color.getColor("blue", img.getRGB(x, y)).getBlue()) / 3;
                h[media]++;
            }
        }
        return h;
    }

    public static int[] histogramaGray(BufferedImage img, boolean[][] imgBool) {
        // Histogramas
        int h[] = new int[256];

        // Inicialização dos vetores
        for (int i = 0; i <= 255; i++) {
            h[i] = 0;
        }

        // Recebe a media das cores Red, Green e Blue que é o tom de cinza do
        // pixel
        int media;

        // pegando os valores RGB
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (imgBool[x][y]) {
                    media = (Color.getColor("red", img.getRGB(x, y)).getRed() + Color.getColor("green", img.getRGB(x, y)).getGreen() + Color.getColor("blue", img.getRGB(x, y)).getBlue()) / 3;
                    h[media]++;
                }
            }
        }
        return h;
    }

    //normalização de histograma por scala
    //normalization by scaling between 0 and 1
    public static double[] normalization(double[] histograma) {

        //Armazena a frequencia max e min do histograma
        double max = histograma[0];
        double min = histograma[0];

        double[] hist = new double[histograma.length];

        //identifica a frequencia maxima e minima existente no histograma
        for (int i = 1; i < histograma.length; i++) {
            if (histograma[i] > max) {
                max = histograma[i];
            } else if (histograma[i] < min) {
                min = histograma[i];
            }
        }
        //calcula os valores do no histograma
        for (int i = 0; i < histograma.length; i++) {
            hist[i] = (histograma[i] - min) / (max - min);
        }

        //retorna o histograma 
        return hist;
    }

    public static BufferedImage equalizaRGB(BufferedImage img, int[] hRed, int[] hGreen, int[] hBlue) {
        BufferedImage imgOut = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        int x, y;

        float totalpixels = img.getWidth() * img.getHeight();

        float acumuloRed = 0;
        float acumuloGreen = 0;
        float acumuloBlue = 0;

        float[] hacumuladoRed = new float[256];
        float[] hacumuladoGreen = new float[256];
        float[] hacumuladoBlue = new float[256];

        for (x = 0; x < 256; x++) {
            hacumuladoRed[x] = hacumuladoGreen[x] = hacumuladoGreen[x] = 0;
        }

        for (x = 0; x < 256; x++) {
            //canal RED
            acumuloRed = acumuloRed + hRed[x] / totalpixels;
            hacumuladoRed[x] = acumuloRed;
            //canal GREEN
            acumuloGreen = acumuloGreen + hGreen[x] / totalpixels;
            hacumuladoGreen[x] = acumuloGreen;
            //canal BLUE
            acumuloBlue = acumuloBlue + hBlue[x] / totalpixels;
            hacumuladoBlue[x] = acumuloBlue;
        }

        for (y = 0; y < img.getHeight(); y++) {
            for (x = 0; x < img.getWidth(); x++) {
                int red = (int) ((hacumuladoRed[Color.getColor("red", img.getRGB(x, y)).getRed()]) * 255);
                int green = (int) ((hacumuladoGreen[Color.getColor("green", img.getRGB(x, y)).getGreen()]) * 255);
                int blue = (int) ((hacumuladoBlue[Color.getColor("blue", img.getRGB(x, y)).getBlue()]) * 255);

                imgOut.setRGB(x, y, new Color(red, green, blue).getRGB());
            }
        }

        return imgOut;
    }

    public static int getTotalPxHistograma(BufferedImage img) {
        int[] histograma = BRImage.description.color.Histogram.histogramaGray(img);

        int soma = 0;
        for (int i : histograma) {
            soma += i;
        }

        return soma;
    }

    public static int getTotalPxImg(BufferedImage img) {
        ArrayList<Integer> imgVet = new ArrayList<Integer>();

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                imgVet.add(img.getRGB(i, j));
            }
        }

        int soma = 0;

        for (int i : imgVet) {
            soma += i;
        }

        return soma;
    }

    public static double getMediana(BufferedImage img) {
        ArrayList<Integer> imgVet = new ArrayList<Integer>();

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                imgVet.add(img.getRGB(i, j));
            }
        }

        int tamanhoImg;
        double med;
        int meio;
        boolean par;

        Collections.sort(imgVet);

        tamanhoImg = imgVet.size();
        meio = tamanhoImg / 2;

        if ((tamanhoImg % 2) == 0) {
            par = true;
        } else {
            par = false;
        }

        if (par) {
            med = (imgVet.get(meio - 1) + imgVet.get(meio + 1)) / 2;
        } else {
            med = imgVet.get(meio);
        }

        return med;
    }

    public static double getMedia(BufferedImage img) {

        ArrayList<Integer> imgVet = new ArrayList<Integer>();

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                imgVet.add(img.getRGB(i, j));
            }
        }

        double tamanho = img.getHeight() * img.getWidth();
        double soma = Histogram.getTotalPxImg(img);
        double media = soma / tamanho;

        return media;

    }

    public static double getDesvioPadrao(BufferedImage img) {

        ArrayList<Integer> imgVet = new ArrayList<Integer>();

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                imgVet.add(img.getRGB(i, j));
            }
        }

        double media = Histogram.getMedia(img);

        ArrayList<Double> imgDesvio = new ArrayList<Double>();
        ArrayList<Double> imgQdDesvio = new ArrayList<Double>();
        double varianca = 0.0;

        for (int i : imgVet) {
            imgDesvio.add(i - media);
        }

        for (double i : imgDesvio) {
            imgQdDesvio.add(i * i);
        }

        Double soma = 0.0;
        for (double i : imgQdDesvio) {
            soma += i;
        }

        varianca = soma / (img.getHeight() * img.getWidth());

        return Math.sqrt(varianca);
    }
}
