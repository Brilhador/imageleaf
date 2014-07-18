/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.process.morphology;

/**
 *
 * @author Anderson
 */
public class Pruning {

    public static boolean[][] simple(boolean[][] img, int iteration) {
        boolean[][] imgOut = img;

        int largura = img.length;
        int altura = img[0].length;

        boolean[][] maskA = {{false, false, false}, {true, true, false}, {false, false, false}};
        boolean[][] maskA90 = {{false, false, false}, {false, true, false}, {false, true, false}};
        boolean[][] maskA180 = {{false, false, false}, {false, true, true}, {false, false, false}};
        boolean[][] maskA270 = {{false, true, false}, {false, true, false}, {false, false, false}};

        boolean[][] maskB = {{true, false, false}, {false, true, false}, {false, false, false}};
        boolean[][] maskB90 = {{false, false, false}, {false, true, false}, {true, false, false}};
        boolean[][] maskB180 = {{false, false, false}, {false, true, false}, {false, false, true}};
        boolean[][] maskB270 = {{false, false, true}, {false, true, false}, {false, false, false}};

        for (int indice = 0; indice < iteration; indice++) {
            boolean[][] imgIteration = clone(imgOut);

            //perrcorrer a imagem;
            for (int i = 0; i < largura; i++) {
                for (int j = 0; j < altura; j++) {
                    if (imgIteration[i][j] == true) {
                        //maskA
                        if (!(imgOut[i][j] == erode(imgIteration, maskA, i, j))) {
                            imgOut[i][j] = false;
                            //maskA90
                        } else if (!(imgOut[i][j] == erode(imgIteration, maskA90, i, j))) {
                            imgOut[i][j] = false;
                            //maskA180
                        } else if (!(imgOut[i][j] == erode(imgIteration, maskA180, i, j))) {
                            imgOut[i][j] = false;
                            //maskA270
                        } else if (!(imgOut[i][j] == erode(imgIteration, maskA270, i, j))) {
                            imgOut[i][j] = false;
                        }
                        //------------------------------------------------------
                        //maskA
                        if (!(imgOut[i][j] == erode(imgIteration, maskB, i, j))) {
                            imgOut[i][j] = false;
                            //maskA90
                        } else if (!(imgOut[i][j] == erode(imgIteration, maskB90, i, j))) {
                            imgOut[i][j] = false;
                            //maskA180
                        } else if (!(imgOut[i][j] == erode(imgIteration, maskB180, i, j))) {
                            imgOut[i][j] = false;
                            //maskA270
                        } else if (!(imgOut[i][j] == erode(imgIteration, maskB270, i, j))) {
                            imgOut[i][j] = false;
                        }
                    }
                }
            }
        }

        return imgOut;

    }

    //erosão
    private static boolean erode(boolean[][] img, boolean[][] kernel, int i, int j) {
        int largura = i - (int) (kernel.length / 2);
        int altura = j - (int) (kernel[0].length / 2);

        for (int k = 0; k < kernel.length; k++) {
            for (int l = 0; l < kernel[0].length; l++) {
                if (kernel[k][l] == true) {
                    if (inBounds(img, largura + k, altura + l)) {
                        if (img[largura + k][altura + l] == false) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;

    }

    private static boolean inBounds(boolean[][] img, int i, int j) {
        return ((i > 0 && i < img.length) && (j > 0 && j < img[0].length));
    }

    private static boolean[][] clone(boolean[][] img) {
        boolean[][] imgOut = new boolean[img.length][img[0].length];

        for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[0].length; j++) {
                imgOut[i][j] = img[i][j];
            }
        }

        return imgOut;
    }
}
