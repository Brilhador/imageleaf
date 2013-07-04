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
    //variaveis
    private boolean signature = false;
    private int angle = 0;
    private boolean chaincode = false;
    private int width = 0;
    private int heigth = 0;
    private boolean fourier = false;
    private int series = 0;
    
    public void startAnglePattern(String caminho, String caminhoArff, boolean signature, int angle, boolean chaincode, int width, int heigth, boolean fourier, int series) {
        this.signature = signature;
        this.chaincode = chaincode;
        this.fourier = fourier;
        relation += System.currentTimeMillis();
        if (signature) {
            this.angle = angle;
            for (int i = 0; i < 360 / angle; i++) {
                attribute.add("distance" + i + " NUMERIC ");
            }
        }
        if(chaincode){
            this.width = width;
            this.heigth = heigth;
            for (int i = 0; i < 8; i++) {
                attribute.add("direction" + i + " NUMERIC ");
            }
        }
        if(fourier){
            this.series = series;
            for (int i = 0; i < series; i++) {
                attribute.add("fourier" + i + " NUMERIC ");
            }
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
                    createAnglePattern(subFiles, diretorio.getAbsolutePath(), diretorio.getName().trim().replaceAll(" ", ""));
                }
                ARFF arff = new ARFF();
                arff.create(caminhoArff, relation, attribute, classe, data);
            }
        }
    }
    
    private void createAnglePattern(File[] files, String caminho, String classe) {
        try {
            List<String> folha = new ArrayList<>();
            //contar a quantidade de imagens usadas para gerar o padrão
            int count = 0;
            int indice = 0;
            for (File file : files) {
                System.out.println(file.getName() + "--> iniciada...");
                //converter file para image
                BufferedImage image = MyImage.FileToImage(file);
                if (image != null) {
                    try {
                        ArrayList<Double> vectorFeature = new ArrayList();
                        if(signature){
                            Signature sigImage = new Signature(image, angle, true, 0, true);
                            for (Double d : sigImage.getSignature()) {
                                vectorFeature.add(d);
                            }
//                            saveImage(caminho + "/segmentacao", file.getName() + "Signature", sigImage.getImageSignature());
                        }
                        if(chaincode){
                            ChainCode chainImage = new ChainCode(image, true, width, heigth, true, true);
                            for (Double d : chainImage.getHistChainCode()) {
                                vectorFeature.add(d);
                            }
//                            saveImage(caminho + "/segmentacao", file.getName() + "ChainCode", chainImage.getChainImage());
                        }
                        if(fourier){
                            DFT dftImage = new DFT(image, true, true, true);
                            for (Double d : dftImage.getCoefficients(series)) {
                                vectorFeature.add(d);
                            }
//                            saveImage(caminho + "/segmentacao", file.getName() + "Fourier", dftImage.getImageFourier());
                        }
                        String textData = "";
                        for (double d : vectorFeature) {
                            textData += d + ",";
                        }
                        data.add(textData += classe);
                        
                        if (vectorFeature != null) {
                            try {
                                //adiciono no relatorio se a folha foi segmenteda
                                folha.add(file.getName() + " : " + "YES");
                                //incrementa o contador
                                count++;
                            } catch (Exception e) {
                                folha.add(file.getName() + " : " + "NO");
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
            System.out.println("-----------------------\n");
            System.out.println("gerando relatorios...\n");
            folha.add("folhas segmentadas: " + count);
            indice++;
            folha.add("total de folhas: " + files.length);
            indice++;
            folha.add("Porcentagem de segmentacao: " + ((((float) count) / files.length) * 100) + "%");
            System.out.println(indice);
            writingPattern(caminho + "/" + "relatorio", folha);
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
}
