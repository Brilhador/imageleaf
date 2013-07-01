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
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import model.ChainCode;
import model.Grafico;
import model.MyImage;
import model.Signature;
import view.curvature.ViewSignature;

/**
 *
 * @author Anderson
 */
public class ControlCurvatureSignature {
    
    private ViewSignature view = null;
    private BufferedImage image = null;
    private BufferedImage grafico = null;
    private int typeInitPoint = 0;

    public ControlCurvatureSignature(BufferedImage image) {
        view = new ViewSignature();
        this.image = image;
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        view.setVisible(true);
    }

    private void initComponents() {
        
        carregaImagePreview(image);
        stopProgressBar();
       
        view.getCbRotation().setEnabled(false);
        view.getCbTranslation().setEnabled(false);
        view.setResizable(false);

        view.getBtnGenerate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        startProgressBar();
                        boolean invInitPoint = view.getCbInitialPoint().isSelected();
                        boolean invScala = view.getCbScala().isSelected();
                        int angle = Integer.parseInt(view.getTxtAngle().getText());
                        Signature signature = new Signature(image, angle, invInitPoint, typeInitPoint, invScala);
                        view.getImageViewSignature().setImage(MyImage.resizeImage(signature.getImageSignature(), view.getImageViewSignature().getWidth(), view.getImageViewSignature().getHeight()));
                        view.getImageViewSignature().setScale(0.9);
                        BufferedImage grafico = Grafico.Signature(signature.getSignature(), view.getImageViewGrafico().getWidth(), view.getImageViewGrafico().getHeight(), "Sing");
                        view.getImageViewGrafico().setImage(grafico);
                        stopProgressBar();
                        return null;
                    }
                };
                work.execute();
            }
        });
        
        view.getCbInitialPoint().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(view.getCbInitialPoint().isSelected()){
                    view.getRbDiameter().setEnabled(true);
                    view.getRbRadius().setEnabled(true);
                }else{
                    view.getRbDiameter().setEnabled(false);
                    view.getRbRadius().setEnabled(false);
                }
            }
        });
        
        view.getRbRadius().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(view.getRbRadius().isSelected()){
                    view.getRbDiameter().setSelected(false);
                }
            }
        });
        
        view.getRbDiameter().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(view.getRbDiameter().isSelected()){
                    view.getRbRadius().setSelected(false);
                }
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
