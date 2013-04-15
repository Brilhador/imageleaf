/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author anderson
 */
public class viewPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form viewPrincipal
     */
    public viewPrincipal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jPanelPrincipal = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        mFile = new javax.swing.JMenu();
        menuOpen = new javax.swing.JMenuItem();
        mEdit = new javax.swing.JMenu();
        mFilters = new javax.swing.JMenu();
        mFilterBlur = new javax.swing.JMenu();
        mFilterBlurLow = new javax.swing.JMenuItem();
        mFilterBlurMedian = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        mCurvature = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        mFile.setText("File");

        menuOpen.setText("Open");
        mFile.add(menuOpen);

        jMenuBar1.add(mFile);

        mEdit.setText("Edit");
        jMenuBar1.add(mEdit);

        mFilters.setText("Filters");

        mFilterBlur.setText("Blur");

        mFilterBlurLow.setText("Low-Pass ");
        mFilterBlurLow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mFilterBlurLowActionPerformed(evt);
            }
        });
        mFilterBlur.add(mFilterBlurLow);

        mFilterBlurMedian.setText("Median");
        mFilterBlur.add(mFilterBlurMedian);

        mFilters.add(mFilterBlur);

        jMenu2.setText("Edge - Detect");

        jMenuItem4.setText("Sobel");
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("Prewitt");
        jMenu2.add(jMenuItem5);

        jMenuItem6.setText("Roberts");
        jMenu2.add(jMenuItem6);

        mFilters.add(jMenu2);

        jMenuBar1.add(mFilters);

        mCurvature.setText("Curvature");

        jMenuItem2.setText("Chain Code");
        mCurvature.add(jMenuItem2);

        jMenuBar1.add(mCurvature);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(jPanelPrincipal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mFilterBlurLowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mFilterBlurLowActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mFilterBlurLowActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JDesktopPane jPanelPrincipal;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenu mCurvature;
    private javax.swing.JMenu mEdit;
    private javax.swing.JMenu mFile;
    private javax.swing.JMenu mFilterBlur;
    private javax.swing.JMenuItem mFilterBlurLow;
    private javax.swing.JMenuItem mFilterBlurMedian;
    private javax.swing.JMenu mFilters;
    private javax.swing.JMenuItem menuOpen;
    // End of variables declaration//GEN-END:variables
    
    
    //getters e setters
    public JDesktopPane getjPanelPrincipal() {
        return jPanelPrincipal;
    }

    public void setjPanelPrincipal(JDesktopPane jPanelPrincipal) {
        this.jPanelPrincipal = jPanelPrincipal;
    }

    public javax.swing.JMenuItem getMenuOpen() {
        return menuOpen;
    }

    public void setMenuOpen(javax.swing.JMenuItem menuOpen) {
        this.menuOpen = menuOpen;
    }

    public JMenu getmCurvature() {
        return mCurvature;
    }

    public void setmCurvature(JMenu mCurvature) {
        this.mCurvature = mCurvature;
    }

    public JMenu getmEdit() {
        return mEdit;
    }

    public void setmEdit(JMenu mEdit) {
        this.mEdit = mEdit;
    }

    public JMenu getmFile() {
        return mFile;
    }

    public void setmFile(JMenu mFile) {
        this.mFile = mFile;
    }

    public JMenu getmFilters() {
        return mFilters;
    }

    public void setmFilters(JMenu mFilters) {
        this.mFilters = mFilters;
    }

    public JMenuItem getmFilterBlurLow() {
        return mFilterBlurLow;
    }

    public void setmFilterBlurLow(JMenuItem mFilterBlurLow) {
        this.mFilterBlurLow = mFilterBlurLow;
    }

    public JMenuItem getmFilterBlurMedian() {
        return mFilterBlurMedian;
    }

    public void setmFilterBlurMedian(JMenuItem mFilterBlurMedian) {
        this.mFilterBlurMedian = mFilterBlurMedian;
    }

    
}
