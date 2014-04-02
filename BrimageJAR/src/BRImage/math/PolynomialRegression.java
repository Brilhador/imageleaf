/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.math;

import Jama.Matrix;
import Jama.QRDecomposition;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author ANDERSON
 */
public class PolynomialRegression {

    public static ArrayList<Dimension> apply(ArrayList<Dimension> points, int degree, boolean invert) {
        int N = points.size();
        Matrix beta = null;
        double SSE = 0;
        double SST = 0;
        double[] x = new double[N];
        double[] y = new double[N];

        //vetores auxiliares
        if (invert) {
            for (int i = 0; i < N; i++) {
                x[i] = points.get(i).height;
                y[i] = points.get(i).width;
            }
        } else {
            for (int i = 0; i < N; i++) {
                x[i] = points.get(i).width;
                y[i] = points.get(i).height;
            }
        }

        // build Vandermonde matrix
        double[][] vandermonde = new double[N][degree + 1];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= degree; j++) {
                vandermonde[i][j] = Math.pow(x[i], j);
            }
        }

        Matrix X = new Matrix(vandermonde);

        // create matrix from vector
        Matrix Y = new Matrix(y, N);

        // find least squares solution
        QRDecomposition qr = new QRDecomposition(X);
        beta = qr.solve(Y);

        // mean of y[] values
        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            sum += y[i];
        }
        double mean = sum / N;

        // total variation to be accounted for
        for (int i = 0; i < N; i++) {
            double dev = y[i] - mean;
            SST += dev * dev;
        }

        // variation not accounted for
        Matrix residuals = X.times(beta).minus(Y);
        SSE = residuals.norm2() * residuals.norm2();

        ArrayList<Dimension> rPoint = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            double aux = 0;
            for (int j = degree; j >= 0; j--) {
                aux = (int) (beta.get(j, 0) + (x[i] * aux));
            }
            rPoint.add(new Dimension((int) x[i], (int) aux));
        }

        return rPoint;

    }

}
