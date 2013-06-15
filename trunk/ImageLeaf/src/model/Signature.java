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

    public double[] createSignal(ArrayList<Dimension> listaDimension, int angle) {
        Dimension centroide = getCentroideMedian(listaDimension);
        Dimension[] point = getDimensionPoint(listaDimension, centroide, angle);
        double[] distance = new double[360 / angle];
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

    public Dimension getCentroideMedian(ArrayList<Dimension> lista) {
        int x = 0;
        int y = 0;
        for (Dimension dimension : lista) {
            x += dimension.width;
            y += dimension.height;
        }
        return new Dimension(x / lista.size(), y / lista.size());
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

    public Dimension getInitAngleByMoreRadius(ArrayList<Dimension> lista, Dimension centroide) {
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

    public Dimension getInitAngleByMoreDiameter(ArrayList<Dimension> lista, Dimension centroide) {
        int dst = 0;
        Dimension point = null;
        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < lista.size(); j++) {
                //verifica se a reta eh um diametro
                boolean result = isDiameter(lista.get(i), lista.get(j), centroide);
//                System.out.println(result);
                if (result) {
                    System.out.println("diametro encontrado");
                    //caso sim calcula a distancia entre os dois pontos
                    int aux = getDistanceManhattan(lista.get(i), lista.get(j));
                    if (aux > dst) {
                        dst = aux;
                        //se for maior que a distancia do ultimo diametro
                        //calcula a distancia dos dois pontos o maior raio
                        int dst1 = getDistanceManhattan(lista.get(i), centroide);
                        int dst2 = getDistanceManhattan(lista.get(j), centroide);
                        //qual ponto tiver o maior raio se torna o ponto inicial
                        if (dst1 > dst2) {
                            point = lista.get(i);
                        } else {
                            point = lista.get(j);
                        }
                    }
                }
            }

        }
//        System.out.println("encontrou");
        return point;
    }

    public Dimension getInitAngleByDiameterMoreDiferenceRadius(ArrayList<Dimension> lista, Dimension centroide) {
        Dimension point = null;
        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < lista.size(); j++) {
                //verifica se a reta eh um diametro
                boolean result = isDiameter(lista.get(i), lista.get(j), centroide);
//                System.out.println(result);
                if (result) {
                    
                }
            }
        }
//        System.out.println("encontrou");
        return point;
    }

    private boolean isDiameter(Dimension d1, Dimension d2, Dimension centroide) {
        /*
         * Funçao da reta para verificar se uma corda eh um diametro
         * y = mx+b;
         */
        try {
            double m = 0;
            if ((d2.width - d1.width) == 0) {
                m = (d2.height - d1.height) / 1;
            } else {
                m = (d2.height - d1.height) / (d2.width - d1.width);
            }
            double b = d2.height - (m * d2.width);
            double result = (((m * centroide.width) + b) / centroide.height);
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    private double calcMedian(double[] distance) {
        double soma = 0;
        for (int i = 0; i < distance.length; i++) {
            soma += distance[i];
        }
        return soma / distance.length;
    }

    private double calcVariance(double[] distance) {
        double median = calcMedian(distance);
        double variance = 0;
        for (int i = 0; i < distance.length; i++) {
            variance += Math.pow((distance[i] - median), 2);
        }
        return variance / (distance.length - 1);
    }
}