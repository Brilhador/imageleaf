/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.*;
import view.ViewPadrao;

/**
 *
 * @author Anderson
 */
public class ControlPadrao {

    private ViewPadrao view;
    private String caminho;
    private File arquivo;
    private File[] arquivos;

    public ControlPadrao() {
        view = new ViewPadrao();
        addEvents();
        view.setVisible(true);
    }

    private void addEvents() {

        view.getJbabrir().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser cDiretorio = new JFileChooser();
                cDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (cDiretorio.showOpenDialog(view) == cDiretorio.APPROVE_OPTION) {
                    caminho = cDiretorio.getSelectedFile().getAbsolutePath();
                    arquivo = new File(caminho);
                    arquivos = arquivo.listFiles();
                    DefaultListModel model = new DefaultListModel();
                    for (File file : arquivos) {
                        model.addElement(file);
                    }
                    view.getJllista().removeAll();
                    view.getJllista().setModel(model);
                }
            }
        });
        
        view.getJbabrirbanco().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser cDiretorio = new JFileChooser();
                cDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                //colocando um filtro para abrir somente imagens
                FilenameFilter filter = new FilenameFilter() {

                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".xml");
                    }
                };
                
                if (cDiretorio.showOpenDialog(view) == cDiretorio.APPROVE_OPTION) {
                    caminho = cDiretorio.getSelectedFile().getAbsolutePath();
                    arquivo = new File(caminho);
                    arquivos = arquivo.listFiles(filter);
                    DefaultListModel model = new DefaultListModel();
                    for (File file : arquivos) {
                        model.addElement(file);
                    }
                    view.getJllista().removeAll();
                    view.getJllista().setModel(model);
                }
            }
        });
        
        view.getJbagrupar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Classificador().agruparEspecie(arquivos, caminho);
            }
        });

        view.getJbcriar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Classificador().calculaHistograma(arquivo);
            }
        });
        
        view.getJbclassificar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Classificador().classificaBancoFolha(arquivos, caminho);
            }
        });
    }
}
