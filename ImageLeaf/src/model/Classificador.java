/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Anderson
 */
public class Classificador {
    //funsao para separar todas as fotos por especie
    //Essa função é utilizada especificamente para o banco train do imageCLEF 2012

    public static void agruparEspecie(File[] arquivo, String caminho) {
        for (File file : arquivo) {
            BufferedImage image;
            XML xml = new XML();
            String especie = xml.lerXml(file, "Species");
            //pega as duas primeiras palavras para manter o padrao no xml
            especie = especie.substring(0, especie.indexOf(" ", especie.indexOf(" ") + 1));
            //pegando o nome da imagem
            String imgNome = xml.lerXml(file, "FileName");
            String tipo = xml.lerXml(file, "Type");
            //so serão selecionada as fotos que são do tipo aquisição scan
            if (tipo.equalsIgnoreCase("scan")) {
                //
                ManageDirectory dir = new ManageDirectory();
                //String diretorio = diretorioPadrao + especie + "\\" + tipo;
                String diretorio = dir.getDiretorioPadrao() + especie;
                System.out.println(diretorio);
                File fileImg = new File(diretorio + "\\" + imgNome);
                System.out.println(diretorio + "\\" + imgNome);
                if (dir.verificaDiretorio(diretorio)) {
                    try {
                        image = ImageIO.read(new File(caminho + "\\" + imgNome));
                        ImageIO.write(image, "JPG", fileImg);
                    } catch (IOException ex) {
                        Logger.getLogger(ManageDirectory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    dir.criarDiretorio(diretorio);
                    try {
                        image = ImageIO.read(new File(caminho + "\\" + imgNome));
                        ImageIO.write(image, "JPG", fileImg);
                    } catch (IOException ex) {
                        Logger.getLogger(ManageDirectory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public static void calculaHistograma(File raiz) {
        File[] diretorios = raiz.listFiles();
        for (File diretorio : diretorios) {
            //filtro para pegar somente os arquivos JPG
            FilenameFilter filter = new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".jpg");
                }
            };
            File[] subFiles = diretorio.listFiles(filter);
            if (subFiles != null) {
                System.out.println(diretorio.getAbsolutePath());
                //converte file em imagens
                BufferedImage[] imagens = Image.FileToImage(subFiles);
                //vetores que receberam os valores do histogramas calculados
                int[] hRed = Histograma.histogramaMedioRed(imagens),
                        hGreen = Histograma.histogramaMedioGreen(imagens),
                        hBlue = Histograma.histogramaMedioBlue(imagens);

                //salva os histogramas medio dentro da pasta do novo padrao
                Histograma.criarTxtHistograma(diretorio.getAbsolutePath() + "\\" + "hmR", hRed);
                Histograma.criarTxtHistograma(diretorio.getAbsolutePath() + "\\" + "hmG", hGreen);
                Histograma.criarTxtHistograma(diretorio.getAbsolutePath() + "\\" + "hmB", hBlue);

                //calculando o desvio padrao
                hRed = Histograma.histogramaDesvioPadraoRed(imagens, hRed);
                hGreen = Histograma.histogramaDesvioPadraoGreen(imagens, hGreen);
                hBlue = Histograma.histogramaDesvioPadraoBlue(imagens, hBlue);

                //salva os histogramas medio dentro da pasta do novo padrao
                Histograma.criarTxtHistograma(diretorio.getAbsolutePath() + "\\" + "hdpR", hRed);
                Histograma.criarTxtHistograma(diretorio.getAbsolutePath() + "\\" + "hdpG", hGreen);
                Histograma.criarTxtHistograma(diretorio.getAbsolutePath() + "\\" + "hdpB", hBlue);

                XML.criarXml(diretorio.getAbsolutePath(), diretorio.getName());
            }
        }
    }

    public static File classificaFolha(BufferedImage img) {
        //nome da especie a qual a folha foi classificada
        File especieOut = null;

        // gera o histograma no 3 canas da imagem
        int[] hRed = Histograma.histogramaRed(img);
        int[] hGreen = Histograma.histogramaGreen(img);
        int[] hBlue = Histograma.histogramaBlue(img);

        //resultado da soma das distancia euclidiana dos 3 canas RGB
        double soma = -1;

        //instacia um objeto manager directory
        ManageDirectory dir = new ManageDirectory();

        //Percorrer todas as especies no banco
        File[] especies = new File(dir.getDiretorioPadrao()).listFiles();
        //percorre cada diretorio e calcula a diferença
        for (File especie : especies) {
            //System.out.println(especie.getAbsoluteFile());
            //carrega o histogrma médio no vetores
            int[] hmRed = Histograma.lerTxtHistograma(especie + "\\hmR.txt");
            int[] hmGreen = Histograma.lerTxtHistograma(especie + "\\hmG.txt");
            int[] hmBlue = Histograma.lerTxtHistograma(especie + "\\hmB.txt");

            double aux = 0;

            //Calcular a distancia euclidiana para os 3 canais
            //range interfe nesses dois calculos
            aux += Distancia.Euclidiana(Histograma.normalizacao(hRed), Histograma.normalizacao(hmRed));
            aux += Distancia.Euclidiana(Histograma.normalizacao(hGreen), Histograma.normalizacao(hmGreen));
            aux += Distancia.Euclidiana(Histograma.normalizacao(hBlue), Histograma.normalizacao(hmBlue));

            if (aux < soma) {
                soma = aux;
                especieOut = especie;
            } else if (soma == -1) {
                soma = aux;
                especieOut = especie;
            }
        }
        //retorna o nome da especie a qual foi classificada
        return especieOut.getAbsoluteFile();
    }

    public static void classificaBancoFolha(File[] arquivo, String caminho) {
        CSV tabCsv = new CSV(new File("C:\\Users\\Anderson\\ImageLeaf\\confusao.csv"));
        tabCsv.readCSV();
        for (File file : arquivo) {
            BufferedImage image;
            XML xml = new XML();
            String especie = xml.lerXml(file, "Species");
            especie.trim();
            especie = especie.substring(0, especie.indexOf(" ", especie.indexOf(" ") + 1));
            String imgNome = xml.lerXml(file, "FileName");
            String tipo = xml.lerXml(file, "Type");
            //so serão selecionada as fotos que são do tipo aquisição scan
            if (tipo.equalsIgnoreCase("scan")) {
                System.out.println("V -->" + especie);
                try {
                    image = ImageIO.read(new File(caminho + "\\" + imgNome));
                    File espInf = classificaFolha(image);
                    String nomeInf = espInf.getName();
                    nomeInf.trim();
                    System.out.println("Inf -->" + nomeInf);
                    if (especie.equalsIgnoreCase(nomeInf)) {
                        for (int line = 0; line < tabCsv.getLineSize(); line++) {
                            try {
                                if (tabCsv.getCell(line, 0).equals(nomeInf)) {
                                    int valor = Integer.parseInt(tabCsv.getCell(line, line));
                                    valor++;
                                    tabCsv.setCell(line, line, valor + "");
                                }
                            } catch (NumberFormatException e) {
                                tabCsv.setCell(line, line, "0");
                            }
                        }
                    } else {
                        for (int line = 0; line < tabCsv.getLineSize(); line++) {
                            if (tabCsv.getCell(line, 0).equals(nomeInf)) {
                                for (int column = 0; column < tabCsv.getColumnSize(); column++) {
                                    try {
                                        if (tabCsv.getCell(0, column).equals(especie)) {
                                            int valor = Integer.parseInt(tabCsv.getCell(line, column));
                                            valor++;
                                            tabCsv.setCell(line, column, valor + "");
                                            break;
                                        }
                                    } catch (NumberFormatException e) {
                                        tabCsv.setCell(line, line, "0");
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Classificador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        tabCsv.writeCSV();
        System.out.println("finalizado tab-confusao");
        //calcular a precisão dos acertos
        calculaTabPrecisao(tabCsv);
    }

    public static void calculaTabPrecisao(CSV arquivo) {
        arquivo.readCSV();
        DecimalFormat fmt = new DecimalFormat("000.00");
        //calcular a precisão dos acertos
        CSV preCsv = new CSV(new File("C:\\Users\\Anderson\\ImageLeaf\\precisao.csv"));
        preCsv.readCSV();
        for (int line = 1; line < arquivo.getLineSize(); line++) {
            double soma = 0;
            double acerto = 0;
            //
            for (int column = 1; column < arquivo.getColumnSize(); column++) {
                System.out.println(arquivo.getCell(line, column) + " line=" + line + " column=" + column);
                soma += Integer.parseInt(arquivo.getCell(line, column));
                acerto = Integer.parseInt(arquivo.getCell(line, line));
                try {
                    if (soma != 0) {
                        preCsv.setCell(line, 1, fmt.format((acerto / soma) * 100));
                    }
                } catch (ArithmeticException e) {
                    preCsv.setCell(line, 1, "");
                    e.printStackTrace();
                }
            }
            preCsv.writeCSV();
            System.out.println("finalizado tab-precisao");
        }
    }
}