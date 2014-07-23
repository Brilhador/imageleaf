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
                        if ((imgIteration[i - 1][j] == maskA[0][1])
                                && (imgIteration[i - 1][j + 1] == maskA[0][2])
                                && (imgIteration[i][j - 1] == maskA[1][0])
                                && (imgIteration[i][j] == maskA[1][1])
                                && (imgIteration[i][j + 1] == maskA[1][2])
                                && (imgIteration[i + 1][j] == maskA[2][1])
                                && (imgIteration[i + 1][j + 1] == maskA[2][2])) {
                            imgOut[i][j] = false;
                        }
                        //maskA90
                        if ((imgIteration[i - 1][j - 1] == maskA90[0][0])
                                && (imgIteration[i - 1][j] == maskA90[0][1])
                                && (imgIteration[i - 1][j + 1] == maskA90[0][2])
                                && (imgIteration[i][j - 1] == maskA90[1][0])
                                && (imgIteration[i][j] == maskA90[1][1])
                                && (imgIteration[i][j + 1] == maskA90[1][2])
                                && (imgIteration[i + 1][j] == maskA90[2][1])) {
                            imgOut[i][j] = false;
                        }
                        //maskA180
                        if ((imgIteration[i - 1][j - 1] == maskA180[0][0])
                                && (imgIteration[i - 1][j] == maskA180[0][1])
                                && (imgIteration[i][j - 1] == maskA180[1][0])
                                && (imgIteration[i][j] == maskA180[1][1])
                                && (imgIteration[i][j + 1] == maskA180[1][2])
                                && (imgIteration[i + 1][j - 1] == maskA180[2][0])
                                && (imgIteration[i + 1][j] == maskA180[2][1])) {
                            imgOut[i][j] = false;
                        }
                        //maskA270
                        if ((imgIteration[i - 1][j] == maskA270[0][1])
                                && (imgIteration[i][j - 1] == maskA270[1][0])
                                && (imgIteration[i][j] == maskA270[1][1])
                                && (imgIteration[i][j + 1] == maskA270[1][2])
                                && (imgIteration[i + 1][j - 1] == maskA270[2][0])
                                && (imgIteration[i + 1][j] == maskA270[2][1])
                                && (imgIteration[i + 1][j + 1] == maskA270[2][2])) {
                            imgOut[i][j] = false;
                        }

                        //------------------------------------------------------
                        //maskB
                        if ((imgOut[i][j] == erode(imgIteration, maskB, i, j))) {
                            imgOut[i][j] = false;
                        }
                        //maskB90
                        if ((imgOut[i][j] == erode(imgIteration, maskB90, i, j))) {
                            imgOut[i][j] = false;
                        }
                        //maskB180
                        if ((imgOut[i][j] == erode(imgIteration, maskB180, i, j))) {
                            imgOut[i][j] = false;
                        }
                        //maskB270
                        if ((imgOut[i][j] == erode(imgIteration, maskB270, i, j))) {
                            imgOut[i][j] = false;
                        }

                    }
                }
            }
        }

        //perrcorrer a imagem para encontrar os pontos finais;
        boolean[][] endPoint = new boolean[largura][altura];

        int amount = 0;

        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                amount = 0;
                if (imgOut[i][j] == true) {
                    if ((imgOut[i - 1][j] == maskA[0][1])
                            && (imgOut[i - 1][j + 1] == maskA[0][2])
                            && (imgOut[i][j - 1] == maskA[1][0])
                            && (imgOut[i][j] == maskA[1][1])
                            && (imgOut[i][j + 1] == maskA[1][2])
                            && (imgOut[i + 1][j] == maskA[2][1])
                            && (imgOut[i + 1][j + 1] == maskA[2][2])) {
                        amount++;
                    }
                    //maskA90
                    if ((imgOut[i - 1][j - 1] == maskA90[0][0])
                            && (imgOut[i - 1][j] == maskA90[0][1])
                            && (imgOut[i - 1][j + 1] == maskA90[0][2])
                            && (imgOut[i][j - 1] == maskA90[1][0])
                            && (imgOut[i][j] == maskA90[1][1])
                            && (imgOut[i][j + 1] == maskA90[1][2])
                            && (imgOut[i + 1][j] == maskA90[2][1])) {
                        amount++;
                    }
                    //maskA180
                    if ((imgOut[i - 1][j - 1] == maskA180[0][0])
                            && (imgOut[i - 1][j] == maskA180[0][1])
                            && (imgOut[i][j - 1] == maskA180[1][0])
                            && (imgOut[i][j] == maskA180[1][1])
                            && (imgOut[i][j + 1] == maskA180[1][2])
                            && (imgOut[i + 1][j - 1] == maskA180[2][0])
                            && (imgOut[i + 1][j] == maskA180[2][1])) {
                        amount++;
                    }
                    //maskA270
                    if ((imgOut[i - 1][j] == maskA270[0][1])
                            && (imgOut[i][j - 1] == maskA270[1][0])
                            && (imgOut[i][j] == maskA270[1][1])
                            && (imgOut[i][j + 1] == maskA270[1][2])
                            && (imgOut[i + 1][j - 1] == maskA270[2][0])
                            && (imgOut[i + 1][j] == maskA270[2][1])
                            && (imgOut[i + 1][j + 1] == maskA270[2][2])) {
                        amount++;
                    }

                    //------------------------------------------------------
                    //maskB
                    if ((imgOut[i][j] == erode(imgOut, maskB, i, j))) {
                        amount++;
                    }
                    //maskB90
                    if ((imgOut[i][j] == erode(imgOut, maskB90, i, j))) {
                        amount++;
                    }
                    //maskB180
                    if ((imgOut[i][j] == erode(imgOut, maskB180, i, j))) {
                        amount++;
                    }
                    //maskB270
                    if ((imgOut[i][j] == erode(imgOut, maskB270, i, j))) {
                        amount++;
                    }

                    if (amount == 1) {
                        endPoint[i][j] = true;
                    }
                }
            }
        }

        //dilatação utilizando a matriz de pontos finais como delimitador
        for (int indice = 0; indice < iteration; indice++) {
            for (int i = 0; i < largura; i++) {
                for (int j = 0; j < altura; j++) {
                    if (endPoint[i][j] == true) {
                        //maskA
                        dilApply(img, endPoint, i, j);
                    }
                }
            }
        }

//        //reconstruindo o elementos
//        for (int i = 0; i < largura; i++) {
//            for (int j = 0; j < altura; j++) {
//                if (endPoint[i][j] == true) {
//                    imgOut[i][j] = true;
//                }
//            }
//        }
        
        return endPoint;
    }

    //erosão
    private static boolean erode(boolean[][] img, boolean[][] kernel, int i, int j) {
        int largura = i - (int) (kernel.length / 2);
        int altura = j - (int) (kernel[0].length / 2);

        for (int k = 0; k < kernel.length; k++) {
            for (int l = 0; l < kernel[0].length; l++) {
                if (inBounds(img, largura + k, altura + l)) {
                    if (img[largura + k][altura + l] != kernel[k][l]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static void dilApply(boolean[][] imgOriginal, boolean[][] imgOut, int i, int j) {

        int largura = i - 1;
        int altura = j - 1;

        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                //usando a imagem original para reconstruir os elementos
                if (inBounds(imgOriginal, largura + k, altura + l) && imgOriginal[largura + k][altura + l] == true) {
                    imgOut[largura + k][altura + l] = true;
                }
            }
        }
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
