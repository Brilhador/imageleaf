/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Anderson
 */
public class Grafico {

    public static BufferedImage curvature(ArrayList<Dimension> lista, int sizeWidth, int sizeHeight, String title) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Dimension dimension : lista) {
            dataset.addValue(dimension.width, "curve", dimension.height + "");
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
            int[] histogramaR = Histograma.histogramaRed(img);
            int[] histogramaG = Histograma.histogramaGreen(img);
            int[] histogramaB = Histograma.histogramaBlue(img);

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
}
