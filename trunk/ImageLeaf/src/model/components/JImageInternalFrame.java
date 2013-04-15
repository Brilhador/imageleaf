/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.JInternalFrame;
import org.jdesktop.swingx.JXImageView;

/**
 *
 * @author anderson
 */
public class JImageInternalFrame extends JInternalFrame {

    private JXImageView imageView = null;
    private BufferedImage image = null;
    private boolean[][] imageBorder = null;

    //contrutor
    public JImageInternalFrame(String title, BufferedImage imageIn) {
        super(title);
        image = imageIn;
        imageView = new JXImageView();
        imageView.setImage(image);
        imageView.setScale(0.5);
        this.add(imageView, BorderLayout.CENTER);
        this.setSize(image.getWidth() / 2, image.getHeight() / 2);
        this.setResizable(true);
        this.setClosable(true);
        this.setVisible(true);
    }

    public JImageInternalFrame(BufferedImage imageIn) {
        image = imageIn;
        imageView = new JXImageView();
        imageView.setImage(image);
        imageView.setScale(0.5);
        this.add(imageView, BorderLayout.CENTER);
        this.setSize(image.getWidth() / 2, image.getHeight() / 2);
        this.setResizable(true);
        this.setClosable(true);
        this.setVisible(true);
    }
    
    //metodos
    public BufferedImage getImage(){
        return image;
    }
    
    public void setImage(BufferedImage image){
        this.image = image;
        imageView.setImage(image);
        imageView.setScale(0.5);
    }

    public boolean[][] getImageBorder() {
        return imageBorder;
    }

    public void setImageBorder(boolean[][] imageBorder) {
        this.imageBorder = imageBorder;
    }
}
