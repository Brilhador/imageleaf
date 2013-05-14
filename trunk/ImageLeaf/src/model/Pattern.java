/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.Classificador.FOUR_SURFACE;
import static model.Classificador.NO_THRESHOLD;
import static model.Classificador.THRESHOLD;
import static model.Classificador.TWO_SURFACE;

/**
 *
 * @author anderson
 */
public class Pattern {

    public static void startHistChain(String caminho) {
        //A partir do caminho, aonde esta localizado o banco de folha lista se todos os diretorios
        File[] diretorios = new File(caminho).listFiles();
        for (File diretorio : diretorios) {
            //filtro para pegar somente os arquivos JPG
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".jpg");
                }
            };
            File[] subFiles = diretorio.listFiles(filter);
            //print a especie que esta sendo criado o padrao
            System.out.println(diretorio.getAbsolutePath());
            if (subFiles != null) {
                //converte file em imagens
                BufferedImage[] imagens = MyImage.FileToImage(subFiles);
                createChainPattern(imagens, diretorio.getAbsolutePath());
            }
        }
        System.out.println("P-finalizado");
    }

    private static void createChainPattern(BufferedImage[] imagens, String caminho) {
        //histograma de saida do padrão da classe
        double[] outHist = new double[8];
        //contar a quantidade de imagens usadas para gerar o padrão
        int[] dados = new int[2];
        dados[1] = imagens.length;
        for (BufferedImage image : imagens) {
            image = Filtro.passaBaixas(image, 5);
            int total = image.getWidth() * image.getHeight();
            int limiar = Limiar.otsuTreshold(Histograma.histogramaGray(image), total);
            int[][] borderDetect = Filtro.bordaSobel(image);
            boolean[][] imageBorder = Limiar.limiarizacao(borderDetect, limiar);
            int[] histChain = new ChainCode(imageBorder).getHistograma();
            if (histChain != null) {
                double[] normHistChain = Histograma.normalizacao(histChain, histChain.length);
                dados[0]++;
                for (int i = 0; i < 8; i++) {
                    outHist[i] += normHistChain[i];
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            outHist[i] /= imagens.length;
        }
        writingPattern(caminho + "/" + "dados", dados);
        writingPattern(caminho + "/" + "chainPattern", outHist);
    }
    
    //funcao que cria um txt com os dados de um histograma
    private static void writingPattern(String caminhoNome, int[] histograma) {
        try {
            FileWriter arquivo = new FileWriter(caminhoNome + ".txt");
            BufferedWriter buffer = new BufferedWriter(arquivo);
            for (int i : histograma) {
                buffer.write(i + "");
                buffer.newLine();
            }
            buffer.flush();
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(ManageDirectory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void writingPattern(String caminhoNome, double[] histograma) {
        try {
            FileWriter arquivo = new FileWriter(caminhoNome + ".txt");
            BufferedWriter buffer = new BufferedWriter(arquivo);
            for (double i : histograma) {
                buffer.write(i + "");
                buffer.newLine();
            }
            buffer.flush();
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(ManageDirectory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
