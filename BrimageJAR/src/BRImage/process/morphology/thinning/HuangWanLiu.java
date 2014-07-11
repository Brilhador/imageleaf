/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.process.morphology.thinning;

import java.awt.Dimension;

/**
 *
 * @author ANDERSON
 */
public class HuangWanLiu {

    public static boolean[][] apply(boolean[][] img) {

        //largura e altura da imagem
        int largura = img.length;
        int altura = img[0].length;

        //matriz de retorno
        boolean[][] auxMat = new boolean[largura][altura];

        //flag auxiliar para saber quando sair do loop
        int excluir = 0;

        do {
            excluir = 0;

            //passo 1
            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {

                    //somente se for objeto
                    if (img[x][y]) {

                        //condições para excluir o pixel
                        //condição 1 ------------------------------------ 
                        //Se o pixel tem 0, 1 ou 8 vizinhos fundos ele não é sinalizado para exclusão
                        int count = 0;

                        for (int i = x - 1; i <= x + 1; i++) {
                            for (int j = y - 1; j <= y + 1; j++) {
                                if (inBounds(largura, altura, i, j)) {
                                    if (img[i][j]) {
                                        count++;
                                    }
                                } else {
                                    count++;
                                }
                            }
                        }
                        //por contar o ponto do meio
                        count--;

                        //condição 1 ------------------------------------
                        if (count < 2 || count > 7) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }

                        //condição 2 ------------------------------------
                        //Se o pixel possui dois vizinhos fundo consecutivos
                        count = 0;
                        boolean flag = false;

                        for (int i = 0; i < 8; i++) {
                            Dimension aux = getVizinho(i, largura, altura, x, y, img);

                            if (aux != null) {
                                if (img[aux.width][aux.height] == true) {
                                    count++;
                                } else {
                                    count = 0;
                                }
                            }

                            if (count == 2) {
                                flag = true;
                            }
                        }

                        //condição 2 ------------------------------------
                        if (flag) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }

                        //condição 3 ------------------------------------
                        //Se o pixel possui três vizinhos fundo consecutivos e 
                        //também pode ser sinalizado se corresponder as máscaras
                        count = 0;

                        for (int i = 0; i < 8; i++) {
                            Dimension aux = getVizinho(i, largura, altura, x, y, img);

                            if (aux != null) {
                                if (img[aux.width][aux.height] == true) {
                                    count++;
                                } else {
                                    count = 0;
                                }
                            }

                            if (count == 3) {
                                flag = true;
                            }
                           
                        }
                        
                        //condição 3 ------------------------------------
                        if (flag) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }

                        //analisando as máscaras
                        boolean[][] mask1 = {{false, true, false}, {true, true, false}, {true, false, false}};
                        boolean[][] mask2 = {{false, true, false}, {false, true, true}, {false, false, true}};
                        boolean[][] mask3 = {{true, true, false}, {false, true, true}, {false, false, false}};
                        boolean[][] mask4 = {{false, true, true}, {true, true, false}, {false, false, false}};

                        //verificando se a máscara corresponde ao pixel
                        if (compare(img, mask1, x, y)) {
                            auxMat[x][y] = true;
                            excluir++;
                        } else if (compare(img, mask2, x, y)) {
                            auxMat[x][y] = true;
                            excluir++;
                        } else if (compare(img, mask3, x, y)) {
                            auxMat[x][y] = true;
                            excluir++;
                        } else if (compare(img, mask4, x, y)) {
                            auxMat[x][y] = true;
                            excluir++;
                        }

                        //condição 4 ------------------------------------
                        //Se o pixel possui quatro vizinhos fundo consecutivos e 
                        //também pode ser sinalizado se corresponder as máscaras
                        count = 0;

                        for (int i = 0; i < 8; i++) {
                            Dimension aux = getVizinho(i, largura, altura, x, y, img);

                            if (aux != null) {
                                if (img[aux.width][aux.height] == true) {
                                    count++;
                                } else {
                                    count = 0;
                                }
                            }

                            if (count == 4) {
                                flag = true;
                            }
                           
                        }
                        
                        //condição 4 ------------------------------------
                        if (flag) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }

                        //analisando as máscaras
                        boolean[][] mask5 = {{true, true, false}, {false, true, true}, {false, false, true}};
                        boolean[][] mask6 = {{false, true, true}, {true, true, false}, {true, false, false}};

