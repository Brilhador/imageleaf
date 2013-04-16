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
    private int lastx = 0;
    private int lasty = 0;
    private int lastDirection = 0;

    //construtor
    public ChainCode(boolean[][] imageBorder) {
        this.imageBorder = imageBorder;
        this.width = imageBorder.length;
        this.heigth = imageBorder[0].length;
        getInicialPixel();
    }

    private void getInicialPixel() {
        //pega o pixel inicial            
        for (int j = 0; j < heigth; j++) {
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
    //pensa numa logica para essa busca
    private int getNextDirection(int lastDirection, int x, int y) {
        int direction = (lastDirection == 0)?0:(lastDirection + 7) % 8;
        while (direction < 8) {
            Dimension d = getNextCoordinates(x, y, direction);
            if (d != null ){
                if ((d.width != lastx || d.height != lasty)) {
                    System.out.println(direction);
                    lastDirection = direction;
                    return direction;
                }
            }
            direction++;
        }
        direction = (lastDirection + 7) % 8;
        while (0 < direction) {
            Dimension d = getNextCoordinates(x, y, direction);
            if (d != null){
                if ((d.width != lastx || d.height != lasty)) {
                    System.out.println(direction);
                    lastDirection = direction;
                    return direction;
                }
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
        Dimension d = null;
        System.out.println("Inicial: \n" + x + "," + y);
        do {
            d = getNextCoordinates(x, y, getNextDirection(lastDirection, x, y));
            if (d != null) {
                lista.add(d);
                lastx = x;
                lasty = y;
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
                System.out.println("Direcao igual a nulo");
                break;
            }
        } while ((startx != x || starty != y));
        System.out.println("finalizado");
        return lista;
    }
}
