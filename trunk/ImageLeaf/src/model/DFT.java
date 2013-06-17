/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author anderson
 */
public class DFT {
    
    private double[] x1 = null;
    private double[] y1 = null;
    
    public DFT(int dir,  double[] x1, double[] y1, int amostragem){
        this.x1 = x1;
        this.y1 = y1;
        create(dir, x1, y1, amostragem);
    }

    public double[] getX1() {
        return x1;
    }

    public double[] getY1() {
        return y1;
    }
    
    private void create(int dir, double[] x1, double[] y1, int amostragem){
        /*
         * x1, y1 são os valores reais e imaginários - arrays of 2^m pontos
         * dir = 1 resulta na transformada de fourier
         * dir = -1 resulta na tranformada inversa de fourier
         */
        double[] x2 =  new double[amostragem];
        double[] y2 =  new double[amostragem];
        double arg = 0;
        double cosarg = 0;
        double sinarg = 0;
        
        for (int i = 0; i < amostragem; i++) {
            x2[i] = 0;
            y2[i] = 0;
            arg = - dir * 2.0 * Math.PI * i / (amostragem);
            for (int j = 0; j < amostragem; j++) {
                cosarg = Math.cos(j * arg);
                sinarg = Math.sin(j * arg);
                x2[i] += ((x1[j] * cosarg) - (y1[j] * sinarg));
                y2[i] += ((x1[j] * sinarg) + (y1[j] * cosarg));
            }
        }
        //TRANSFORMADA DISCRETA DE FOURIER
        if(dir == 1){
            for (int i = 0; i < amostragem; i++) {
                x1[i] = x2[i] / amostragem;
                y1[i] = y2[i] / amostragem;
            }
        }else{//TRANSFORMADA INVERSA DE FOURIER
            for (int i = 0; i < amostragem; i++) {
                x1[i] = x2[i];
                y1[i] = y2[i];
            }
        }
    }
    
    public void invRotation(){
        for (int i = 0; i < x1.length; i++) {
            x1[i] = Math.abs(x1[i]);
            y1[i] = Math.abs(y1[i]);
        }
    }
    
    public void invScala(){
        double valor = x1[0];
        for (int i = 0; i < x1.length; i++) {
            x1[i] = x1[i] / valor;
            y1[i] = y1[i] / valor;
        }
    }
}
