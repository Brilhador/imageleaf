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

    public double[] createSignal(ArrayList<Dimension> listaDimension) {
        Dimension centroide = getCentroideMedian(listaDimension);
        return null;
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

    public Dimension getInitAngle(ArrayList<Dimension> lista, Dimension centroide) {
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

    public Dimension getInitAngleByDistance(ArrayList<Dimension> lista, Dimension centroide){
        //pega o pixel no angulo zero
        Dimension point = centroide;
        //distancia
        int dst = 0;
        //identificar os pontos 
        for (Dimension d : lista) {
            //0
            int aux = getDistance(centroide, d);
            if (aux > dst) {
                dst = aux;
                point = d;
            }
        }
        return point;
    }
    
    public Dimension[] getDimensionPoint(ArrayList<Dimension> lista, Dimension centroide, int angle) {
        int quant = 360 / angle;
        System.out.println("quantidade: " + quant);
        int mod = angle % 360;
        int dst = 360;
        Dimension[] vector = new Dimension[quant];
        vector[0] = getInitAngleByDistance(lista, centroide);
        Dimension initAngle = vector[0];
        for (int i = 1; i < quant; i++) {
            for (Dimension dimension : lista) {
                int aux = (int) calcAngle(dimension, initAngle, centroide);
                if (Math.abs(mod - aux) < dst) {
                    vector[i] = dimension;
                    if (aux == mod) {
                        break;
                    }
                }
            }
            mod += angle % 360 ;
            dst = 360;
        }
        return vector;
    }

    private int calcAngle(Dimension point, Dimension initAngle, Dimension centroide) {
        double radians = 0;
        Dimension a = new Dimension((point.width - centroide.width), (point.height - centroide.height));
        Dimension b = new Dimension((initAngle.height - centroide.height), (initAngle.height - centroide.height));
        double alfa = Math.atan2(a.height, a.width);
        double beta = Math.atan2(b.height, b.width);
        radians = alfa - beta;
        if(radians > 0){
//            System.out.println("1 e 2");
            return (int) Math.toDegrees(radians);
        }else{
//            System.out.println("3 e 4");
            return (int) Math.toDegrees(radians) + 360;
        }
    }

    private int calcAtan2(Dimension point, Dimension initAngle, Dimension centroide) {
        double angle = 0;
        int x = (point.width - centroide.width) + (point.height - centroide.height);
        int y = (centroide.width - initAngle.width) + (centroide.height - initAngle.height);
        angle = Math.atan2(y, x);
        angle *= 360 / Math.PI;
        return (int) angle;
    }

    private int getDistance(Dimension centroide, Dimension point) {
        return Distancia.Manhattan(centroide, point);
    }
}