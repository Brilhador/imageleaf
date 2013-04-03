/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import static model.Histograma.*;

/**
 * PDI - UTFPR
 *
 * @author Anderson
 */
public class Classificador {

    //CONSTANTES UTILIZADAS PARA SELECIONAR O MODO DE EXTRACAO DE INFORMACAO
    public static final int NO_THRESHOLD = 0;
    public static final int THRESHOLD = 1;
    public static final int TWO_SURFACE = 3;
    public static final int FOUR_SURFACE = 4;
    
    //CONSTANTES UTILIZADAS PARA SELECIONAR O CALCULO D DISTANCIA ENTRE OS HISTOGRAMAS
    public static final int EUCLIDIANA = 0;
    public static final int MANHATTAN = 1;
    
    //CONSTANTES UTILIZADAS PARA SELECIONAR QUAL SUPERFICIE SERA CONSIDERADA NA CLASSIFICAÇAO
    public static final int TOTAL = 0;
    public static final int INFERIOR = 1;
    public static final int SUPERIOR = 2;
    
    
    /**
     * @function Para agrupar as imagens do Banco ImageCLEF 2012 por especie e
     * por tipo da imagem (Scan)
     * @param File[] arquivo
     * @param String caminho
     */
    public static void agruparEspecie(String caminho, String nome) {
        //para pegar somente os arquivos xml
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        };
        //pega todos arquivos xml e passa para um vetor de files
        File[] arquivos = new File(caminho).listFiles(filter);
        for (File file : arquivos) {
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
                //String diretorio = diretorioPadrao + especie + "/" + tipo;
                String diretorio = dir.getDiretorioPadrao() + nome + "/" + especie;
                System.out.println(diretorio);
                File fileImg = new File(diretorio + "/" + imgNome);
                System.out.println(diretorio + "/" + imgNome);
                if (dir.verificaDiretorio(diretorio)) {
                    try {
                        System.out.println(caminho + "/" + imgNome);
                        image = ImageIO.read(new File(caminho + "/" + imgNome));
                        ImageIO.write(image, "JPG", fileImg);
                    } catch (IOException ex) {
                        Logger.getLogger(ManageDirectory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    dir.criarDiretorio(diretorio);
                    try {
                        image = ImageIO.read(new File(caminho + "/" + imgNome));
                        ImageIO.write(image, "JPG", fileImg);
                    } catch (IOException ex) {
                        Logger.getLogger(ManageDirectory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    /**
     * @function calcula o histograma medio e de desvio padrao de uma especie,
     * alocadas dentro de um diretorio especifico.
     * @param File
     */
    public static void criaPadrao(String caminho, int mode) {
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
                switch (mode) {
                    case NO_THRESHOLD:
                        criaPadraoNoThreshold(imagens, diretorio.getAbsolutePath());
                        break;
                    case THRESHOLD:
                        criaPadraoThreshold(imagens, diretorio.getAbsolutePath());
                        break;
                    case TWO_SURFACE:
                        criaPadraoTwoSurface(imagens, diretorio.getAbsolutePath());
                        break;
                    case FOUR_SURFACE:
                        criaPadraoFourSurface(imagens, diretorio.getAbsolutePath());
                        break;
                    default:
                        System.out.println("MODO DE PADRAO NAO IDENTIFICADO!");
                }
            }
        }
    }

    private static void criaPadraoNoThreshold(BufferedImage[] imagens, String caminho) {
        //vetores que receberam os valores do histogramas calculados
        int[] hRed = Histograma.histogramaMedioRed(imagens, false),
                hGreen = Histograma.histogramaMedioGreen(imagens, false),
                hBlue = Histograma.histogramaMedioBlue(imagens, false);

        //salva os histogramas medio dentro da pasta do novo padrao
        Histograma.criarTxtHistograma(caminho + "/" + "hmRnoT", hRed);
        Histograma.criarTxtHistograma(caminho + "/" + "hmGnoT", hGreen);
        Histograma.criarTxtHistograma(caminho + "/" + "hmBnoT", hBlue);

        //calculando o desvio padrao
        hRed = Histograma.histogramaDesvioPadraoRed(imagens, hRed, false);
        hGreen = Histograma.histogramaDesvioPadraoGreen(imagens, hGreen, false);
        hBlue = Histograma.histogramaDesvioPadraoBlue(imagens, hBlue, false);

        //salva os histogramas de desvio padrao dentro da pasta do novo padrao
        Histograma.criarTxtHistograma(caminho + "/" + "hdpRnoT", hRed);
        Histograma.criarTxtHistograma(caminho + "/" + "hdpGnoT", hGreen);
        Histograma.criarTxtHistograma(caminho + "/" + "hdpBnoT", hBlue);
    }

    private static void criaPadraoThreshold(BufferedImage[] imagens, String caminho) {
        //vetores que receberam os valores do histogramas calculados
        int[] hRed = Histograma.histogramaMedioRed(imagens, true),
                hGreen = Histograma.histogramaMedioGreen(imagens, true),
                hBlue = Histograma.histogramaMedioBlue(imagens, true);

        //salva os histogramas medio dentro da pasta do novo padrao
        Histograma.criarTxtHistograma(caminho + "/" + "hmRyesT", hRed);
        Histograma.criarTxtHistograma(caminho + "/" + "hmGyesT", hGreen);
        Histograma.criarTxtHistograma(caminho + "/" + "hmByesT", hBlue);

        //calculando o desvio padrao
        hRed = Histograma.histogramaDesvioPadraoRed(imagens, hRed, true);
        hGreen = Histograma.histogramaDesvioPadraoGreen(imagens, hGreen, true);
        hBlue = Histograma.histogramaDesvioPadraoBlue(imagens, hBlue, true);

        //salva os histogramas de desvio padrao dentro da pasta do novo padrao
        Histograma.criarTxtHistograma(caminho + "/" + "hdpRyesT", hRed);
        Histograma.criarTxtHistograma(caminho + "/" + "hdpGyesT", hGreen);
        Histograma.criarTxtHistograma(caminho + "/" + "hdpByesT", hBlue);
    }

    private static void criaPadraoTwoSurface(BufferedImage[] imagens, String caminho) {
        BufferedImage[] imgsAuxSup = new BufferedImage[imagens.length];
        BufferedImage[] imgsAuxInf = new BufferedImage[imagens.length];
        for (int i = 0; i < imagens.length; i++) {
            BufferedImage img = imagens[i];
            imgsAuxSup[i] = img.getSubimage(0, 0, img.getWidth(), img.getHeight() / 2);
            imgsAuxInf[i] = img.getSubimage(0, img.getHeight() / 2, img.getWidth(), img.getHeight() / 2);
        }
        // 1 = Superior
        // 2 = Inferior
        calculaThresholdTwoSuf(imgsAuxSup, caminho, 1);
        calculaThresholdTwoSuf(imgsAuxInf, caminho, 2);
    }

    private static void calculaThresholdTwoSuf(BufferedImage[] imagens, String caminho, int label) {
        //vetores que receberam os valores do histogramas calculados
        int[] hRed = Histograma.histogramaMedioRed(imagens, true),
                hGreen = Histograma.histogramaMedioGreen(imagens, true),
                hBlue = Histograma.histogramaMedioBlue(imagens, true);

        //salva os histogramas medio dentro da pasta do novo padrao
        Histograma.criarTxtHistograma(caminho + "/" + "hmRtwoS" + label, hRed);
        Histograma.criarTxtHistograma(caminho + "/" + "hmGtwoS" + label, hGreen);
        Histograma.criarTxtHistograma(caminho + "/" + "hmBtwoS" + label, hBlue);

        //calculando o desvio padrao
        hRed = Histograma.histogramaDesvioPadraoRed(imagens, hRed, true);
        hGreen = Histograma.histogramaDesvioPadraoGreen(imagens, hGreen, true);
        hBlue = Histograma.histogramaDesvioPadraoBlue(imagens, hBlue, true);

        //salva os histogramas de desvio padrao dentro da pasta do novo padrao
        Histograma.criarTxtHistograma(caminho + "/" + "hdpRtwoS" + label, hRed);
        Histograma.criarTxtHistograma(caminho + "/" + "hdpGtwoS" + label, hGreen);
        Histograma.criarTxtHistograma(caminho + "/" + "hdpBtwoS" + label, hBlue);
    }

    private static void criaPadraoFourSurface(BufferedImage[] imagens, String caminho) {
        BufferedImage[] imgsAuxSupEsq = new BufferedImage[imagens.length];
        BufferedImage[] imgsAuxSupDir = new BufferedImage[imagens.length];
        BufferedImage[] imgsAuxInfEsq = new BufferedImage[imagens.length];
        BufferedImage[] imgsAuxInfDir = new BufferedImage[imagens.length];
        for (int i = 0; i < imagens.length; i++) {
            BufferedImage img = imagens[i];
            imgsAuxSupEsq[i] = img.getSubimage(0, 0, img.getWidth() / 2, img.getHeight() / 2);
            imgsAuxSupDir[i] = img.getSubimage(img.getWidth() / 2, 0, img.getWidth() / 2, img.getHeight() / 2);
            imgsAuxInfEsq[i] = img.getSubimage(0, img.getHeight() / 2, img.getWidth() / 2, img.getHeight() /2);
            imgsAuxInfDir[i] = img.getSubimage(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2, img.getHeight() / 2);
        }
        //1 = Superior a esquerda
        //2 = Superior a direita
        //3 = Inferior a esquerda
        //4 = Inferior a direita
        calculaThresholdFourSuf(imgsAuxSupEsq, caminho, 1);
        calculaThresholdFourSuf(imgsAuxSupDir, caminho, 2);
        calculaThresholdFourSuf(imgsAuxInfEsq, caminho, 3);
        calculaThresholdFourSuf(imgsAuxInfDir, caminho, 4);
    }

    private static void calculaThresholdFourSuf(BufferedImage[] imagens, String caminho, int label) {
        //vetores que receberam os valores do histogramas calculados
        int[] hRed = Histograma.histogramaMedioRed(imagens, true),
                hGreen = Histograma.histogramaMedioGreen(imagens, true),
                hBlue = Histograma.histogramaMedioBlue(imagens, true);

        //salva os histogramas medio dentro da pasta do novo padrao
        Histograma.criarTxtHistograma(caminho + "/" + "hmRFourS" + label, hRed);
        Histograma.criarTxtHistograma(caminho + "/" + "hmGFourS" + label, hGreen);
        Histograma.criarTxtHistograma(caminho + "/" + "hmBFourS" + label, hBlue);

        //calculando o desvio padrao
        hRed = Histograma.histogramaDesvioPadraoRed(imagens, hRed, true);
        hGreen = Histograma.histogramaDesvioPadraoGreen(imagens, hGreen, true);
        hBlue = Histograma.histogramaDesvioPadraoBlue(imagens, hBlue, true);

        //salva os histogramas de desvio padrao dentro da pasta do novo padrao
        Histograma.criarTxtHistograma(caminho + "/" + "hdpRFourS" + label, hRed);
        Histograma.criarTxtHistograma(caminho + "/" + "hdpGFourS" + label, hGreen);
        Histograma.criarTxtHistograma(caminho + "/" + "hdpBFourS" + label, hBlue);
    }

    public static File classificaFolha(BufferedImage img, int tipo, int distancia, int surperficie) {

        switch (tipo) {
            case NO_THRESHOLD:
                return clasFolhaNoThreshold(img, distancia);
            case THRESHOLD:
                return clasFolhaThreshold(img, distancia);
            case TWO_SURFACE:
                return clasFolhaTwoSurface(img, distancia, surperficie);
            case FOUR_SURFACE:
                return clasFolhaFourSurface(img, distancia);
            default:
                return null;
        }

    }
    
    //0 = euclidiana
    //1 = manhattan
    private static File clasFolhaNoThreshold(BufferedImage img, int distancia) {
        //nome da especie a qual a folha foi classificada
        File especieOut = null;

        //resultado da soma das distancia euclidiana dos 3 canas RGB
        double soma = -1;

        //instacia um objeto manager directory
        ManageDirectory dir = new ManageDirectory();

        //histograma geral da imagem
        int[] hRed = histogramaRed(img);
        int[] hGreen = histogramaGreen(img);
        int[] hBlue = histogramaBlue(img);

        //local aonde os padroes estao armazenado
        File[] especies = new File(dir.getDiretorioPadrao() + "Especies/").listFiles();

        for (File especie : especies) {
            //carrega o histogrma médio no vetores
            int[] hmRed = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmRnoT.txt");
            int[] hmGreen = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmGnoT.txt");
            int[] hmBlue = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmBnoT.txt");

            double aux = 0;

            //Calcular a distancia euclidiana para os 3 canais
            //range interfe nesses dois calculos
            switch(distancia){
                case EUCLIDIANA:
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hRed, 256), Histograma.normalizacao(hmRed, 256));
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hGreen, 256), Histograma.normalizacao(hmGreen, 256));
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hBlue, 256), Histograma.normalizacao(hmBlue, 256));
                    break;
                case MANHATTAN:
                    aux += Distancia.Manhattan(Histograma.normalizacao(hRed, 256), Histograma.normalizacao(hmRed, 256));
                    aux += Distancia.Manhattan(Histograma.normalizacao(hGreen, 256), Histograma.normalizacao(hmGreen, 256));
                    aux += Distancia.Manhattan(Histograma.normalizacao(hBlue, 256), Histograma.normalizacao(hmBlue, 256));
                    break;
                default:
                    return null;
            }
            

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

