/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Dimension;
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
                createChainPattern(subFiles, diretorio.getAbsolutePath());
            }
        }
    }

    private static void createChainPattern(File[] files, String caminho) {
        try {
            //histograma de saida do padrão da classe
            double[] outFeature = new double[8];
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
                    image = Filtro.passaBaixas(image, 5);
                    //pega o total imagem
                    int total = image.getWidth() * image.getHeight();
                    //gera o histograma de tons de cinzas da imagem e depois calcula o limiar da imagem
                    int limiar = Limiar.otsuTreshold(Histograma.histogramaGray(image), total);
                    //realiza a limiarizaçao
                    boolean[][] imageBorder = Limiar.limiarizacaoBool(image, limiar);
                    //calcula o codigo da cadeia e pega o histograma de direçoes
                    int[] vectorFeature = new ChainCode(imageBorder).getHistograma();
                    if (vectorFeature != null) {
                        try {
                            //limiariza a image e desenha o contorno do chain code
                            image = drawPathChainCode(new ChainCode(imageBorder).getDimesionChainCode(), Limiar.limiarizacao(image, limiar));
                            //salvar a imagem da folha segmentada na pasta
                            saveImage(caminho + "/segmentacao", file.getName(), image);
                            //normaliza o histograma de direçoes colocando em uma escala de 0 a 1
                            double[] normFeature = Histograma.normalizacao(vectorFeature, vectorFeature.length);
                            for (int i = 0; i < 8; i++) {
                                outFeature[i] += normFeature[i];
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
            writingPattern(caminho + "/" + "chainPattern", outFeature);
            System.out.println("relatorios... finalizados!");
            System.out.println("-----------------------\n\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startAnglePattern(String caminho) {
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
                createAnglePattern(subFiles, diretorio.getAbsolutePath(), 10);
            }
        }
    }

    private static void createAnglePattern(File[] files, String caminho, int angle) {
        try {
            //histograma de saida do padrão da classe
            double[] outFeature = new double[360 / angle];
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
                    image = Filtro.passaBaixas(image, 5);
                    //pega o total imagem
                    int total = image.getWidth() * image.getHeight();
                    //gera o histograma de tons de cinzas da imagem e depois calcula o limiar da imagem
                    int limiar = Limiar.otsuTreshold(Histograma.histogramaGray(image), total);
                    //realiza a limiarizaçao
                    boolean[][] imageBorder = Limiar.limiarizacaoBool(image, limiar);
                    try {
                        //calcula o codigo da cadeia 
                        ArrayList<Dimension> listaDimension = new ChainCode(imageBorder).getDimesionChainCode();
                        int[] vectorFeature = new Signature().createSignal(listaDimension, angle);
                        if (vectorFeature != null) {
                            try {
                                //limiariza a image e desenha o contorno do chain code
                                image = drawPathChainCode(listaDimension, Limiar.limiarizacao(image, limiar));
                                //salvar a imagem da folha segmentada na pasta
                                saveImage(caminho + "/segmentacao", file.getName(), image);
                                //normaliza o histograma de direçoes colocando em uma escala de 0 a 1
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
    private static void writingPattern(String caminhoNome, double[] histograma) {
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

    private static void writingPattern(String caminhoNome, List<String> dados) {
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

    private static void saveImage(String caminho, String nome, BufferedImage image) {
        new ManageDirectory().criarDiretorio(caminho);
        try {
            ImageIO.write(image, "JPG", new File(caminho + "/" + nome));
        } catch (IOException ex) {
            Logger.getLogger(Pattern.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //desenhar o chain code
    private static BufferedImage drawPathChainCode(ArrayList<Dimension> lista, BufferedImage segImage) {
        for (Dimension dimension : lista) {
            segImage.setRGB(dimension.width, dimension.height, Color.RED.getRGB());
        }
        Dimension dimension = lista.get(0);
        segImage.setRGB(dimension.width, dimension.height, Color.GREEN.getRGB());
        segImage.setRGB(dimension.width + 1, dimension.height, Color.GREEN.getRGB());//0
        segImage.setRGB(dimension.width, dimension.height - 1, Color.GREEN.getRGB());//2
        segImage.setRGB(dimension.width - 1, dimension.height, Color.GREEN.getRGB());//4
        segImage.setRGB(dimension.width, dimension.height + 1, Color.GREEN.getRGB());//6
        return segImage;
    }
}
