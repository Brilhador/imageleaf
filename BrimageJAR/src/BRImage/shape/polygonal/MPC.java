/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.shape.polygonal;

import BRImage.shape.line.Besenham;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author ANDERSON
 *
 * Aproximação poligonal a partir do contorno Original (MPC)
 *
 * Dissertação de mestrado - Métodos para aproximação poligonal e
 * Desenvolvimento de extratores de caracteristicas de forma a partir da Função
 * Tangencial
 *
 * Juliano Dalioa de Carvalho
 *
 */
public class MPC {

    public static ArrayList<Dimension> poligonal(ArrayList<Dimension> contorno, ArrayList<Integer> segmento, int minPixel, int maxAngulo) {
        boolean iteracao = false;

        for (int i = 1; i < segmento.size() - 1; i++) {
            //primeira regra
            if (Math.abs(segmento.get(i) - segmento.get(i + 1)) < minPixel) {
                segmento.remove(i + 1);
                iteracao = true;
            } else {
                Dimension A = contorno.get(segmento.get(i)); //ponto de inicio do primeiro segmento
                Dimension C = contorno.get(segmento.get(i + 1)); //ponto final do segundo segmento

                //produto escalar
                double AC = (A.width * C.width) + (A.height * C.height);

                //Norma dos vetores
                double a = Math.sqrt(Math.pow(A.width, 2) + Math.pow(A.height, 2));
                double c = Math.sqrt(Math.pow(C.width, 2) + Math.pow(C.height, 2));

                //calculo do angulo
                double angulo = Math.toDegrees(Math.acos(Math.sin(AC / (a * c))));

                //segunda regra
                if (angulo > maxAngulo) {
                    segmento.remove(i + 1);
                    iteracao = true;
                }
            }
        }

        if (iteracao) {
            poligonal(contorno, segmento, minPixel, maxAngulo);
        }

        ArrayList<Dimension> pontos = new ArrayList<>();
        for (int i = 0; i < segmento.size(); i++) {
            pontos.add(contorno.get(segmento.get(i)));
        }

        return getContornoFinal(contorno, segmento);
    }

    private static ArrayList<Dimension> getContornoFinal(ArrayList<Dimension> contorno, ArrayList<Integer> segmento) {
        ArrayList<Dimension> novoContorno = new ArrayList<>();

        for (int i = 0; i < segmento.size() - 1; i++) {
            Dimension initPoint = contorno.get(segmento.get(i));
            Dimension finalPoint = contorno.get(segmento.get(i + 1));
            ArrayList<Dimension> aux = Besenham.getLine(initPoint, finalPoint);
            for (Dimension dimension : aux) {
                novoContorno.add(dimension);
            }
        }

        Dimension initPoint = contorno.get(segmento.get(segmento.size()-1));
        Dimension finalPoint = contorno.get(segmento.get(0));
        ArrayList<Dimension> aux = Besenham.getLine(initPoint, finalPoint);
        for (Dimension dimension : aux) {
            novoContorno.add(dimension);
        }

        return novoContorno;
    }

    public static ArrayList<Integer> getSegmentoLinear(ArrayList<Dimension> contorno) {
        ArrayList<Integer> segmento = new ArrayList<>();
        Dimension centroide = getCentroideMedian(contorno);

        int angulo = 0;
        int anterior = 0;
        int indice = 0;

        segmento.add(0);
        anterior = angle(contorno.get(1), contorno.get(0), centroide);

        for (int i = 1; i < contorno.size(); i++) {
            if ((i + 1) < contorno.size()) {
                angulo = angle(contorno.get(i), contorno.get(indice), centroide);
                if (angulo != anterior) {
                    segmento.add(i);
                    indice = i;
                }
            } else {
                angulo = angle(contorno.get(0), contorno.get(indice), centroide);
                if (angulo != anterior) {
                    segmento.add(0);
                }
            }
            anterior = angulo;
        }
        return segmento;
    }

    private static Dimension getCentroideMedian(ArrayList<Dimension> lista) {
        int x = 0;
        int y = 0;
        for (Dimension dimension : lista) {
            x += dimension.width;
            y += dimension.height;
        }
        return new Dimension(x / lista.size(), y / lista.size());
    }

    private static int angle(Dimension point, Dimension initAngle, Dimension centroide) {
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
}
