/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JToolBar;
import org.jdesktop.swingx.JXImageView;

/**
 *
 * @author anderson
 */
public class JImageInternalFrame extends JInternalFrame {

    private JXImageView imageView = null;
    private ToolBar toolBar = null;
    private BufferedImage firstImage = null;
    private BufferedImage image = null;
    private boolean[][] imageBorder = null;

    //contrutor
    public JImageInternalFrame(String title, BufferedImage imageIn) {
        super(title);
        firstImage = imageIn;
        image = imageIn;
        //adding JXImageVIew in Jframe
        imageView = new JXImageView();
        imageView.setImage(image);
        imageView.setScale(0.5);
        this.add(imageView, BorderLayout.CENTER);
        //adding JToolBar
        toolBar = new ToolBar();
        this.add(toolBar, BorderLayout.BEFORE_FIRST_LINE);
        this.setSize(image.getWidth() / 2, image.getHeight() / 2);
        this.setResizable(true);
        this.setClosable(true);
        this.setVisible(true);
    }

    public JImageInternalFrame(BufferedImage imageIn) {
        firstImage = imageIn;
        image = imageIn;
        //adding JXImageVIew in Jframe
        imageView = new JXImageView();
        imageView.setImage(image);
        imageView.setScale(0.5);
        this.add(imageView, BorderLayout.CENTER);
        //adding JToolBar
        toolBar = new ToolBar();
        this.add(toolBar, BorderLayout.BEFORE_FIRST_LINE);
        this.setSize(image.getWidth() / 2, image.getHeight() / 2);
        this.setResizable(true);
        this.setClosable(true);
        this.setVisible(true);
    }

    //metodos
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
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
    
    class ToolBar extends JToolBar{
        
        private JButton btnInZoom = null;
        private JButton btnOutZoom = null;
        private JButton btnRestore = null;
        
        public ToolBar(){
            super();
            btnRestore = new JButton("Restore");
            btnInZoom = new JButton("Zoom ++");
            btnOutZoom = new JButton("Zoom --");
            initComponents();
            add(btnRestore);
            add(btnInZoom);
            add(btnOutZoom);
        }
        
        public void initComponents(){
            
            btnRestore.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    image = firstImage;
                    imageView.setImage(firstImage);
                    imageView.setScale(0.5);
                    imageBorder = null;
                }
            });
            
            btnInZoom.addActionListener(imageView.getZoomInAction());
            
            btnOutZoom.addActionListener(imageView.getZoomOutAction());
        }
        
    }
}