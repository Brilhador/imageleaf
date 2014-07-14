/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.shape.line;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author ANDERSON
 */
public class Besenham {

    public static ArrayList<Dimension> getLine(Dimension initPoint, Dimension finalPoint) {
        ArrayList<Dimension> pontos = new ArrayList<>();

        int x, y, erro, deltaX, deltaY;
        erro = 0;
        x = initPoint.width;
        y = initPoint.height;

        deltaX = finalPoint.width - initPoint.width;
        deltaY = finalPoint.height - initPoint.height;

        if ((Math.abs(deltaY) >= Math.abs(deltaX) && initPoint.height > finalPoint.height)
                || (Math.abs(deltaY) < Math.abs(deltaX) && deltaY < 0)) {
            x = finalPoint.width;
            y = finalPoint.height;
            deltaX = initPoint.width - finalPoint.width;
            deltaY = initPoint.height - finalPoint.height;
        }

        pontos.add(initPoint);

        if (deltaX >= 0) {
            if (Math.abs(deltaX) >= Math.abs(deltaY)) {
                for (int i = 1; i < Math.abs(deltaX); i++) {
                    if (erro < 0) {
                        x++;
                        pontos.add(new Dimension(x, y));
                        erro += deltaY;
                    } else {
                        x++;
                        y++;
                        pontos.add(new Dimension(x, y));
                        erro += deltaY - deltaX;
                    }
                }
            } else {
                for (int i = 1; i < Math.abs(deltaY); i++) {
                    if (erro < 0) {
                        x++;
                        y++;
                        pontos.add(new Dimension(x, y));
                        erro += deltaY - deltaX;
                    } else {
                        y++;
                        pontos.add(new Dimension(x, y));
                        erro -= deltaX;
                    }
                }
            }
        } else { // deltaX<0
            if (Math.abs(deltaX) >= Math.abs(deltaY)) {
                for (int i = 1; i < Math.abs(deltaX); i++) {
                    if (erro < 0) {
                        x--;
                        pontos.add(new Dimension(x, y));
                        erro += deltaY;
                    } else {
                        x--;
                        y++;
                        pontos.add(new Dimension(x, y));
                        erro += deltaY + deltaX;
                    }
                }
            } else {
                for (int i = 1; i < Math.abs(deltaY); i++) {
                    if (erro < 0) {
                        x--;
                        y++;
                        pontos.add(new Dimension(x, y));
                        erro += deltaY + deltaX;
                    } else {
                        y++;
                        pontos.add(new Dimension(x, y));
                        erro += deltaX;
                    }
                }
            }
        }

        return pontos;
    }

}
