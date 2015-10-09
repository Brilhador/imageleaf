/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BRImage.useful;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Anderson
 */
public class Perimeter implements Serializable, Comparable{
    
    private ArrayList<Coordinate> coordenada = null;
    private Integer tamanho = 0;

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
    
    public int getSize(){
        return coordenada.size();
    }
    
    @Override
    public int compareTo(Object o) {
        Perimeter p = (Perimeter) o;
        return tamanho.compareTo(p.getSize());
    }
    
}
