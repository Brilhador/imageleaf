/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BRImage.useful;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import BRImage.description.color.Histogram;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

/**
 *
 * @author Anderson
 */
public class Grafico {

    public static BufferedImage DFT2IMG(double[] value1, double[] value2, int width, int height, String title) {

        try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //carregar dataset com os histogramas
            for (int i = 1; i < value1.length; i++) {
                dataset.addValue(value1[i], "real", "" + i);
                dataset.addValue(value2[i], "indice", "" + i);
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    title,
                    "REAL",
                    "FREQUENCIA",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);

            return chart.createBufferedImage(width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage DFT2IMG(double[] value1, double[] value2, int width, int height, String title, int indice) {

        try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            if (value1.length >= indice) {
                //carregar dataset com os histogramas
                for (int i = 0; i < indice; i++) {
                    dataset.addValue(value1[i], "real", "" + i);
                    dataset.addValue(value2[i], "indice", "" + i);
                }

                JFreeChart chart = ChartFactory.createLineChart(
                        title,
                        "REAL",
                        "FREQUENCIA",
                        dataset,
                        PlotOrientation.VERTICAL,
                        true,
                        false,
                        false);

                return chart.createBufferedImage(width, height);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;

    }

    public static BufferedImage DFT2IMG(double[] x1, double[] y1, double[] x2, double[] y2, int width, int height, String title) {

        try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //carregar dataset com os histogramas
            for (int i = 0; i < x1.length; i++) {
                dataset.addValue(x1[i], "real - 1", "" + i);
                dataset.addValue(y1[i], "imaginario - 1", "" + i);
                dataset.addValue(x2[i], "real - 2", "" + i);
                dataset.addValue(y2[i], "imaginario - 2", "" + i);
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    title,
                    "REAL",
                    "IMAGINARIO",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);

            return chart.createBufferedImage(width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
     public static BufferedImage DFT2IMG(double[] coefficients, int width, int height, String title) {

        try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //carregar dataset com os histogramas
            for (int i = 0; i < coefficients.length; i++) {
                dataset.addValue(coefficients[i], "coefficients", "" + i);
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    title,
                    "REAL",
                    "INDICE",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);

            return chart.createBufferedImage(width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage Signature(int[] signature1, int[] signature2, int width, int height, String title) {

        try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //carregar dataset com os histogramas
            for (int i = 0; i < signature2.length; i++) {
                dataset.addValue(signature1[i], "1", "" + i);
                dataset.addValue(signature2[i], "2", "" + i);
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    title,
                    "Angle",
                    "Distance",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);

            return chart.createBufferedImage(width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static BufferedImage Signature(double[] signature,  int width, int height, String title) {

        try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //carregar dataset com os histogramas
            for (int i = 0; i < signature.length; i++) {
                dataset.addValue(signature[i], "signature", "" + i);
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    title,
                    "Angle",
                    "Distance",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);

            return chart.createBufferedImage(width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static BufferedImage Signature(double[] signature1, double[] signature2,  int width, int height, String title) {

        try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //carregar dataset com os histogramas
            for (int i = 0; i < signature1.length; i++) {
                dataset.addValue(signature1[i], "signature1", "" + i);
                dataset.addValue(signature2[i], "signature2", "" + i);
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    title,
                    "Angle",
                    "Distance",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);

            return chart.createBufferedImage(width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage curvatureDimension(ArrayList<Dimension> lista, int sizeWidth, int sizeHeight, String title) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < lista.size(); i++) {
            Dimension dimension = lista.get(i);
            dataset.addValue(dimension.width, "curve - (x)", "" + i);
            dataset.addValue(dimension.height, "curve - (y)", "" + i);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                title,
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);

        //custom chart
        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.RED);
        chart.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.BLUE);

        return chart.createBufferedImage(sizeWidth, sizeHeight);

    }

    public static BufferedImage curvatureChainCode(ArrayList<Integer> lista, int sizeWidth, int sizeHeight, String title) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < lista.size(); i++) {
            dataset.addValue(lista.get(i), "chain code", "" + i);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                title,
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);

        //custom chart
        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.RED);

        return chart.createBufferedImage(sizeWidth, sizeHeight);

    }

    public static BufferedImage histogramaRGB(BufferedImage img, int sizeWidth, int sizeHeight, String title) {

        try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //pega o histograma da imagem
            int[] histogramaR = Histogram.histogramaRed(img);
            int[] histogramaG = Histogram.histogramaGreen(img);
            int[] histogramaB = Histogram.histogramaBlue(img);

            //carregar dataset com os histogramas
            for (int i = 0; i <= 255; i++) {
                dataset.addValue(histogramaR[i], "Red", "" + i);
                dataset.addValue(histogramaG[i], "Green", "" + i);
                dataset.addValue(histogramaB[i], "Blue", "" + i);
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    title,
                    "Pixels",
                    "Frequência",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);

            //custom chart
            chart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.RED);
            chart.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.GREEN);
            chart.getCategoryPlot().getRenderer().setSeriesPaint(2, Color.BLUE);

            return chart.createBufferedImage(sizeWidth, sizeHeight);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage histogramaRGB(int[] histogramaR, int[] histogramaG, int[] histogramaB, int width, int height, String title) {

        try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //carregar dataset com os histogramas
            for (int i = 0; i <= 255; i++) {
                dataset.addValue(histogramaR[i], "Red", "" + i);
                dataset.addValue(histogramaG[i], "Green", "" + i);
                dataset.addValue(histogramaB[i], "Blue", "" + i);
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    title,
                    "Pixels",
                    "Frequência",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);

            //custom chart
            chart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.RED);
            chart.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.GREEN);
            chart.getCategoryPlot().getRenderer().setSeriesPaint(2, Color.BLUE);

            return chart.createBufferedImage(width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
     public static BufferedImage histogramaGRAY(BufferedImage img, int sizeWidth, int sizeHeight, String title) {

        try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //pega o histograma da imagem
            int[] histogramaGRAY = Histogram.histogramaGray(img);

            //carregar dataset com os histogramas
            for (int i = 0; i <= 255; i++) {
                dataset.addValue(histogramaGRAY[i], "GRAY", "" + i);
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    title,
                    "Pixels",
                    "Frequência",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);

            //custom chart
            chart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.BLACK);

            return chart.createBufferedImage(sizeWidth, sizeHeight);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage histograma(int[] histograma, int width, int height, String title, String labelx, String labely) {

        try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            //carregar dataset com os histogramas
            for (int i = 0; i < histograma.length; i++) {
                dataset.addValue(histograma[i], "histograma", "" + i);
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    title,
                    labelx,
                    labely,
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);

            return chart.createBufferedImage(width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage histogramaChainCode(double[] histograma, int width, int height, String title, String labelx, String labely) {

        try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//            dataset.setType(HistogramType.FREQUENCY);

            //carregar dataset com os histogramas
            for (int i = 0; i < histograma.length; i++) {
                dataset.addValue(histograma[i], "histograma", "" + i);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    title,
                    labelx,
                    labely,
                    dataset,
                    PlotOrientation.VERTICAL,
                    false,
                    false,
                    false);
            
            return chart.createBufferedImage(width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static BufferedImage histogramaChainCode(double[] histograma1, double[] histograma2, int width, int height, String title, String labelx, String labely) {
try {

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//            dataset.setType(HistogramType.FREQUENCY);

            //carregar dataset com os histogramas
            for (int i = 0; i < histograma1.length; i++) {
                dataset.addValue(histograma1[i], "img1", "" + i);
                dataset.addValue(histograma2[i], "img2", "" + i);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    title,
                    labelx,
                    labely,
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);
            
            return chart.createBufferedImage(width, height);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
