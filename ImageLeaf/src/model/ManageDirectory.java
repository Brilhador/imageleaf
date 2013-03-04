/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author Anderson
 */
public class ManageDirectory {

    private String diretorioPadrao = System.getProperty("user.home") + "\\ImageLeaf\\Especies\\";
    
    //getters e Setters
    public String getDiretorioPadrao() {
        return diretorioPadrao;
    }

    public void setDiretorioPadrao(String diretorioPadrao) {
        this.diretorioPadrao = diretorioPadrao;
    }

    //funcao que cria os diretorios do progrma
    public void criarDiretorio(String caminho) {
        new File(caminho).mkdirs();
    }

    //verifica se o diretorio existe{
    public boolean verificaDiretorio(String caminho) {
        return new File(caminho).exists();
    }
}