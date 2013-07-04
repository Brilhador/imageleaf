/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anderson
 */
public class Signature {

    //variaveis
    private BufferedImage imageOriginal = null;
    private BufferedImage imageSignature = null;
    private double[] signature = null;
    private Dimension centroid = null;
    private Dimension initPoint = null;
    private ArrayList<Dimension> border = null;
    //constantes
    private static final int RADIUS = 0;
    private static final int DIAMETER = 1;

    public Signature(BufferedImage originalImage, int angle, boolean invInicialPoint, int type, boolean invScala) {
        try {
            imageOriginal = originalImage;
            border = getBorder(originalImage);
            centroid = getCentroideMedian(border);
            if (invInicialPoint) {
                switch (type) {
                    case 0:
                        initPoint = getInitAngleByMoreRadius(border, centroid);
                        break;
                    case 1:
                        initPoint = getInitAngleByMoreDiameter(border, centroid);
                        break;
                }
            } else {
                initPoint = getInitAngle(border, centroid);
            }
            if (invScala) {
                signature = createNormSignal(border, initPoint, angle);
            } else {
                signature = createSignal(border, initPoint, angle);
            }
            drawSignature(border, centroid, getDimensionPoint(border, centroid, initPoint, angle));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Dimension> getBorder(BufferedImage image) {
        int total = image.getWidth() * image.getHeight();
        image = Filtro.mediana(image, 5);
        int limiar = Limiar.otsuTreshold(Histograma.histogramaGray(image), total);
        return new BorderDetector(Limiar.limiarizacaoBool(image, limiar)).getBorder();
    }

    private double[] createSignal(ArrayList<Dimension> listaDimension, Dimension initPoint, int angle) {
        Dimension centroide = getCentroideMedian(listaDimension);
        Dimension[] point = getDimensionPoint(listaDimension, centroide, initPoint, angle);
        double[] distance = new double[360 / angle];
        for (int i = 0; i < point.length; i++) {
            distance[i] = getDistanceManhattan(centroide, point[i]);
        }
        return distance;
    }

    private double[] createNormSignal(ArrayList<Dimension> listaDimension, Dimension initPoint, int angle) {
        Dimension centroide = getCentroideMedian(listaDimension);
        Dimension[] point = getDimensionPoint(listaDimension, centroide, initPoint, angle);
        double[] distance = new double[360 / angle];
        for (int i = 0; i < point.length; i++) {
            distance[i] = getDistanceManhattan(centroide, point[i]);
        }
        return Histograma.normalizacao(distance);
    }

    private Dimension getCentroideMedian(ArrayList<Dimension> lista) {
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

    private Dimension getInitAngleByMoreRadius(ArrayList<Dimension> lista, Dimension centroide) {
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

    private Dimension getInitAngleByMoreDiameter(ArrayList<Dimension> lista, Dimension centroide) {
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

    private Dimension[] getDimensionPoint(ArrayList<Dimension> lista, Dimension centroide, Dimension initPoint, int angle) {
        int quant = 360 / angle;
        int mod = angle % 360;
        int dif = 360;
        Dimension[] vector = new Dimension[quant];
        vector[0] = initPoint;
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
    
     public void drawSignature(ArrayList<Dimension> lista, Dimension centroide, Dimension[] point) {
        imageSignature = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), imageOriginal.getType());
        Graphics2D g2d = imageSignature.createGraphics();
        g2d.drawImage(imageOriginal, null, 0, 0);
        //desenha a linha do primeiro elemento da lista
        g2d.setColor(Color.BLUE);
        g2d.drawLine(centroide.width, centroide.height, point[0].width, point[0].height);
        for (int i = 1; i < point.length; i++) {
//            drawPoint(drawImage, point[i], Color.RED);
            g2d.setColor(Color.RED);
            g2d.drawLine(centroide.width, centroide.height, point[i].width, point[i].height);
        }
        g2d.dispose();
        
        for (Dimension dimension : lista) {
            drawPoint(imageSignature, dimension, Color.GREEN);
        }

        drawPoint(imageSignature, centroide, Color.YELLOW);
        g2d.dispose();
    }

    private void drawPoint(BufferedImage drawImage, Dimension point, Color cor) {
        drawImage.setRGB(point.width, point.height, cor.getRGB());
        drawImage.setRGB(point.width + 1, point.height, cor.getRGB());//0
        drawImage.setRGB(point.width, point.height - 1, cor.getRGB());//2
        drawImage.setRGB(point.width - 1, point.height, cor.getRGB());//4
        drawImage.setRGB(point.width, point.height + 1, cor.getRGB());//6
    } 

    public BufferedImage getImageSignature() {
        return imageSignature;
    }

    public void setImageSignature(BufferedImage imageSignature) {
        this.imageSignature = imageSignature;
    }

    public double[] getSignature() {
        return signature;
    }

    public void setSignature(double[] signature) {
        this.signature = signature;
    }
}