/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.blur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.Filtro;
import model.MyImage;
import model.components.JImageInternalFrame;
import view.blur.ViewBlur;
import view.viewPrincipal;

/**
 *
 * @author Anderson
 */
public class ControlBlur {

    private viewPrincipal parentFrame = null;
    private ViewBlur view = null;
    private BufferedImage image = null;
    private BufferedImage filterImage = null;

    //constructor
    public ControlBlur(BufferedImage image, viewPrincipal parentFrame) {
        this.image = image;
        this.parentFrame = parentFrame;
        view = new ViewBlur();
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        view.setVisible(true);
    }

    private void initComponents() {

        //carrega a image na preview
        carregaImagePreview(image);
        stopProgressBar();
        view.getRbLow().setSelected(false);

        view.getBtnPreview().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        startProgressBar();
                        filterImage = Filtro.mediana(image, view.getSlMaskSize().getValue());
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
                        if (view.getRbMedian().isSelected()) {
                            filterImage = Filtro.mediana(image, view.getSlMaskSize().getValue());
                        } else if (view.getRbLow().isSelected()) {
                            filterImage = Filtro.passaBaixas(image, view.getSlMaskSize().getValue());
                        }
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

        view.getRbLow().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbLow().isSelected()) {
                    view.getRbMedian().setSelected(false);
                }
            }
        });

        view.getRbMedian().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbMedian().isSelected()) {
                    view.getRbLow().setSelected(false);
                }
            }
        });

    }

    //carregar image no lblImagePreview
    public void carregaImagePreview(BufferedImage image) {
        view.getImagePreview().setImageResize(image);
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
