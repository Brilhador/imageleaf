/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.blur.ControlBlur;
import control.curvature.ControlCurvatureChainCode;
import control.curvature.ControlCurvatureFourier;
import control.curvature.ControlCurvatureSignature;
import control.segmeting.ControlEdge;
import control.recognition.ControlPatternGeneration;
import control.recognition.ControlSpeciesGroup;
import control.recognition.ControlSimileImage;
import control.segmeting.ControlThresholding;

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
    private JFileChooser chooser = new JFileChooser();

    ;

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
                chooser.setFileFilter(filter);
                chooser.setMultiSelectionEnabled(true);
                if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                    //get path of image selected
                    for (File file : chooser.getSelectedFiles()) {
                        String path = file.getAbsolutePath();
                        addFrameImage(path);
                    }
                }
                if (chooser != null) {
                    chooser.setCurrentDirectory(chooser.getSelectedFile().getAbsoluteFile());
                }
            }
        });

        //menu Edit

        //menu filtes
        view.getmFiltersBlur().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getFrameSelected() != null) {
                    new ControlBlur(getFrameSelected().getImage(), view);
                } else {
                    JOptionPane.showMessageDialog(view, "Selecione uma imagem", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //filtes edge-detect
        view.getmSegmentingEdge().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getFrameSelected() != null) {
                    new ControlEdge(getFrameSelected().getImage(), view);
                } else {
                    JOptionPane.showMessageDialog(view, "Selecione uma imagem", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        view.getmSegmentingThresholding().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getFrameSelected() != null) {
                    new ControlThresholding(getFrameSelected().getImage(), view);
                } else {
                    JOptionPane.showMessageDialog(view, "Selecione uma imagem", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //menu curvature 
        view.getmCurvatureChainCode().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getFrameSelected() != null) {
//                    if (getFrameSelected().getImageBorder() != null) {
                    new ControlCurvatureChainCode(getFrameSelected().getImage(), getFrameSelected().getImageBorder());
//                    } else {
//                        int response = JOptionPane.showConfirmDialog(view, "As bordas da imagem nao foram identificadas, deseja identifica-las agora?", null, JOptionPane.YES_NO_OPTION);
//                        if (response == JOptionPane.YES_OPTION) {
//                            new ControlEdgeSobel(getFrameSelected().getImage(), view);
//                        }
//                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Selecione uma imagem", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        view.getmCurvatureSignature().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getFrameSelected() != null) {
                    new ControlCurvatureSignature(getFrameSelected().getImage());
                } else {
                    JOptionPane.showMessageDialog(view, "Selecione uma imagem", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        view.getmCurvatureFourier().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getFrameSelected() != null) {
                    new ControlCurvatureFourier(getFrameSelected().getImage());
                } else {
                    JOptionPane.showMessageDialog(view, "Selecione uma imagem", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //menu recognition
        view.getmSimileImage().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ControlSimileImage();
            }
        });

        //menu recognition
        view.getmRecognitionGroup().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ControlSpeciesGroup();
            }
        });

        view.getmRecognitionPattern().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ControlPatternGeneration();
            }
        });

    }

    //metodos
    private void addFrameImage(String path) {
        try {
            File fileImage = new File(path);
            BufferedImage image = ImageIO.read(fileImage);
            JImageInternalFrame frame = new JImageInternalFrame(fileImage.getName(), image);
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
