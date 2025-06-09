package org.timerdar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.data.category.DefaultCategoryDataset;
import org.timerdar.responses.TimeAndTempList;

import java.io.ByteArrayOutputStream;

public class ChartMaker {

    public byte[] createChart(String city, TimeAndTempList temps){

        try{
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 0; i < 24; i++){
                dataset.addValue(temps.getTemperature_2m().get(i), "Temperature", temps.getTime().get(i));
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    "Temperature next 24 hours in " + city +", C",
                    "DateTime",
                    "Temperature",
                    dataset);
            CategoryAxis cat = chart.getCategoryPlot().getDomainAxis();
            cat.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ChartUtils.writeChartAsPNG(outputStream, chart, 1000, 600);
            return outputStream.toByteArray();

        }catch (Exception e){
            throw new RuntimeException("Error while creating chart: " + e.getMessage());
        }

    }

}
