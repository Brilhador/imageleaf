/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.math;

import BRImage.useful.Coordinate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ANDERSON
 */
public class LinearRegression {

    public static ArrayList<Coordinate> apply(ArrayList<Coordinate> points, boolean invert) {

        int n = points.size();

        int mediaX = 0;
        int mediaY = 0;

        int somaX = 0;
        int somaY = 0;

        double[] x = new double[n];
        double[] y = new double[n];

        //vetores auxiliares
        if (invert) {
            for (int i = 0; i < n; i++) {
                y[i] = points.get(i).getX();
                x[i] = points.get(i).getY();
            }
        } else {
            for (int i = 0; i < n; i++) {
                x[i] = points.get(i).getX();
                y[i] = points.get(i).getY();
            }
        }

        //media de cada coordenada
        for (int i = 0; i < n; i++) {
            somaX += x[i];
            somaY += y[i];
        }

        mediaX = somaX / n;
        mediaY = somaY / n;

        //calculo 
        double xx = 0;
        double yy = 0;
        double xy = 0;

        for (int i = 0; i < n; i++) {
            xx += Math.pow((x[i] - mediaX), 2);
            yy += Math.pow((y[i] - mediaY), 2);
            xy += (x[i] - mediaX) * (y[i] - mediaY);
        }

        //estimação dos parametros
        double beta1 = xy / xx;
        double beta0 = mediaY - beta1 * mediaX;

        //System.out.println(" y = " + beta1 + "\n x = " + beta0);
        //estimativa
        ArrayList<Coordinate> rPoint = new ArrayList<>();

        Arrays.sort(x);

        if (invert) {
            for (int i = 0; i < n; i++) {
                rPoint.add(new Coordinate((int) (beta0 + beta1 * x[i]), (int) x[i]));
            }
        } else {
            for (int i = 0; i < n; i++) {
                rPoint.add(new Coordinate((int) x[i], (int) (beta0 + beta1 * x[i])));
            }
        }

        return rPoint;

    }
}
