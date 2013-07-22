/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import javax.media.jai.PlanarImage;

/**
 *
 * @author Thiago
 */
public class Coocorrencia {

    static int pos, i, j, distancia = 1;
    //Numero escala de cinza das texturas
    private int NUM_GRAY_VALUES;
    //Numero de níveis de cinza.
    private int GRAY_RANGES = 256;
    //Histograma de cinza da imagem
    private double[] grayHistogram;
    //Valor do nível de cinza de cada pixel da imagem
    private int[] grayValue;
    //Matriz de coocorrencia
    private double[][] coocorrencia;
    //Distancia para calculo
    private int HARALICK_DIST;
    private int HARALICK_ANG;
    private PlanarImage image;
    private Raster raster;
    int imagemRBG[][];
    private int pixelcount = 0;

    public Coocorrencia(BufferedImage img, int numGrayValues, int haralickDist, int haralickAng) {
        this.image = PlanarImage.wrapRenderedImage(img);
        this.NUM_GRAY_VALUES = numGrayValues;
        this.grayHistogram = new double[GRAY_RANGES];
        this.raster = image.getData();
        this.grayValue = new int[raster.getHeight() * raster.getWidth()];
        this.coocorrencia = new double[NUM_GRAY_VALUES][NUM_GRAY_VALUES];
        this.HARALICK_DIST = haralickDist;
        HARALICK_ANG = haralickAng;
        imagemRBG();
        calcular();
    }

    private void calcular() {
        calculaValores();

        for (int y = 0; y < raster.getHeight(); y++) {
            for (int x = 0; x < raster.getWidth(); x++) {
                pos = raster.getWidth() * y + x;

                switch (HARALICK_ANG) {
                    case 0:
                        // 0 horizontal 
                        i = x - HARALICK_DIST;
                        j = x;
                        if (!(i < 0)) {
                            iCoocorrencia(grayValue[pos], grayValue[pos - distancia]);
                        }
                        break;
                    case 90:
                        // 90 vertigal 
                        i = x;
                        j = y - distancia;
                        if (!(j < 0)) {
                            iCoocorrencia(grayValue[pos], grayValue[pos - distancia * raster.getWidth()]);
                        }
                        break;
                    case 45:
                        // 45 diagonal
                        i = x + distancia;
                        j = y - distancia;
                        if (i < raster.getWidth() && !(j < 0)) {
                            iCoocorrencia(grayValue[pos], grayValue[pos + distancia - distancia * raster.getWidth()]);
                        }
                        break;
                    case 135:
                        // 135 vertical
                        i = x - distancia;
                        j = y - distancia;
                        if (!(i < 0) && !(j < 0)) {
                            iCoocorrencia(grayValue[pos], grayValue[pos - distancia - distancia * raster.getWidth()]);
                        }
                        break;
                }
            }
        }
    }

    private void imagemRBG() {
        int rr;
        int bb;
        int gg;
        imagemRBG = new int[raster.getHeight()][raster.getWidth()];

        for (int y = 0; y < raster.getHeight(); y++) {
            for (int x = 0; x < raster.getWidth(); x++) {
                rr = raster.getSample(x, y, 0);
                bb = raster.getSample(x, y, 1);
                gg = raster.getSample(x, y, 2);
                imagemRBG[y][x] = (rr + bb + gg) / 3;
            }
        }

    }

    public void calculaValores() {
        int gray;
        int posicao = 0;
        for (int y = 0; y < raster.getHeight(); y++) {
            for (int x = 0; x < raster.getWidth(); x++) {
                gray = imagemRBG[y][x];
                //gray = raster.getSample(x, y, 0);
                grayValue[posicao] = gray;
                grayHistogram[gray]++;
                posicao++;
            }
        }
    }

    public void iCoocorrencia(int ref, int px) {
        coocorrencia[ref][px]++;
        coocorrencia[px][ref]++;
        pixelcount += 2;
        //System.out.println("->" + ref + "->" + px);
    }

    public void normalizar() {
        for (int k = 0; k < 256; k++) {
            for (int l = 0; l < 256; l++) {
                coocorrencia[k][l] = coocorrencia[k][l] / (pixelcount);
            }
        }
    }

    public void printMatriz() {
        System.out.println("Matriz Co ocorrencia");
        for (int z = 0; z < coocorrencia.length; z++) {
            for (int y = 0; y < coocorrencia[z].length; y++) {
                System.out.print(" " + coocorrencia[z][y]);
            }
            System.out.println("");
        }
        System.out.println("*************");

    }

    public void printGrayHistogram() {
        System.out.println("Gray Histogram");
        for (int k = 0; k < grayHistogram.length; k++) {
            System.out.print("[" + grayHistogram[k] + "]");
        }
        System.out.println("*************");
    }

    public double[][] getCooocorrencia() {
        return this.coocorrencia;
    }

    public double getMediaCinzas() {
        return new Histograms(image).getMedia();
        }
    }
