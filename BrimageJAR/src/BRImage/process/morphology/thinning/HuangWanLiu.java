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

        //flag auxiliar para saber quando sair do loop
        int excluir = 0;

        do {
            excluir = 0;

            //matriz de retorno
            boolean[][] auxMat = new boolean[largura][altura];

            //passo 1
            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {

//                    System.out.println("x: " + x + " y:" + y);
                    //somente se for objeto
                    if (img[x][y]) {

//                        System.out.println("------------------------------------\n\n\n");
//                        for (int i = 0; i < img.length; i++) {
//                            for (int j = 0; j < img[0].length; j++) {
//                                System.out.print(img[i][j] + " ");
//                            }
//                            System.out.println("");
//                        }

//                        System.out.println("true - x: " + x + " y:" + y);
                        //template do pixel 
                        boolean[][] pixel = new boolean[3][3];

                        //variaveis auxiliares no processo
                        int count = 0;
                        boolean flag = false;

                        //Contagem do número de vizinhos
                        int amount = 0;

                        /*
                         * 567
                         * 4 0
                         * 321
                         */
                        pixel[0][0] = getVizinhoBool(5, largura, altura, x, y, img);
                        pixel[0][1] = getVizinhoBool(6, largura, altura, x, y, img);
                        pixel[0][2] = getVizinhoBool(7, largura, altura, x, y, img);
                        pixel[1][0] = getVizinhoBool(4, largura, altura, x, y, img);
                        pixel[1][1] = true;
                        pixel[1][2] = getVizinhoBool(0, largura, altura, x, y, img);
                        pixel[2][0] = getVizinhoBool(3, largura, altura, x, y, img);
                        pixel[2][1] = getVizinhoBool(2, largura, altura, x, y, img);
                        pixel[2][2] = getVizinhoBool(1, largura, altura, x, y, img);

                        for (int i = 0; i < 8; i++) {
//                            System.out.println(getVizinhoBool(i, largura, altura, x, y, img) + ",");
                            if (getVizinhoBool(i, largura, altura, x, y, img)) {
                                amount++;
                            }
                        }

//                        System.out.println("quant: " + amount);
                        //condições para excluir o pixel
                        //condição 1 ------------------------------------ 
                        //Se o pixel tem 0, 1 ou 8 vizinhos fundos ele não é sinalizado para exclusão
                        if (amount < 2 && amount > 7) {
                            continue;
                        }

                        //condição 2 ------------------------------------
                        //Se o pixel possui dois vizinhos fundo consecutivos
                        if (amount == 2) {
                            // {t5, t6, t7} , {t4 , P , t0}, {t3, t2, t1}
                            boolean[][] mask1 = {{false, true, true}, {false, true, false}, {false, false, false}};
                            boolean[][] mask2 = {{false, false, true}, {false, true, true}, {false, false, false}};
                            boolean[][] mask3 = {{false, false, false}, {false, true, true}, {false, false, true}};
                            boolean[][] mask4 = {{false, false, false}, {false, true, false}, {false, true, true}};
                            boolean[][] mask5 = {{false, false, false}, {false, true, false}, {true, true, false}};
                            boolean[][] mask6 = {{false, false, false}, {true, true, false}, {true, false, false}};
                            boolean[][] mask7 = {{true, false, false}, {true, true, false}, {false, false, false}};
                            boolean[][] mask8 = {{true, true, false}, {false, true, false}, {false, false, false}};

                            //variavél auxiliar
                            flag = true;

                             //máscara 1
                            for (int i = 0; i < 8; i++) {
                                if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask1))) {
                                    flag = false;
                                    break;
                                }
                            }

                            //mascara 2
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask2))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 3
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask3))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 4
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask4))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 5
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask5))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 6
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask6))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 7
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask7))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 8
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask8))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            if (flag) {
                                auxMat[x][y] = true;
                                excluir++;
                            }

                        } else if (amount == 3) {
                            //condição 3 ------------------------------------
                            //Se o pixel possui três vizinhos fundo consecutivos e 
                            //também pode ser sinalizado se corresponder as máscaras
                            // {t5, t6, t7} , {t4 , P , t0}, {t3, t2, t1}
                            boolean[][] mask1 = {{true, true, true}, {false, true, false}, {false, false, false}};
                            boolean[][] mask2 = {{false, true, true}, {false, true, true}, {false, false, false}};
                            boolean[][] mask3 = {{false, false, true}, {false, true, true}, {false, false, true}};
                            boolean[][] mask4 = {{false, false, false}, {false, true, true}, {false, true, true}};
                            boolean[][] mask5 = {{false, false, false}, {false, true, false}, {true, true, true}};
                            boolean[][] mask6 = {{false, false, false}, {true, true, false}, {true, true, false}};
                            boolean[][] mask7 = {{true, false, false}, {true, true, false}, {true, false, false}};
                            boolean[][] mask8 = {{true, true, false}, {true, true, false}, {false, false, false}};
                            //excessões
                            boolean[][] mask9 = {{false, true, false}, {true, true, false}, {true, false, false}};
                            boolean[][] mask10 = {{false, true, false}, {false, true, true}, {false, false, true}};
                            boolean[][] mask11 = {{true, true, false}, {false, true, true}, {false, false, false}};
                            boolean[][] mask12 = {{false, true, true}, {true, true, false}, {false, false, false}};

                            //verificando se a máscara corresponde ao pixel
                            //variavél auxiliar
                            flag = true;

                            //máscara 1
                            for (int i = 0; i < 8; i++) {
                                if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask1))) {
                                    flag = false;
                                    break;
                                }
                            }

                            //mascara 2
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask2))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 3
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask3))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 4
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask4))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 5
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask5))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 6
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask6))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 7
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask7))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 8
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask8))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 9
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask9))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 10
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask10))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 11
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask11))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 12
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask12))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            if (flag) {
                                auxMat[x][y] = true;
                                excluir++;
                            }

                        } else if (amount == 4) {

                            //condição 4 ------------------------------------
                            //Se o pixel possui quatro vizinhos fundo consecutivos e 
                            //também pode ser sinalizado se corresponder as máscaras
                            // {t5, t6, t7} , {t4 , P , t0}, {t3, t2, t1}
                            boolean[][] mask1 = {{true, true, true}, {false, true, true}, {false, false, false}};
                            boolean[][] mask2 = {{false, true, true}, {false, true, true}, {false, false, true}};
                            boolean[][] mask3 = {{false, false, true}, {false, true, true}, {false, true, true}};
                            boolean[][] mask4 = {{false, false, false}, {false, true, true}, {true, true, true}};
                            boolean[][] mask5 = {{false, false, false}, {true, true, false}, {true, true, true}};
                            boolean[][] mask6 = {{true, false, false}, {true, true, false}, {true, true, false}};
                            boolean[][] mask7 = {{true, true, false}, {true, true, false}, {true, false, false}};
                            boolean[][] mask8 = {{true, true, true}, {true, true, false}, {false, false, false}};
                            //analisando as máscaras
                            boolean[][] mask9 = {{true, true, false}, {false, true, true}, {false, false, true}};
                            boolean[][] mask10 = {{false, true, true}, {true, true, false}, {true, false, false}};

                            //verificando se a máscara corresponde ao pixel
                            //variavél auxiliar
                            flag = true;

                            //máscara 1
                            for (int i = 0; i < 8; i++) {
//                                boolean a = getVizinhoBool(i, 3, 3, 1, 1, pixel);
//                                boolean b = getVizinhoBool(i, 3, 3, 1, 1, mask1);
//                                System.out.println("p: " + a + "m: " + b);
                                if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask1))) {
//                                    System.out.println("são diferentes");
                                    flag = false;
                                    break;
                                }
                            }

                            //mascara 2
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask2))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 3
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask3))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 4
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask4))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 5
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask5))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 6
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask6))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 7
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask7))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 8
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask8))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 9
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask9))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 10
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask10))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            if (flag) {
                                auxMat[x][y] = true;
                                excluir++;
                            }

                        } else if (amount == 5) {
//                          condição 5 ------------------------------------
                            //Se o pixel possui cinco vizinhos fundo consecutivos
                            // {t5, t6, t7} , {t4 , P , t0}, {t3, t2, t1}
                            boolean[][] mask1 = {{true, true, true}, {false, true, true}, {false, false, true}};
                            boolean[][] mask2 = {{false, true, true}, {false, true, true}, {false, true, true}};
                            boolean[][] mask3 = {{false, false, true}, {false, true, true}, {true, true, true}};
                            boolean[][] mask4 = {{false, false, false}, {true, true, true}, {true, true, true}};
                            boolean[][] mask5 = {{true, false, false}, {true, true, false}, {true, true, true}};
                            boolean[][] mask6 = {{true, true, false}, {true, true, false}, {true, true, false}};
                            boolean[][] mask7 = {{true, true, true}, {true, true, false}, {true, false, false}};
                            boolean[][] mask8 = {{true, true, true}, {true, true, true}, {false, false, false}};

                            //verificando se a máscara corresponde ao pixel
                            //variavél auxiliar
                            flag = true;

                            //máscara 1
                            for (int i = 0; i < 8; i++) {
//                                boolean a = getVizinhoBool(i, 3, 3, 1, 1, pixel);
//                                boolean b = getVizinhoBool(i, 3, 3, 1, 1, mask1);
//                                System.out.println("p: " + a + "m: " + b);
                                if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask1))) {
//                                    System.out.println("são diferentes");
                                    flag = false;
                                    break;
                                }
                            }

                            //mascara 2
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask2))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 3
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask3))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 4
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask4))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 5
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask5))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 6
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask6))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 7
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask7))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 8
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask8))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            if (flag) {
                                auxMat[x][y] = true;
                                excluir++;
                            }

                        } else if (amount == 6) {
                            //condição 6 ------------------------------------
                            //Se o pixel possui seis vizinhos fundo consecutivos
                            // {t5, t6, t7} , {t4 , P , t0}, {t3, t2, t1}
                            boolean[][] mask1 = {{true, true, true}, {false, true, true}, {false, true, true}};
                            boolean[][] mask2 = {{false, true, true}, {false, true, true}, {true, true, true}};
                            boolean[][] mask3 = {{false, false, true}, {true, true, true}, {true, true, true}};
                            boolean[][] mask4 = {{true, false, false}, {true, true, true}, {true, true, true}};
                            boolean[][] mask5 = {{true, true, false}, {true, true, false}, {true, true, true}};
                            boolean[][] mask6 = {{true, true, true}, {true, true, false}, {true, true, false}};
                            boolean[][] mask7 = {{true, true, true}, {true, true, true}, {true, false, false}};
                            boolean[][] mask8 = {{true, true, true}, {true, true, true}, {false, false, true}};

                            //verificando se a máscara corresponde ao pixel
                            //variavél auxiliar
                            flag = true;

                            //máscara 1
                            for (int i = 0; i < 8; i++) {
                                if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask1))) {
                                    flag = false;
                                    break;
                                }
                            }

                            //mascara 2
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask2))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 3
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask3))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 4
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask4))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 5
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask5))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 6
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask6))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 7
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask7))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 8
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask8))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            if (flag) {
                                auxMat[x][y] = true;
                                excluir++;
                            }

                        } else if (amount == 7) {
                            // {t5, t6, t7} , {t4 , P , t0}, {t3, t2, t1}
                            boolean[][] mask1 = {{true, true, true}, {true, true, true}, {true, false, true}};
                            boolean[][] mask2 = {{true, true, true}, {false, true, true}, {true, true, true}};
                            boolean[][] mask3 = {{true, false, true}, {true, true, true}, {true, true, true}};
                            boolean[][] mask4 = {{true, true, true}, {true, true, false}, {true, true, true}};

                            //verificando se a máscara corresponde ao pixel
                            //variavél auxiliar
                            flag = true;

                            //máscara 1
                            for (int i = 0; i < 8; i++) {
                                if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask1))) {
                                    flag = false;
                                    break;
                                }
                            }

                            //mascara 2
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask2))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 3
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask3))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            //mascara 4
                            if (!flag) {
                                flag = true;
                                for (int i = 0; i < 8; i++) {
                                    if (!(getVizinhoBool(i, 3, 3, 1, 1, pixel) == getVizinhoBool(i, 3, 3, 1, 1, mask4))) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }

                            if (flag) {
                                auxMat[x][y] = true;
                                excluir++;
                            }
                        }

                        //pixel é eliminavel
                        if (auxMat[x][y]) {

                            boolean twoPixel = false;

                            //verificação para validar se o objeto do pixel possui largura de 2 pixels
                            try {
                                if (img[x][y - 1] == false && img[x][y + 1] == true && img[x][y + 2] == false) {
                                    twoPixel = true;
                                } else if (img[x][y - 2] == false && img[x][y - 1] == true && img[x][y + 1] == false) {
                                    twoPixel = true;
                                } else if (img[x - 1][y] == false && img[x + 1][y] == true && img[x + 2][y] == false) {
                                    twoPixel = true;
                                } else if (img[x - 2][y] == false && img[x - 1][y] == true && img[x + 1][y] == false) {
                                    twoPixel = true;
                                }
                            } catch (Exception e) {
                                twoPixel = true;
                            }

                            //2 passo - o objeto não possuir largura de dois pixels delete ele, senão teste
                            if (twoPixel) {

                                //O pixel não é sinalidado se ele corresponder algumas das máscara abaixo
                                boolean[][] mask1 = {{false, false, false}, {true, true, true}, {true, true, true}, {false, false, false}};
                                boolean[][] mask2 = {{false, false, false}, {true, true, false}, {false, true, false}, {false, false, false}};
                                boolean[][] mask3 = {{false, false, false}, {false, true, false}, {false, true, true}, {false, false, false}};
                                boolean[][] mask4 = {{false, false, false, false}, {false, true, true, false}, {false, true, true, false}, {false, false, false, false}};
                                boolean[][] mask5 = {{false, false, false, false}, {false, true, true, false}, {false, false, true, false}};
                                boolean[][] mask6 = {{false, true, true, false}, {false, true, true, false}, {false, true, true, false}};
                                boolean[][] mask7 = {{false, false, false, false}, {false, true, true, false}, {false, true, false, false}};

                                //máscara1 
                                try {
                                    if ((img[x - 1][y] == mask1[0][1])
                                            && (img[x][y - 1] == mask1[1][0])
                                            && (img[x][y + 1] == mask1[1][2])
                                            && (img[x + 1][y - 1] == mask1[2][0])
                                            && (img[x + 1][y] == mask1[2][1])
                                            && (img[x + 1][y + 1] == mask1[2][2])
                                            && (img[x + 2][y] == mask1[3][1])) {
                                        auxMat[x][y] = false;
                                    } else if ((img[x - 2][y] == mask2[0][1])
                                            && (img[x - 2][y + 1] == mask2[0][2])
                                            && (img[x - 1][y - 1] == mask2[1][0])
                                            && (img[x - 1][y] == mask2[1][1])
                                            && (img[x - 1][y + 1] == mask2[1][2])
                                            && (img[x][y - 1] == mask2[2][0])
                                            && (img[x][y] == mask2[2][1])
                                            && (img[x][y + 1] == mask2[2][2])
                                            && (img[x + 1][y - 1] == mask2[3][0])
                                            && (img[x + 1][y] == mask2[3][1])) {
                                        auxMat[x][y] = false;
                                    } else if ((img[x - 1][y] == mask3[0][1])
                                            && (img[x - 1][y + 1] == mask3[0][2])
                                            && (img[x][y - 1] == mask3[1][0])
                                            && (img[x][y + 1] == mask3[1][2])
                                            && (img[x + 1][y - 1] == mask3[2][0])
                                            && (img[x + 1][y] == mask3[2][1])
                                            && (img[x + 1][y + 1] == mask3[2][2])
                                            && (img[x + 2][y - 1] == mask3[3][0])
                                            && (img[x + 2][y] == mask3[3][1])) {
                                        auxMat[x][y] = false;
                                    } else if ((img[x - 1][y - 1] == mask4[0][0])
                                            && (img[x - 1][y] == mask4[0][1])
                                            && (img[x - 1][y + 1] == mask4[0][2])
                                            && (img[x - 1][y + 2] == mask4[0][3])
                                            && (img[x][y - 1] == mask4[1][0])
                                            && (img[x][y] == mask4[1][1])
                                            && (img[x][y + 1] == mask4[1][2])
                                            && (img[x][y + 2] == mask4[1][3])
                                            && (img[x + 1][y - 1] == mask4[2][0])
                                            && (img[x + 1][y] == mask4[2][1])
                                            && (img[x + 1][y + 1] == mask4[2][2])
                                            && (img[x + 1][y + 2] == mask4[2][3])
                                            && (img[x + 2][y - 1] == mask4[3][0])
                                            && (img[x + 2][y] == mask4[3][1])
                                            && (img[x + 2][y + 1] == mask4[3][2])
                                            && (img[x + 2][y + 2] == mask4[3][3])) {
                                        auxMat[x][y] = false;
                                    } else if ((img[x - 1][y] == mask5[0][1])
                                            && (img[x - 1][y + 1] == mask5[0][2])
                                            && (img[x - 1][y + 2] == mask5[0][3])
                                            && (img[x][y - 1] == mask5[1][0])
                                            && (img[x][y] == mask5[1][1])
                                            && (img[x][y + 1] == mask5[1][2])
                                            && (img[x][y + 2] == mask5[1][3])
                                            && (img[x + 1][y - 1] == mask5[2][0])
                                            && (img[x + 1][y] == mask5[2][1])
                                            && (img[x + 1][y + 1] == mask5[2][2])) {
                                        auxMat[x][y] = false;
                                    } else if ((img[x - 1][y] == mask5[0][1])
                                            && (img[x - 1][y + 1] == mask5[0][2])
                                            && (img[x][y - 1] == mask5[1][0])
                                            && (img[x][y] == mask5[1][1])
                                            && (img[x][y + 1] == mask5[1][2])
                                            && (img[x][y + 2] == mask5[1][3])
                                            && (img[x + 1][y] == mask5[2][1])
                                            && (img[x + 1][y + 1] == mask5[2][2])) {
                                        auxMat[x][y] = false;
                                    } else if ((img[x - 1][y - 2] == mask5[0][0])
                                            && (img[x - 1][y - 1] == mask5[0][1])
                                            && (img[x - 1][y] == mask5[0][2])
                                            && (img[x][y - 2] == mask5[1][0])
                                            && (img[x][y - 1] == mask5[1][1])
                                            && (img[x][y] == mask5[1][2])
                                            && (img[x][y + 1] == mask5[1][3])
                                            && (img[x + 1][y - 1] == mask5[2][1])
                                            && (img[x + 1][y] == mask5[2][2])
                                            && (img[x + 1][y + 1] == mask5[2][3])) {
                                        auxMat[x][y] = false;
                                    }

                                } catch (Exception e) {
//                                    auxMat[x][y] = false;
                                }
                                
                                if(auxMat[x][y] == false){
                                    excluir--;
                                }
                            }
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

//            System.out.println("Excluir:" + excluir);
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
    private static boolean getVizinhoBool(int dir, int largura, int altura, int x, int y, boolean[][] img) {
        switch (dir) {
            case 6:
                return inBounds(largura, altura, x, y - 1) ? img[x][y - 1] : true;
            case 7:
                return inBounds(largura, altura, x + 1, y - 1) ? img[x + 1][y - 1] : true;
            case 0:
                return inBounds(largura, altura, x + 1, y) ? img[x + 1][y] : true;
            case 1:
                return inBounds(largura, altura, x + 1, y + 1) ? img[x + 1][y + 1] : true;
            case 2:
                return inBounds(largura, altura, x, y + 1) ? img[x][y + 1] : true;
            case 3:
                return inBounds(largura, altura, x - 1, y + 1) ? img[x - 1][y + 1] : true;
            case 4:
                return inBounds(largura, altura, x - 1, y) ? img[x - 1][y] : true;
            case 5:
                return inBounds(largura, altura, x - 1, y - 1) ? img[x - 1][y - 1] : true;
            default:
                return true;
        }
    }
}
