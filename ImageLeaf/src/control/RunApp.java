/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Color;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.CSV;
import model.ManageDirectory;

/**
 *
 * @author Anderson
 */
public class RunApp {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        try {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        } catch (UnsupportedLookAndFeelException ex) {
                            Logger.getLogger(RunApp.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(RunApp.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InstantiationException ex) {
                            Logger.getLogger(RunApp.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(RunApp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                new ManageDirectory().criarDiretorio(new ManageDirectory().getDiretorioPadrao());
                new ControlMenu();
            }
        });
    }
}
