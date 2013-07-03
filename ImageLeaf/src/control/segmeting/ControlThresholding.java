/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.segmeting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.Filtro;
import model.Histograma;
import model.Limiar;
import model.components.JImageInternalFrame;
import view.segmeting.ViewThresholding;
import view.viewPrincipal;

/**
 *
 * @author Anderson
 */
public class ControlThresholding {
    private viewPrincipal parentFrame = null;
    private ViewThresholding view = null;
    private BufferedImage image = null;
    private BufferedImage filterImage = null;

    public ControlThresholding(BufferedImage image, viewPrincipal parentFrame) {
        this.image = image;
        this.parentFrame = parentFrame;
        view = new ViewThresholding();
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        view.setVisible(true);
    }

    private void initComponents() {

        //carrega a imagem inicial
        carregaImagePreview(image);
        stopProgressBar();
        view.getRbOtsu().setSelected(true);
        view.getBtnPreview().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        startProgressBar();
                        int valueLimiar = 0;
                        if (view.getRbMaxEntropy().isSelected()) {
                            int total = image.getWidth() * image.getHeight();
                            valueLimiar = Limiar.maxentropia(Histograma.histogramaGray(image), total);
                            view.getLblValueLimiar().setText(valueLimiar + "");
                        } else if (view.getRbOtsu().isSelected()) {
                            int total = image.getWidth() * image.getHeight();
                            valueLimiar = Limiar.otsuTreshold(Histograma.histogramaGray(image), total);
                            view.getLblValueLimiar().setText(valueLimiar + "");
                        } else if (view.getRbManual().isSelected()) {
                            valueLimiar = view.getSlLimiarValue().getValue();
                        }
                        filterImage = image;
                        filterImage = Limiar.limiarizacao(filterImage, valueLimiar);
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
                        int valueLimiar = 0;
                        if (view.getRbMaxEntropy().isSelected()) {
                            int total = image.getWidth() * image.getHeight();
                            valueLimiar = Limiar.maxentropia(Histograma.histogramaGray(image), total);
                            view.getLblValueLimiar().setText(valueLimiar + "");
                        } else if (view.getRbOtsu().isSelected()) {
                            int total = image.getWidth() * image.getHeight();
                            valueLimiar = Limiar.otsuTreshold(Histograma.histogramaGray(image), total);
                            view.getLblValueLimiar().setText(valueLimiar + "");
                        } else if (view.getRbManual().isSelected()) {
                            valueLimiar = view.getSlLimiarValue().getValue();
                        }
                        //copiando a imagem original para a image de manipulacao
                        filterImage = image;
                        filterImage = Limiar.limiarizacao(filterImage, valueLimiar);
                        //recarregar imagem na tela
                        JImageInternalFrame frame = (JImageInternalFrame) parentFrame.getjPanelPrincipal().getSelectedFrame();
                        frame.setImage(filterImage);
                        //salvar a border no frame
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
 

        //selecionar somente um radio button
        view.getRbMaxEntropy().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbMaxEntropy().isSelected()) {
                    view.getRbOtsu().setSelected(false);
                    view.getRbManual().setSelected(false);
                    view.getLblValueLimiar().setText("automatic");
                }
            }
        });

        view.getRbOtsu().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbOtsu().isSelected()) {
                    view.getRbMaxEntropy().setSelected(false);
                    view.getRbManual().setSelected(false);
                    view.getLblValueLimiar().setText("automatic");
                }
            }
        });

        view.getRbManual().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbManual().isSelected()) {
                    view.getRbMaxEntropy().setSelected(false);
                    view.getRbOtsu().setSelected(false);
                    int value = view.getSlLimiarValue().getValue();
                    view.getLblValueLimiar().setText(value + "");
                }
            }
        });

        //evento slide bar para alterar o valor limiar
        view.getSlLimiarValue().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbManual().isSelected()) {
                    int value = view.getSlLimiarValue().getValue();
                    view.getLblValueLimiar().setText(value + "");
                }
            }
        });
    }

    //metodos adicionais
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
