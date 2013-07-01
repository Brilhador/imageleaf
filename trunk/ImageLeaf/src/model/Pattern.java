/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author anderson
 */
public class Pattern {

    private String relation = "imageClef2012";
    private ArrayList<String> attribute = new ArrayList<>();
    private ArrayList<String> classe = new ArrayList<>();
    private ArrayList<String> data = new ArrayList<>();

    public void startAnglePattern(String caminho, int angle) {
        relation += angle + System.currentTimeMillis();
        for (int i = 0; i < 360 / angle; i++) {
            attribute.add("distance" + i + " NUMERIC ");
        }
        //A partir do caminho, aonde esta localizado o banco de folha lista se todos os diretorios
        File[] diretorios = new File(caminho).listFiles();
        for (File diretorio : diretorios) {
            if (diretorio.isDirectory()) {
                classe.add(diretorio.getName().trim().replaceAll(" ", ""));
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
                    createAnglePattern(subFiles, diretorio.getAbsolutePath(), diretorio.getName().trim().replaceAll(" ", ""), angle);
                }
                ARFF arff = new ARFF();
                arff.create(caminho, relation, attribute, classe, data);
            }
        }
    }

    private void createAnglePattern(File[] files, String caminho, String classe, int angle) {
        try {
            //histograma de saida do padrão da classe
            int[] outFeature = new int[360 / angle];
            List<String> folha = new ArrayList<>();
            //contar a quantidade de imagens usadas para gerar o padrão
            int count = 0;
            int indice = 0;
            for (File file : files) {
                System.out.println(file.getName() + "--> iniciada...");
                //converter file para image
                BufferedImage image = MyImage.FileToImage(file);
                if (image != null) {
                    //aplicar o filtro para suavizar a imagem
                    image = Filtro.mediana(image, 5);
                    //pega o total imagem
                    int total = image.getWidth() * image.getHeight();
                    //gera o histograma de tons de cinzas da imagem e depois calcula o limiar da imagem
                    int limiar = Limiar.otsuTreshold(Histograma.histogramaGray(image), total);
                    //realiza a limiarizaçao
                    boolean[][] imageBorder = Limiar.limiarizacaoBool(image, limiar);
                    try {
                        //calcula o codigo da cadeia 
                        ArrayList<Dimension> listaDimension = new ChainCode(image, true, 10, 10, true, true).getBorder();
                        /*
                         * vetor normalizado
                         */
                        //assinatura normalizada
                        double[] signature = new Signature().createSignal(listaDimension, angle);
                        DFT dft = new DFT(1, signature, new double[signature.length], signature.length);
                        dft.invRotation();
                        dft.invScala();
                        double[] vectorFeature = dft.getX1();

                        String textData = "";
                        /*
                         * mudar tipo do vetor
                         */
                        for (double d : vectorFeature) {
                            textData += d + ",";
                        }
                        data.add(textData += classe);
                        if (vectorFeature != null) {
                            try {
                                //limiariza a image e desenha o contorno do chain code
                                Dimension centroide = new Signature().getCentroideMedian(listaDimension);
                                Dimension[] point = new Signature().getDimensionPoint(listaDimension, centroide, angle);
                                image = drawPathChainCode(Limiar.limiarizacao(image, limiar), listaDimension, centroide,point);
                                //salvar a imagem da folha segmentada na pasta
                                saveImage(caminho + "/segmentacao", file.getName(), image);
                                //normaliza o histograma de direçoes colocando em uma escala de 0 a 1
                                for (int i = 0; i < vectorFeature.length; i++) {
                                    outFeature[i] += vectorFeature[i];
                                }
                                //adiciono no relatorio se a folha foi segmenteda
                                folha.add(file.getName() + " : " + "YES");
                                //incrementa o contador
                                count++;
                            } catch (Exception e) {
                                folha.add(file.getName() + " : " + "NO");
                                //salvar a imagem da folha segmentada na pasta
                                image = Limiar.limiarizacao(image, limiar);
                                saveImage(caminho + "/segmentacao", file.getName(), image);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    folha.add(file.getName() + " : " + "ERRO READ");
                }
                System.out.println(file.getName() + "--> finalizada!\n");
                indice++;
            }
            for (int i = 0; i < outFeature.length; i++) {
                outFeature[i] /= files.length;
            }
            System.out.println("-----------------------\n");
            System.out.println("gerando relatorios...\n");
            folha.add("folhas segmentadas: " + count);
            indice++;
            folha.add("total de folhas: " + files.length);
            indice++;
            folha.add("Porcentagem de segmentacao: " + ((((float) count) / files.length) * 100) + "%");
            System.out.println(indice);
            writingPattern(caminho + "/" + "relatorio", folha);
            writingPattern(caminho + "/" + "signatureAngle", outFeature);
            System.out.println("relatorios... finalizados!");
            System.out.println("-----------------------\n\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //funcao que cria um txt com os dados de um histograma
    private void writingPattern(String caminhoNome, int[] histograma) {
        try {
            FileWriter arquivo = new FileWriter(caminhoNome + ".txt");
            BufferedWriter buffer = new BufferedWriter(arquivo);
            for (Object i : histograma) {
                buffer.write(i + "");
                buffer.newLine();
            }
            buffer.flush();
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(ManageDirectory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writingPattern(String caminhoNome, List<String> dados) {
        try {
            FileWriter arquivo = new FileWriter(caminhoNome + ".txt");
            BufferedWriter buffer = new BufferedWriter(arquivo);
            int c = 0;
            for (String i : dados) {
                buffer.write(i);
                buffer.newLine();
            }
            buffer.flush();
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(ManageDirectory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveImage(String caminho, String nome, BufferedImage image) {
        new ManageDirectory().criarDiretorio(caminho);
        try {
            ImageIO.write(image, "JPG", new File(caminho + "/" + nome));
        } catch (IOException ex) {
            Logger.getLogger(Pattern.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //desenhar o chain code 
    public BufferedImage drawPathChainCode(BufferedImage image, ArrayList<Dimension> lista, Dimension centroide, Dimension[] point) {
        BufferedImage drawImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = drawImage.createGraphics();
        g2d.drawImage(image, null, 0, 0);
        //desenha a linha do primeiro elemento da lista
        g2d.setColor(Color.BLUE);
        g2d.drawLine(centroide.width, centroide.height, point[0].width, point[0].height);
        for (int i = 1; i < point.length; i++) {
//            drawPoint(drawImage, point[i], Color.RED);
            g2d.setColor(Color.RED);
            g2d.drawLine(centroide.width, centroide.height, point[i].width, point[i].height);
        }
        g2d.dispose();

        for (Dimension dimension : lista) {
            drawPoint(drawImage, dimension, Color.GREEN);
        }

        drawPoint(drawImage, centroide, Color.RED);
        return drawImage;
    }

    private void drawPoint(BufferedImage drawImage, Dimension point, Color cor) {
        drawImage.setRGB(point.width, point.height, cor.getRGB());
        drawImage.setRGB(point.width + 1, point.height, cor.getRGB());//0
        drawImage.setRGB(point.width, point.height - 1, cor.getRGB());//2
        drawImage.setRGB(point.width - 1, point.height, cor.getRGB());//4
        drawImage.setRGB(point.width, point.height + 1, cor.getRGB());//6
    }
}
