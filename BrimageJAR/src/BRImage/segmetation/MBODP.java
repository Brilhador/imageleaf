/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.segmetation;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author Anderson
 */
public class MBODP {

    //variaveis globais    
    private boolean[][] imageBorder = null;
    private int width = 0;
    private int heigth = 0;
    private int startx = 0;
    private int starty = 0;
    private int lastx = 0;
    private int lasty = 0;
    private int lastDirection = 0;
    //limite mínimo de petimetro
    private int limite = 0;

    //construtor
    public MBODP(boolean[][] imageBorder, int limite) {
        this.imageBorder = imageBorder;
        this.width = imageBorder.length;
        this.heigth = imageBorder[0].length;
        this.limite = limite;
    }

    public MBODP(boolean[][] imageBorder) {
        this.imageBorder = imageBorder;
        this.width = imageBorder.length;
        this.heigth = imageBorder[0].length;
    }

    public ArrayList<Dimension> getObjects() {
        ArrayList<Dimension> lista = new ArrayList<>();
        ArrayList<Integer> perimetro = new ArrayList<>();
        ArrayList<Dimension> objeto = new ArrayList<>();

        //identificar novo objeto 
        while (getNextObject()) {
            //encontra o proximo o objeto e retorna seu contorno
            ArrayList<Dimension> contorno = getBorder();

            try {

                System.out.println(contorno.size());

                if (contorno.size() > limite) {
                    //adiciona o perimetro na lista
                    perimetro.add(contorno.size());
                    //adicionar centroide a lista
                    objeto.add(getCentroide(contorno));
                }

                //remove objeto da imagem
                removeObject(contorno);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        //calcular a média e o desvio padrãos dos périmetros
//        //média
//        double media = 0;
//        double soma = 0;
//
//        for (Integer tam : perimetro) {
//            soma += tam;
//        }
//
//        media = soma / perimetro.size();
//
//        //desvio padrão
//        double variancia = 0;
//        for (Integer tam : perimetro) {
//            variancia += Math.abs(tam - media);
//        }
//        variancia /= (perimetro.size() - 1);
//
//        double desvio = Math.sqrt(variancia);
//        System.out.println("desvio:" + desvio);
//        System.out.println("media: " + media);
//
//        //removendo os objetos fora do padrão
//        double min = media - desvio;
//        double max = media + desvio;
//
//        for (int i = 0; i < perimetro.size(); i++) {
//            int tam = perimetro.get(i);
//            if ((tam >= min && tam <= max)) {
//                objeto.add(lista.get(i));
//            }
//        }
        return objeto;
    }

    private void removeObject(ArrayList<Dimension> Contorno) {
        //Coordenadas Limite
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;

        for (Dimension d : Contorno) {
            if (minX >= d.width) {
                minX = d.width;
            }
            if (maxX <= d.width) {
                maxX = d.width;
            }
            if (minY >= d.height) {
                minY = d.height;
            }
            if (maxY <= d.height) {
                maxY = d.height;
            }
        }

        //apagar objeto
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                imageBorder[x][y] = false;
            }
        }
    }

    private boolean getNextObject() {
        //pega o pixel inicial   
        for (int y = heigth - 1; y >= 0; y--) {
            for (int x = width - 1; x >= 0; x--) {
                if (imageBorder[x][y]) {
                    startx = x;
                    starty = y;
                    return true;
                }
            }
        }
        return false;
    }

    //pega o valor da direçao
    //pensa numa logica para essa busca
    private Dimension getNextDirection(int x, int y) {
        int point = ((lastDirection % 2) == 0) ? ((lastDirection + 7) % 8) : ((lastDirection + 6) % 8);
        int direction = point;
        Dimension d = null;
        while (direction < 8) {
            d = getNextCoordinates(x, y, direction);
            if (d != null) {
                if ((d.width != lastx || d.height != lasty)) {
//                    System.out.println(direction);
                    lastDirection = direction;
                    return d;
                }
            }
            direction++;
        }
        direction = 0;
        while (direction < point) {
            d = getNextCoordinates(x, y, direction);
            if (d != null) {
                if ((d.width != lastx || d.height != lasty)) {
//                    System.out.println(direction);
                    lastDirection = direction;
                    return d;
                }
            }
            direction++;
        }
        return d;
    }

    /*
     * 321
     * 4 0
     * 567
     */
    private Dimension getNextCoordinates(int x, int y, int direction) {
        switch (direction) {
            case 0:
                return ((inBounds(x + 1, y)) && (isObjectLeaf(x + 1, y))) ? new Dimension(x + 1, y) : null;
            case 1:
                return ((inBounds(x + 1, y - 1)) && (isObjectLeaf(x + 1, y - 1))) ? new Dimension(x + 1, y - 1) : null;
            case 2:
                return ((inBounds(x, y - 1)) && (isObjectLeaf(x, y - 1))) ? new Dimension(x, y - 1) : null;
            case 3:
                return ((inBounds(x - 1, y - 1)) && (isObjectLeaf(x - 1, y - 1))) ? new Dimension(x - 1, y - 1) : null;
            case 4:
                return ((inBounds(x - 1, y)) && (isObjectLeaf(x - 1, y))) ? new Dimension(x - 1, y) : null;
            case 5:
                return ((inBounds(x - 1, y + 1)) && (isObjectLeaf(x - 1, y + 1))) ? new Dimension(x - 1, y + 1) : null;
            case 6:
                return ((inBounds(x, y + 1)) && (isObjectLeaf(x, y + 1))) ? new Dimension(x, y + 1) : null;
            case 7:
                return ((inBounds(x + 1, y + 1)) && (isObjectLeaf(x + 1, y + 1))) ? new Dimension(x + 1, y + 1) : null;
            default:
                return null;
        }
    }

    private boolean inBounds(int x, int y) {
        return ((x > 0 && x < width) && (y > 0 && y < heigth));
    }

    private boolean isObjectLeaf(int x, int y) {
        return imageBorder[x][y];
    }

    private ArrayList<Dimension> getBorder() {
        //gera chain code
        ArrayList<Dimension> lista = new ArrayList<>();
        //coordenada auxiliares
        int x = startx;
        int y = starty;
        //Primeiro ponto do contorno
        lista.add(new Dimension(startx, starty));
        Dimension d = null;
//        System.out.println("Inicial: \n" + x + "," + y);
        do {
            d = getNextDirection(x, y);
            if (d != null) {
                lista.add(d);
                lastx = x;
                lasty = y;
                x = d.width;
                y = d.height;
            } else {
                break;
            }
        } while ((startx != x || starty != y));//sai quando encontra o ponto inicial
        return lista;
    }

    private static Dimension getCentroide(ArrayList<Dimension> lista) {
        int x = 0;
        int y = 0;
        for (Dimension dimension : lista) {
            x += dimension.width;
            y += dimension.height;
        }
        return new Dimension(x / lista.size(), y / lista.size());
    }
}
