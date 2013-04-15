/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.blur;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.SwingWorker;
import model.Filtro;
import model.MyImage;
import model.components.JImageInternalFrame;
import org.jdesktop.swingx.JXImageView;
import view.blur.ViewBlurLow;
import view.viewPrincipal;

/**
 *
 * @author anderson
 */
public class ControlBlurLow {

    private viewPrincipal parentFrame = null;
    private ViewBlurLow view = null;
    private BufferedImage image = null;
    private BufferedImage filterImage = null;

    //constructor
    public ControlBlurLow(BufferedImage image, viewPrincipal parentFrame) {
        this.image = image;
        this.parentFrame = parentFrame;
        view = new ViewBlurLow();
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        view.setVisible(true);
    }

    private void initComponents() {

        //carrega a image na preview
        carregaImagePreview(image);
        stopProgressBar();

        view.getBtnPreview().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        startProgressBar();
                        filterImage = Filtro.passaBaixas(image, view.getSlMaskSize().getValue());
                        carregaImagePreview(filterImage);
                        stopProgressBar();
                        return null;
                    }
                };
                work.execute();
            }
        });

        view.getBtnRestore().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregaImagePreview(image);
                filterImage = null;
            }
        });

        view.getBtnApply().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        startProgressBar();
                        filterImage = Filtro.passaBaixas(image, view.getSlMaskSize().getValue());
                        JImageInternalFrame frame = (JImageInternalFrame) parentFrame.getjPanelPrincipal().getSelectedFrame();
                        frame.setImage(filterImage);
                        parentFrame.repaint();
                        stopProgressBar();
                        view.dispose();
                        return null;
                    }
                };
                work.execute();
            }
        });

        //ação ao redimensionar Jframe
        view.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (filterImage == null) {
                    carregaImagePreview(image);
                } else {
                    carregaImagePreview(filterImage);
                }
            }
        });

    }

    //carregar image no lblImagePreview
    public void carregaImagePreview(BufferedImage image) {
        int width = view.getLblImagePreview().getWidth();
        int heigth = view.getLblImagePreview().getHeight();
        BufferedImage resizeImage = MyImage.resizeImage(image, width, heigth);
        view.getLblImagePreview().setIcon(new ImageIcon(resizeImage));
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
