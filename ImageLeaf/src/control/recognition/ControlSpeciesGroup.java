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
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import model.Group;
import view.recognition.ViewSpeciesGroup;

/**
 *
 * @author anderson
 */
public class ControlSpeciesGroup {

    private ViewSpeciesGroup view = null;
    private String caminho = null;

    public ControlSpeciesGroup() {
        view = new ViewSpeciesGroup();
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
                JFileChooser cDiretorio = new JFileChooser();
                cDiretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (cDiretorio.showOpenDialog(view) == cDiretorio.APPROVE_OPTION) {
                    caminho = cDiretorio.getSelectedFile().getAbsolutePath();
                    view.getTxtPath().setText(caminho);
                }
            }
        });

        view.getBrnStart().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker work = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        if (!view.getTxtPath().getText().equals("...") && view.getTxtNameDir().getText() != null) {
                            startProgressBar();
                            new Group().startBaseCLEF(caminho, view.getTxtNameDir().getText());
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
        view.getPgbar().setIndeterminate(true);
        view.getPgbar().setVisible(true);
    }

    public void stopProgressBar() {
        view.getPgbar().setVisible(false);
        view.getPgbar().setIndeterminate(false);
    }
}
