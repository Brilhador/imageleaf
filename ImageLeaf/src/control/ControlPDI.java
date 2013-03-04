/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import model.Filtro;
import model.Histograma;
import model.Limiar;
import view.ViewPDI;

/**
 *
 * @author Anderson
 */
public class ControlPDI {

    private ViewPDI view = null;
    private String caminho;
    private Image image;

    public ControlPDI() {
        view = new ViewPDI();

        addEvents();
        view.setVisible(true);
    }

    public void addEvents() {
        //adiciona o evento abrir imagem
        //e exibe imagem aberta na tela
        view.getJmiAbrir().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser cFile = new JFileChooser();
                if (cFile.showOpenDialog(view) == cFile.APPROVE_OPTION) {
                    caminho = cFile.getSelectedFile().getAbsolutePath();
                    try {
                        //redimensionando a imagem para exibir no label
                        BufferedImage imagOut = model.Image.resizeImage(ImageIO.read(new File(caminho)), view.getJlImage().getWidth(), view.getJlImage().getHeight());
                        view.getJlImage().setIcon(new ImageIcon(imagOut));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        //adiciona o evento salvar imagem exibida na tela
        view.getJbsalvar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser cFile = new JFileChooser();
                if (cFile.showSaveDialog(view.getContentPane()) == cFile.APPROVE_OPTION) {
                    File file = cFile.getSelectedFile();
                    try {
                        ImageIO.write((BufferedImage) image, "JPG", file);
                    } catch (IOException ex) {
                        Logger.getLogger(ControlPDI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        view.getJbexecutar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    image = ImageIO.read(new File(caminho));
                } catch (IOException ex) {
                    Logger.getLogger(ControlPDI.class.getName()).log(Level.SEVERE, null, ex);
                }
                //converte a imagem para preto e branco
                image = model.Image.convertToGray((BufferedImage) image);
                int limiar = 32;
                //filtros de suavização
                if (view.getJrpassabaixas().isSelected()) {
                    image = Filtro.passaBaixas((BufferedImage) image, view.getJsmascara().getValue());
                } else if (view.getJrmediana().isSelected()) {
                    image = Filtro.mediana((BufferedImage) image, view.getJsmascara().getValue());
                }
                //equalizacao da imagem por canais rgb
                if (view.getJrequalizargb().isSelected()) {
                    image = Histograma.equalizaRGB((BufferedImage) image,
                            Histograma.histogramaRed((BufferedImage) image),
                            Histograma.histogramaGreen((BufferedImage) image),
                            Histograma.histogramaBlue((BufferedImage) image));
                }
                //limiar automatico por maxima soma das entropias
                if (view.getJrmaxentropia().isSelected()) {
                    BufferedImage BUimage = (BufferedImage) image;
                    limiar = Limiar.maxentropia(Histograma.histogramaGray((BufferedImage) image), BUimage.getHeight() * BUimage.getWidth());
                }
                //detectores de borda
                if (view.getJrsobel().isSelected()) {
                    int[][] img = Filtro.bordaSobel((BufferedImage) image);
                    image = Limiar.limiarizacao((BufferedImage) image, img, limiar);
                } else if (view.getJrprewitt().isSelected()) {
                    int[][] img = Filtro.bordaPrewitt((BufferedImage) image);
                    image = Limiar.limiarizacao((BufferedImage) image, img, limiar);
                } else if (view.getJrroberts().isSelected()) {
                    int[][] img = Filtro.bordaRoberts((BufferedImage) image);
                    image = Limiar.limiarizacao((BufferedImage) image, img, limiar);
                }
                view.getJllimiar().setText(limiar + "");
                BufferedImage imagOut = model.Image.resizeImage((BufferedImage) image, view.getJlImage().getWidth(), view.getJlImage().getHeight());
                view.getJlImage().setIcon(new ImageIcon(imagOut));
            }
        });
    }
}