    private static File clasFolhaThreshold(BufferedImage img, int distancia) {
        //nome da especie a qual a folha foi classificada
        File especieOut = null;

        //resultado da soma das distancia euclidiana dos 3 canas RGB
        double soma = -1;

        //instacia um objeto manager directory
        ManageDirectory dir = new ManageDirectory();

        int limiar = Limiar.otsuTreshold(Histograma.histogramaGray(img), img.getWidth() * img.getHeight());

        //histograma geral da imagem
        int[] hRed = histogramaRed(img, Limiar.limiarizacaoBool(img, limiar));
        int[] hGreen = histogramaGreen(img, Limiar.limiarizacaoBool(img, limiar));
        int[] hBlue = histogramaBlue(img, Limiar.limiarizacaoBool(img, limiar));

        //local aonde os padroes estao armazenado
        File[] especies = new File(dir.getDiretorioPadrao() + "Especies/").listFiles();

        for (File especie : especies) {
            //carrega o histogrma médio no vetores
            int[] hmRed = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmRyesT.txt");
            int[] hmGreen = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmGyesT.txt");
            int[] hmBlue = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmByesT.txt");

            double aux = 0;

            switch(distancia){
                case EUCLIDIANA:
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hRed, 256), Histograma.normalizacao(hmRed, 256));
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hGreen, 256), Histograma.normalizacao(hmGreen, 256));
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hBlue, 256), Histograma.normalizacao(hmBlue, 256));
                    break;
                case MANHATTAN:
                    aux += Distancia.Manhattan(Histograma.normalizacao(hRed, 256), Histograma.normalizacao(hmRed, 256));
                    aux += Distancia.Manhattan(Histograma.normalizacao(hGreen, 256), Histograma.normalizacao(hmGreen, 256));
                    aux += Distancia.Manhattan(Histograma.normalizacao(hBlue, 256), Histograma.normalizacao(hmBlue, 256));
                    break;
                default:
                    return null;
            }

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
    //mode ir� definir qual criterio sera utilizando na comparacao das folhas
    private static File clasFolhaTwoSurface(BufferedImage img, int distancia, int superficie) {
        //nome da especie a qual a folha foi classificada
        File especieOut = null;

        //resultado da soma das distancia euclidiana
        double soma = -1;

        //instacia um objeto manager directory
        ManageDirectory dir = new ManageDirectory();

        //Divide a imagem em superior e inferior
        BufferedImage superior = img.getSubimage(0, 0, img.getWidth(), img.getHeight() / 2);
        BufferedImage inferior = img.getSubimage(0, img.getHeight() / 2, img.getWidth(), img.getHeight() / 2);

        //limiar para gerar o threshold
        int limiar = Limiar.otsuTreshold(Histograma.histogramaGray(img), img.getWidth() * img.getHeight());

        //histograma da imagem superior
        int[] hRedSup = histogramaRed(superior, Limiar.limiarizacaoBool(superior, limiar));
        int[] hGreenSup = histogramaGreen(superior, Limiar.limiarizacaoBool(superior, limiar));
        int[] hBlueSup = histogramaBlue(superior, Limiar.limiarizacaoBool(superior, limiar));

        //histograma da imagem inferior
        int[] hRedInf = histogramaRed(inferior, Limiar.limiarizacaoBool(inferior, limiar));
        int[] hGreenInf = histogramaGreen(inferior, Limiar.limiarizacaoBool(inferior, limiar));
        int[] hBlueInf = histogramaBlue(inferior, Limiar.limiarizacaoBool(inferior, limiar));

        //local aonde os padroes estao armazenado
        File[] especies = new File(dir.getDiretorioPadrao() + "Especies/").listFiles();

        for (File especie : especies) {
            //carrega o histogrma médio no vetores
            int[] hmRedSup = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmRtwoS1.txt");
            int[] hmGreenSup = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmGtwoS1.txt");
            int[] hmBlueSup = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmBtwoS1.txt");

            //carrega o histogrma médio no vetores
            int[] hmRedInf = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmRtwoS2.txt");
            int[] hmGreenInf = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmGtwoS2.txt");
            int[] hmBlueInf = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmBtwoS2.txt");

            double aux = 0;

            switch(distancia){
                case EUCLIDIANA:
                    switch(superficie){
                        case TOTAL:
                            //Calcular a distancia euclidiana para os 3 canais, da imagem superior
                            aux += Distancia.Euclidiana(Histograma.normalizacao(hRedSup, 256), Histograma.normalizacao(hmRedSup, 256));
                            aux += Distancia.Euclidiana(Histograma.normalizacao(hGreenSup, 256), Histograma.normalizacao(hmGreenSup, 256));
                            aux += Distancia.Euclidiana(Histograma.normalizacao(hBlueSup, 256), Histograma.normalizacao(hmBlueSup, 256));
                        case INFERIOR:
                            //Calcular a distancia euclidiana para os 3 canais, da imagem inferior
                            aux += Distancia.Euclidiana(Histograma.normalizacao(hRedInf, 256), Histograma.normalizacao(hmRedInf, 256));
                            aux += Distancia.Euclidiana(Histograma.normalizacao(hGreenInf, 256), Histograma.normalizacao(hmGreenInf, 256));
                            aux += Distancia.Euclidiana(Histograma.normalizacao(hBlueInf, 256), Histograma.normalizacao(hmBlueInf, 256));
                            break;
                        case SUPERIOR:
                            //Calcular a distancia euclidiana para os 3 canais, da imagem superior
                            aux += Distancia.Euclidiana(Histograma.normalizacao(hRedSup, 256), Histograma.normalizacao(hmRedSup, 256));
                            aux += Distancia.Euclidiana(Histograma.normalizacao(hGreenSup, 256), Histograma.normalizacao(hmGreenSup, 256));
                            aux += Distancia.Euclidiana(Histograma.normalizacao(hBlueSup, 256), Histograma.normalizacao(hmBlueSup, 256));
                            break;
                    }
                    break;
                case MANHATTAN:
                    switch(superficie){
                        case TOTAL:
                            //Calcular a distancia euclidiana para os 3 canais, da imagem superior
                            aux += Distancia.Manhattan(Histograma.normalizacao(hRedSup, 256), Histograma.normalizacao(hmRedSup, 256));
                            aux += Distancia.Manhattan(Histograma.normalizacao(hGreenSup, 256), Histograma.normalizacao(hmGreenSup, 256));
                            aux += Distancia.Manhattan(Histograma.normalizacao(hBlueSup, 256), Histograma.normalizacao(hmBlueSup, 256));
                        case INFERIOR:
                            //Calcular a distancia euclidiana para os 3 canais, da imagem inferior
                            aux += Distancia.Manhattan(Histograma.normalizacao(hRedInf, 256), Histograma.normalizacao(hmRedInf, 256));
                            aux += Distancia.Manhattan(Histograma.normalizacao(hGreenInf, 256), Histograma.normalizacao(hmGreenInf, 256));
                            aux += Distancia.Manhattan(Histograma.normalizacao(hBlueInf, 256), Histograma.normalizacao(hmBlueInf, 256));
                            break;
                        case SUPERIOR:
                              //Calcular a distancia euclidiana para os 3 canais, da imagem superior
                            aux += Distancia.Manhattan(Histograma.normalizacao(hRedSup, 256), Histograma.normalizacao(hmRedSup, 256));
                            aux += Distancia.Manhattan(Histograma.normalizacao(hGreenSup, 256), Histograma.normalizacao(hmGreenSup, 256));
                            aux += Distancia.Manhattan(Histograma.normalizacao(hBlueSup, 256), Histograma.normalizacao(hmBlueSup, 256));                       
                            break;
                    }
                    break;
                default:
                    return null;

            }
            
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

    private static File clasFolhaFourSurface(BufferedImage img, int distancia) {
        //nome da especie a qual a folha foi classificada
        File especieOut = null;

        //resultado da soma das distancia euclidiana
        double soma = -1;

        //instacia um objeto manager directory
        ManageDirectory dir = new ManageDirectory();

        //Divide a imagem em superior e inferior
        BufferedImage supEsq = img.getSubimage(0, 0, img.getWidth() / 2, img.getHeight() / 2);
        BufferedImage supDir = img.getSubimage(img.getWidth() / 2, 0, img.getWidth() / 2, img.getHeight() / 2);
        BufferedImage infEsq = img.getSubimage(0, img.getHeight() / 2, img.getWidth() / 2, img.getHeight() / 2);
        BufferedImage infDir = img.getSubimage(img.getWidth() / 2, img.getHeight() / 2, img.getWidth() / 2, img.getHeight() / 2);

        //limiar para gerar o threshold
        int limiar = Limiar.otsuTreshold(Histograma.histogramaGray(img), img.getWidth() * img.getHeight());

        //histograma da imagem superior esquerda
        int[] hRedSupEsq = histogramaRed(supEsq, Limiar.limiarizacaoBool(supEsq, limiar));
        int[] hGreenSupEsq = histogramaGreen(supEsq, Limiar.limiarizacaoBool(supEsq, limiar));
        int[] hBlueSupEsq = histogramaBlue(supEsq, Limiar.limiarizacaoBool(supEsq, limiar));
        
        //histograma da imagem superior direita
        int[] hRedSupDir = histogramaRed(supDir, Limiar.limiarizacaoBool(supDir, limiar));
        int[] hGreenSupDir = histogramaGreen(supDir, Limiar.limiarizacaoBool(supDir, limiar));
        int[] hBlueSupDir = histogramaBlue(supDir, Limiar.limiarizacaoBool(supDir, limiar));

        //histograma da imagem inferior esquerda
        int[] hRedInfEsq = histogramaRed(infEsq, Limiar.limiarizacaoBool(infEsq, limiar));
        int[] hGreenInfEsq = histogramaGreen(infEsq, Limiar.limiarizacaoBool(infEsq, limiar));
        int[] hBlueInfEsq = histogramaBlue(infEsq, Limiar.limiarizacaoBool(infEsq, limiar));
        
        //histograma da imagem inferior direita
        int[] hRedInfDir = histogramaRed(infDir, Limiar.limiarizacaoBool(infDir, limiar));
        int[] hGreenInfDir = histogramaGreen(infDir, Limiar.limiarizacaoBool(infDir, limiar));
        int[] hBlueInfDir = histogramaBlue(infDir, Limiar.limiarizacaoBool(infDir, limiar));

        //local aonde os padroes estao armazenado
        File[] especies = new File(dir.getDiretorioPadrao() + "Especies/").listFiles();

        for (File especie : especies) {
            //carrega o histogrma médio superior esquerda
            int[] hmRedSupEsq = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmRFourS1.txt");
            int[] hmGreenSupEsq = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmGFourS1.txt");
            int[] hmBlueSupEsq = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmBFourS1.txt");
            
            //carrega o histogrma médio superior direita
            int[] hmRedSupDir = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmRFourS2.txt");
            int[] hmGreenSupDir = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmGFourS2.txt");
            int[] hmBlueSupDir = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmBFourS2.txt");

            //carrega o histogrma médio inferior esquerda
            int[] hmRedInfEsq = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmRFourS3.txt");
            int[] hmGreenInfEsq = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmGFourS3.txt");
            int[] hmBlueInfEsq = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmBFourS3.txt");
            
             //carrega o histogrma médio inferior direita
            int[] hmRedInfDir = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmRFourS4.txt");
            int[] hmGreenInfDir = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmGFourS4.txt");
            int[] hmBlueInfDir = Histograma.lerTxtHistograma(especie.getAbsolutePath() + "/hmBFourS4.txt");

            double aux = 0;

            switch(distancia){
                case EUCLIDIANA:
                    //Calcular a distancia euclidiana para os 3 canais, da imagem superior esquerda
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hRedSupEsq, 256), Histograma.normalizacao(hmRedSupEsq, 256));
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hGreenSupEsq, 256), Histograma.normalizacao(hmGreenSupEsq, 256));
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hBlueSupEsq, 256), Histograma.normalizacao(hmBlueSupEsq, 256));

                    //Calcular a distancia euclidiana para os 3 canais, da imagem superior direita
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hRedSupDir, 256), Histograma.normalizacao(hmRedSupDir, 256));
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hGreenSupDir, 256), Histograma.normalizacao(hmGreenSupDir, 256));
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hBlueSupDir, 256), Histograma.normalizacao(hmBlueSupDir, 256));

                    //Calcular a distancia euclidiana para os 3 canais, da imagem inferior esquerda
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hRedInfEsq, 256), Histograma.normalizacao(hmRedInfEsq, 256));
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hGreenInfEsq, 256), Histograma.normalizacao(hmGreenInfEsq, 256));
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hBlueInfEsq, 256), Histograma.normalizacao(hmBlueInfEsq, 256));

                    //Calcular a distancia euclidiana para os 3 canais, da imagem inferior direita
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hRedInfDir, 256), Histograma.normalizacao(hmRedInfDir, 256));
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hGreenInfDir, 256), Histograma.normalizacao(hmGreenInfDir, 256));
                    aux += Distancia.Euclidiana(Histograma.normalizacao(hBlueInfDir, 256), Histograma.normalizacao(hmBlueInfDir, 256));
                    break;
                case MANHATTAN:
                    //Calcular a distancia euclidiana para os 3 canais, da imagem superior esquerda
                    aux += Distancia.Manhattan(Histograma.normalizacao(hRedSupEsq, 256), Histograma.normalizacao(hmRedSupEsq, 256));
                    aux += Distancia.Manhattan(Histograma.normalizacao(hGreenSupEsq, 256), Histograma.normalizacao(hmGreenSupEsq, 256));
                    aux += Distancia.Manhattan(Histograma.normalizacao(hBlueSupEsq, 256), Histograma.normalizacao(hmBlueSupEsq, 256));

                    //Calcular a distancia euclidiana para os 3 canais, da imagem superior direita
                    aux += Distancia.Manhattan(Histograma.normalizacao(hRedSupDir, 256), Histograma.normalizacao(hmRedSupDir, 256));
                    aux += Distancia.Manhattan(Histograma.normalizacao(hGreenSupDir, 256), Histograma.normalizacao(hmGreenSupDir, 256));
                    aux += Distancia.Manhattan(Histograma.normalizacao(hBlueSupDir, 256), Histograma.normalizacao(hmBlueSupDir, 256));

                    //Calcular a distancia euclidiana para os 3 canais, da imagem inferior esquerda
                    aux += Distancia.Manhattan(Histograma.normalizacao(hRedInfEsq, 256), Histograma.normalizacao(hmRedInfEsq, 256));
                    aux += Distancia.Manhattan(Histograma.normalizacao(hGreenInfEsq, 256), Histograma.normalizacao(hmGreenInfEsq, 256));
                    aux += Distancia.Manhattan(Histograma.normalizacao(hBlueInfEsq, 256), Histograma.normalizacao(hmBlueInfEsq, 256));

                    //Calcular a distancia euclidiana para os 3 canais, da imagem inferior direita
                    aux += Distancia.Manhattan(Histograma.normalizacao(hRedInfDir, 256), Histograma.normalizacao(hmRedInfDir, 256));
                    aux += Distancia.Manhattan(Histograma.normalizacao(hGreenInfDir, 256), Histograma.normalizacao(hmGreenInfDir, 256));
                    aux += Distancia.Manhattan(Histograma.normalizacao(hBlueInfDir, 256), Histograma.normalizacao(hmBlueInfDir, 256));
            }

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

    public static void classificaBancoFolha(String caminho, int mode, int distancia, int superficie) {
        //para pegar somente os arquivos xml
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        };
        //pega todos arquivos xml e passa para um vetor de files
        File[] arquivo = new File(caminho).listFiles(filter);
        CSV tabCsv = new CSV(new File(new ManageDirectory().getDiretorioPadrao() + "confusao.csv"));
        CSV preCsv = new CSV(new File(new ManageDirectory().getDiretorioPadrao() + "precisao.csv"));
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
                try {
                    image = ImageIO.read(new File(caminho + "/" + imgNome));
                    File espInf = classificaFolha(image, mode, distancia, superficie);
                    String nomeInf = espInf.getName();
                    nomeInf.trim();
                    //print
                    System.out.println("vdd->" + especie + "= inf->" + nomeInf);
                    calculaTabConfusao(tabCsv, nomeInf, especie);
                } catch (IOException ex) {
                    Logger.getLogger(Classificador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        calculaTabPrecisao(tabCsv, preCsv);
        System.out.println("fim do processo!");
    }

    private static void calculaTabConfusao(CSV tabela, String nomeInf, String especie) {
        //print
        tabela.readCSV();
        if (especie.equalsIgnoreCase(nomeInf)) {
            for (int line = 0; line < tabela.getLineSize(); line++) {
                try {
                    if (tabela.getCell(line, 0).equals(nomeInf)) {
                        int valor = Integer.parseInt(tabela.getCell(line, line));
                        valor++;
                        tabela.setCell(line, line, valor + "");
                    }
                } catch (NumberFormatException e) {
                    tabela.setCell(line, line, "0");
                }
            }
        } else {
            for (int line = 0; line < tabela.getLineSize(); line++) {
                if (tabela.getCell(line, 0).equals(especie)) {
                    for (int column = 0; column < tabela.getColumnSize(); column++) {
                        try {
                            if (tabela.getCell(0, column).equals(nomeInf)) {
                                int valor = Integer.parseInt(tabela.getCell(line, column));
                                valor++;
                                tabela.setCell(line, column, valor + "");
                                break;
                            }
                        } catch (NumberFormatException e) {
                            tabela.setCell(line, line, "0");
                        }
                    }
                }
            }
        }
        tabela.writeCSV();
    }

    private static void calculaTabPrecisao(CSV tabela, CSV precisao) {
        tabela.readCSV();
        precisao.readCSV();
        //calcular a precisão dos acertos
        for (int line = 1; line < tabela.getLineSize(); line++) {
            double soma = 0;
            double acerto = Integer.parseInt(tabela.getCell(line, line));;
            //
            for (int column = 1; column < tabela.getColumnSize(); column++) {
                soma += Integer.parseInt(tabela.getCell(line, column));
            }
            try {
                if (soma != 0) {
                    precisao.setCell(line, 1, ((acerto / soma) * 100) + "");
                }
            } catch (ArithmeticException e) {
                precisao.setCell(line, 1, "");
                e.printStackTrace();
            }
        }
        precisao.writeCSV();
    }
}