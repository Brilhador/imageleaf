/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anderson
 */
public class CSV {

    //variaveis de controle do objeto
    private File arquivo = null;
    private BufferedWriter buffer = null;
    private String[][] tabela = null;
    
    //construtor
    //cria o arquivo .csv no caminho de destino
    public CSV(String caminho, String nome) {
        arquivo = new File(caminho + "\\" + nome + ".csv");
    }
    
    public CSV(File file){
        arquivo = file.getAbsoluteFile();
    }
    
    //getters e setters
    public String[][] getTabela() {
        return tabela;
    }

    public void setTabela(String[][] tabela) {
        this.tabela = tabela;
    }
    
    public String getCell(int line, int column){
        return tabela[line][column];
    }
    
    public void setCell(int line, int column, String text){
        tabela[line][column] = text;
    }
    
    public int getLineSize(){
        return tabela[0].length;
    }
    
    public int getColumnSize(){
        return tabela.length;
    }
    
    //recebe uma matriz de String (column x line)
    /**
     * 
     * @param String[][] (line x column) 
     */
    public void writeCSV(String[][] tabela){
        try {
            String textBuffer = "";
            buffer = new BufferedWriter(new FileWriter(arquivo));
            for(int line = 0; line < tabela.length; line++){
                for(int column = 0; column < tabela[0].length; column++){
                    textBuffer += tabela[line][column] + ";";
                }
                buffer.write(textBuffer);
                buffer.newLine();
                textBuffer = "";
            }
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeCSV(){
        try {
            String textBuffer = "";
            buffer = new BufferedWriter(new FileWriter(arquivo));
            for(int line = 0; line < tabela.length; line++){
                for(int column = 0; column < tabela[0].length; column++){
                    textBuffer += tabela[line][column] + ";";
                }
                buffer.write(textBuffer);
                buffer.newLine();
                textBuffer = "";
            }
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //recebe o arquivo e faz leitura
    public void readCSV(){
        int numlinha = 0;
        ArrayList<String> linha = new ArrayList<String>();
        ArrayList<ArrayList> tabela = new ArrayList<ArrayList>();
        try{
            BufferedReader read = new BufferedReader(new FileReader(arquivo));
            while(read.ready()){
                linha = new ArrayList<String>(Arrays.asList(read.readLine().split(";")));
                tabela.add(linha);
                numlinha++;
            }
            read.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        this.tabela = new String[numlinha][tabela.get(0).size()];
        for(int line = 0; line < numlinha;line++){
            for(int column = 0; column < tabela.get(0).size();column++){
                try{
                  this.tabela[line][column] =  (String) tabela.get(line).get(column);
                }catch(IndexOutOfBoundsException e){
                  this.tabela[line][column] = "0";
                }
            }
        }
    }    
}
