/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.ViewClassificar;
import view.ViewPDI;
import view.ViewPadrao;
import view.ViewPrincipal;

/**
 *
 * @author Anderson
 */
public class ControlPrincipal {

    private ViewPrincipal view = null;

    public ControlPrincipal() {
        view = new ViewPrincipal();
        //adiciona ação as botões da tela
        addEvents();
        //apos adicionar evento exibe a tela
        view.setVisible(true);
    }
   
    public void addEvents(){
        view.getJbpdi().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ControlPDI();
            }
        });
        
        view.getJbpadrao().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ControlPadrao();
            }
        });
        
        view.getJbclassificar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ControlClassificar();
            }
        });
    }
    
}
