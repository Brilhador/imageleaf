/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author anderson
 */
public class Signature {

    public int[] createSignal(ArrayList<Dimension> listaDimension, int angle) {
        Dimension centroide = getCentroideMedian(listaDimension);
        Dimension[] point = getDimensionPoint(listaDimension, centroide, angle);
        int[] distance = new int[360 / angle];
        for (int i = 0; i < point.length; i++) {
            distance[i] = getDistanceManhattan(centroide, point[i]);
        }
        return distance;
    }

    public double[] createNormSignal(ArrayList<Dimension> listaDimension, int angle) {
        Dimension centroide = getCentroideMedian(listaDimension);
        Dimension[] point = getDimensionPoint(listaDimension, centroide, angle);
        int[] distance = new int[360 / angle];
        for (int i = 0; i < point.length; i++) {
            distance[i] = getDistanceManhattan(centroide, point[i]);
        }
        return Histograma.normalizacao(distance, distance.length);
    }

    private double[] reorganizeDistance(double[] vector) {
        try {
            double[] outVector = new double[vector.length];
            int indice = 0;
            for (int i = 1; i < vector.length; i++) {
                if (vector[i] > vector[indice]) {
                    indice = i;
                }
            }
            for (int i = indice, j = 0; j < vector.length - indice; i++, j++) {
                outVector[j] = vector[i];
            }
            for (int i = 0, j = vector.length - indice; i < indice; i++, j++) {
                outVector[j] = vector[i];
            }
            return outVector;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Dimension getCentroideMedian(ArrayList<Dimension> lista) {
        int x = 0;
        int y = 0;
        for (Dimension dimension : lista) {
            x += dimension.width;
            y += dimension.height;
        }
        return new Dimension(x / lista.size(), y / lista.size());
    }

    public Dimension getCentrideBySides(ArrayList<Dimension> lista) {
        int x = 0;
        int y = 0;
        for (Dimension dimension : lista) {
        }
        return null;
    }

    private Dimension getInitAngle(ArrayList<Dimension> lista, Dimension centroide) {
        //pega o pixel no angulo zero
        Dimension point = centroide;
        //identificar os pontos 
        for (Dimension d : lista) {
            //0
            if (point.width < d.width && point.height == d.height) {
                point = d;
            }
        }
        return point;
    }

    public Dimension getInitAngleByDistance(ArrayList<Dimension> lista, Dimension centroide) {
        //pega o pixel no angulo zero
        Dimension point = centroide;
        //distancia
        double dst = 0;
        //identificar os pontos 
        for (Dimension d : lista) {
            //0
            double aux = getDistanceManhattan(centroide, d);
            if (aux > dst) {
                dst = aux;
                point = d;
            }
        }
        return point;
    }

    public Dimension getInitAngleByDstTwoPoint(ArrayList<Dimension> lista, Dimension centroide) {
        int meio = lista.size() / 2;
        int dst = 0;
        Dimension point = null;
        for (int i = 0, j = meio; i < meio; i++, j--) {
            int aux = getDistanceManhattan(lista.get(i), lista.get(j));
            if (aux > dst) {
                dst = aux;
                if (getDistanceManhattan(centroide, lista.get(i)) >= getDistanceManhattan(centroide, lista.get(j))) {
                    point = lista.get(i);
                } else {
                    point = lista.get(j);
                }
            }
        }
        return point;
    }

    public Dimension[] getDimensionPoint(ArrayList<Dimension> lista, Dimension centroide, int angle) {
        int quant = 360 / angle;
        int mod = angle % 360;
        int dif = 360;
        Dimension[] vector = new Dimension[quant];
        vector[0] = getInitAngle(lista, centroide);
        Dimension initAngle = vector[0];
        for (int i = 1; i < quant; i++) {
            for (Dimension dimension : lista) {
                int aux = (int) calcAngle(dimension, initAngle, centroide);
                if (vector[i] != null) {
                    if (aux == mod) {
                        if (getDistanceManhattan(centroide, vector[i]) < getDistanceManhattan(centroide, dimension)) {
                            vector[i] = dimension;
                        }
                    } else if (Math.abs(mod - aux) < dif) {
                        vector[i] = dimension;
                        dif = Math.abs(mod - aux);
                    }
                } else {
                    vector[i] = dimension;
                }
            }
            mod += angle % 360;
            dif = 360;
        }
        return vector;
    }

    private int calcAngle(Dimension point, Dimension initAngle, Dimension centroide) {
        double radians = 0;
        Dimension a = new Dimension((point.width - centroide.width), (point.height - centroide.height));
        Dimension b = new Dimension((initAngle.width - centroide.width), (initAngle.height - centroide.height));
        double alfa = Math.atan2(a.height, a.width);
        double beta = Math.atan2(b.height, b.width);
        radians = alfa - beta;
        if (radians > 0) {
//            System.out.println("1 e 2");
            return (int) Math.toDegrees(radians);
        } else {
//            System.out.println("3 e 4");
            return (int) Math.toDegrees(radians) + 360;
        }
    }

    private int getDistanceManhattan(Dimension point1, Dimension point2) {
        return Distancia.Manhattan(point1, point2);
    }
}