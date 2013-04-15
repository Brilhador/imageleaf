/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.blur.ControlBlurLow;
import control.blur.ControlBlurMedian;
import control.curvature.ControlCurvatureChainCode;
import control.edge.ControlEdgeSobel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import model.components.JImageInternalFrame;
import org.jfree.ui.ExtensionFileFilter;
import view.viewPrincipal;

/**
 *
 * @author anderson
 */
public class ControlPrincipal {

    private viewPrincipal view = null;

    public ControlPrincipal() {
        view = new viewPrincipal();
        //add events in components
        initComponents();
        view.setExtendedState(JFrame.MAXIMIZED_BOTH);
        view.setVisible(true);
    }

    private void initComponents() {

        //Menu FILE
        view.getMenuOpen().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                FileFilter filter = new ExtensionFileFilter("JPG", ".jpg");
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(filter);
                if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                    //get path of image selected
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    addFrameImage(path);
                }
            }
        });

        //menu Edit

        //menu filtes
        view.getmFilterBlurLow().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (getFrameSelected() != null) {
                    new ControlBlurLow(getFrameSelected().getImage(), view);
                } else {
                    JOptionPane.showMessageDialog(view, "Selecione uma imagem", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        view.getmFilterBlurMedian().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (getFrameSelected() != null) {
                    new ControlBlurMedian(getFrameSelected().getImage(), view);
                } else {
                    JOptionPane.showMessageDialog(view, "Selecione uma imagem", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //filtes edge-detect
        view.getmFilterEdgeSobel().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (getFrameSelected() != null) {
                    new ControlEdgeSobel(getFrameSelected().getImage(), view);
                } else {
                    JOptionPane.showMessageDialog(view, "Selecione uma imagem", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //curvature chain code
        view.getmCurvatureChainCode().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (getFrameSelected() != null) {
                    if(getFrameSelected().getImageBorder() != null){
                         new ControlCurvatureChainCode(getFrameSelected().getImage(), getFrameSelected().getImageBorder());
                    }else{
                        int response = JOptionPane.showConfirmDialog(view, "As bordas da imagem nao foram identificadas, deseja identifica-las agora?", null, JOptionPane.YES_NO_OPTION);
                        if(response == JOptionPane.YES_OPTION){
                            new ControlEdgeSobel(getFrameSelected().getImage(), view);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Selecione uma imagem", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    //metodos
    private void addFrameImage(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            JImageInternalFrame frame = new JImageInternalFrame(image);
            //adicionando o frame 
            view.getjPanelPrincipal().add(frame);
            view.getjPanelPrincipal().validate();
        } catch (Exception ex) {
            Logger.getLogger(ControlPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JImageInternalFrame getFrameSelected() {
        return (JImageInternalFrame) view.getjPanelPrincipal().getSelectedFrame();
    }
}
