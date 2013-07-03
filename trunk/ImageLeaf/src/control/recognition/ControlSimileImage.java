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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.ChainCode;
import model.Complex;
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
    //VARIAVEIS AUXILIARES
    private int angle = 0;
    private int indice = 0;
    private ArrayList<Dimension> listaImage1 = null;
    private ArrayList<Dimension> listaImage2 = null;

    public ControlSimileImage() {
        view = new ViewSimileImage();
        initEvents();
        view.setVisible(true);
    }

    private void initEvents() {

        stopProgressBar();
        view.getTxtAngle().setEnabled(false);
        view.getTxtWidth().setEnabled(false);
        view.getTxtHeight().setEnabled(false);
        view.getTxtSeries().setEnabled(false);

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
                        try {
                            if (view.getRbSignature().isSelected()) {
                                int angle = Integer.parseInt(view.getTxtAngle().getText());
                                Signature sigImag1 = new Signature(image1, angle, true, 0, true);
                                Signature sigImag2 = new Signature(image2, angle, true, 0, true);
                                double distance = Distancia.Euclidiana(sigImag1.getSignature(), sigImag2.getSignature());
                                view.getTxtDstResult().setText(distance + "");
                                BufferedImage grafico = Grafico.Signature(sigImag1.getSignature(), sigImag2.getSignature(), view.getJxGrafico().getWidth(), view.getJxGrafico().getHeight(), "Signature comparison");
                                view.getJxGrafico().setImage(grafico);
                                view.getJxImage1().setImageResize(sigImag1.getImageSignature());
                                view.getJxImage2().setImageResize(sigImag2.getImageSignature());
                            } else if (view.getRbChainCode().isSelected()) {
                                int width = Integer.parseInt(view.getTxtWidth().getText());
                                int heigth = Integer.parseInt(view.getTxtWidth().getText());
                                ChainCode chainImg1 = new ChainCode(image1, true, width, heigth, true, true);
                                ChainCode chainImg2 = new ChainCode(image2, true, width, heigth, true, true);
                                double distance = Distancia.Euclidiana(chainImg1.getHistChainCode(), chainImg2.getHistChainCode());
                                view.getTxtDstResult().setText(distance + "");
                                BufferedImage grafico = Grafico.histogramaChainCode(chainImg1.getHistChainCode(), chainImg2.getHistChainCode(), view.getJxGrafico().getWidth(), view.getJxGrafico().getHeight(),"Histogram of direction", "Direction","Frequency");
                                view.getJxGrafico().setImage(grafico);
                                view.getJxImage1().setImageResize(chainImg1.getChainImage());
                                view.getJxImage2().setImageResize(chainImg2.getChainImage());
                            }else if(view.getRbFourier().isSelected()){
                                int series = Integer.parseInt(view.getTxtSeries().getText());
                                DFT dft1 = new DFT(image1, true, true, true);
                                DFT dft2 = new DFT(image2, true, true, true);
                                double distance = Distancia.Euclidiana(dft1.getCoefficients(series), dft2.getCoefficients(series));
                                view.getTxtDstResult().setText(distance + "");
                                BufferedImage grafico = Grafico.Signature(dft1.getCoefficients(series), dft2.getCoefficients(series), view.getJxGrafico().getWidth(), view.getJxGrafico().getHeight(), "Series comparison");
                                view.getJxGrafico().setImage(grafico);
                                view.getJxImage1().setImageResize(dft1.getImageFourier());
                                view.getJxImage2().setImageResize(dft2.getImageFourier());
                            } else {
                                JOptionPane.showMessageDialog(view, "select a shape descriptor!", "Informative", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        stopProgressBar();
                        return null;
                    }
                };
                worker.execute();
            }
        });

        view.getRbSignature().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbSignature().isSelected()) {
                    view.getRbChainCode().setSelected(false);
                    view.getRbFourier().setSelected(false);
                    view.getTxtAngle().setEnabled(true);
                    view.getTxtWidth().setEnabled(false);
                    view.getTxtHeight().setEnabled(false);
                    view.getTxtSeries().setEnabled(false);
                }
            }
        });

        view.getRbChainCode().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbChainCode().isSelected()) {
                    view.getRbSignature().setSelected(false);
                    view.getRbFourier().setSelected(false);
                    view.getTxtAngle().setEnabled(false);
                    view.getTxtWidth().setEnabled(true);
                    view.getTxtHeight().setEnabled(true);
                    view.getTxtSeries().setEnabled(false);
                }
            }
        });

        view.getRbFourier().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbFourier().isSelected()) {
                    view.getRbSignature().setSelected(false);
                    view.getRbChainCode().setSelected(false);
                    view.getTxtAngle().setEnabled(false);
                    view.getTxtWidth().setEnabled(false);
                    view.getTxtHeight().setEnabled(false);
                    view.getTxtSeries().setEnabled(true);
                }
            }
        });

    }

    public void startProgressBar() {
        view.getPgBar().setIndeterminate(true);
        view.getPgBar().setVisible(true);
    }

    public void stopProgressBar() {
        view.getPgBar().setVisible(false);
        view.getPgBar().setIndeterminate(false);
    }
}
