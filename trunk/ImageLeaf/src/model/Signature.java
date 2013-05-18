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

//    public Dimension getAnglePoint(ArrayList<Dimension> lista, Dimension centroide, Dimension point, int angle) {
//        for (Dimension dimension : lista) {
//            if (angle == (int) calcAngle(point, centroide)) {
//                return dimension;
//            }
//        }
//        return null;
//    }
    public Dimension[] getDimensionPoint(ArrayList<Dimension> lista, Dimension centroide, int angle) {
        int quant = 360 / angle;
        int mod = angle % 360;
        Dimension[] vector = new Dimension[quant];
        vector[0] = getInitAngle(lista, centroide);
        Dimension initAngle = vector[0];
        for (int i = 1; i < quant; i++) {
            for (Dimension dimension : lista) {
                int aux = (int) calcAngle(dimension, initAngle, centroide);
                System.out.println(aux);
                if (aux <= mod) {
                    vector[i] = dimension;
                    if (aux == mod) {
                        break;
                    }
                }
            }
            mod += mod;
        }
        return vector;
    }

    private int calcAngle(Dimension point, Dimension initAngle, Dimension centroide) {
        double radians = 0;
        Dimension a = new Dimension((point.width - centroide.width), (point.height - centroide.height));
        Dimension b = new Dimension((initAngle.height - centroide.height), (initAngle.height - centroide.height));
        double alfa = Math.atan2(a.height, a.width);
        double beta = Math.atan2(b.height, b.width);
        radians = Math.abs(alfa - beta);
        //angle *= 360 / Math.PI;
        //0 -90
        if (0 < radians && radians < Math.PI / 2) {
            return (int) Math.toDegrees(radians);
            //90 - 180
        } else if (Math.PI / 2 < radians && radians < Math.PI) {
            return (int) Math.toDegrees(radians);
            //180 -270
        } else if (Math.PI < radians && radians < 3 * Math.PI / 2) {
            return (int) Math.toDegrees(radians);
            //180 - 360
        } else if(3*Math.PI/2 < radians && radians < 2*Math.PI){
            return (int) Math.toDegrees(radians);
        }
        return (int) Math.toDegrees(radians);
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
//    public Dimension getCentroide(ArrayList<Dimension> lista) {
//        //maximos pontos
//        Dimension left = lista.get(0);
//        Dimension right = lista.get(0);
//        Dimension top = lista.get(0);
//        Dimension button = lista.get(0);
//        //Distancia
//        int dLeft = 0;
//        int dWidth = 0;
//        int dHeigth = 0;
//        int dRight = 0;
//        int dTop = 0;
//        int dButton = 0;
//        //Dimension
//        Dimension startX = null;
//        Dimension startY = null;
//        //encontrado os maximos pontos
//        for (Dimension dimension : lista) {
//            if (left.width > dimension.width) {
//                left = dimension;
//            }
//            if (right.width < dimension.width) {
//                right = dimension;
//            }
//            if (top.height > dimension.height) {
//                top = dimension;
//            }
//            if (button.height < dimension.height) {
//                button = dimension;
//            }
//        }
//        for (Dimension dimension : lista) {
//            if (top.width > dimension.width) {
//                Dimension aux = dimension;
//                for (Dimension d : lista) {
//                    if (aux.width < d.width && aux.height == d.height) {
//                        int value = d.width - aux.width;
//                        if (value > dWidth) {
//                            dWidth = value;
//                            startX = aux;
//                        }
//                    }
//                }
//            }
//            if (top.width < dimension.width) {
//                Dimension aux = dimension;
//                for (Dimension d : lista) {
//                    if (aux.width > d.width && aux.height == d.height) {
//                        int value = aux.width - d.width;
//                        if (value > dWidth) {
//                            dWidth = value;
//                            startX = aux;
//                        }
//                    }
//                }
//            }
//        }
//        for (Dimension dimension : lista) {
//            if (left.height > dimension.height) {
//                Dimension aux = dimension;
//                for (Dimension d : lista) {
//                    if (aux.height < d.height && aux.width == d.width) {
//                        int value = d.height - aux.height;
//                        if (value > dHeigth) {
//                            dHeigth = value;
//                            startY = aux;
//                        }
//                    }
//                }
//            }
//             if (left.height < dimension.height) {
//                Dimension aux = dimension;
//                for (Dimension d : lista) {
//                    if (aux.height > d.height && aux.width == d.width) {
//                        int value = aux.height - d.height ;
//                        if (value > dHeigth) {
//                            dHeigth = value;
//                            startY = aux;
//                        }
//                    }
//                }
//            }
//        }
////        //definido a maior distancia
////        for (Dimension dimension : lista) {
////            //width
////            if (dimension.height == left.height) {
////                int value = dimension.width - left.width;
////                if (value > dLeft) {
////                    dLeft = value;
////                }
////            }
////            if (dimension.height == right.height) {
////                int value = right.width - dimension.width;
////                if (value > dRight) {
////                    dRight = value;
////                }
////            }
////            //heigth
////            if (dimension.width == top.width) {
////                int value = dimension.height - top.height;
////                if (value > dTop) {
////                    dTop = value;
////                }
////            }
////            if (dimension.width == button.width) {
////                int value = button.height - dimension.height;
////                if (value > dButton) {
////                    dButton = value;
////                }
////            }
////        }
////        //verificando qual teve a maior distancia
////        if (dLeft > dRight) {
////            startX = left;
////            System.out.println("left");
////        } else {
////            startX = right;
////            System.out.println("right");
////        }
////        if (dTop > dButton) {
////            startY = top;
////            System.out.println("top");
////        } else {
////            startY = button;
////            System.out.println("button");
////        }
//        //retorna o ponto de encontro
////        System.out.println("x " + startY.width + " y " + startX.height);
//        return new Dimension(startY.width, startX.height);
//    }
//}
