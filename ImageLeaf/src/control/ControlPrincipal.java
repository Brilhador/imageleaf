/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileFilter;
import model.ChainCode;
import org.jfree.ui.ExtensionFileFilter;
import view.viewPrincipal;
import org.jdesktop.swingx.JXImageView;

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
    }

    //metodos
    private void addFrameImage(String path) {
        try {
            //criando um imageView
            JXImageView imageView = new JXImageView();
            BufferedImage image = ImageIO.read(new File(path));
            imageView.setImage(image);
            imageView.setScale(0.5);
            //criando um frame
            JInternalFrame frame = new JInternalFrame();
            frame.add(imageView, BorderLayout.CENTER);
            frame.setSize(image.getWidth()/2, image.getHeight()/2);
            frame.setResizable(true);
            frame.setClosable(true);
            frame.setVisible(true);
            //adicionando o frame 
            view.getjPanelPrincipal().add(frame);
            view.getjPanelPrincipal().validate();
        } catch (Exception ex) {
            Logger.getLogger(ControlPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private BufferedImage getImageToFrame(JFrame frame) {
        //pegar a imagem que esta dentro da internalFrame
        JXImageView imageView = (JXImageView) frame.getRootPane().getContentPane().getComponent(0);
        return (BufferedImage) imageView.getImage();
    }
}
