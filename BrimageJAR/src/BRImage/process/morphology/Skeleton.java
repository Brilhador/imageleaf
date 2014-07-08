/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.process.morphology;

import java.awt.Dimension;

/**
 *
 * @author ANDERSON
 */
public class Skeleton {

    //Afinamento do disponivel no livro gonzalez woods pag. 535
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

                        //condição A -----------------------------------------------
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

                        //condição A violada 
                        if (count <= 2 || count >= 6) {
                            continue;
                        }

                        //Condição B -----------------------------------------------
                        count = 0;

                        for (int i = 2; i <= 8; i++) {
                            Dimension a = getVizinho(i, largura, altura, x, y, img);
                            Dimension b = getVizinho(i + 1, largura, altura, x, y, img);

                            if (a != null && b != null) {
                                if ((img[a.width][a.height] == false) && (img[b.width][b.height] == true)) {
                                    count++;
                                }
                            }
                        }

                        Dimension a = getVizinho(9, largura, altura, x, y, img);
                        Dimension b = getVizinho(2, largura, altura, x, y, img);

                        if (a != null && b != null) {
                            if ((img[a.width][a.height] == false) && (img[b.width][b.height] == true)) {
                                count++;
                            }
                        }

                        //condição B violada
                        if (count != 1) {
                            continue;
                        }

                        //condição C -----------------------------------------------
                        Dimension c = null;

                        a = getVizinho(2, largura, altura, x, y, img);
                        b = getVizinho(4, largura, altura, x, y, img);
                        c = getVizinho(6, largura, altura, x, y, img);

                        //condição C violada
                        if (a != null && b != null && c != null) {
                            if ((img[a.width][a.height] == true) && (img[b.width][b.height] == true) && (img[c.width][c.height] == true)) {
                                continue;
                            }
                        }

                        //condição D -----------------------------------------------
                        a = getVizinho(4, largura, altura, x, y, img);
                        b = getVizinho(6, largura, altura, x, y, img);
                        c = getVizinho(8, largura, altura, x, y, img);

                        //condição D violada
                        if (a != null && b != null && c != null) {
                            if ((img[a.width][a.height] == true) && (img[b.width][b.height] == true) && (img[c.width][c.height] == true)) {
                                continue;
                            }
                        }

                        //condições sastifeitas ------------------------------------
                        auxMat[x][y] = true;
                        excluir++;
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
            
            //redefinir a matriz auxiliar
            auxMat = new boolean[largura][altura];
            
            //passo 2
            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {
                    //somente se for objeto
                    if (img[x][y]) {

                    //condição A -----------------------------------------------
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

                        //condição A violada 
                        if (count <= 2 || count >= 6) {
                            continue;
                        }

                        //Condição B -----------------------------------------------
                        count = 0;

                        for (int i = 2; i <= 8; i++) {
                            Dimension a = getVizinho(i, largura, altura, x, y, img);
                            Dimension b = getVizinho(i + 1, largura, altura, x, y, img);

                            if (a != null && b != null) {
                                if ((img[a.width][a.height] == false) && (img[b.width][b.height] == true)) {
                                    count++;
                                }
                            }
                        }

                        Dimension a = getVizinho(9, largura, altura, x, y, img);
                        Dimension b = getVizinho(2 + 1, largura, altura, x, y, img);

                        if (a != null && b != null) {
                            if ((img[a.width][a.height] == false) && (img[b.width][b.height] == true)) {
                                count++;
                            }
                        }

                        //condição B violada
                        if (count != 1) {
                            continue;
                        }

                        //condição C -----------------------------------------------
                        Dimension c = null;

                        a = getVizinho(2, largura, altura, x, y, img);
                        b = getVizinho(4, largura, altura, x, y, img);
                        c = getVizinho(8, largura, altura, x, y, img);

                        //condição C violada
                        if (a != null && b != null && c != null) {
                            if ((img[a.width][a.height] == true) && (img[b.width][b.height] == true) && (img[c.width][c.height] == true)) {
                                continue;
                            }
                        }

                        //condição D -----------------------------------------------
                        a = getVizinho(2, largura, altura, x, y, img);
                        b = getVizinho(6, largura, altura, x, y, img);
                        c = getVizinho(8, largura, altura, x, y, img);

                        //condição D violada
                        if (a != null && b != null && c != null) {
                            if ((img[a.width][a.height] == true) && (img[b.width][b.height] == true) && (img[c.width][c.height] == true)) {
                                continue;
                            }
                        }

                        //condições sastifeitas ------------------------------------
                        auxMat[x][y] = true;
                        excluir++;
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

        } while (excluir != 0);

        return img;
    }

    private static boolean inBounds(int largura, int altura, int x, int y) {
        return ((x >= 0 && x < largura) && (y >= 0 && y < altura));
    }

    /*
     * 923
     * 8 4
     * 765
     */
    private static Dimension getVizinho(int dir, int largura, int altura, int x, int y, boolean[][] img) {
        switch (dir) {
            case 2:
                return inBounds(largura, altura, x, y - 1) ? new Dimension(x, y - 1) : null;
            case 3:
                return inBounds(largura, altura, x + 1, y - 1) ? new Dimension(x + 1, y - 1) : null;
            case 4:
                return inBounds(largura, altura, x + 1, y) ? new Dimension(x + 1, y) : null;
            case 5:
                return inBounds(largura, altura, x + 1, y + 1) ? new Dimension(x + 1, y + 1) : null;
            case 6:
                return inBounds(largura, altura, x, y + 1) ? new Dimension(x, y + 1) : null;
            case 7:
                return inBounds(largura, altura, x - 1, y + 1) ? new Dimension(x - 1, y + 1) : null;
            case 8:
                return inBounds(largura, altura, x - 1, y) ? new Dimension(x - 1, y) : null;
            case 9:
                return inBounds(largura, altura, x - 1, y - 1) ? new Dimension(x - 1, y - 1) : null;
            default:
                return null;
        }
    }

}
