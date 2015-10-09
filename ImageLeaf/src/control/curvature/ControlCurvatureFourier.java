/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.curvature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import model.DFT;
import model.Grafico;
import model.MyImage;
import model.Signature;
import view.curvature.ViewFourier;

/**
 *
 * @author Anderson
 */
public class ControlCurvatureFourier {
    
    private ViewFourier view = null;
    private BufferedImage image = null;

    public ControlCurvatureFourier(BufferedImage image) {
        view = new ViewFourier();
        this.image = image;
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        view.setResizable(false);
        view.setVisible(true);
    }

    private void initComponents() {
        
        carregaImagePreview(image);
        stopProgressBar();
        
        view.getCbInitialPoint().setEnabled(false);
        
        view.getBtnGenerate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        startProgressBar();
                        boolean invRotation = view.getCbRotation().isSelected();
                        boolean invScala = view.getCbScala().isSelected();
                        boolean invTranslation = view.getCbTranslation().isSelected();
                        int numCoff = Integer.parseInt(view.getTxtNumberCoefficients().getText());
                        DFT dft = new DFT(image, invRotation, invScala, invTranslation);
                        view.getImageViewRecorte().setImageResize(dft.getImageFourier());
                        BufferedImage grafico = Grafico.DFT2IMG(dft.getCoefficients(numCoff), view.getImageViewGrafico().getWidth(), view.getImageViewGrafico().getHeight(), "Coefficients");
                        view.getImageViewGrafico().setImage(grafico);
                        stopProgressBar();
                        return null;
                    }
                };
                work.execute();
            }
        });
        
    }
    
    //carregar image no lblImagePreview
    public void carregaImagePreview(BufferedImage image) {
        view.getImageView().setImageResize(image);
        view.getImageView().setScale(0.9);
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
}
