/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.segmetation.superpixel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Vector;
import javax.imageio.ImageIO;

/**
 * @author tejopa, 2014
 * @version 1 http://popscan.blogspot.com
 */

public class Superpixel {

    // arrays to store values during process 

    double[] distances;
    int[] labels;
    int[] reds;
    int[] greens;
    int[] blues;

    Cluster[] clusters;

    BufferedImage img = null;
    BufferedImage dstImage = null;

    // in case of instable clusters, max number of loops 
    int maxClusteringLoops = 50;

    /**
     * @param args
     */
    public Superpixel(BufferedImage inImage, double S, double m) {

        img = inImage;
        dstImage = calculate(img, S, m);
        // save the resulting image 
//        saveImage(dst, dstImage); 
    }

    public Superpixel() {
    }

    public BufferedImage getDstImage() {
        return dstImage;
    }

    public Cluster[] getClusters() {
        return clusters;
    }

    public int[] getLabels() {
        return labels;
    }
    
    public BufferedImage calculate(BufferedImage image,
            double S, double m) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage result = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_RGB);
        long start = System.currentTimeMillis();

        // Pegando os pixels da imagem
        int[] pixels = image.getRGB(0, 0, w, h, null, 0, w);

        //Criando e preenchendo a tabela de pesquisa
        distances = new double[w * h];
        Arrays.fill(distances, Integer.MAX_VALUE);
        labels = new int[w * h];
        Arrays.fill(labels, -1);
        
        
        //Divisão dos valores RGB
        reds = new int[w * h];
        greens = new int[w * h];
        blues = new int[w * h];
        
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int pos = x + y * w;
                int color = pixels[pos];
                reds[pos] = color >> 16 & 0x000000FF;
                greens[pos] = color >> 8 & 0x000000FF;
                blues[pos] = color >> 0 & 0x000000FF;
            }
        }

        //Criando os cluster
        createClusters(image, S, m);
        
        //Laço até que todos os cluster estejam estaveis
        //Até quando os cluster não sofram alteração
        int loops = 0;
        boolean pixelChangedCluster = true;
        
        while (pixelChangedCluster && loops < maxClusteringLoops) {
            pixelChangedCluster = false;
            loops++;
            
            //Para cada centro de Cluster C
            for (int i = 0; i < clusters.length; i++) {
                
                Cluster c = clusters[i];
                // for each pixel i in 2S region around 
                //Para cada pixel i em torno da região 2S
                //Centro do cluster
                int xs = Math.max((int) (c.avg_x - S), 0);
                int ys = Math.max((int) (c.avg_y - S), 0);
                int xe = Math.min((int) (c.avg_x + S), w);
                int ye = Math.min((int) (c.avg_y + S), h);
                
                for (int y = ys; y < ye; y++) {
                    for (int x = xs; x < xe; x++) {
                        
                        int pos = x + w * y;
                        
                        double D = c.distance(x, y, reds[pos], greens[pos], blues[pos], S, m, w, h);
                        
                        if ((D < distances[pos]) && (labels[pos] != c.id)) {
                            distances[pos] = D;
                            labels[pos] = c.id;
                            pixelChangedCluster = true;
                        }
                    } // fim para x
                } // fim para y 
            } // fim para os cluster
            
            // reseta os cluster
            for (int index = 0; index < clusters.length; index++) {
                clusters[index].reset();
            }

            //adiciona cada pixel ao cluster baseado no rótulo
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int pos = x + y * w;
                    clusters[labels[pos]].addPixel(x, y, reds[pos], greens[pos], blues[pos]);
                }
            }

            //calcula os centros dos cluster
            for (int index = 0; index < clusters.length; index++) {
                clusters[index].calculateCenter();
            }
        }

        //Cria a imagem de saídas com as bordas dos pixels
        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {
                int id1 = labels[x + y * w];
                int id2 = labels[(x + 1) + y * w];
                int id3 = labels[x + (y + 1) * w];
                
                if (id1 != id2 || id1 != id3) {
                    result.setRGB(x, y, 0x000000);
                    //result.setRGB(x-1, y, 0x000000); 
                    //result.setRGB(x, y-1, 0x000000); 
                    //result.setRGB(x-1, y-1, 0x000000); 
                } else {
                    result.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }
 
        //Marca o centro do superpixel com um pixel vermelho
        for (int i = 0; i < clusters.length; i++) {
            Cluster c = clusters[i];
            result.setRGB((int) c.avg_x, (int) c.avg_y, Color.red.getRGB()); 
        }

        long end = System.currentTimeMillis();
        System.out.println("Clustered to " + clusters.length
                + " superpixels in " + loops
                + " loops in " + (end - start) + " ms.");
        return result;
    }

    /* 
     * Cria os cluster iniciais
     */
    public void createClusters(BufferedImage image,
            double S, double m) {
        Vector<Cluster> temp = new Vector<Cluster>();
        
        int w = image.getWidth();
        int h = image.getHeight();
        
        boolean even = false;
        double xstart = 0;
        int id = 0;
        
        for (double y = S / 2; y < h; y += S) {
            // alternate clusters x-position 
            //para criar uma grade haxagonal agradavel
            if (even) {
                xstart = S / 2.0;
                even = false;
            } else {
                xstart = S;
                even = true;
            }
            for (double x = xstart; x < w; x += S) {
                int pos = (int) (x + y * w);
                Cluster c = new Cluster(id,
                        reds[pos], greens[pos], blues[pos],
                        (int) x, (int) y, S, m);
                temp.add(c);
                id++;
            }
        }
        
        clusters = new Cluster[temp.size()];
        
        for (int i = 0; i < temp.size(); i++) {
            clusters[i] = temp.elementAt(i);
        }
    }

    /**
     * @param filename
     * @param image
     */
    public static void saveImage(String filename,
            BufferedImage image) {
        File file = new File(filename);
        try {
            ImageIO.write(image, "png", file);
        } catch (Exception e) {
            System.out.println(e.toString() + " Image '" + filename
                    + "' saving failed.");
        }
    }

    /**
     * @param filename
     * @return
     */
    public static BufferedImage loadImage(String filename) {
        BufferedImage result = null;
        try {
            result = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.out.println(e.toString() + " Image '"
                    + filename + "' not found.");
        }
        return result;
    }
}
