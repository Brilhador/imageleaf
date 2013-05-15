/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author anderson
 */
public class AngleTable {

    //matriz de angulos
    private int[][] angle = new int[8 * 8][8];
    
    public AngleTable(){
        try{
        initAngleTable();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public int getAngle(int d1, int d2){
        return angle[d1][d2];
    }
    
    private void initAngleTable(){
        
        //direcao 0
        angle[0][0] = 0;
        angle[0][1] = 45;
        angle[0][2] = 90;
        angle[0][3] = 135;
        angle[0][4] = 180;
        angle[0][5] = -135;
        angle[0][6] = -90;
        angle[0][7] = -45;
        
        //direcao 1
        angle[1][0] = -45;
        angle[1][1] = 0;
        angle[1][2] = 45;
        angle[1][3] = 90;
        angle[1][4] = 135;
        angle[1][5] = 180;
        angle[1][6] = -135;
        angle[1][7] = -90;
        
        //direcao 2
        angle[2][0] = -90;
        angle[2][1] = -45;
        angle[2][2] = 0;
        angle[2][3] = 45;
        angle[2][4] = 90;
        angle[2][5] = 135;
        angle[2][6] = 180;
        angle[2][7] = -135;
        
        //direcao 3
        angle[3][0] = -135;
        angle[3][1] = -90;
        angle[3][2] = -45;
        angle[3][3] = 0;
        angle[3][4] = 45;
        angle[3][5] = 90;
        angle[3][6] = 135;
        angle[3][7] = 180;
        
        //direcao 4
        angle[4][0] = 180;
        angle[4][1] = -135;
        angle[4][2] = -90;
        angle[4][3] = -45;
        angle[4][4] = 0;
        angle[4][5] = 45;
        angle[4][6] = 90;
        angle[4][7] = 135;
        
        //direcao 5
        angle[5][0] = 135;
        angle[5][1] = 180;
        angle[5][2] = -135;
        angle[5][3] = -90;
        angle[5][4] = -45;
        angle[5][5] = 0;
        angle[5][6] = 45;
        angle[5][7] = 90;
        
        //direcao 6
        angle[6][0] = 90;
        angle[6][1] = 135;
        angle[6][2] = 180;
        angle[6][3] = -135;
        angle[6][4] = -90;
        angle[6][5] = -45;
        angle[6][6] = 0;
        angle[6][7] = 45;
        
        //direcao 7
        angle[7][0] = 45;
        angle[7][1] = 90;
        angle[7][2] = 135;
        angle[7][3] = 180;
        angle[7][4] = -135;
        angle[7][5] = -90;
        angle[7][6] = -45;
        angle[7][7] = 0;
    }
}
