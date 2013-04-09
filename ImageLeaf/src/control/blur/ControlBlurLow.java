/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.blur;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import model.Filtro;
import model.MyImage;
import org.jdesktop.swingx.JXImageView;
import view.blur.ViewBlurLow;
import view.viewPrincipal;

/**
 *
 * @author anderson
 */
public class ControlBlurLow {
    
    private viewPrincipal parentFrame = null;
    private ViewBlurLow view = null;
    private BufferedImage image = null;
    BufferedImage filterImage = null;
    
    
    //constructor
    public ControlBlurLow(BufferedImage image, viewPrincipal parentFrame){
        this.image = image;
        this.parentFrame = parentFrame;
        view = new ViewBlurLow();
        view.setLocationRelativeTo(null);
        initComponents();
        view.setVisible(true);
    }
    
    private void initComponents(){
        
        //carrega a image na preview
        carregaImagePreview(image);
        
        view.getBtnPreview().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                    filterImage = Filtro.passaBaixas(image, view.getSlMaskSize().getValue()); 
                    carregaImagePreview(filterImage);
            }
        });
        
        view.getBtnRestore().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carregaImagePreview(image);
            }
        });
        
        view.getBtnApply().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                filterImage = Filtro.passaBaixas(image, view.getSlMaskSize().getValue()); 
                JInternalFrame frame = parentFrame.getjPanelPrincipal().getSelectedFrame();
                JXImageView imageView = (JXImageView) frame.getRootPane().getContentPane().getComponent(0);
                imageView.setImage(filterImage);
                imageView.setScale(0.5);
                //adicionado ao frame
                frame.add(imageView, BorderLayout.CENTER);
                frame.setSize(filterImage.getWidth()/2, filterImage.getHeight()/2);
                parentFrame.repaint();
                view.dispose();
            }
        });
        
    }
    
    //carregar image no lblImagePreview
    public void carregaImagePreview(BufferedImage image){
        int width = view.getLblImagePreview().getWidth();
        int heigth = view.getLblImagePreview().getHeight();
        BufferedImage resizeImage = MyImage.resizeImage(image, width, heigth);
        view.getLblImagePreview().setIcon(new ImageIcon(resizeImage));
    }
    
}
