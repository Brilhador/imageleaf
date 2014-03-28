/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.segmetation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hough {

    public static ArrayList<HoughLine> apply(int width, int height, ArrayList<Dimension> point, int threshold) {

        ArrayList<HoughLine> linha = new ArrayList<>();

        //descritização do espaço de hough - (p, theta) para intervalos finitos
        int houghWidth = 180;
        int houghHeigth = (int) Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));

        //matriz acumuladora
        int[][] acumulador = new int[houghWidth][houghHeigth];

        //iniciar matriz acumulativa
        for (int i = 0; i < houghWidth; i++) {
            for (int j = 0; j < houghHeigth; j++) {
                acumulador[i][j] = 0;
            }
        }

        //Para cada ponto (x,y) da imagem calcular os valores de theta e p
        for (Dimension d : point) {
            for (int th = 0; th < houghWidth; th++) {
                int p = (int) ((d.width * Math.cos(th)) + (d.height * Math.sin(th)));
                if (0 < p && p < houghHeigth) {
                    acumulador[th][p]++;
                }
            }

        }
        
        //idetectando as linhas
        for (int i = 0; i < houghWidth; i++) {
            for (int j = 0; j < houghHeigth; j++) {
                if(acumulador[i][j] >= threshold){
                    linha.add(new HoughLine(i, j));
                }
            }
        } 

        return linha;
    }

}
