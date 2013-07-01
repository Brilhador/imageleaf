/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author anderson
 */
public class Sorter {

    private static final int EUCLIDIANA = 0;
    private static final int MANHATTAN = 1;

    public static void sorterMultipleImage(String pathImage, String pathPattern, int distancia) {
        //para pegar somente os arquivos xml
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        };
        //pega todos arquivos xml e passa para um vetor de files
        File[] arquivo = new File(pathImage).listFiles(filter);
        CSV tabCsv = new CSV(new File(new ManageDirectory().getDiretorioPadrao() + "confusao.csv"));
        CSV preCsv = new CSV(new File(new ManageDirectory().getDiretorioPadrao() + "precisao.csv"));
        for (File file : arquivo) {
            BufferedImage image;
            XML xml = new XML();
            String especie = xml.lerXml(file, "Species");
            especie.trim();
            especie = especie.substring(0, especie.indexOf(" ", especie.indexOf(" ") + 1));
            String imgNome = xml.lerXml(file, "FileName");
            System.out.println(imgNome);
            String tipo = xml.lerXml(file, "Type");
            //so serão selecionada as fotos que são do tipo aquisição scan
            if (tipo.equalsIgnoreCase("scan")) {
                try {
                    image = ImageIO.read(new File(pathImage + "/" + imgNome));
                    File espInf = sorterImage(image, pathPattern, distancia);
                    if (espInf != null) {
                        String nomeInf = espInf.getName();
                        nomeInf.trim();
                        //print
                        System.out.println("vdd->" + especie + "= inf->" + nomeInf);
                        calculaTabConfusao(tabCsv, nomeInf, especie);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Classificador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        calculaTabPrecisao(tabCsv, preCsv);
        System.out.println("fim do processo!");
    }

    private static File sorterImage(BufferedImage img, String pathPattern, int distancia) {
        //nome da especie a qual a folha foi classificada
        File especieOut = null;

        //resultado da soma das distancia euclidiana dos 3 canas RGB
        double soma = -1;

        //instacia um objeto manager directory
        ManageDirectory dir = new ManageDirectory();

        try {
            //histograma do chain codigo da imagem
            double[] feature = createSignatureAngle(img, 10);

            if (feature != null) {
                //local aonde os padroes estao armazenado
                File[] especies = new File(pathPattern).listFiles();

                for (File especie : especies) {

                    //carrega o histogrma médio no vetores
                    double[] patternFeature = lerTxtFeaturePattern(especie + "/signatureAngle.txt", 10);

                    //variavel auxiliar para armazenar a diferencas
                    double aux = 0;

                    //calcula a distancia entre a imagem e o padrao
                    switch (distancia) {
                        case EUCLIDIANA:
                            aux = Distancia.Euclidiana(feature, patternFeature);
                            break;
                        case MANHATTAN:
                            aux = Distancia.Manhattan(feature, patternFeature);
                            break;
                        default:
                            return null;
                    }

                    //se a distancia calculada for menor que a soma, troca se os valores
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static double[] createSignatureAngle(BufferedImage image, int angle) {
        double[] outFeature = new double[360/angle];
        //aplicar o filtro para suavizar a imagem
        image = Filtro.mediana(image, 5);
        //pega o total imagem
        int total = image.getWidth() * image.getHeight();
        //gera o histograma de tons de cinzas da imagem e depois calcula o limiar da imagem
        int limiar = Limiar.otsuTreshold(Histograma.histogramaGray(image), total);
        //realiza a limiarizaçao
        boolean[][] imageBorder = Limiar.limiarizacaoBool(image, limiar);
        //calcula o codigo da cadeia 
        ArrayList<Dimension> listaDimension = new ChainCode(image, true, 10, 10, true, true).getBorder();
        double[] vectorFeature = new Signature(image,angle, false,0, true).getSignature();
        return vectorFeature;
    }

    //funcao que pega um txt de inteiros e converte em histograma
    public static double[] lerTxtFeaturePattern(String caminhoNome, int angle) {
        double[] histograma = new double[360/angle];
        try {
            FileReader reader = new FileReader(caminhoNome);
            BufferedReader buffer = new BufferedReader(reader);
            for (int i = 0; i < histograma.length; i++) {
                histograma[i] = Integer.parseInt(buffer.readLine());
            }
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return histograma;
    }

    private static void calculaTabConfusao(CSV tabela, String nomeInf, String especie) {
        //print
        tabela.readCSV();
        int tam = tabela.getLineSize();
        if (especie.equalsIgnoreCase(nomeInf)) {
            for (int line = 0; line < tam; line++) {
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
