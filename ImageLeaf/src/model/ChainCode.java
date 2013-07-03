/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
    //variaveis de informações
    private ArrayList<Integer> chainCode = null;
    private ArrayList<Dimension> border = null;
    private BufferedImage chainImage = null;
    private BufferedImage originalImage = null;

    //construtor
    public ChainCode(BufferedImage originalImage, boolean invScala, int newWidth, int newHeigth, boolean invInicialPoint, boolean invRotation) {
        try {
            this.originalImage = originalImage;
            this.imageBorder = limiarizacaoBoolean(originalImage);
            this.width = imageBorder.length;
            this.heigth = imageBorder[0].length;
            getInicialPixel();
            border = createBorder();
            if (invScala) {
                chainImage = invScala(originalImage, border, newWidth, newHeigth);
                this.imageBorder = limiarizacaoBoolean(chainImage);
                this.width = imageBorder.length;
                this.heigth = imageBorder[0].length;
                getInicialPixel();
                border = createBorder();
                chainCode = createChainCode();
            } else {
                chainImage = originalImage;
                chainCode = createChainCode();
            }
            if (invRotation) {
                chainCode = invRotation(chainCode);
            }
            if (invInicialPoint) {
                int tam = (int) ((newHeigth + newWidth) * 0.25);
                chainCode = invInitialPoint(chainCode, tam);
                border = createBorder();
                chainCode = createChainCode();
                if (invRotation) {
                    chainCode = invRotation(chainCode);
                }
            }
            chainImage = drawBorder(border, new Dimension(startx, starty));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean[][] limiarizacaoBoolean(BufferedImage image) {
        int total = image.getWidth() * image.getHeight();
        image = Filtro.mediana(image, 5);
        int limiar = Limiar.otsuTreshold(Histograma.histogramaGray(image), total);
        return Limiar.limiarizacaoBool(image, limiar);
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

    private ArrayList<Dimension> createBorder() {
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

    private ArrayList<Integer> createChainCode() {
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

    private BufferedImage invScala(BufferedImage originalImage, ArrayList<Dimension> lista, int newWidth, int newHeigth) {
        int maxHeigth = lista.get(0).height;
        int minHeigth = lista.get(0).height;
        int maxWidth = lista.get(0).width;
        int minWidth = lista.get(0).width;
        for (Dimension dimension : lista) {
            if (dimension.height > maxHeigth) {
                maxHeigth = dimension.height;
            }
            if (dimension.height < minHeigth) {
                minHeigth = dimension.height;
            }
            if (dimension.width > maxWidth) {
                maxWidth = dimension.width;
            }
            if (dimension.width < minWidth) {
                minWidth = dimension.width;
            }
        }
        BufferedImage image = originalImage.getSubimage(minWidth - 5, minHeigth - 5, (maxWidth - minWidth) + 5, (maxHeigth - minHeigth) + 5);
        return MyImage.resizeImage(image, newWidth, newHeigth);
    }

    private ArrayList<Integer> invInitialPoint(ArrayList<Integer> chaincode, int tam) {
        int soma = Integer.MAX_VALUE;
        int indice = 0;
        for (int i = 0; i < chaincode.size(); i++) {
            int aux = chaincode.get(i);
            for (int j = 1; j < tam; j++) {
                if ((i + j) >= chaincode.size()) {
                    aux += chaincode.get((i + j) - chaincode.size());
                } else {
                    aux += chaincode.get(i + j);
                }
            }
            if (aux < soma) {
                System.out.println("aux" + aux);
                System.out.println("soma:" + soma + "    ---> trocou");
                soma = aux;
                indice = i;
            }
        }
        ArrayList<Integer> newCode = new ArrayList<>();
        for (int i = indice; i < chaincode.size(); i++) {
            newCode.add(chaincode.get(i));
        }
        for (int i = 0; i < indice; i++) {
            newCode.add(chaincode.get(i));
        }
        startx = border.get(indice).width;
        starty = border.get(indice).height;
        return newCode;

    }

    private ArrayList<Integer> invRotation(ArrayList<Integer> chaincode) {
        ArrayList<Integer> newCode = new ArrayList<>();
        for (int i = 0; i < chaincode.size(); i++) {
            if (i + 1 < chaincode.size()) {
                if (chaincode.get(i) > chaincode.get(i + 1)) {
                    newCode.add(Math.abs(chaincode.get(i) - (chaincode.get(i + 1) + 8)));
                } else {
                    newCode.add(Math.abs(chaincode.get(i) - (chaincode.get(i + 1))));
                }
            } else {
                if (chaincode.get(i) > chaincode.get(0)) {
                    newCode.add(Math.abs(chaincode.get(i) - (chaincode.get(0) + 8)));
                } else {
                    newCode.add(Math.abs(chaincode.get(i) - (chaincode.get(0))));
                }
            }
        }
        return newCode;
    }

    public BufferedImage drawBorder(ArrayList<Dimension> lista, Dimension initPoint) {
        BufferedImage newChainImage = new BufferedImage(chainImage.getWidth(), chainImage.getHeight(), chainImage.getType());
        Graphics2D g2d = newChainImage.createGraphics();
        g2d.drawImage(chainImage, null, 0, 0);
        //desenha a linha do primeiro elemento da lista
        for (Dimension dimension : lista) {
            drawPoint(newChainImage, dimension, Color.GREEN);
        }

        drawPoint(newChainImage, initPoint, Color.RED);
        
        g2d.dispose();
        return newChainImage;
    }

    private void drawPoint(BufferedImage drawImage, Dimension point, Color cor) {
        drawImage.setRGB(point.width, point.height, cor.getRGB());
    }

    //getters e setters
    public BufferedImage getChainImage() {
        return chainImage;
    }

    public void setChainImage(BufferedImage chainImage) {
        this.chainImage = chainImage;
    }

    public ArrayList<Integer> getChainCode() {
        return chainCode;
    }

    public String getChaincode() {
        String code = "";
        for (Integer c : chainCode) {
            code += c;
        }
        return code;
    }

    public double[] getDoubleChainCode() {
        ArrayList<Integer> lista = getChainCode();
        double[] vector = new double[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            vector[i] = lista.get(i);
        }
        return vector;
    }

    public double[] getHistChainCode() {
        ArrayList<Integer> lista = getChainCode();
        double[] hist = new double[8];
        for (int i = 0; i < 8; i++) {
            hist[i] = 0;
        }
        for (int i = 0; i < lista.size(); i++) {
            hist[lista.get(i)]++;
        }
        return hist;
    }

    public void setChainCode(ArrayList<Integer> chainCode) {
        this.chainCode = chainCode;
    }

    public ArrayList<Dimension> getBorder() {
        return border;
    }

    public void setBorder(ArrayList<Dimension> border) {
        this.border = border;
    }
}
