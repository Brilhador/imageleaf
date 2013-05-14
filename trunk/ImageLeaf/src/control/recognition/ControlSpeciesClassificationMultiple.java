/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.recognition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.Pattern;
import model.Sorter;
import view.recognition.ViewSpeciesClassificationMultiple;

/**
 *
 * @author anderson
 */
public class ControlSpeciesClassificationMultiple {

    private ViewSpeciesClassificationMultiple view = null;
    private String pathData = null;
    private String pathPattern = null;
    private int distancia = 0;

    public ControlSpeciesClassificationMultiple() {
        view = new ViewSpeciesClassificationMultiple();
        initEvents();
        view.setVisible(true);
    }

    private void initEvents() {

        stopProgressBar();
        view.getRbEuclidiana().setSelected(true);

        view.getBtnLocaleData().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser cDiretorio = new JFileChooser();
                cDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (cDiretorio.showOpenDialog(view) == cDiretorio.APPROVE_OPTION) {
                    pathData = cDiretorio.getSelectedFile().getAbsolutePath();
                    view.getTxtPathData().setText(pathData);
                }
            }
        });

        view.getBtnLocalePattern().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser cDiretorio = new JFileChooser();
                cDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (cDiretorio.showOpenDialog(view) == cDiretorio.APPROVE_OPTION) {
                    pathPattern = cDiretorio.getSelectedFile().getAbsolutePath();
                    view.getTxtPathPattern().setText(pathPattern);
                }
            }
        });

        view.getBtnStart().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        if (!view.getTxtPathData().getText().equals("...") && !view.getTxtPathPattern().getText().equals("...")) {
                            startProgressBar();
                            new Sorter().sorterMultipleImage(pathData, pathPattern, distancia);
                            stopProgressBar();
                            JOptionPane.showMessageDialog(view, "Finished", "", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(view, "Erro!", "", JOptionPane.ERROR_MESSAGE);
                        }
                        return null;
                    }
                };
                work.execute();
            }
        });

        view.getRbEuclidiana().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbEuclidiana().isSelected()) {
                    view.getRbManhattan().setSelected(false);
                    distancia = 0;
                }
            }
        });
        
        view.getRbManhattan().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getRbManhattan().isSelected()) {
                    view.getRbEuclidiana().setSelected(false);
                    distancia = 1;
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
