/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.blur;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

/**
 *
 * @author Anderson
 */
public class ViewBlurMedian extends javax.swing.JFrame {

    /**
     * Creates new form ViewBlurMedian
     */
    public ViewBlurMedian() {
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

        btnApply = new javax.swing.JToggleButton();
        btnRestore = new javax.swing.JButton();
        btnPreview = new javax.swing.JToggleButton();
        lblImagePreview = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        slMaskSize = new javax.swing.JSlider();
        lblMaskSize = new javax.swing.JLabel();
        lblMedian = new javax.swing.JLabel();
        pgBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnApply.setText("Apply");

        btnRestore.setText("Restore");

        btnPreview.setText("Preview");

        lblImagePreview.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slMaskSize.setMajorTickSpacing(2);
        slMaskSize.setMaximum(15);
        slMaskSize.setMinimum(3);
        slMaskSize.setMinorTickSpacing(2);
        slMaskSize.setPaintLabels(true);
        slMaskSize.setPaintTicks(true);
        slMaskSize.setSnapToTicks(true);
        slMaskSize.setValue(3);

        lblMaskSize.setText("Mask Size");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slMaskSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaskSize))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(lblMaskSize)
                .addGap(18, 18, 18)
                .addComponent(slMaskSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblMedian.setText("Median");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMedian)
                    .addComponent(btnPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRestore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnApply, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pgBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addComponent(lblImagePreview, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMedian)
                        .addGap(6, 6, 6)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(btnPreview)
                        .addGap(6, 6, 6)
                        .addComponent(btnRestore)
                        .addGap(6, 6, 6)
                        .addComponent(btnApply)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pgBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblImagePreview, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnApply;
    private javax.swing.JToggleButton btnPreview;
    private javax.swing.JButton btnRestore;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblImagePreview;
    private javax.swing.JLabel lblMaskSize;
    private javax.swing.JLabel lblMedian;
    private javax.swing.JProgressBar pgBar;
    private javax.swing.JSlider slMaskSize;
    // End of variables declaration//GEN-END:variables

    public JToggleButton getBtnApply() {
        return btnApply;
    }

    public void setBtnApply(JToggleButton btnApply) {
        this.btnApply = btnApply;
    }

    public JToggleButton getBtnPreview() {
        return btnPreview;
    }

    public void setBtnPreview(JToggleButton btnPreview) {
        this.btnPreview = btnPreview;
    }

    public JButton getBtnRestore() {
        return btnRestore;
    }

    public void setBtnRestore(JButton btnRestore) {
        this.btnRestore = btnRestore;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public void setjPanel1(JPanel jPanel1) {
        this.jPanel1 = jPanel1;
    }

    public JLabel getLblImagePreview() {
        return lblImagePreview;
    }

    public void setLblImagePreview(JLabel lblImagePreview) {
        this.lblImagePreview = lblImagePreview;
    }

    public JLabel getLblMaskSize() {
        return lblMaskSize;
    }

    public void setLblMaskSize(JLabel lblMaskSize) {
        this.lblMaskSize = lblMaskSize;
    }

    public JLabel getLblMedian() {
        return lblMedian;
    }

    public void setLblMedian(JLabel lblMedian) {
        this.lblMedian = lblMedian;
    }

    public JSlider getSlMaskSize() {
        return slMaskSize;
    }

    public void setSlMaskSize(JSlider slMaskSize) {
        this.slMaskSize = slMaskSize;
    }

    public JProgressBar getPgBar() {
        return pgBar;
    }

    public void setPgBar(JProgressBar pgBar) {
        this.pgBar = pgBar;
    }
}
