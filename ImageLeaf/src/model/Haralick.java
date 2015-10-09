/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Thiago
 */
public class Haralick {

    private Coocorrencia coocorrencia;
    private double[][] cooccurrenceMatrix;
    private int NUM_VALORES_CINZAS = 256;
    /** p_(x+y) */
    private double[] p_x_mais_y = new double[2 * NUM_VALORES_CINZAS - 1];
    /** p_(x-y) */
    private double[] p_x_menos_y = new double[NUM_VALORES_CINZAS];
    /** Media das linhas */
    private double media_x = 0;
    /** Media das colunas */
    private double media_y = 0;
    /** Varianca linha */
    private double var_x = 0;
    /** Varianca Coluna */
    private double var_y = 0;
    /** HXY1 estatistica */
    private double hx = 0;
    /** HXY2 estatistica */
    private double hy = 0;
    /** HXY1 estatistica */
    private double hxy1 = 0;
    /** HXY2 estatistica */
    private double hxy2 = 0;
    // -
    double[] haralick = null;

    public Haralick(Coocorrencia cc) {
        cc.normalizar();
        this.coocorrencia = cc;
        this.cooccurrenceMatrix = cc.getCooocorrencia();
        calcularEstatisticas();
        calcular();
    }
    
    public static String[] get_posHaralick() {
        String posHaralick[] = new String[13];
        posHaralick[0] = "Angular2ndMoment";
        posHaralick[1] = "Contrast";
        posHaralick[2] = "Correlation";
        posHaralick[3] = "Variance";
        posHaralick[4] = "InverseDiff.Moment";
        posHaralick[5] = "SumAverage";
        posHaralick[6] = "SumVariance";
        posHaralick[7] = "SumEntropy";
        posHaralick[8] = "Entropy";
        posHaralick[9] = "DifferenceVariance";
        posHaralick[10] = "DifferenceEntropy";
        posHaralick[11] = "InformationMesuresofCorrelation1-";
        posHaralick[12] = "InformationMesuresofCorrelation2-";

        return posHaralick;
    }
    public double[] getFeatures() {
        return this.haralick;
    }

    private void calcular() {
        haralick = new double[13];
        double valorMedioCinzas = coocorrencia.getMediaCinzas();


        for (int i = 0; i < NUM_VALORES_CINZAS; i++) {
            double soma_j_p_x_menos_y = 0;
            for (int j = 0; j < NUM_VALORES_CINZAS; j++) {
                double p_ij = cooccurrenceMatrix[i][j];

                soma_j_p_x_menos_y += j * p_x_menos_y[j];

                haralick[0] += p_ij * p_ij;
                //haralick[2] += i * j * p_ij - media_x * media_y;
                haralick[2] += p_ij * (i - media_x)*(j - media_y);
                haralick[3] += (i - valorMedioCinzas) * (i - valorMedioCinzas) * p_ij;
                haralick[4] += p_ij / (1 + (i - j) * (i - j));
                haralick[8] += p_ij * log(p_ij);
            }

            haralick[1] += i * i * p_x_menos_y[i];
            haralick[9] += (i - soma_j_p_x_menos_y) * (i - soma_j_p_x_menos_y) * p_x_menos_y[i];
            haralick[10] += p_x_menos_y[i] * log(p_x_menos_y[i]);
        }

        //haralick[2] /= Math.sqrt(var_x * var_y);  
        haralick[2] /= (var_x * var_y);
        haralick[8] *= -1;
        haralick[10] *= -1;
        double maxhxhy = Math.max(hx, hy);
        if (Math.signum(maxhxhy) == 0) {
            haralick[11] = 0;
        } else {
            haralick[11] = (haralick[8] - hxy1) / maxhxhy;
        }
        haralick[12] = Math.sqrt(1 - Math.exp(-2 * (hxy2 - haralick[8])));

        for (int i = 0; i < 2 * NUM_VALORES_CINZAS - 1; i++) {
            haralick[5] += i * p_x_mais_y[i];
            haralick[7] += p_x_mais_y[i] * log(p_x_mais_y[i]);

            double soma_j_p_x_mais_y = 0;
            for (int j = 0; j < 2 * NUM_VALORES_CINZAS - 1; j++) {
                soma_j_p_x_mais_y += j * p_x_mais_y[j];
            }
            haralick[6] += (i - soma_j_p_x_mais_y) * (i - soma_j_p_x_mais_y) * p_x_mais_y[i];
        }

        haralick[7] *= -1;
    }

    private void calcularEstatisticas() {
        /** p_x */
        final double[] p_x = new double[NUM_VALORES_CINZAS];
        /** p_y */
        final double[] p_y = new double[NUM_VALORES_CINZAS];


        // p_x, p_y, p_x+y, p_x-y
        for (int i = 0; i < NUM_VALORES_CINZAS; i++) {
            for (int j = 0; j < NUM_VALORES_CINZAS; j++) {
                double p_ij = cooccurrenceMatrix[i][j];

                p_x[i] += p_ij;
                p_y[j] += p_ij;

                p_x_mais_y[i + j] += p_ij;
                p_x_menos_y[Math.abs(i - j)] += p_ij;
            }
        }

        // valores medios
        for (int i = 0; i < NUM_VALORES_CINZAS; i++) {
            media_x += i * p_x[i];
            media_y += i * p_y[i];
        }

        for (int i = 0; i < NUM_VALORES_CINZAS; i++) {
            // variancas - desvio padrão    
            var_x += (i - media_x) * (i - media_x) * p_x[i];
            var_y += (i - media_y) * (i - media_y) * p_y[i];

            // hx and hy
            hx += p_x[i] * log(p_x[i]);
            hy += p_y[i] * log(p_y[i]);

            // hxy1 and hxy2
            for (int j = 0; j < NUM_VALORES_CINZAS; j++) {
                double p_ij = cooccurrenceMatrix[i][j];
                hxy1 += p_ij * log(p_x[i] * p_y[j]);
                hxy2 += p_x[i] * p_y[j] * log(p_x[i] * p_y[j]);
            }
        }
        hx *= -1;
        hy *= -1;
        hxy1 *= -1;
        hxy2 *= -1;
    }

    private double log(double value) {
        double log = Math.log(value);
        if (log == Double.NEGATIVE_INFINITY) {
            log = 0;
        }
        return log;
    }
}
