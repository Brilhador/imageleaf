/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author anderson
 */
public class Group {
    
    public static void startBaseCLEF(String caminho, String nome) {
        //para pegar somente os arquivos xml
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        };
        //pega todos arquivos xml e passa para um vetor de files
        File[] arquivos = new File(caminho).listFiles(filter);
        for (File file : arquivos) {
            BufferedImage image;
            XML xml = new XML();
            String especie = xml.lerXml(file, "Species");
            //pega as duas primeiras palavras para manter o padrao no xml
            especie = especie.substring(0, especie.indexOf(" ", especie.indexOf(" ") + 1));
            //pegando o nome da imagem
            String imgNome = xml.lerXml(file, "FileName");
            String tipo = xml.lerXml(file, "Type");
            //so serão selecionada as fotos que são do tipo aquisição scan
            if (tipo.equalsIgnoreCase("scan")) {
                //
                ManageDirectory dir = new ManageDirectory();
                //String diretorio = diretorioPadrao + especie + "/" + tipo;
                String diretorio = dir.getDiretorioPadrao() + nome + "/" + especie;
                System.out.println(diretorio);
                File fileImg = new File(diretorio + "/" + imgNome);
                System.out.println(diretorio + "/" + imgNome);
                if (dir.verificaDiretorio(diretorio)) {
                    try {
                        System.out.println(caminho + "/" + imgNome);
                        image = ImageIO.read(new File(caminho + "/" + imgNome));
                        ImageIO.write(image, "JPG", fileImg);
                    } catch (IOException ex) {
                        Logger.getLogger(ManageDirectory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    dir.criarDiretorio(diretorio);
                    try {
                        image = ImageIO.read(new File(caminho + "/" + imgNome));
                        ImageIO.write(image, "JPG", fileImg);
                    } catch (IOException ex) {
                        Logger.getLogger(ManageDirectory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
}
