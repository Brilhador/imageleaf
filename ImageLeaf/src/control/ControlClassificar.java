/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import model.*;
import view.ViewClassificar;

/**
 *
 * @author Anderson
 */
public class ControlClassificar {

    private ViewClassificar view;
    private String caminho;
    private BufferedImage image;
    private BufferedImage imageE;
    private BufferedImage histograma, histogramaM, histogramaD;
    private int[] hred, hgreen, hblue,
            hmred, hmgreen, hmblue,
            hdpred, hdpgreen, hdpblue;

    public ControlClassificar() {
        view = new ViewClassificar();
        addEvents();
        view.setVisible(true);
    }

    private void addEvents() {
        view.getJbabrir().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //abre a imagem e a exibe na tela
                JFileChooser cFile = new JFileChooser();
                if (cFile.showOpenDialog(view) == cFile.APPROVE_OPTION) {
                    caminho = cFile.getSelectedFile().getAbsolutePath();
                    try {
                        image = ImageIO.read(new File(caminho));
                    } catch (IOException ex) {
                        Logger.getLogger(ControlClassificar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    view.getJlImage().setIcon(new ImageIcon(model.Image.resizeImage(image, view.getJlImage().getWidth(), view.getJlImage().getHeight())));
                }

                //Gera o histograma da imagem
                hred = Histograma.histogramaRed(image);
                hgreen = Histograma.histogramaGreen(image);
                hblue = Histograma.histogramaBlue(image);

                histograma = Grafico.histogramaRGB(hred,
                        hgreen,
                        hblue,
                        view.getJlhisimage().getWidth(),
                        view.getJlhisimage().getHeight(),
                        "Histograma");
                view.getJlhisimage().setIcon(new ImageIcon(histograma));
            }
        });

        view.getJbclassificar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //pega o caminho da especie a qual folha foi classificada
                File file = Classificador.classificaFolha((BufferedImage) image);
                caminho = file.getAbsolutePath();
                //altero o nome da especie na tela
                view.getJlespecienome().setText(XML.lerXml(caminho + "\\dados.xml", "nome"));
                //leio os arquivos txt dos histograma medio
                hmred = Histograma.lerTxtHistograma(caminho + "\\hmR.txt");
                hmgreen = Histograma.lerTxtHistograma(caminho + "\\hmG.txt");
                hmblue = Histograma.lerTxtHistograma(caminho + "\\hmB.txt");
                histogramaM = Grafico.histogramaRGB(hmred, hmgreen, hmblue, view.getJlhisimageE().getWidth(), view.getJlhisimageE().getHeight(), "Médio");
                try {
                    //altera a imagem da folha padrao
                    FilenameFilter filterName = new FilenameFilter() {

                        @Override
                        public boolean accept(File dir, String name) {
                            return name.endsWith(".jpg");
                        }
                    };

                    File[] img = file.listFiles(filterName);
                    imageE = ImageIO.read(img[0]);
                } catch (IOException ex) {
                    Logger.getLogger(ControlClassificar.class.getName()).log(Level.SEVERE, null, ex);
                }
                imageE = model.Image.resizeImage(imageE, view.getJlImageE().getWidth(), view.getJlImageE().getHeight());
                view.getJlImageE().setIcon(new ImageIcon(imageE));
                //altero a imagem do jlabel na tela com o histograma criado
                view.getJlhisimageE().setIcon(new ImageIcon(histogramaM));
                //leio os arquivos txt dos histograma de desvio padrao
                hdpred = Histograma.lerTxtHistograma(caminho + "\\hdpR.txt");
                hdpgreen = Histograma.lerTxtHistograma(caminho + "\\hdpG.txt");
                hdpblue = Histograma.lerTxtHistograma(caminho + "\\hdpB.txt");
                histogramaD = Grafico.histogramaRGB(hdpred, hdpgreen, hdpblue, view.getJlhisimageE().getWidth(), view.getJlhisimageE().getHeight(), "Desvio Padrão");
            }
        });

        view.getJbselecionar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser cDiretorio = new JFileChooser();
                cDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (cDiretorio.showOpenDialog(view) == cDiretorio.APPROVE_OPTION) {
                    caminho = cDiretorio.getSelectedFile().getAbsolutePath();
                    //altero o nome da especie na tela
                    view.getJlespecienome().setText(XML.lerXml(caminho + "\\dados.xml", "nome"));
                    //leio os arquivos txt dos histograma medio
                    hmred = Histograma.lerTxtHistograma(caminho + "\\hmR.txt");
                    hmgreen = Histograma.lerTxtHistograma(caminho + "\\hmG.txt");
                    hmblue = Histograma.lerTxtHistograma(caminho + "\\hmB.txt");
                    histogramaM = Grafico.histogramaRGB(hmred, hmgreen, hmblue, view.getJlhisimageE().getWidth(), view.getJlhisimageE().getHeight(), "Médio");
                    try {
                        //altera a imagem da folha padrao
                        FilenameFilter filterName = new FilenameFilter() {

                            @Override
                            public boolean accept(File dir, String name) {
                                return name.endsWith(".jpg");
                            }
                        };

                        File[] img = cDiretorio.getSelectedFile().listFiles(filterName);
                        imageE = ImageIO.read(img[0]);
                    } catch (IOException ex) {
                        Logger.getLogger(ControlClassificar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    imageE = model.Image.resizeImage(imageE, view.getJlImageE().getWidth(), view.getJlImageE().getHeight());
                    view.getJlImageE().setIcon(new ImageIcon(imageE));
                    //altero a imagem do jlabel na tela com o histograma criado
                    view.getJlhisimageE().setIcon(new ImageIcon(histogramaM));
                    //leio os arquivos txt dos histograma de desvio padrao
                    hdpred = Histograma.lerTxtHistograma(caminho + "\\hdpR.txt");
                    hdpgreen = Histograma.lerTxtHistograma(caminho + "\\hdpG.txt");
                    hdpblue = Histograma.lerTxtHistograma(caminho + "\\hdpB.txt");
                    histogramaD = Grafico.histogramaRGB(hdpred, hdpgreen, hdpblue, view.getJlhisimageE().getWidth(), view.getJlhisimageE().getHeight(), "Desvio Padrão");
                }
            }
        });

        view.getJbhmedio().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.getJlhisimageE().setIcon(new ImageIcon(histogramaM));
            }
        });

        view.getJbhdesvio().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.getJlhisimageE().setIcon(new ImageIcon(histogramaD));
            }
        });
    }
}
