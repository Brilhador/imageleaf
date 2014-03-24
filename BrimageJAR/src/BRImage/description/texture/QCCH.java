package BRImage.description.texture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 *
 * @author Thiago
 */
public class QCCH {

    BufferedImage bufferedImage;
    BufferedImage img;
    Raster raster;
    int radius;
    double[] histogram = new double[40];

    public QCCH(BufferedImage bufferedImage, int radius) {
        this.bufferedImage = bufferedImage;
        this.img = bufferedImage;
        this.raster = img.getData();
        this.radius = radius;
        historam_qcch();
    }
    
    public double[] get_histogram(){
        return this.histogram;
    }

    private void historam_qcch() {
        //horizontal
        double h;
        //vertical
        double v;
        //diagonal
        double d;
        //anti-diagonal
        double a;
        //average
        double avg;
        //System.out.println("wdth->" + bufferedImage.getWidth());
        //System.out.println("heght->" + bufferedImage.getHeight());

        for (int i = radius; i < bufferedImage.getWidth() - radius; i++) {
            for (int j = radius; j < bufferedImage.getHeight() - radius; j++) {
                h = Math.abs(rbg_value(bufferedImage.getRGB(i - radius, j)) - rbg_value(bufferedImage.getRGB(i + radius, j)));
                v = Math.abs(rbg_value(bufferedImage.getRGB(i, j - radius)) - rbg_value(bufferedImage.getRGB(i, j + radius)));
                d = Math.abs(rbg_value(bufferedImage.getRGB(i - radius, j - radius)) - rbg_value(bufferedImage.getRGB(i + radius, j + radius)));
                a = Math.abs(rbg_value(bufferedImage.getRGB(i + radius, j - radius)) - rbg_value(bufferedImage.getRGB(i - radius, j + radius)));

                avg = (h + v + d + a) / 4;

                iHistogram(avg);
            }
        }

    }

    private void iHistogram(double avg) {
        int iPosition = 0;
        if (avg >= 0 && avg <= 15.5) {
            iPosition = (int) Math.floor(avg);
        } else if (avg > 15.5 && avg <= 35.5) {
            double result = 17.5;
            while (result < avg) {
                result += 2;
                iPosition++;
            }
            iPosition += 16;
        } else if (avg > 35.5 && avg <= 85.5) {
            double result = 40.5;
            while (result < avg) {
                result += 5;
                iPosition++;
            }
            iPosition += 26;
        } else if (avg > 85.5 && avg <= 115.5) {
            double result = 95.5;
            while (result < avg) {
                result += 10;
                iPosition++;
            }
            iPosition += 36;
        } else {
            iPosition = 39;
        }

        histogram[iPosition]++;
    }

    private double rbg_value(int iPixel) {
        Color rbg_pixel = new Color(iPixel);
        return 0.299 * rbg_pixel.getRed() + 0.587 * rbg_pixel.getGreen()
                + 0.114 * rbg_pixel.getBlue();
    }
}
