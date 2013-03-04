/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.File;
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
                new ManageDirectory().criarDiretorio(new ManageDirectory().getDiretorioPadrao());
                new ControlPrincipal();
            }
        }); 
    }   
}
