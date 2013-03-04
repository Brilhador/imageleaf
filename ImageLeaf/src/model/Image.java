/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Anderson
 */
public class Image {

    //redimensiona um array de imagens
    public static BufferedImage[] resizeImages(File[] arquivos, int width, int heigth) {
        BufferedImage[] imagens = new BufferedImage[arquivos.length];

        for (int i = 0; i < arquivos.length; i++) {
            try {
                File file = arquivos[i];
                BufferedImage img = ImageIO.read(file);
                BufferedImage resizeImg = new BufferedImage(width, heigth, img.getType());
                Graphics2D g2d = resizeImg.createGraphics();
                g2d.drawImage(img, 0, 0, width, heigth, null);
                g2d.dispose();
                imagens[i] = img;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return imagens;
    }

    //altera o tamanho da imagem
    public static BufferedImage resizeImage(BufferedImage img, int width, int heigth) {
        BufferedImage resizeImg = new BufferedImage(width, heigth, img.getType());
        Graphics2D g2d = resizeImg.createGraphics();
        g2d.drawImage(img, 0, 0, width, heigth, null);
        g2d.dispose();
        return resizeImg;
    }

    public static BufferedImage[] FileToImage(File[] arquivos) {
        BufferedImage[] imagens = new BufferedImage[arquivos.length];
        for (int i = 0; i < arquivos.length; i++) {
            try {
                ImageInputStream stream = new FileImageInputStream(arquivos[i]);
                imagens[i] = ImageIO.read(stream);
            } catch (IOException ex) {
                Logger.getLogger(ManageDirectory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return imagens;
    }
    
    public static BufferedImage convertToGray(BufferedImage img) {
        BufferedImage imgOut = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int red = (int) Color.getColor("red", img.getRGB(x, y)).getRed();
                int green = (int) Color.getColor("green", img.getRGB(x, y)).getGreen();
                int blue = (int) Color.getColor("blue", img.getRGB(x, y)).getBlue();

                int media = (red + green + blue) / 3;

                imgOut.setRGB(x, y, new Color(media, media, media).getRGB());
            }
        }
        return imgOut;
    }
}
