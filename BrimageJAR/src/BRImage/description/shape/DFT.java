/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.description.shape;

import BRImage.description.color.Histogram;
import BRImage.process.Filter;
import BRImage.segmetation.BorderDetector;
import BRImage.segmetation.Thresholding;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anderson
 */
public class DFT {

    private BufferedImage imageOriginal = null;
    private BufferedImage imageFourier = null;
    private ArrayList<Dimension> border = null;
    //variaveis da transformada de fourier
    private double[] x1 = null;
    private double[] y1 = null;

    public DFT(BufferedImage original, boolean invRotation, boolean invScala, boolean invTranslation) {
        try {
            this.imageOriginal = original;
            border = getBorder(imageOriginal);
            this.x1 = createComplexBorder(border);
            this.y1 = new double[this.x1.length];
            create(1, x1, y1, this.x1.length);
            if (invRotation) {
                invRotation();
            }
            if (invScala) {
                invScala();
            }
            if (invTranslation) {
                invTranslation();
            }
            drawBorder(border);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DFT(int dir, double[] x1, double[] y1, int amostragem) {
        this.x1 = x1;
        this.y1 = y1;
        create(dir, x1, y1, amostragem);
    }

    public double[] getCoefficients() {
        return x1;
    }
    
    public double[] getCoefficients(int indice) {
        double[] vector = new double[indice];
        for (int i = 0; i < indice; i++) {
            vector[i] = x1[i];
        }
        return vector;
    }

    private double[] createComplexBorder(ArrayList<Dimension> border) {
        double[] vector = new double[border.size()];
        for (int i = 0; i < border.size(); i++) {
            Dimension d = border.get(i);
            vector[i] = d.width + d.height;
        }
        return vector;
    }

    private ArrayList<Dimension> getBorder(BufferedImage image) {
        int total = image.getWidth() * image.getHeight();
        image = Filter.median(image, 5);
        int limiar = Thresholding.otsuTreshold(Histogram.histogramaGray(image), total);
        return new BorderDetector(Thresholding.limiarizacaoBool(image, limiar)).getBorder();
    }

    private void create(int dir, double[] x1, double[] y1, int amostragem) {
        /*
         * x1, y1 são os valores reais e imaginários - arrays of 2^m pontos
         * dir = 1 resulta na transformada de fourier
         * dir = -1 resulta na tranformada inversa de fourier
         */
        double[] x2 = new double[amostragem];
        double[] y2 = new double[amostragem];
        double arg = 0;
        double cosarg = 0;
        double sinarg = 0;

        for (int i = 0; i < amostragem; i++) {
            x2[i] = 0;
            y2[i] = 0;
            arg = -dir * 2.0 * Math.PI * i / (amostragem);
            for (int j = 0; j < amostragem; j++) {
                cosarg = Math.cos(j * arg);
                sinarg = Math.sin(j * arg);
                x2[i] += ((x1[j] * cosarg) - (y1[j] * sinarg));
                y2[i] += ((x1[j] * sinarg) + (y1[j] * cosarg));
            }
        }
        //TRANSFORMADA DISCRETA DE FOURIER
        if (dir == 1) {
            for (int i = 0; i < amostragem; i++) {
                x1[i] = x2[i] / amostragem;
                y1[i] = y2[i] / amostragem;
            }
        } else {//TRANSFORMADA INVERSA DE FOURIER
            for (int i = 0; i < amostragem; i++) {
                x1[i] = x2[i];
                y1[i] = y2[i];
            }
        }
    }

    private void invRotation() {
        for (int i = 0; i < x1.length; i++) {
            x1[i] = Math.abs(x1[i]);
            y1[i] = Math.abs(y1[i]);
        }
    }

    private void invScala() {
        double valor = x1[1];
        for (int i = 0; i < x1.length; i++) {
            x1[i] = x1[i] / valor;
            y1[i] = y1[i] / valor;
        }
    }

    private void invTranslation() {
        double[] vector = new double[border.size()];
        for (int i = 1; i < border.size() - 1; i++) {
            vector[i] = x1[i];
        }
        x1 = vector;
    }

    private double[] getInvTranslation(int indice) {
        double[] vector = new double[indice - 1];
        for (int i = 1; i < indice - 1; i++) {
            vector[i] = x1[i];
        }
        return vector;
    }

    public BufferedImage getImageOriginal() {
        return imageOriginal;
    }

    public void setImageOriginal(BufferedImage imageOriginal) {
        this.imageOriginal = imageOriginal;
    }

    public BufferedImage getImageFourier() {
        return imageFourier;
    }

    public void setImageFourier(BufferedImage imageFourier) {
        this.imageFourier = imageFourier;
    }

    public ArrayList<Dimension> getBorder() {
        return border;
    }

    public void setBorder(ArrayList<Dimension> border) {
        this.border = border;
    }

    public void drawBorder(ArrayList<Dimension> lista) {
        imageFourier = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), imageOriginal.getType());
        Graphics2D g2d = imageFourier.createGraphics();
        g2d.drawImage(imageOriginal, null, 0, 0);
        //desenha a linha do primeiro elemento da lista

        for (Dimension dimension : lista) {
            drawPoint(imageFourier, dimension, Color.GREEN);
        }
        g2d.dispose();
    }

    private void drawPoint(BufferedImage drawImage, Dimension point, Color cor) {
        drawImage.setRGB(point.width, point.height, cor.getRGB());
    }
}
