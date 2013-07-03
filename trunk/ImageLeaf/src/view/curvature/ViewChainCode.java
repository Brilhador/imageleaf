/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.curvature;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import model.components.JImageView;
import org.jdesktop.swingx.JXImageView;

/**
 *
 * @author anderson
 */
public class ViewChainCode extends javax.swing.JFrame {

    /**
     * Creates new form ViewChainCode
     */
    public ViewChainCode() {
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

        jLabel1 = new javax.swing.JLabel();
        btnGenerate = new javax.swing.JButton();
        pgBar = new javax.swing.JProgressBar();
        ImageView = new JImageView();
        ImageViewGrafico = new JImageView();
        ImageViewRecorte = new JImageView();
        jLabel2 = new javax.swing.JLabel();
        lblValueChainCode = new javax.swing.JLabel();
        cbRotation = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        cbTranslation = new javax.swing.JCheckBox();
        cbScala = new javax.swing.JCheckBox();
        txtWidth = new javax.swing.JTextField();
        txtHeigth = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbInitialPoint = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Chain Code");

        btnGenerate.setText("Generate Curvature");

        pgBar.setName(""); // NOI18N

        ImageView.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ImageViewLayout = new javax.swing.GroupLayout(ImageView);
        ImageView.setLayout(ImageViewLayout);
        ImageViewLayout.setHorizontalGroup(
            ImageViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        ImageViewLayout.setVerticalGroup(
            ImageViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        ImageViewGrafico.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ImageViewGraficoLayout = new javax.swing.GroupLayout(ImageViewGrafico);
        ImageViewGrafico.setLayout(ImageViewGraficoLayout);
        ImageViewGraficoLayout.setHorizontalGroup(
            ImageViewGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 568, Short.MAX_VALUE)
        );
        ImageViewGraficoLayout.setVerticalGroup(
            ImageViewGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 189, Short.MAX_VALUE)
        );

        ImageViewRecorte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ImageViewRecorteLayout = new javax.swing.GroupLayout(ImageViewRecorte);
        ImageViewRecorte.setLayout(ImageViewRecorteLayout);
        ImageViewRecorteLayout.setHorizontalGroup(
            ImageViewRecorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        ImageViewRecorteLayout.setVerticalGroup(
            ImageViewRecorteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel2.setText("Code:");

        lblValueChainCode.setText("...");

        cbRotation.setText("Rotation");

        jLabel4.setText("Options invariants");

        cbTranslation.setText("Translation");

        cbScala.setText("Scala");

        txtWidth.setText("30");
        txtWidth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWidthActionPerformed(evt);
            }
        });

        txtHeigth.setText("50");

        jLabel5.setText("width:");

        jLabel6.setText("heigth:");

        cbInitialPoint.setText("Initial Point");

        jLabel7.setText("Coordinates: (X, Y)");

        jLabel10.setText("Border");

        jLabel9.setBackground(new java.awt.Color(0, 255, 0));
        jLabel9.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(255, 51, 0));
        jLabel3.setOpaque(true);

        jLabel8.setText("Ponto Inicial");

        jLabel11.setText("legend:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblValueChainCode, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7)
                    .addComponent(ImageViewGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ImageView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ImageViewRecorte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbTranslation)
                                    .addComponent(cbRotation)
                                    .addComponent(cbScala)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtHeigth, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(cbInitialPoint)))
                            .addComponent(jLabel4)
                            .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pgBar, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pgBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(31, 31, 31)
                                .addComponent(cbInitialPoint)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbRotation)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbTranslation)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbScala)
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtHeigth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(btnGenerate))
                            .addComponent(ImageViewRecorte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ImageView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblValueChainCode))
                        .addGap(12, 12, 12)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(ImageViewGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtWidthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWidthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtWidthActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXImageView ImageView;
    private org.jdesktop.swingx.JXImageView ImageViewGrafico;
    private org.jdesktop.swingx.JXImageView ImageViewRecorte;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JCheckBox cbInitialPoint;
    private javax.swing.JCheckBox cbRotation;
    private javax.swing.JCheckBox cbScala;
    private javax.swing.JCheckBox cbTranslation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblValueChainCode;
    private javax.swing.JProgressBar pgBar;
    private javax.swing.JTextField txtHeigth;
    private javax.swing.JTextField txtWidth;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JButton getBtnGenerate() {
        return btnGenerate;
    }

    public void setBtnGenerate(javax.swing.JButton btnGenerate) {
        this.btnGenerate = btnGenerate;
    }

    public JImageView getImageView() {
        return (JImageView) ImageView;
    }

    public void setImageView(JImageView ImageView) {
        this.ImageView = ImageView;
    }
    

    public JXImageView getLblImageCurvature() {
        return ImageViewGrafico;
    }

    public void setLblImageCurvature(JXImageView lblImageCurvature) {
        this.ImageViewGrafico = lblImageCurvature;
    }

    public JProgressBar getPgBar() {
        return pgBar;
    }

    public void setPgBar(JProgressBar pgBar) {
        this.pgBar = pgBar;
    }

    public void setImageView(JXImageView ImageView) {
        this.ImageView = ImageView;
    }

    public JXImageView getImageViewGrafico() {
        return ImageViewGrafico;
    }

    public void setImageViewGrafico(JXImageView ImageViewGrafico) {
        this.ImageViewGrafico = ImageViewGrafico;
    }

    public JXImageView getImageViewRecorte() {
        return ImageViewRecorte;
    }

    public void setImageViewRecorte(JXImageView ImageViewRecorte) {
        this.ImageViewRecorte = ImageViewRecorte;
    }

    public JCheckBox getCbInitialPoint() {
        return cbInitialPoint;
    }

    public void setCbInitialPoint(JCheckBox cbInitialPoint) {
        this.cbInitialPoint = cbInitialPoint;
    }

    public JCheckBox getCbRotation() {
        return cbRotation;
    }

    public void setCbRotation(JCheckBox cbRotation) {
        this.cbRotation = cbRotation;
    }

    public JCheckBox getCbScala() {
        return cbScala;
    }

    public void setCbScala(JCheckBox cbScala) {
        this.cbScala = cbScala;
    }

    public JCheckBox getCbTranslation() {
        return cbTranslation;
    }

    public void setCbTranslation(JCheckBox cbTranslation) {
        this.cbTranslation = cbTranslation;
    }

    public JLabel getLblValueChainCode() {
        return lblValueChainCode;
    }

    public void setLblValueChainCode(JLabel lblValueChainCode) {
        this.lblValueChainCode = lblValueChainCode;
    }

    public JTextField getTxtHeigth() {
        return txtHeigth;
    }

    public void setTxtHeigth(JTextField txtHeigth) {
        this.txtHeigth = txtHeigth;
    }

    public JTextField getTxtWidth() {
        return txtWidth;
    }

    public void setTxtWidth(JTextField txtWidth) {
        this.txtWidth = txtWidth;
    }
    
    
}
