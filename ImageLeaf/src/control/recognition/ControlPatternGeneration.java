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
import model.Group;
import model.Pattern;
import view.recognition.ViewPatternGeneration;

/**
 *
 * @author anderson
 */
public class ControlPatternGeneration {
    
    private ViewPatternGeneration view = null;
    private String caminho = null;
    
    public ControlPatternGeneration(){
        view = new ViewPatternGeneration();
        initEvents();
        view.setVisible(true);
    }

    private void initEvents() {
        
        stopProgressBar();

        view.getBtnLocale().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser cDiretorio = new JFileChooser();
                cDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (cDiretorio.showOpenDialog(view) == cDiretorio.APPROVE_OPTION) {
                    caminho = cDiretorio.getSelectedFile().getAbsolutePath();
                    view.getTxtPath().setText(caminho);
                }
            }
        });

        view.getBtnStart().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        if (!view.getTxtPath().getText().equals("...")) {
                            startProgressBar();
                            new Pattern().startAnglePattern(caminho);
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
