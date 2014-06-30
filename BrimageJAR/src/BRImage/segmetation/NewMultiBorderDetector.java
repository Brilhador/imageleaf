/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.segmetation;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Anderson
 */
public class NewMultiBorderDetector {

    //variaveis globais    
    private boolean[][] imageBorder = null;
    private int width = 0;
    private int heigth = 0;
    private int startx = 0;
    private int starty = 0;
    private int lastx = 0;
    private int lasty = 0;
    private int lastDirection = 0;

    //construtor
    public NewMultiBorderDetector(boolean[][] imageBorder) {
        this.width = imageBorder.length;
        this.heigth = imageBorder[0].length;
        copyImgBorder(imageBorder);
    }

    public ArrayList<Dimension> getObjects() {
        ArrayList<Dimension> lista = new ArrayList<>();
        int x = 0;
        int y = 0;

        //identificar novo objeto 
        while (getNextPixel(x, y)) {
            //encontra o proximo o objeto e retorna seu contorno
            ArrayList<Dimension> contorno = getBorder();

            //remove o objeto da matriz
            if (contorno != null) {
                removeObject(contorno);
                //adicionar centroide a lista
                lista.add(getCentroide(contorno));
            }else{
                return lista;
            }
        }

        return lista;
    }

    private void removeObject(ArrayList<Dimension> Contorno) {
        //Coordenadas Limite
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;

        for (Dimension d : Contorno) {
            if (minX > d.width) {
                minX = d.width;
            }
            if (maxX < d.width) {
                maxX = d.width;
            }
            if (minY > d.height) {
                minY = d.height;
            }
            if (maxY < d.height) {
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

    private void copyImgBorder(boolean[][] imageBorder) {
        this.imageBorder = new boolean[width][heigth];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < heigth; y++) {
                this.imageBorder[x][y] = imageBorder[x][y];
            }
        }
    }

    private boolean getNextPixel(int x, int y) {
        //pega o pixel inicial            
//        for (int j = y; j < heigth - 1; j++) {
        for (int j = heigth - 1; j >= 0; j--) {
//            for (int i = x; i < width - 1; i++) {
            for (int i = width - 1; i >= 0; i--) {
                if (imageBorder[i][j]) {
                    startx = i;
                    starty = j;
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
        Dimension d = null;
//        System.out.println("Inicial: \n" + x + "," + y);
        do {
            d = getNextDirection(x, y);
            if (d == null) {
                System.out.println("Erro, Não foi encontrada uma direção");
                return null;
            }
            //se nao for nulo continua 
            lista.add(d);
            lastx = x;
            lasty = y;
            x = d.width;
            y = d.height;
            //System.out.println(x + "," + y);
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
