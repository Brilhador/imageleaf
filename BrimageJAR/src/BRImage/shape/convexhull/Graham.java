/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BRImage.shape.convexhull;

import BRImage.useful.Coordinate;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ANDERSON
 */
public class Graham {
    
    public static ArrayList<Coordinate> getConvexHull(ArrayList<Coordinate> contorno){
        
//        Collections.sort(contorno);
        
        int i = 0;
        
        while(i < (contorno.size()-2)){
            
            //calculo do produto vetorial entre as arestas
            double aux1 = (contorno.get(i+1).getX() - contorno.get(i).getX()) * (contorno.get(i+2).getY() - contorno.get(i).getY());
            double aux2 = (contorno.get(i+1).getY() - contorno.get(i).getY()) * (contorno.get(i+2).getX() - contorno.get(i).getX());
            double result = aux1 - aux2;
            
            if(result < 0){
                contorno.remove(i+1);
                if(i!=0){
                    i--;
                    continue;
                }
            }
            i++;
        }
        
        return contorno;
    }
    
}
