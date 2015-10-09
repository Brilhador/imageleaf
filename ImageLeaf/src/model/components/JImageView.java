/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import model.MyImage;
import org.jdesktop.swingx.JXImageView;

/**
 *
 * @author anderson
 */
public class JImageView extends JXImageView{
    
    
    public JImageView(){
        super();
        initEvent();
    }
    
    public void initEvent(){
        
        //evento zoom com click do mouse
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getButton() == MouseEvent.BUTTON1){
                    if(e.getClickCount() == 2){
                        setScale(getScale() * 1.2);
                    }
                }else if (e.getButton() == MouseEvent.BUTTON3){
                    if(e.getClickCount() == 2){
                        setScale(getScale() * 0.8);
                    }
                }else if(e.getButton() == MouseEvent.BUTTON2){
                    if(e.getClickCount() == 1){
                        setScale(1.0);
                    }
                }
            }
        });
    }
    
    //metodos  
    public void setImageResize(BufferedImage image){
        BufferedImage resizeImage = MyImage.resizeImage(image, getWidth(), getHeight());
        setImage(resizeImage);
    }
    
}
