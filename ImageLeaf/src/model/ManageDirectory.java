/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.*;

/**
 *
 * @author Anderson
 */
public class ManageDirectory {

    private String diretorioPadrao = System.getProperty("user.home") + "/ImageLeaf/";
    
    //getters e Setters
    public String getDiretorioPadrao() {
        return diretorioPadrao;
    }

    public void setDiretorioPadrao(String diretorioPadrao) {
        this.diretorioPadrao = diretorioPadrao;
    }

    //funcao que cria os diretorios do progrma
    public void criarDiretorio(String caminho) {
        System.out.println(diretorioPadrao);
        new File(caminho).mkdirs();
    }

    //verifica se o diretorio existe{
    public boolean verificaDiretorio(String caminho) {
        return new File(caminho).exists();
    }
}