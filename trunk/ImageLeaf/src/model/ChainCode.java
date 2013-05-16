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
//        System.out.println("Inicial: \n" + x + "," + y);
        do {
            d = getNextDirection(x, y);
            if (d == null) {
                System.out.println("Erro, dimensao igual a nulo");
                return null;
            }
            //se nao for nulo continua 
            lista.add(d);
            lastx = x;
            lasty = y;
            x = d.width;
            y = d.height;
//            System.out.println(x + "," + y);
        } while ((startx != x || starty != y));//sai quando encontra o ponto inicial
//        System.out.println("finalizado");
        return lista;
    }

    public ArrayList<Integer> getChainCode() {
        //gera chain code
        ArrayList<Integer> lista = new ArrayList<>();
        //coordenada auxiliares
        int x = startx;
        int y = starty;
        Dimension d = null;
//        System.out.println("Inicial: \n" + x + "," + y);
        do {
            d = getNextDirection(x, y);
            if (d == null) {
                System.out.println("Erro, dimensao igual a nulo");
                return null;
            }
            //se nao for nulo continua 
            lista.add(lastDirection);
            lastx = x;
            lasty = y;
            x = d.width;
            y = d.height;
//            System.out.println(x + "," + y);
        } while ((startx != x || starty != y));//sai quando encontra o ponto inicial
//        System.out.println("finalizado");
        return lista;
    }

    public int[] getHistograma() {
        //gerando o chain code
        ArrayList<Integer> chaincode = getChainCode();

        if (chaincode != null) {
            //histograma de frenquencia de direçao do chain code
            int[] histChain = new int[8];

            //gerando o histograma
            for (int i : chaincode) {
                histChain[i]++;
            }

            //retornando o histograma
            return histChain;
        }else{
            return null;
        }
    }
    
    public int[] getAngleHistograma(){
        //tabela de angulos
        AngleTable table = new AngleTable();
        
        //vetor de caracteristicas
        //0 = 0, 1 = 45, 2 = 90, 3 = 135, 4 = 180, 5 = 225, 6 = 270, 7 = 315
        //0 = 0, 1 = 45, 2 = 90, 3 = 135, 4 = 180, 5 = -135, 6 = -90, 7 = -45
        int[] vetor = {0,0,0,0,0,0,0,0};
        
        //gerando o chain code
        ArrayList<Integer> chaincode = getChainCode();
        
        for (int i = 0; i < chaincode.size()-1; i++) {
            int angle = table.getAngle(chaincode.get(i), chaincode.get(i+1));
            switch(angle){
                case 0:
                    vetor[0]++;
                    break;
                case 45:
                    vetor[1]++;
                    break;
                case 90:
                    vetor[2]++;
                    break;
                case 135:
                    vetor[3]++;
                    break;
                case 180:
                    vetor[4]++;
                    break;
                case -135:
                    vetor[5]++;
                    break;
                case -90:
                    vetor[6]++;
                    break;
                case -45:
                    vetor[7]++;
                    break;
            }
        }
        return vetor;
    }
}
