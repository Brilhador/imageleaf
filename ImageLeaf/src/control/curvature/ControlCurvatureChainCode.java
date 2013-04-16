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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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

        view.getBtnGenerate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        int width = view.getLblImageCurvature().getWidth();
                        int heigth = view.getLblImageCurvature().getHeight();
                        startProgressBar();
                        ArrayList<Dimension> lista = new ChainCode(imageBorder).getDimesionChainCode();
                        drawPathChainCode(lista);
                        grafico = Grafico.curvatureDimension(lista, width, heigth, "Curvature");
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
    
    public void drawPathChainCode(ArrayList<Dimension> lista){
        BufferedImage drawImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = drawImage.createGraphics();
        g2d.drawImage(image, null, 0, 0);
        g2d.dispose();
        for (Dimension dimension : lista) {
            drawImage.setRGB(dimension.width, dimension.height, Color.RED.getRGB());
        }
//        Dimension dimension = lista.get(0);
//        drawImage.setRGB(dimension.width, dimension.height, Color.RED.getRGB());
        carregaImagePreview(drawImage);
    }
}
