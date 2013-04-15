/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author anderson
 */
public class ChainCode {

    //variaveis globais    
    private boolean[][] imageBorder = null;
    private int width = 0;
    private int heigth = 0;
    private int startx = 0;
    private int starty = 0;

    //construtor
    public ChainCode(boolean[][] imageBorder) {
        this.imageBorder = imageBorder;
        this.width = imageBorder.length;
        this.heigth = imageBorder[0].length;
        getInicialPixel();
    }

    private void getInicialPixel() {
        //pega o pixel inicial            
        for (int j = heigth - 1; j >= 0; j--) {
            for (int i = width - 1; i >= 0; i--) {
                if (imageBorder[i][j]) {
                    startx = i;
                    starty = j;
                    break;
                }
            }
        }
    }

    //pega o valor da direçao
    private int getNextDirection(int lastDirection, int x, int y) {
        int direction = lastDirection / 2;
        while (direction < 8) {
            Dimension d = getNextCoordinates(x, y, direction);
            if (d != null) {
                return direction;
            }
            direction++;
        }
        direction = 0;
        while (direction < lastDirection) {
            Dimension d = getNextCoordinates(x, y, direction);
            if (d != null) {
                return direction;
            }
            direction++;
        }
        return -1;
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
        return ((x >= 0 && x < width) && (y >= 0 && y < heigth));
    }

    private boolean isObjectLeaf(int x, int y) {
        return imageBorder[x][y];
    }

    public ArrayList<Dimension> getDimesionChainCode() {
        //gera chain code
        ArrayList<Dimension> lista = new ArrayList<>();
        //coordenada auxiliares
        int x = startx;
        int y = starty;
        int lastDirection = 0;
        System.out.println("Inicial: " + x + "," + y);
        do {
            lastDirection = getNextDirection(lastDirection, x, y);
            Dimension d = getNextCoordinates(x, y, lastDirection);
            if (d != null) {
                lista.add(d);
                x = d.width;
                y = d.height;
                System.out.println(x + "," + y);
            } else {
                break;
            }
        } while ((startx != x || starty != y));
        System.out.println("finalizado");
        return lista;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getChainCode() {
        //gera chain code
        ArrayList<Integer> lista = new ArrayList<>();
        //coordenada auxiliares
        int x = startx;
        int y = starty;
        int lastDirection = 0;
        System.out.println("Inicial: " + x + "," + y);
        do {
            lastDirection = getNextDirection(lastDirection, x, y);
            Dimension d = getNextCoordinates(x, y, lastDirection);
            if (d != null) {
                lista.add(lastDirection);
                x = d.width;
                y = d.height;
                System.out.println(lastDirection);
            } else {
                break;
            }
        } while ((startx != x || starty != y));
        System.out.println("finalizado");
        return lista;
    }
}
