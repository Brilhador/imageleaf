/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Anderson
 */
public class Filtro {

    /**
     * ****************************************************************************************************************************************
     */
    /*
     * Detecção de Bordas
     */
    public static int[][] bordaSobel(BufferedImage img) {
        //pesos utilizados para cada pixels
        float[] template = {-1, 0, 1, -2, 0, 2, -1, 0, 1};

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

    public static int[][] bordaPrewitt(BufferedImage img) {
        //pesos utilizados para cada pixels
        float[] template = {-1, 0, 1, -1, 0, 1, -1, 0, 1};

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

    public static int[][] bordaRoberts(BufferedImage img) {
        //pesos utilizados para cada pixels
        float[] template = {-1, 0, 0, 1};

        float[][] GY = new float[img.getWidth()][img.getHeight()];
        float[][] GX = new float[img.getWidth()][img.getHeight()];

        //matriz de saida
        int[][] output = new int[img.getWidth()][img.getHeight()];

        int sum = 0;

        for (int x = 0; x < img.getWidth() - 1; x++) {
            for (int y = 0; y < img.getHeight() - 1; y++) {
                sum = 0;

                for (int x1 = 0; x1 < 2; x1++) {
                    for (int y1 = 0; y1 < 2; y1++) {
                        int x2 = (x + x1);
                        int y2 = (y + y1);

                        Color cor = new Color(img.getRGB(x2, y2));
                        float value = ((cor.getRed() + cor.getGreen() + cor.getBlue()) / 3) * (template[y1 * 2 + x1]);
                        sum += value;
                    }
                }
                GY[x][y] = sum;

                sum = 0;
                for (int x1 = 0; x1 < 2; x1++) {
                    for (int y1 = 0; y1 < 2; y1++) {
                        int x2 = (x + x1);
                        int y2 = (y + y1);

                        Color cor = new Color(img.getRGB(x2, y2));
                        float value = ((cor.getRed() + cor.getGreen() + cor.getBlue()) / 3) * (template[x1 * 2 + y1]);
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

    public static int[][] bordaLaplaciano(BufferedImage img) {
        //pesos utilizados para cada pixels
        float[] template = {0, -1, 0,
                            -1, 4, -1,
                            0, -1, 0};

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

    /**
     * ****************************************************************************************************************************************
     */
    public static BufferedImage passaBaixas(BufferedImage img, int maskSize) {

        BufferedImage imgOut = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        int altura, largura;

        ArrayList mascara = new ArrayList<Integer>();

        //perrcorrer a imagem;
        for (largura = 0; largura < img.getWidth(); largura++) {
            for (altura = 0; altura < img.getHeight(); altura++) {
                // encher a mascara
                for (int i = largura - maskSize / 2; i <= largura + maskSize / 2; i++) {
                    for (int j = altura - maskSize / 2; j <= altura + maskSize / 2; j++) {
                        if (i >= 0 && i < img.getWidth()) {
                            if (j >= 0 && j < img.getHeight()) {
                                mascara.add(new Color(img.getRGB(i, j)));
                            }
                        }
                    }
                }
                //media dos canais RGB
                int mediaR = 0;
                int mediaG = 0;
                int mediaB = 0;

                for (int t = 0; t < mascara.size(); t++) {
                    Color cor = (Color) mascara.get(t);
                    mediaR += cor.getRed();
                    mediaG += cor.getGreen();
                    mediaB += cor.getBlue();
                }

                mediaR /= mascara.size();
                mediaG /= mascara.size();
                mediaB /= mascara.size();

                //Criar a cor baseado no niveis de cada canal
                Color c = new Color(mediaR, mediaG, mediaB);

                //altera o pixel na imagem de saida
                imgOut.setRGB(largura, altura, c.getRGB());

                //limpar para o proximo pixel
                mascara.clear();
            }
        }
        return imgOut;
    }

    public static BufferedImage mediana(BufferedImage img, int maskSize) {

        BufferedImage imgOut = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        int altura, largura;

        ArrayList mascaraR = new ArrayList<Integer>();
        ArrayList mascaraG = new ArrayList<Integer>();
        ArrayList mascaraB = new ArrayList<Integer>();


        //perrcorrer a imagem;
        for (largura = 0; largura < img.getWidth(); largura++) {
            for (altura = 0; altura < img.getHeight(); altura++) {
                // encher a mascara
                for (int i = largura - maskSize / 2; i <= largura + maskSize / 2; i++) {
                    for (int j = altura - maskSize / 2; j <= altura + maskSize / 2; j++) {
                        if (i >= 0 && i < img.getWidth()) {
                            if (j >= 0 && j < img.getHeight()) {
                                mascaraR.add(new Color(img.getRGB(i, j)).getRed());
                                mascaraG.add(new Color(img.getRGB(i, j)).getGreen());
                                mascaraB.add(new Color(img.getRGB(i, j)).getBlue());

                            }
                        }
                    }
                }
                // ordena a mascara
                Collections.sort(mascaraR);
                Collections.sort(mascaraG);
                Collections.sort(mascaraB);

                // grava no arquivo novo
                Color c = new Color((Integer) mascaraR.get(mascaraR.size() / 2),
                        (Integer) mascaraG.get(mascaraG.size() / 2), (Integer) mascaraB.get(mascaraB.size() / 2));

                imgOut.setRGB(largura, altura, c.getRGB());

                // limpa pro próximo pixel
                mascaraR.clear();
                mascaraG.clear();
                mascaraB.clear();
            }
        }
        return imgOut;
    }
}
