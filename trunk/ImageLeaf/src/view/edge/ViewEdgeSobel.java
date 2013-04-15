/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.edge;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

/**
 *
 * @author anderson
 */
public class ViewEdgeSobel extends javax.swing.JFrame {

    /**
     * Creates new form ViewEdgeSobel
     */
    public ViewEdgeSobel() {
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

        lblImagePreview = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        slLimiarValue = new javax.swing.JSlider();
        rbMaxEntropy = new javax.swing.JRadioButton();
        rbOtsu = new javax.swing.JRadioButton();
        lblValueLimiar = new javax.swing.JLabel();
        rbManual = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        btnPreview = new javax.swing.JButton();
        btnRestore = new javax.swing.JButton();
        btnApply = new javax.swing.JButton();
        pgBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblImagePreview.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Sobel");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("Limiar");

        slLimiarValue.setMajorTickSpacing(1);
        slLimiarValue.setMaximum(255);
        slLimiarValue.setMinimum(1);
        slLimiarValue.setMinorTickSpacing(1);
        slLimiarValue.setValue(100);

        rbMaxEntropy.setText("Max Entropy");

        rbOtsu.setText("Otsu");

        lblValueLimiar.setForeground(new java.awt.Color(255, 0, 0));

        rbManual.setText("Manual");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbManual)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblValueLimiar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(slLimiarValue, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbMaxEntropy)
                        .addGap(56, 56, 56)
                        .addComponent(rbOtsu))
                    .addComponent(jLabel2)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbMaxEntropy)
                            .addComponent(rbOtsu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbManual))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblValueLimiar, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(slLimiarValue, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnPreview.setText("Preview");

        btnRestore.setText("Restore");

        btnApply.setText("Apply");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pgBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnApply, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRestore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblImagePreview, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPreview)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRestore)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnApply)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addComponent(pgBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblImagePreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnRestore;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblImagePreview;
    private javax.swing.JLabel lblValueLimiar;
    private javax.swing.JProgressBar pgBar;
    private javax.swing.JRadioButton rbManual;
    private javax.swing.JRadioButton rbMaxEntropy;
    private javax.swing.JRadioButton rbOtsu;
    private javax.swing.JSlider slLimiarValue;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JButton getBtnApply() {
        return btnApply;
    }

    public void setBtnApply(javax.swing.JButton btnApply) {
        this.btnApply = btnApply;
    }

    public javax.swing.JButton getBtnPreview() {
        return btnPreview;
    }

    public void setBtnPreview(javax.swing.JButton btnPreview) {
        this.btnPreview = btnPreview;
    }

    public javax.swing.JButton getBtnRestore() {
        return btnRestore;
    }

    public void setBtnRestore(javax.swing.JButton btnRestore) {
        this.btnRestore = btnRestore;
    }

    public javax.swing.JLabel getLblImagePreview() {
        return lblImagePreview;
    }

    public void setLblImagePreview(javax.swing.JLabel lblImagePreview) {
        this.lblImagePreview = lblImagePreview;
    }

    public javax.swing.JLabel getLblValueLimiar() {
        return lblValueLimiar;
    }

    public void setLblValueLimiar(javax.swing.JLabel lblValueLimiar) {
        this.lblValueLimiar = lblValueLimiar;
    }

    public javax.swing.JProgressBar getPgBar() {
        return pgBar;
    }

    public void setPgBar(javax.swing.JProgressBar pgBar) {
        this.pgBar = pgBar;
    }

    public javax.swing.JRadioButton getRbMaxEntropy() {
        return rbMaxEntropy;
    }

    public void setRbMaxEntropy(javax.swing.JRadioButton rbMaxEntropy) {
        this.rbMaxEntropy = rbMaxEntropy;
    }

    public javax.swing.JRadioButton getRbOtsu() {
        return rbOtsu;
    }

    public void setRbOtsu(javax.swing.JRadioButton rbOtsu) {
        this.rbOtsu = rbOtsu;
    }

    public javax.swing.JSlider getSlLimiarValue() {
        return slLimiarValue;
    }

    public void setSlLimiarValue(javax.swing.JSlider slLimiarValue) {
        this.slLimiarValue = slLimiarValue;
    }

    public JRadioButton getRbManual() {
        return rbManual;
    }

    public void setRbManual(JRadioButton rbManual) {
        this.rbManual = rbManual;
    }
    
}
