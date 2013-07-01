/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.curvature;

import model.components.JImageView;

/**
 *
 * @author Anderson
 */
public class ViewSignature extends javax.swing.JFrame {

    /**
     * Creates new form ViewSignature
     */
    public ViewSignature() {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        lblSignature = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ImageView = new JImageView();
        jLabel4 = new javax.swing.JLabel();
        cbInitialPoint = new javax.swing.JCheckBox();
        cbRotation = new javax.swing.JCheckBox();
        cbTranslation = new javax.swing.JCheckBox();
        cbScala = new javax.swing.JCheckBox();
        btnGenerate = new javax.swing.JButton();
        pgBar = new javax.swing.JProgressBar();
        ImageViewGrafico = new JImageView();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        rbRadius = new javax.swing.JRadioButton();
        rbDiameter = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ImageViewSignature = new JImageView();
        jLabel9 = new javax.swing.JLabel();
        txtAngle = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblSignature.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSignature.setText("Signature");

        jLabel8.setText("line and starting point");

        ImageView.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ImageViewLayout = new javax.swing.GroupLayout(ImageView);
        ImageView.setLayout(ImageViewLayout);
        ImageViewLayout.setHorizontalGroup(
            ImageViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        ImageViewLayout.setVerticalGroup(
            ImageViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        jLabel4.setText("Options invariants");

        cbInitialPoint.setText("Initial Point");

        cbRotation.setText("Rotation");

        cbTranslation.setText("Translation");

        cbScala.setText("Scala");

        btnGenerate.setText("Generate Signature");

        pgBar.setName(""); // NOI18N

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

        jLabel7.setText("Sing signature ");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        rbRadius.setText("Larger radius");

        rbDiameter.setText("Larger diameter");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbRadius)
                    .addComponent(rbDiameter))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbRadius)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbDiameter)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel1.setBackground(new java.awt.Color(51, 51, 255));
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(255, 51, 51));
        jLabel2.setOpaque(true);

        jLabel3.setText("line e points capture");

        jLabel5.setBackground(new java.awt.Color(255, 255, 51));
        jLabel5.setOpaque(true);

        jLabel6.setText("centroid");

        ImageViewSignature.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ImageViewSignatureLayout = new javax.swing.GroupLayout(ImageViewSignature);
        ImageViewSignature.setLayout(ImageViewSignatureLayout);
        ImageViewSignatureLayout.setHorizontalGroup(
            ImageViewSignatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        ImageViewSignatureLayout.setVerticalGroup(
            ImageViewSignatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        jLabel9.setText("Angle:");

        txtAngle.setText("10");

        jLabel10.setBackground(new java.awt.Color(51, 255, 51));
        jLabel10.setOpaque(true);

        jLabel11.setText("Border");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ImageView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8)))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ImageViewSignature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel11)))
                                        .addGap(32, 32, 32)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbInitialPoint)
                                            .addComponent(cbScala)
                                            .addComponent(cbTranslation)
                                            .addComponent(cbRotation)
                                            .addComponent(jLabel4)
                                            .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel9)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtAngle, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6))))
                            .addComponent(jLabel7)
                            .addComponent(ImageViewGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pgBar, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblSignature))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSignature)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(cbInitialPoint)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbRotation)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbTranslation)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbScala)
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtAngle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ImageViewSignature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ImageView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnGenerate)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING))))
                        .addGap(7, 7, 7)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ImageViewGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pgBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXImageView ImageView;
    private org.jdesktop.swingx.JXImageView ImageViewGrafico;
    private org.jdesktop.swingx.JXImageView ImageViewSignature;
    private javax.swing.JButton btnGenerate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
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
    private javax.swing.JLabel lblSignature;
    private javax.swing.JProgressBar pgBar;
    private javax.swing.JRadioButton rbDiameter;
    private javax.swing.JRadioButton rbRadius;
    private javax.swing.JTextField txtAngle;
    // End of variables declaration//GEN-END:variables

    public JImageView  getImageView() {
        return (JImageView) ImageView;
    }

    public void setImageView(org.jdesktop.swingx.JXImageView ImageView) {
        this.ImageView = ImageView;
    }

    public JImageView getImageViewGrafico() {
        return (JImageView) ImageViewGrafico;
    }

    public void setImageViewGrafico(org.jdesktop.swingx.JXImageView ImageViewGrafico) {
        this.ImageViewGrafico = ImageViewGrafico;
    }

    public JImageView getImageViewSignature() {
        return (JImageView) ImageViewSignature;
    }

    public void setImageViewSignature(org.jdesktop.swingx.JXImageView ImageViewSignature) {
        this.ImageViewSignature = ImageViewSignature;
    }

    public javax.swing.JButton getBtnGenerate() {
        return btnGenerate;
    }

    public void setBtnGenerate(javax.swing.JButton btnGenerate) {
        this.btnGenerate = btnGenerate;
    }

    public javax.swing.JCheckBox getCbInitialPoint() {
        return cbInitialPoint;
    }

    public void setCbInitialPoint(javax.swing.JCheckBox cbInitialPoint) {
        this.cbInitialPoint = cbInitialPoint;
    }

    public javax.swing.JCheckBox getCbRotation() {
        return cbRotation;
    }

    public void setCbRotation(javax.swing.JCheckBox cbRotation) {
        this.cbRotation = cbRotation;
    }

    public javax.swing.JCheckBox getCbScala() {
        return cbScala;
    }

    public void setCbScala(javax.swing.JCheckBox cbScala) {
        this.cbScala = cbScala;
    }

    public javax.swing.JCheckBox getCbTranslation() {
        return cbTranslation;
    }

    public void setCbTranslation(javax.swing.JCheckBox cbTranslation) {
        this.cbTranslation = cbTranslation;
    }

    public javax.swing.JProgressBar getPgBar() {
        return pgBar;
    }

    public void setPgBar(javax.swing.JProgressBar pgBar) {
        this.pgBar = pgBar;
    }

    public javax.swing.JRadioButton getRbDiameter() {
        return rbDiameter;
    }

    public void setRbDiameter(javax.swing.JRadioButton rbDiameter) {
        this.rbDiameter = rbDiameter;
    }

    public javax.swing.JRadioButton getRbRadius() {
        return rbRadius;
    }

    public void setRbRadius(javax.swing.JRadioButton rbRadius) {
        this.rbRadius = rbRadius;
    }

    public javax.swing.JTextField getTxtAngle() {
        return txtAngle;
    }

    public void setTxtAngle(javax.swing.JTextField txtAngle) {
        this.txtAngle = txtAngle;
    }
}
