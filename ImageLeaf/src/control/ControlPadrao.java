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
        
        view.getJbabrirbanco().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser cDiretorio = new JFileChooser();
                cDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (cDiretorio.showOpenDialog(view) == cDiretorio.APPROVE_OPTION) {
                    caminho = cDiretorio.getSelectedFile().getAbsolutePath();
                    view.getLblcaminho().setText(caminho);
                }
            }
        });
        
        view.getJbagrupar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Classificador().agruparEspecie(caminho, view.getTxtnomepadrao().getText());
            }
        });

        view.getJbcriar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int mode = -1;
                if(view.getRbnot().isSelected()){
                    mode = Classificador.NO_THRESHOLD;
                }
                else if(view.getRbyest().isSelected()){
                    mode = Classificador.THRESHOLD;
                }
                else if(view.getRbtwosurf().isSelected()){
                    mode = Classificador.TWO_SURFACE;
                }
                else if(view.getRbfoursurf().isSelected()){
                    mode = Classificador.FOUR_SURFACE;
                }
                new Classificador().criaPadrao(caminho, mode);
            }
        });
        
        view.getJbclassificar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int superficie = 0;
                if(view.getRbsup().isSelected()){
                    superficie = Classificador.SUPERIOR;
                }else if(view.getRbinf().isSelected()){
                    superficie = Classificador.INFERIOR;
                }
                
                int distancia = -1;
                if(view.getRbeuclidiana().isSelected()){
                    distancia = Classificador.EUCLIDIANA;
                }
                else if(view.getRbmanhattan().isSelected()){
                    distancia = Classificador.MANHATTAN;
                }
                
                int mode = -1;
                if(view.getRbnot().isSelected()){
                    mode = Classificador.NO_THRESHOLD;
                }
                else if(view.getRbyest().isSelected()){
                    mode = Classificador.THRESHOLD;
                }
                else if(view.getRbtwosurf().isSelected()){
                    mode = Classificador.TWO_SURFACE;
                }
                else if(view.getRbfoursurf().isSelected()){
                    mode = Classificador.FOUR_SURFACE;
                }
                new Classificador().classificaBancoFolha(caminho, mode, distancia, superficie);
            }
        });
    }
}
