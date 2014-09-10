/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.process;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Anderson
 */
public class Filter {

    /**
     * ****************************************************************************************************************************************
     */
    public static BufferedImage average(BufferedImage img, int maskSize) {

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

    public static BufferedImage median(BufferedImage img, int maskSize) {

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
    
    public static boolean[][] median(boolean[][] img, int maskSize) {

        boolean[][] imgOut = new boolean[img.length][img[0].length];
        
        int altura, largura;

        ArrayList mascara = new ArrayList<Boolean>();


        //perrcorrer a imagem;
        for (largura = 0; largura < img.length; largura++) {
            for (altura = 0; altura < img[0].length; altura++) {
                // encher a mascara
                for (int i = largura - maskSize / 2; i <= largura + maskSize / 2; i++) {
                    for (int j = altura - maskSize / 2; j <= altura + maskSize / 2; j++) {
                        if (i >= 0 && i < img.length) {
                            if (j >= 0 && j < img[0].length) {
                                mascara.add(img[i][j]);
                            }
                        }
                    }
                }
                // ordena a mascara
                Collections.sort(mascara);


                // grava no arquivo novo
                boolean b = (boolean) mascara.get(mascara.size() / 2);
                imgOut[largura][altura] = b;

                // limpa pro próximo pixel
                mascara.clear();
            }
        }
        return imgOut;
    }
}
