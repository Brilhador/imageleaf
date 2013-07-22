/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.image.Raster;
import java.awt.image.renderable.ParameterBlock;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;

/**
 *
 * @author Thiago
 */
public class Histograms {
    
    PlanarImage img;
    ArrayList<Integer> imgVet = new ArrayList<Integer>();
    Raster rs;
    
    public Histograms(PlanarImage img) {
        this.img = img;
        rs = img.getData();
        
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                imgVet.add(rs.getSample(i, j, 0));
            }
        }
    }
    
    public int[] gerarHistograma(int bits) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(img);
        pb.add(null);
        pb.add(1);
        pb.add(1);
        pb.add(new int[]{bits});
        pb.add(new double[]{0});
        pb.add(new double[]{256});
        
        RenderedOp op = JAI.create("histogram", pb, null);
        Histogram histogram = (Histogram) op.getProperty("histogram");
        
        // captura conteudo do histograma
        int[] local_array = new int[histogram.getNumBins(0)];
        for (int i = 0; i < histogram.getNumBins(0); i++) {
        local_array[i] = histogram.getBinSize(0, i);
        }
         
        return local_array;
    }
    
    public int[] gerarHistograma() {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(img);
        pb.add(null);
        pb.add(1);
        pb.add(1);
        pb.add(new int[]{256});
        pb.add(new double[]{0});
        pb.add(new double[]{256});
        
        RenderedOp op = JAI.create("histogram", pb, null);
        Histogram histogram = (Histogram) op.getProperty("histogram");

        // captura conteudo do histograma
        int[] local_array = new int[histogram.getNumBins(0)];
        for (int i = 0; i < histogram.getNumBins(0); i++) {
            local_array[i] = histogram.getBinSize(0, i);
        }
        
        return local_array;
    }
    
    public int getTotalPxHistograma() {
        int[] histograma = this.gerarHistograma();
        
        int soma = 0;
        for (int i : histograma) {
            soma += i;
        }
        
        return soma;
    }
    
    public int getTotalPxImg() {
        int soma = 0;
        
        for (int i : imgVet) {
            soma += i;
        }
        
        return soma;
    }
    
    public double getMediana() {
        int tamanhoImg;
        double med;
        int meio;
        boolean par;
        
        Collections.sort(imgVet);
        
        tamanhoImg = imgVet.size();
        meio = tamanhoImg / 2;
        
        if ((tamanhoImg % 2) == 0) {
            par = true;
        } else {
            par = false;
        }
        
        if (par) {
            med = (imgVet.get(meio - 1) + imgVet.get(meio + 1)) / 2;
        } else {
            med = imgVet.get(meio);
        }
        
        return med;
    }
    
    public double getMedia() {
        
        double tamanho = img.getHeight() * img.getWidth();
        double soma = this.getTotalPxImg();
        double media = soma / tamanho;
        
        return media;
        
    }
    
    public double getDesvioPadrao() {
        double media = this.getMedia();
        ArrayList<Double> imgDesvio = new ArrayList<Double>();
        ArrayList<Double> imgQdDesvio = new ArrayList<Double>();
        double varianca = 0.0;
        
        for (int i : imgVet) {
            imgDesvio.add(i - media);
        }
        
        for (double i : imgDesvio) {
            imgQdDesvio.add(i * i);
        }
        
        Double soma = 0.0;
        for (double i : imgQdDesvio) {
            soma += i;
        }
        
        varianca = soma / (img.getHeight() * img.getWidth());
        
        return Math.sqrt(varianca);
    }    
}
