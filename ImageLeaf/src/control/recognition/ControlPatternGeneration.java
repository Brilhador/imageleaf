/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.recognition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import model.Group;
import model.Pattern;
import view.recognition.ViewPatternGeneration;

/**
 *
 * @author anderson
 */
public class ControlPatternGeneration {

    private ViewPatternGeneration view = null;
    private String pathData = null;
    private String pathARFF = null;
    private JFileChooser cDiretorio = new JFileChooser();

    public ControlPatternGeneration() {
        view = new ViewPatternGeneration();
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initEvents();
        view.setVisible(true);
    }

    private void initEvents() {

        stopProgressBar();

        view.getBtnLocale().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (cDiretorio.showOpenDialog(view) == cDiretorio.APPROVE_OPTION) {
                    pathData = cDiretorio.getSelectedFile().getAbsolutePath();
                    view.getTxtPath().setText(pathData);
                }
                cDiretorio.setCurrentDirectory(cDiretorio.getSelectedFile().getAbsoluteFile());
            }
        });

        view.getBtnLocaleARFF().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (cDiretorio.showOpenDialog(view) == cDiretorio.APPROVE_OPTION) {
                    pathARFF = cDiretorio.getSelectedFile().getAbsolutePath();
                    view.getTxtPathARFF().setText(pathARFF);
                }
                cDiretorio.setCurrentDirectory(cDiretorio.getSelectedFile().getAbsoluteFile());
            }
        });

        view.getBtnStart().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        try {
                            if (!view.getTxtPath().getText().equals("...")) {
                                startProgressBar();
                                String nome = view.getTxtNameARFF().getText();
                                boolean signature = view.getCkSignature().isSelected();
                                boolean measures = view.getCkMeasures().isSelected();
                                boolean chaincode = view.getCkChainCode().isSelected();
                                boolean fourier = view.getCkFourier().isSelected();
                                boolean haralick = view.getCkHaralick().isSelected();
                                boolean qcch = view.getCkQCCH().isSelected();
                                int angle = Integer.parseInt(view.getTxtAngle().getText());
                                int width = Integer.parseInt(view.getTxtWidth().getText());
                                int heigth = Integer.parseInt(view.getTxtHeigth().getText());
                                int series = Integer.parseInt(view.getTxtSeries().getText());
                                if (signature == false && chaincode == false && fourier == false && haralick == false && qcch == false) {
                                    JOptionPane.showMessageDialog(view, "Select a descritor", "", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    Pattern pattern = new Pattern();
                                    pattern.startAnglePattern(nome, pathData, pathARFF, signature, angle, measures, chaincode, width, heigth, fourier, series, qcch, haralick);
                                }
                                stopProgressBar();
                                JOptionPane.showMessageDialog(view, "Finished", "", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(view, "Erro!", "", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                work.execute();
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
