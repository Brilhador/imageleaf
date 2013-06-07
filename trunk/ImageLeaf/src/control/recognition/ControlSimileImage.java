/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.recognition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import model.ChainCode;
import model.DFT;
import model.Distancia;
import model.Filtro;
import model.Grafico;
import model.Histograma;
import model.Limiar;
import model.MyImage;
import model.Signature;
import view.recognition.ViewSimileImage;

/**
 *
 * @author anderson
 */
public class ControlSimileImage {

    private ViewSimileImage view = null;
    private BufferedImage image1 = null;
    private BufferedImage image2 = null;

    public ControlSimileImage() {
        view = new ViewSimileImage();
        initEvents();
        view.setVisible(true);
    }

    private void initEvents() {

        stopProgressBar();

        view.getBtnOpen1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser cFile = new JFileChooser();
                if (cFile.showOpenDialog(view) == cFile.APPROVE_OPTION) {
                    try {
                        String pathImage = cFile.getSelectedFile().getAbsolutePath();
                        image1 = ImageIO.read(new File(pathImage));
                        view.getJxImage1().setImage(MyImage.resizeImage(image1, view.getJxImage1().getWidth(), view.getJxImage1().getHeight()));
                    } catch (IOException ex) {
                        Logger.getLogger(ControlSimileImage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        view.getBtnOpen2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser cFile = new JFileChooser();
                if (cFile.showOpenDialog(view) == cFile.APPROVE_OPTION) {
                    try {
                        String pathImage = cFile.getSelectedFile().getAbsolutePath();
                        image2 = ImageIO.read(new File(pathImage));
                        view.getJxImage2().setImage(MyImage.resizeImage(image2, view.getJxImage2().getWidth(), view.getJxImage2().getHeight()));
                    } catch (IOException ex) {
                        Logger.getLogger(ControlSimileImage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        view.getBtnStart().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        startProgressBar();
                        int angle = 1;
                        int indice = 6;
                        //calculando a imagem 1
                        ArrayList<Dimension> listaImage1 = createChainCode(image1);
                        ArrayList<Dimension> listaImage2 = createChainCode(image2);
                        if (listaImage1 == null ||  listaImage2 == null) {
                            JOptionPane.showMessageDialog(view, "Erro", null, JOptionPane.ERROR_MESSAGE);
                        } else {
                            double[] vectorImage1 = createNormSignal(listaImage1, angle);
                            double[] vectorImage2 = createNormSignal(listaImage2, angle);
                            DFT dft1 = new DFT(1, vectorImage1, new double[vectorImage1.length], vectorImage1.length);
                            DFT dft2 = new DFT(1, vectorImage2, new double[vectorImage2.length], vectorImage2.length);
                            double dst = Distancia.Euclidiana(dft1.getX1(), dft2.getX1(), indice);
                            view.getTxtDstResult().setText(dst + "");
                            BufferedImage grafico = Grafico.DFT2IMG(dft1.getX1(),dft2.getX1(), view.getJxGrafico().getWidth(), view.getJxGrafico().getHeight(), "Signature", indice);
                            //desenhar assinatura nas imagens da tela
                            Dimension centroide = new Signature().getCentroideMedian(listaImage1);
                            Dimension[] point = new Signature().getDimensionPoint(listaImage1, centroide, angle);
                            BufferedImage sigImage1 = drawPathChainCode(image1, listaImage1, centroide, point);
                            //desenhar assinatura nas imagens da tela
                            centroide = new Signature().getCentroideMedian(listaImage2);
                            point = new Signature().getDimensionPoint(listaImage2, centroide, angle);
                            BufferedImage sigImage2 = drawPathChainCode(image2, listaImage2, centroide, point);
                            //mudando as imagens
                            view.getJxImage1().setImage(MyImage.resizeImage(sigImage1, view.getJxImage1().getWidth(), view.getJxImage1().getHeight()));
                            view.getJxImage2().setImage(MyImage.resizeImage(sigImage2, view.getJxImage2().getWidth(), view.getJxImage2().getHeight()));
                            view.getJxGrafico().setImage(grafico);
                        }
                        stopProgressBar();
                        return null;
                    }
                };
                worker.execute();
            }
        });

    }

    private ArrayList<Dimension> createChainCode(BufferedImage image) {
        //aplicar o filtro para suavizar a imagem
        image = Filtro.mediana(image, 5);
        //pega o total imagem
        int total = image.getWidth() * image.getHeight();
        //gera o histograma de tons de cinzas da imagem e depois calcula o limiar da imagem
        int limiar = Limiar.otsuTreshold(Histograma.histogramaGray(image), total);
        //realiza a limiarizaçao
        boolean[][] imageBorder = Limiar.limiarizacaoBool(image, limiar);
        //calcula o codigo da cadeia 
        return new ChainCode(imageBorder).getDimesionChainCode();
    }

    private double[] createSignal(ArrayList<Dimension> listaDimension, int angle) {
        double[] vectorFeature = new Signature().createSignal(listaDimension, angle);
        return vectorFeature;
    }
    
    private double[] createNormSignal(ArrayList<Dimension> listaDimension, int angle) {
        double[] vectorFeature = new Signature().createNormSignal(listaDimension, angle);
        return vectorFeature;
    }
    
    private double[] createNormVarianceSignal(ArrayList<Dimension> listaDimension, int angle) {
        double[] vectorFeature = new Signature().createNormVarianceSignal(listaDimension, angle);
        return vectorFeature;
    }

    public void startProgressBar() {
        view.getPgBar().setIndeterminate(true);
        view.getPgBar().setVisible(true);
    }

    public void stopProgressBar() {
        view.getPgBar().setVisible(false);
        view.getPgBar().setIndeterminate(false);
    }

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
