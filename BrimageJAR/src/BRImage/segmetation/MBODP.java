/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.segmetation;

import BRImage.useful.Coordinate;
import BRImage.useful.Perimeter;
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

        this.width = imageBorder.length;
        this.heigth = imageBorder[0].length;

        this.imageBorder = new boolean[width][heigth];

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.heigth; y++) {
                this.imageBorder[x][y] = imageBorder[x][y];
            }
        }

        this.limite = limite;
    }

    public MBODP(boolean[][] imageBorder) {
        this.width = imageBorder.length;
        this.heigth = imageBorder[0].length;

        this.imageBorder = new boolean[width][heigth];

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.heigth; y++) {
                this.imageBorder[x][y] = imageBorder[x][y];
            }
        }
    }

    //retorna o centroide das coordenadas do perimetro
    public ArrayList<Coordinate> getObjects() {

        ArrayList<Coordinate> objeto = new ArrayList<>();
        //identificar novo objeto 
        while (getNextObject()) {
            try {

                //encontra o proximo o objeto e retorna seu contorno
                ArrayList<Coordinate> contorno = getBorder();

                if (contorno.size() > limite) {
                    //adicionar centroide a lista
                    objeto.add(getCentroide(contorno));
                }

                //remove objeto da imagem
                removeObject(contorno);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return objeto;
    }

    public ArrayList<Perimeter> getPerimeter() {

        ArrayList<Perimeter> perimetro = new ArrayList<>();

        //identificar novo objeto 
        while (getNextObject()) {
            //encontra o proximo o objeto e retorna seu contorno
            //getBorder sempre retorna um objeto novo

            try {

                ArrayList<Coordinate> contorno = getBorder();

                if (contorno.size() > limite) {
                    //adiciona o perimetro na lista
                    perimetro.add(new Perimeter(contorno));
                }
                
                //remove objeto da imagem
                removeObject(contorno);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return perimetro;
    }

    private void removeObject(ArrayList<Coordinate> Contorno) {
        //Coordenadas Limite
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;

        for (Coordinate d : Contorno) {
            if (minX >= d.getX()) {
                minX = d.getX();
            }
            if (maxX <= d.getX()) {
                maxX = d.getX();
            }
            if (minY >= d.getY()) {
                minY = d.getY();
            }
            if (maxY <= d.getY()) {
                maxY = d.getY();
            }
        }

        //apagar objeto
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                imageBorder[x][y] = false;
            }
        }
    }

    //desenvolver um getNextObject mais inteligente
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
    private Coordinate getNextDirection(int x, int y) {
        int point = ((lastDirection % 2) == 0) ? ((lastDirection + 7) % 8) : ((lastDirection + 6) % 8);
        int direction = point;
        Coordinate d = null;
        while (direction < 8) {
            d = getNextCoordinates(x, y, direction);
            if (d != null) {
                if ((d.getX() != lastx || d.getY() != lasty)) {
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
                if ((d.getX() != lastx || d.getY() != lasty)) {
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
    private Coordinate getNextCoordinates(int x, int y, int direction) {
        switch (direction) {
            case 0:
                return ((inBounds(x + 1, y)) && (isObjectLeaf(x + 1, y))) ? new Coordinate(x + 1, y) : null;
            case 1:
                return ((inBounds(x + 1, y - 1)) && (isObjectLeaf(x + 1, y - 1))) ? new Coordinate(x + 1, y - 1) : null;
            case 2:
                return ((inBounds(x, y - 1)) && (isObjectLeaf(x, y - 1))) ? new Coordinate(x, y - 1) : null;
            case 3:
                return ((inBounds(x - 1, y - 1)) && (isObjectLeaf(x - 1, y - 1))) ? new Coordinate(x - 1, y - 1) : null;
            case 4:
                return ((inBounds(x - 1, y)) && (isObjectLeaf(x - 1, y))) ? new Coordinate(x - 1, y) : null;
            case 5:
                return ((inBounds(x - 1, y + 1)) && (isObjectLeaf(x - 1, y + 1))) ? new Coordinate(x - 1, y + 1) : null;
            case 6:
                return ((inBounds(x, y + 1)) && (isObjectLeaf(x, y + 1))) ? new Coordinate(x, y + 1) : null;
            case 7:
                return ((inBounds(x + 1, y + 1)) && (isObjectLeaf(x + 1, y + 1))) ? new Coordinate(x + 1, y + 1) : null;
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

    private ArrayList<Coordinate> getBorder() {
        //gera chain code
        ArrayList<Coordinate> lista = new ArrayList<>();
        ArrayList<Coordinate> initial = new ArrayList<>();
        //coordenada auxiliares
        int x = startx;
        int y = starty;
        //Primeiro ponto do contorno
        lista.add(new Coordinate(startx, starty));
        initial.add(new Coordinate(startx, starty));

        //Pega o primeiro pixel próximo, senão tiver nem entra
        Coordinate d = getNextDirection(x, y);

        if (d != null) {
            lista.add(d);
            initial.add(d);
            lastx = x;
            lasty = y;
            x = d.getX();
            y = d.getY();
        } else {
            return lista;
        }

        //caso tenha mais de um pixel
        do {
            d = getNextDirection(x, y);
            if (d != null) {
                lista.add(d);
                lastx = x;
                lasty = y;
                x = d.getX();
                y = d.getY();
            } else {
                return lista;
            }
        } while (hasFound(initial, x, y));//sai quando encontra o ponto inicial

        return lista;
    }

    private boolean hasFound(ArrayList<Coordinate> lista, int x, int y) {
        for (Coordinate coordinate : lista) {
            if (coordinate.getX() == x && coordinate.getY() == y) {
                return false;
            }
        }
        return true;
    }

    public static Coordinate getCentroide(ArrayList<Coordinate> lista) {
        int x = 0;
        int y = 0;
        for (Coordinate dimension : lista) {
            x += dimension.getX();
            y += dimension.getY();
        }
        return new Coordinate(x / lista.size(), y / lista.size());
    }
}
