/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author anderson
 */
public class Signature {
    
    public ArrayList<Integer> createSignalBorders(int numValues, ArrayList<Integer> chaincode){
        ArrayList<Integer> listDifferences = calculatorDifferences(chaincode);
        ArrayList<Integer> listSignal = separatingMaxDifferences(numValues, listDifferences);
        for (Integer integer : listSignal) {
            System.out.println(integer);
        }
        return null;
    }
    
    //talvez alterar o tipo de calculo de diferecas
    private ArrayList<Integer> calculatorDifferences(ArrayList<Integer> chaincode){
        //cria um arraylist para receber todas as diferenças calculadas
        ArrayList<Integer> listDifferences = new ArrayList<>();
        //percorre alista recebida por parametro e calcula as difernças
        for (int index = 0; index < chaincode.size(); index++){
            //caso chegue na ultima posicao calcular a ultima com a primeira do vetor
            if((index+1) != chaincode.size()){
                listDifferences.add(chaincode.get(index) - chaincode.get(index+1));
            }else{
                listDifferences.add(chaincode.get(index) - chaincode.get(0));
            }
        }
        return listDifferences;
    }
    
    private ArrayList<Integer> separatingMaxDifferences(int numElements, ArrayList<Integer> listDifferences){
        //array auxiliar
        ArrayList<Integer> signal = new ArrayList<>();
        for (Integer difference : listDifferences) {
            
            if(signal.size() < numElements && difference != 0){
                signal.add(difference);
            }else{
                int minValue = 8;
                int minIndex = 0;
                for(int index = 0; index < signal.size(); index++){
                    //pega a menor diferrenca presente no signal
                    if((Math.abs(signal.get(index)) < Math.abs(minValue)) && difference != 0){
                        System.out.println(signal.get(index));
                        minValue = signal.get(index);
                        minIndex = index;
                    }
                }
                //verifica se a difference eh maior que a difrenca minima do vetor
                if(minValue < difference){
                    signal.remove(minIndex);
                    signal.add(difference);
                }
            }
        }
        return signal;
    }
} 
