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
public class Signature {

    public ArrayList<Integer> createSignal(ArrayList<Dimension> listaDimension) {
        Dimension dCentroide = getCentroide(listaDimension);
        return null;
    }

    public Dimension getCentroide(ArrayList<Dimension> lista) {
        //maximos pontos
        Dimension left = lista.get(0);
        Dimension right = lista.get(0);
        Dimension top = lista.get(0);
        Dimension button = lista.get(0);
        //Distancia
        int dLeft = 0;
        int dRight = 0;
        int dTop = 0;
        int dButton = 0;
        //Dimension
        Dimension startX;
        Dimension startY;
        //encontrado os maximos pontos
        for (Dimension dimension : lista) {
            if (left.width > dimension.width) {
                left = dimension;
            }
            if (right.width < dimension.width) {
                right = dimension;
            }
            if (top.height > dimension.height) {
                top = dimension;
            }
            if (button.height < dimension.height) {
                button = dimension;
            }
        }
        //definido a maior distancia
        for (Dimension dimension : lista) {
            //width
            if (dimension.height == left.height) {
                int value = dimension.width - left.width;
                if (value > dLeft) {
                    dLeft = value;
                }
            }
            if (dimension.height == right.height) {
                int value = right.width - dimension.width;
                if (value > dRight) {
                    dRight = value;
                }
            }
            //heigth
            if (dimension.width == top.width) {
                int value = dimension.height - top.height;
                if (value > dTop) {
                    dTop = value;
                }
            }
            if (dimension.width == button.width) {
                int value = button.height - dimension.height;
                if (value > dButton) {
                    dButton = value;
                }
            }
        }
        //verificando qual teve a maior distancia
        if(dLeft > dRight){
            startX = left;
            System.out.println("left");
        }else{
            startX = right;
            System.out.println("right");
        }
        if(dTop > dButton){
            startY = top;
            System.out.println("top");
        }else{
            startY = button;
            System.out.println("button");
        }
        //retorna o ponto de encontro
        System.out.println("x " + startY.width + " y " + startX.height);
        return new Dimension(startY.width, startX.height);
    }
}
