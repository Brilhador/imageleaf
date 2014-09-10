/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BRImage.useful;

import java.io.Serializable;

/**
 *
 * @author ANDERSON
 */
public class Coordinate implements Serializable, Comparable{
    
    private Integer x = 0;
    private Integer y = 0;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int compareTo(Object o) {
        Coordinate c = (Coordinate) o;
        return y.compareTo(c.getY());
    }
}