                        //verificando se a máscara corresponde ao pixel
                        if (compare(img, mask5, x, y)) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        } else if (compare(img, mask6, x, y)) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }

                        //condição 5 ------------------------------------
                        //Se o pixel possui cinco ou seis vizinhos fundo consecutivos 
                        count = 0;

                        for (int i = 0; i < 8; i++) {
                            Dimension aux = getVizinho(i, largura, altura, x, y, img);

                            if (aux != null) {
                                if (img[aux.width][aux.height] == true) {
                                    count++;
                                } else {
                                    count = 0;
                                }
                            }

                            if (count == 5) {
                                flag = true;
                            }
                           
                        }
                        
                        //condição 5 ------------------------------------
                        if (flag) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }

                        //condição 6 ------------------------------------
                        //Se o pixel possui sete vizinhos fundo consecutivos 
                        count = 0;

                        Dimension vizinho4 = getVizinho(4, largura, altura, x, y, img);

                        for (int i = 0; i < 8; i++) {
                            Dimension aux = getVizinho(i, largura, altura, x, y, img);

                            if (aux != null) {
                                if (img[aux.width][aux.height] == true) {
                                    count++;
                                } else {
                                    count = 0;
                                }
                            }

                            //condição 6 ------------------------------------
                            if (count == 7 && (img[vizinho4.width][vizinho4.height] == true)) {
                                flag = true;
                            }
                        }
                        
                        //condição 6 ------------------------------------
                        if (flag) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }
                    }
                }
            }

            //passo 2
            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {

                    if (auxMat[x][y]) {
                    //Condição 1 
                        //O pixel não é sinalidado se ele corresponder algumas das máscara abaixo
                        boolean[][] mask1 = {{false, false, false}, {true, true, true}, {true, true, true}, {false, false, false}};
                        boolean[][] mask2 = {{false, false, false}, {true, true, false}, {false, true, false}, {false, false, false}};
                        boolean[][] mask3 = {{false, false, false}, {false, true, false}, {false, true, true}, {false, false, false}};
                        boolean[][] mask4 = {{false, false, false, false}, {false, true, true, false}, {false, true, true, false}, {false, false, false, false}};
                        boolean[][] mask5 = {{false, false, false, false}, {false, true, true, false}, {false, false, true, false}};
                        boolean[][] mask6 = {{false, true, true, false}, {false, true, true, false}, {false, true, true, false}};
                        boolean[][] mask7 = {{false, false, false, false}, {false, true, true, false}, {false, true, false, false}};

                        int[] cond1 = {0, 2, 9, 11};
                        int[] cond2 = {0, 11};
                        int[] cond3 = {0, 11};
                        int[] cond5 = {0, 11};
                        int[] cond6 = {0, 3, 8, 11};
                        int[] cond7 = {3, 8};

                        //verificando se a máscara corresponde ao pixel
                        if (compare(img, mask1, x, y, cond1)) {
                            auxMat[x][y] = false;
                        } else if (compare(img, mask2, x, y, cond2)) {
                            auxMat[x][y] = false;
                        } else if (compare(img, mask3, x, y, cond3)) {
                            auxMat[x][y] = false;
                        } else if (compare(img, mask4, x, y)) {
                            auxMat[x][y] = false;
                        } else if (compare(img, mask5, x, y, cond5)) {
                            auxMat[x][y] = false;
                        } else if (compare(img, mask6, x, y, cond6)) {
                            auxMat[x][y] = false;
                        } else if (compare(img, mask7, x, y, cond7)) {
                            auxMat[x][y] = false;
                        } else {
                            img[x][y] = false;
                        }
                    }
                }
            }

        } while (excluir != 0);

        return img;
    }

    private static boolean inBounds(int largura, int altura, int x, int y) {
        return ((x >= 0 && x < largura) && (y >= 0 && y < altura));
    }

    /*
     * 567
     * 4 0
     * 321
     */
    private static Dimension getVizinho(int dir, int largura, int altura, int x, int y, boolean[][] img) {
        switch (dir) {
            case 6:
                return inBounds(largura, altura, x, y - 1) ? new Dimension(x, y - 1) : null;
            case 7:
                return inBounds(largura, altura, x + 1, y - 1) ? new Dimension(x + 1, y - 1) : null;
            case 0:
                return inBounds(largura, altura, x + 1, y) ? new Dimension(x + 1, y) : null;
            case 1:
                return inBounds(largura, altura, x + 1, y + 1) ? new Dimension(x + 1, y + 1) : null;
            case 2:
                return inBounds(largura, altura, x, y + 1) ? new Dimension(x, y + 1) : null;
            case 3:
                return inBounds(largura, altura, x - 1, y + 1) ? new Dimension(x - 1, y + 1) : null;
            case 4:
                return inBounds(largura, altura, x - 1, y) ? new Dimension(x - 1, y) : null;
            case 5:
                return inBounds(largura, altura, x - 1, y - 1) ? new Dimension(x - 1, y - 1) : null;
            default:
                return null;
        }
    }

    private static boolean compare(boolean[][] img, boolean[][] mask, int x, int y) {
        int largura = img.length;
        int altura = img[0].length;

        int lk = mask.length;
        int ak = mask[0].length;

        //
        x -= (lk / 2);
        y -= (ak / 2);

        int tam = lk * ak;

        //identificar o kernel
        int count = 0;
        for (int k = 0; k < lk; k++) {
            for (int l = 0; l < ak; l++) {
                if (inBounds(largura, altura, x + k, y + l)) {
                    if (img[x + k][y + l] == mask[k][l]) {
                        count++;
                    }
                }

            }
        }

        if (count == tam) {
            return true;
        }

        return false;
    }

    private static boolean compare(boolean[][] img, boolean[][] mask, int x, int y, int[] no) {
        int largura = img.length;
        int altura = img[0].length;

        int lk = mask.length;
        int ak = mask[0].length;

        //
        x -= (lk / 2);
        y -= (ak / 2);

        int tam = lk * ak;

        //identificar o kernel
        int count = 0;
        int inter = 0;
        for (int k = 0; k < lk; k++) {
            for (int l = 0; l < ak; l++) {
                
                boolean aux = false;

                for (int i = 0; i < no.length; i++) {
                    if (no[i] == inter) {
                        aux = true;
                        count++;
                    }
                }

                if (aux != true) {
                    if (inBounds(largura, altura, x + k, y + l)) {
                        if (img[x + k][y + l] == mask[k][l]) {
                            count++;
                        }else{
                            return false;
                        }
                    }
                }
                
                inter++;
            }
        }

        if (count == tam) {
            return true;
        }

        return false;
    }
}
