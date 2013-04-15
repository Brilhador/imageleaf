/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.curvature;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import model.ChainCode;
import model.Grafico;
import model.MyImage;
import view.curvature.ViewChainCode;

/**
 *
 * @author anderson
 */
public class ControlCurvatureChainCode {
    
    private ViewChainCode view = null;
    private BufferedImage image = null;
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
        
        view.getBtnGenerate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {

                    @Override
                    protected Object doInBackground() throws Exception {
                        startProgressBar();
                        ArrayList<Dimension> lista = new ChainCode(imageBorder).getChainCode();
                        int width = view.getLblImageCurvature().getWidth();
                        int height = view.getLblImageCurvature().getHeight();
                        BufferedImage grafico = Grafico.curvature(lista, width, height, "Curvature");
                        view.getLblImageCurvature().setIcon(new ImageIcon(grafico));
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
        int width = view.getLblImage().getWidth();
        int heigth = view.getLblImage().getHeight();
        BufferedImage resizeImage = MyImage.resizeImage(image, width, heigth);
        view.getLblImage().setIcon(new ImageIcon(resizeImage));
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
