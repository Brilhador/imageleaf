/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BRImage.useful;

import java.util.ArrayList;

/**
 *
 * @author Anderson
 */
public class Perimeter {
    
    private ArrayList<Coordinate> coordenada = null;
    private int tamanho = 0;

    public Perimeter(ArrayList<Coordinate> coordenada) {
        this.coordenada = coordenada;
        tamanho = coordenada.size();
    }

    public ArrayList<Coordinate> getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(ArrayList<Coordinate> coordenada) {
        this.coordenada = coordenada;
    }
    
    
    
}
