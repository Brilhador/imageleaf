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
                        if (count >= 2 && count <= 7) {
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

                        Dimension aux = getVizinho(0, largura, altura, x, y, img);

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
                            aux = getVizinho(i, largura, altura, x, y, img);

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

                        aux = getVizinho(0, largura, altura, x, y, img);

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
                        //máscara 1
                        flag = true;

                        int maskAltura = mask1.length;
                        int maskLargura = mask1[0].length;

                        for (int i = 0; i < 8; i++) {
                            if (!getVizinhoBool(i, largura, altura, x, y, img) == getVizinhoBool(i, maskLargura, maskAltura, x, y, mask1)) {
                                flag = false;
                                break;
                            }
                        }

                        if (flag) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }

                        //máscara 2
                        flag = true;

                        maskAltura = mask2.length;
                        maskLargura = mask2[0].length;

                        for (int i = 0; i < 8; i++) {
                            if (!getVizinhoBool(i, largura, altura, x, y, img) == getVizinhoBool(i, maskLargura, maskAltura, x, y, mask2)) {
                                flag = false;
                                break;
                            }
                        }

                        if (flag) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }

                        //máscara 3
                        flag = true;

                        maskAltura = mask3.length;
                        maskLargura = mask3[0].length;

                        for (int i = 0; i < 8; i++) {
                            if (!getVizinhoBool(i, largura, altura, x, y, img) == getVizinhoBool(i, maskLargura, maskAltura, x, y, mask3)) {
                                flag = false;
                                break;
                            }
                        }

                        if (flag) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }

                        //máscara 4
                        flag = true;

                        maskAltura = mask4.length;
                        maskLargura = mask4[0].length;

                        for (int i = 0; i < 8; i++) {
                            if (!getVizinhoBool(i, largura, altura, x, y, img) == getVizinhoBool(i, maskLargura, maskAltura, x, y, mask4)) {
                                flag = false;
                                break;
                            }
                        }

                        if (flag) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }

                        //condição 4 ------------------------------------
                        //Se o pixel possui quatro vizinhos fundo consecutivos e 
                        //também pode ser sinalizado se corresponder as máscaras
                        count = 0;

                        for (int i = 0; i < 8; i++) {
                            aux = getVizinho(i, largura, altura, x, y, img);

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

                        aux = getVizinho(0, largura, altura, x, y, img);

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
                        //máscara 5
                        flag = true;

                        maskAltura = mask5.length;
                        maskLargura = mask5[0].length;

                        for (int i = 0; i < 8; i++) {
                            if (!getVizinhoBool(i, largura, altura, x, y, img) == getVizinhoBool(i, maskLargura, maskAltura, x, y, mask5)) {
                                flag = false;
                                break;
                            }
                        }

                        if (flag) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }

                        //máscara 6
                        flag = true;

                        maskAltura = mask6.length;
                        maskLargura = mask6[0].length;

                        for (int i = 0; i < 8; i++) {
                            if (!getVizinhoBool(i, largura, altura, x, y, img) == getVizinhoBool(i, maskLargura, maskAltura, x, y, mask6)) {
                                flag = false;
                                break;
                            }
                        }

                        if (flag) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }

                        //condição 5 ------------------------------------
                        //Se o pixel possui cinco ou seis vizinhos fundo consecutivos 
                        count = 0;

                        for (int i = 0; i < 8; i++) {
                            aux = getVizinho(i, largura, altura, x, y, img);

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

                        aux = getVizinho(0, largura, altura, x, y, img);

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
                            aux = getVizinho(i, largura, altura, x, y, img);

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

                        aux = getVizinho(0, largura, altura, x, y, img);

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

                        //condição 6 ------------------------------------
                        if (flag) {
                            auxMat[x][y] = true;
                            excluir++;
                            break;
                        }
                    }
                }
            }

            //O pixel não é sinalidado se ele corresponder algumas das máscara abaixo
            boolean[][] mask1 = {{false, false, false}, {true, true, true}, {true, true, true}, {false, false, false}};
            boolean[][] mask2 = {{false, false, false}, {true, true, false}, {false, true, false}, {false, false, false}};
            boolean[][] mask3 = {{false, false, false}, {false, true, false}, {false, true, true}, {false, false, false}};
            boolean[][] mask4 = {{false, false, false, false}, {false, true, true, false}, {false, true, true, false}, {false, false, false, false}};
            boolean[][] mask5 = {{false, false, false, false}, {false, true, true, false}, {false, false, true, false}};
            boolean[][] mask6 = {{false, true, true, false}, {false, true, true, false}, {false, true, true, false}};
            boolean[][] mask7 = {{false, false, false, false}, {false, true, true, false}, {false, true, false, false}};

            //passo 2
            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {

                    if (auxMat[x][y]) {

                        //máscara1 
                        boolean flag = false;

                        try {
                            if ((img[x][y - 1] == mask1[1][1 - 1])
                                    && (img[x - 1][y] == mask1[1 - 1][1])
                                    && (img[x + 1][y] == mask1[1 + 1][1])
                                    && (img[x - 1][y + 1] == mask1[1 - 1][1 + 1])
                                    && (img[x][y + 1] == mask1[1][1 + 1])
                                    && (img[x + 1][y + 1] == mask1[1 + 1][1 + 1])
                                    && (img[x][y + 2] == mask1[1][1 + 2])) {
                                flag = true;
                            }
                        } catch (Exception e) {
                            flag = false;
                        }

                        //máscara 2
                        if (!flag) {
                            try {
                                if ((img[x][y - 2] == mask2[1][2 - 2])
                                        && (img[x + 1][y - 2] == mask2[1 + 1][2 - 2])
                                        && (img[x - 1][y - 1] == mask2[1 - 1][2 - 1])
                                        && (img[x][y - 1] == mask2[1][2 - 1])
                                        && (img[x + 1][y - 1] == mask2[1 + 1][2 - 1])
                                        && (img[x - 1][y] == mask2[1 - 1][2])
                                        && (img[x + 1][y] == mask2[1 + 1][2])
                                        && (img[x - 1][y + 1] == mask2[1 - 1][2 + 1])
                                        && (img[x][y + 1] == mask2[1][2 + 1])) {
                                    flag = true;
                                }
                            } catch (Exception e) {
                                flag = false;
                            }
                        }

                        //máscara 3
                        if (!flag) {
                            try {
                                if ((img[x][y - 1] == mask3[1][1 - 1])
                                        && (img[x - 1][y] == mask3[1 - 1][1])
                                        && (img[x + 1][y] == mask3[1 + 1][1])
                                        && (img[x - 1][y + 1] == mask3[1 - 1][1 + 1])
                                        && (img[x][y + 1] == mask3[1][1 + 1])
                                        && (img[x + 1][y + 1] == mask3[1 + 1][1 + 1])
                                        && (img[x][y + 2] == mask3[1][1 + 2])
                                        && (img[x + 1][y - 1] == mask3[1 + 1][1 - 1])
                                        && (img[x - 1][y + 2] == mask3[1 - 1][1 + 2])) {
                                    flag = true;
                                }
                            } catch (Exception e) {
                                flag = false;
                            }
                        }

//                        //máscara 4
                        if (!flag) {
                            try {
                                if ((img[x - 1][y - 1] == mask4[1 - 1][1 - 1])
                                        && (img[x][y - 1] == mask4[1][1 - 1])
                                        && (img[x + 1][y - 1] == mask4[1 + 1][1 - 1])
                                        && (img[x + 2][y - 1] == mask4[1 + 2][1 - 1])
                                        && (img[x - 1][y] == mask4[x - 1][1])
                                        && (img[x + 1][y] == mask4[x + 1][1])
                                        && (img[x + 2][y] == mask4[x + 2][1])
                                        && (img[x - 1][y + 1] == mask4[x - 1][1 + 1])
                                        && (img[x][y + 1] == mask4[x][1 + 1])
                                        && (img[x + 1][y + 1] == mask4[x + 1][1 + 1])
                                        && (img[x + 2][y + 1] == mask4[x + 2][1 + 1])
                                        && (img[x - 1][y + 2] == mask4[x - 1][1 + 2])
                                        && (img[x][y + 2] == mask4[x][1 + 2])
                                        && (img[x + 1][y + 2] == mask4[x + 1][1 + 2])
                                        && (img[x + 2][y + 2] == mask4[x + 2][1 + 2])) {
                                    flag = true;
                                }
                            } catch (Exception e) {
                                flag = false;
                            }
                        }

                        //máscara 5
                        if (!flag) {
                            try {
                                if ((img[x][y - 1] == mask5[1][1 - 1])
                                        && (img[x + 1][y - 1] == mask5[1 + 1][1 - 1])
                                        && (img[x + 2][y - 1] == mask5[1 + 2][1 - 1])
                                        && (img[x - 1][y] == mask5[1 - 1][1])
                                        && (img[x + 1][y] == mask5[1 + 1][1])
                                        && (img[x + 2][y] == mask5[1 + 2][1])
                                        && (img[x - 1][y + 1] == mask5[1 - 1][1 + 1])
                                        && (img[x][y + 1] == mask5[1][1 + 1])
                                        && (img[x + 1][y + 1] == mask5[1 + 1][1 + 1])) {
                                    flag = true;
                                }
                            } catch (Exception e) {
                                flag = false;
                            }
                        }

                        //máscara 6
                        if (!flag) {
                            try {
                                if ((img[x][y - 1] == mask6[1][1 - 1])
                                        && (img[x + 1][y - 1] == mask6[1 + 1][1 - 1])
                                        && (img[x - 1][y] == mask6[1 - 1][1])
                                        && (img[x + 1][y] == mask6[1 + 1][1])
                                        && (img[x + 2][y] == mask6[1 + 2][1])
                                        && (img[x][y + 1] == mask6[1][1 + 1])
                                        && (img[x + 1][y + 1] == mask6[1 + 1][1 + 1])) {
                                    flag = true;
                                }
                            } catch (Exception e) {
                                flag = false;
                            }
                        }

                        //máscara 7
                        if (!flag) {
                            try {
                                if ((img[x - 2][y - 1] == mask7[2 - 2][1])
                                        && (img[x - 2][y - 1] == mask7[2 - 2][1])
                                        && (img[x - 1][y - 1] == mask7[2 - 1][1 - 1])
                                        && (img[x - 1][y] == mask7[2 - 1][1])
                                        && (img[x - 1][y + 1] == mask7[2 - 1][1 + 1])
                                        && (img[x][y - 1] == mask7[2][1 - 1])
                                        && (img[x][y + 1] == mask7[2][1 + 1])
                                        && (img[x + 1][y] == mask7[2 + 1][1])
                                        && (img[x + 1][y + 1] == mask7[2 + 1][1 + 1])) {
                                    flag = true;
                                }
                            } catch (Exception e) {
                                flag = false;
                            }
                        }

                        //verifica se a flag foi alterada para exclusão
                        if (flag) {
                            auxMat[x][y] = false;
                            excluir--;
                        }
                    }
                }
            }

            //exclusão
            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {
                    if (auxMat[x][y]) {
                        img[x][y] = false;
                    }
                }
            }

            System.out.println("finalizou um loop excluir=" + excluir);
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

    private static boolean getVizinhoBool(int dir, int largura, int altura, int x, int y, boolean[][] img) {
        switch (dir) {
            case 6:
                return inBounds(largura, altura, x, y - 1) ? img[x][y - 1] : false;
            case 7:
                return inBounds(largura, altura, x + 1, y - 1) ? img[x + 1][y - 1] : false;
            case 0:
                return inBounds(largura, altura, x + 1, y) ? img[x + 1][y] : false;
            case 1:
                return inBounds(largura, altura, x + 1, y + 1) ? img[x + 1][y + 1] : false;
            case 2:
                return inBounds(largura, altura, x, y + 1) ? img[x][y + 1] : false;
            case 3:
                return inBounds(largura, altura, x - 1, y + 1) ? img[x - 1][y + 1] : false;
            case 4:
                return inBounds(largura, altura, x - 1, y) ? img[x - 1][y] : false;
            case 5:
                return inBounds(largura, altura, x - 1, y - 1) ? img[x - 1][y - 1] : false;
            default:
                return false;
        }
    }
}
