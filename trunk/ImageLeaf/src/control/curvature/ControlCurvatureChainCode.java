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
        view.getCbTranslation().setEnabled(false);
        view.setResizable(false);

        view.getBtnGenerate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        startProgressBar();
                        boolean invScala = view.getCbScala().isSelected();
                        boolean invInitialPoint = view.getCbInitialPoint().isSelected();
                        boolean invRotation = view.getCbRotation().isSelected();
                        int width = Integer.parseInt(view.getTxtWidth().getText());
                        int heigth = Integer.parseInt(view.getTxtHeigth().getText());
                        ChainCode chaincode = new ChainCode(image, invScala, width, heigth, invInitialPoint, invRotation);
                        view.getImageViewRecorte().setImage(MyImage.resizeImage(chaincode.getChainImage(), view.getImageViewRecorte().getWidth(), view.getImageViewRecorte().getHeight()));
                        view.getImageViewRecorte().setScale(0.9);
                        view.getLblValueChainCode().setText(chaincode.getChaincode());
                        view.getImageViewGrafico().setImage(Grafico.histogramaChainCode(chaincode.getDoubleChainCode(), view.getImageViewGrafico().getWidth(), view.getImageViewGrafico().getHeight(), "Histogram of direction", "Direction","Frequency"));
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
