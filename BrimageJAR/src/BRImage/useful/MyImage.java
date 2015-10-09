/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.useful;

import BRImage.description.color.Histogram;
import BRImage.segmetation.Thresholding;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Anderson
 */
public class MyImage {

    public static BufferedImage paintPoint(BufferedImage imageOriginal, ArrayList<Coordinate> point) {
        BufferedImage imgOut = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), imageOriginal.getType());
        Graphics2D g2d = imgOut.createGraphics();
        g2d.drawImage(imageOriginal, null, 0, 0);
        for (Coordinate dimension : point) {
            drawPoint(imgOut, dimension, Color.RED);
        }
        g2d.dispose();
        return imgOut;
    }

    public static BufferedImage paintPoint(BufferedImage imageOriginal, Coordinate point) {
        BufferedImage imgOut = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), imageOriginal.getType());
        Graphics2D g2d = imgOut.createGraphics();
        g2d.drawImage(imageOriginal, null, 0, 0);
        drawPoint(imgOut, point, Color.RED);
        g2d.dispose();
        return imgOut;
    }

    public static BufferedImage paintPoint(BufferedImage imageOriginal, ArrayList<Coordinate> point, Color cor) {
        BufferedImage imgOut = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), imageOriginal.getType());
        Graphics2D g2d = imgOut.createGraphics();
        g2d.drawImage(imageOriginal, null, 0, 0);
        for (Coordinate dimension : point) {
            drawPoint(imgOut, dimension, cor);
        }
        g2d.dispose();
        return imgOut;
    }

    public static BufferedImage paintPoint(BufferedImage imageOriginal, Coordinate point, Color cor) {
        BufferedImage imgOut = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), imageOriginal.getType());
        Graphics2D g2d = imgOut.createGraphics();
        g2d.drawImage(imageOriginal, null, 0, 0);
        drawPoint(imgOut, point, cor);
        g2d.dispose();
        return imgOut;
    }

    public static BufferedImage paintCross(BufferedImage imageOriginal, Coordinate point, int tam, Color cor) {
        BufferedImage imgOut = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), imageOriginal.getType());
        Graphics2D g2d = imgOut.createGraphics();
        g2d.drawImage(imageOriginal, null, 0, 0);
        drawCross(imgOut, point, cor, tam);
        g2d.dispose();
        return imgOut;
    }

    public static BufferedImage paintCross(BufferedImage imageOriginal, ArrayList<Coordinate> point, int tam, Color cor) {
        BufferedImage imgOut = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), imageOriginal.getType());
        Graphics2D g2d = imgOut.createGraphics();
        g2d.drawImage(imageOriginal, null, 0, 0);
        for (Coordinate dimension : point) {
            drawCross(imgOut, dimension, cor, tam);
        }
        g2d.dispose();
        return imgOut;
    }

    public static BufferedImage paintLine(BufferedImage imageOriginal, ArrayList<Coordinate> point, Color cor) {
        BufferedImage imgOut = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), imageOriginal.getType());
        Graphics2D g2d = imgOut.createGraphics();
        g2d.drawImage(imageOriginal, null, 0, 0);
        g2d.setColor(cor);
        for (int i = 0; i < point.size() - 1; i++) {
            g2d.drawLine(point.get(i).getX(), point.get(i).getY(), point.get(i + 1).getX(), point.get(i + 1).getY());
        }
        g2d.dispose();
        return imgOut;
    }

    public static void drawPoint(BufferedImage drawImage, Coordinate point, Color cor) {
        if ((point.getX() + 1) < drawImage.getWidth()) {
            drawImage.setRGB(point.getX() + 1, point.getY(), cor.getRGB());//0
        }
        if ((point.getY() - 1) > 0) {
            drawImage.setRGB(point.getX(), point.getY() - 1, cor.getRGB());//2
        }
        if ((point.getX() - 1) > 0) {
            drawImage.setRGB(point.getX() - 1, point.getY(), cor.getRGB());//4
        }
        if ((point.getY() + 1) < drawImage.getHeight()) {
            drawImage.setRGB(point.getX(), point.getY() + 1, cor.getRGB());//6
        }
    }

    public static void drawCross(BufferedImage drawImage, Coordinate point, Color cor, int tam) {
        drawImage.setRGB(point.getX(), point.getY(), cor.getRGB());
        if ((point.getX() + tam) < drawImage.getWidth()) {//2
            for (int i = 1; i < tam; i++) {
                drawPoint(drawImage, new Coordinate(point.getX() + i, point.getY()), cor);
            }
        }

        if ((point.getX() - tam) > 0) {
            for (int i = 1; i < tam; i++) {//4
                drawPoint(drawImage, new Coordinate(point.getX() - i, point.getY()), cor);
            }
        }

        if ((point.getY() - tam) > 0) {
            for (int i = 1; i < tam; i++) {
                drawPoint(drawImage, new Coordinate(point.getX(), point.getY() - i), cor);
            }
        }

        if ((point.getY() + tam) < drawImage.getHeight()) {
            for (int i = 1; i < tam; i++) {
                drawPoint(drawImage, new Coordinate(point.getX(), point.getY() + i), cor);
            }
        }
    }

    //salvar imagem
    public static boolean salvar(BufferedImage image, File file) {
        try {
            return ImageIO.write(image, "JPG", file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean salvar(BufferedImage image, String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            return ImageIO.write(image, "JPG", new File(path));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean salvarPNG(BufferedImage image, File file) {
        try {
            return ImageIO.write(image, "PNG", file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean salvarPNG(BufferedImage image, String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            return ImageIO.write(image, "PNG", new File(path));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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

    //rotacionar
    public static BufferedImage rotateImage(BufferedImage rotateImage, double angle) {
        angle %= 360;
        if (angle < 0) {
            angle += 360;
        }

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(angle), rotateImage.getWidth() / 2.0, rotateImage.getHeight() / 2.0);

        double ytrans = 0;
        double xtrans = 0;
        if (angle <= 90) {
            xtrans = tx.transform(new Point2D.Double(0, rotateImage.getHeight()), null).getX();
            ytrans = tx.transform(new Point2D.Double(0.0, 0.0), null).getY();
        } else if (angle <= 180) {
            xtrans = tx.transform(new Point2D.Double(rotateImage.getWidth(), rotateImage.getHeight()), null).getX();
            ytrans = tx.transform(new Point2D.Double(0, rotateImage.getHeight()), null).getY();
        } else if (angle <= 270) {
            xtrans = tx.transform(new Point2D.Double(rotateImage.getWidth(), 0), null).getX();
            ytrans = tx.transform(new Point2D.Double(rotateImage.getWidth(), rotateImage.getHeight()), null).getY();
        } else {
            xtrans = tx.transform(new Point2D.Double(0, 0), null).getX();
            ytrans = tx.transform(new Point2D.Double(rotateImage.getWidth(), 0), null).getY();
        }

        AffineTransform translationTransform = new AffineTransform();
        translationTransform.translate(-xtrans, -ytrans);
        tx.preConcatenate(translationTransform);

        return new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR).filter(rotateImage, null);
    }

    public static BufferedImage[] FileToImage(File[] arquivos) {
        BufferedImage[] imagens = new BufferedImage[arquivos.length];
        for (int i = 0; i < arquivos.length; i++) {
            try {
                ImageInputStream stream = new FileImageInputStream(arquivos[i]);
                imagens[i] = ImageIO.read(stream);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return imagens;
    }

    public static BufferedImage FileToImage(File arquivo) {
        try {
            ImageInputStream stream = new FileImageInputStream(arquivo);
            BufferedImage outImage = ImageIO.read(stream);
            return outImage;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyImage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    public static BufferedImage extractObj(BufferedImage img) {
        //Imagem de saida
        BufferedImage imgOut = img;

        //histograma
        int[] histograma = Histogram.histogramaGray(img);

        //total de pixels da imagem
        int total = img.getWidth() * img.getHeight();

        boolean[][] objBool = Thresholding.limiarizacaoBool(img, Thresholding.otsuTreshold(histograma, total));

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if (!objBool[x][y]) {
                    imgOut.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        return imgOut;
    }

    public BufferedImage recortarObjeto(BufferedImage imagem, boolean[][] borda) {
        // proporcoes
        int largura = imagem.getWidth();
        int altura = imagem.getHeight();

        int xmin = largura;
        int xmax = 0;
        int ymin = altura;
        int ymax = 0;

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                if (!borda[x][y]) {
                    if (x < xmin) {
                        xmin = x;
                    }
                    if (x > xmax) {
                        xmax = x;
                    }
                    if (y < ymin) {
                        ymin = y;
                    }
                    if (y > ymax) {
                        ymax = y;
                    }
                }
            }
        }

        return imagem.getSubimage(xmin, ymin, xmax, ymax);
    }

}
