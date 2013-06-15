/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.curvature;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.ChainCode;
import model.Grafico;
import model.Histograma;
import model.Signature;
import view.curvature.ViewChainCode;

/**
 *
 * @author anderson
 */
public class ControlCurvatureChainCode {

    private ViewChainCode view = null;
    private BufferedImage image = null;
    private BufferedImage grafico = null;
    private boolean[][] imageBorder = null;

    public ControlCurvatureChainCode(BufferedImage image, boolean[][] border) {
        view = new ViewChainCode();
        this.image = image;
        this.imageBorder = border;
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        view.setVisible(true);
    }

    private void initComponents() {

        stopProgressBar();
        carregaImagePreview(image);
        view.getRbCoordinates().setSelected(true);

        view.getBtnGenerate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        int width = view.getLblImageCurvature().getWidth();
                        int heigth = view.getLblImageCurvature().getHeight();
                        startProgressBar();
                        if (view.getRbCoordinates().isSelected()) {
                            try {
                                ArrayList<Dimension> lista = new ChainCode(imageBorder).getDimesionChainCode();
                                Dimension centroide = new Signature().getCentroideMedian(lista);
                                Dimension initPoint = new Signature().getInitAngleByMoreRadius(lista, centroide);
                                Dimension[] point = new Signature().getDimensionPoint(lista, centroide, 20);
//                                double[] distance = new Signature().createNormSignal(lista, 10);
//                                for (double i : distance) {
//                                    System.out.println(i);
//                                }
                                if (lista != null) {
                                    drawPathChainCode(lista, centroide, point);
                                    grafico = Grafico.curvatureDimension(lista, width, heigth, "Curvature");
                                    view.getLblImageCurvature().setImage(grafico);
                                } else {
                                    JOptionPane.showMessageDialog(view, "Erro!", "", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (view.getRbChainCode().isSelected()) {
                            ArrayList<Dimension> lista = new ChainCode(imageBorder).getDimesionChainCode();
                            if (lista != null) {
//                                ArrayList<Integer> listaCode = new ChainCode(imageBorder).getChainCode();
//                                Signature sig = new Signature();
//                                sig.createSignalBorders(10, listaCode);
//                                drawPathChainCode(lista);
//                                grafico = Grafico.curvatureChainCode(listaCode, width, heigth, "Curvature");
//                                view.getLblImageCurvature().setImage(grafico);
                            } else {
                                JOptionPane.showMessageDialog(view, "Erro!", "", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if (view.getRbHistChain().isSelected()) {
                            ArrayList<Dimension> lista = new ChainCode(imageBorder).getDimesionChainCode();
                            if (lista != null) {
//                                int[] vectorFeature = new ChainCode(imageBorder).getAngleHistograma();
//                                double[] normFeature = Histograma.normalizacao(vectorFeature, vectorFeature.length);
//                                ArrayList<Dimension> lista = new ChainCode(imageBorder).getDimesionChainCode();
                                Dimension centroide = new Signature().getCentroideMedian(lista);
                                Dimension[] point = new Signature().getDimensionPoint(lista, centroide, 90);
                                
//                                drawPathChainCode(lista);
//                                grafico = Grafico.histograma(normFeature, width, heigth, "Histogram Chain Code", "Direction", "Frequency");
//                                grafico = Grafico.curvatureDimension(point, width, heigth, "teste");
                                view.getLblImageCurvature().setImage(grafico);
                            } else {
                                JOptionPane.showMessageDialog(view, "Erro!", "", JOptionPane.ERROR_MESSAGE);
                            }

                        } else {
                            JOptionPane.showMessageDialog(view, "Selecione um tipo de curvatura", "", JOptionPane.INFORMATION_MESSAGE);
                        }
                        stopProgressBar();
                        return null;
                    }
                };
                work.execute();
            }
        });

        view.getRbCoordinates().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbCoordinates().isSelected()) {
                    view.getRbChainCode().setSelected(false);
                    view.getRbHistChain().setSelected(false);
                }
            }
        });

        view.getRbChainCode().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbChainCode().isSelected()) {
                    view.getRbCoordinates().setSelected(false);
                    view.getRbHistChain().setSelected(false);
                }
            }
        });

        view.getRbHistChain().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbHistChain().isSelected()) {
                    view.getRbCoordinates().setSelected(false);
                    view.getRbChainCode().setSelected(false);
                }
            }
        });
    }

    //carregar image no lblImagePreview
    public void carregaImagePreview(BufferedImage image) {
        view.getImageView().setImageResize(image);
    }

    public void startProgressBar() {
        view.getPgBar().setOrientation(JProgressBar.VERTICAL);
        view.getPgBar().setIndeterminate(true);
        view.getPgBar().setVisible(true);
    }

    public void stopProgressBar() {
        view.getPgBar().setVisible(false);
        view.getPgBar().setIndeterminate(false);
    }

    public void drawPathChainCode(ArrayList<Dimension> lista, Dimension centroide, Dimension[] point) {
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
        carregaImagePreview(drawImage);
    }

    private void drawPoint(BufferedImage drawImage, Dimension point, Color cor) {
        drawImage.setRGB(point.width, point.height, cor.getRGB());
        drawImage.setRGB(point.width + 1, point.height, cor.getRGB());//0
        drawImage.setRGB(point.width, point.height - 1, cor.getRGB());//2
        drawImage.setRGB(point.width - 1, point.height, cor.getRGB());//4
        drawImage.setRGB(point.width, point.height + 1, cor.getRGB());//6
    }
}
