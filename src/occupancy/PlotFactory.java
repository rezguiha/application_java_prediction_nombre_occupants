/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package occupancy;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 *
 * @author rezguiha
 */
public class PlotFactory {
    DataContainer dataContainer;
    Hashtable<String,TimeSeries> timeSeriesContainer;
    
    public PlotFactory(DataContainer dataContainer) throws IOException, ParseException{
        this.dataContainer=dataContainer;
        timeSeriesContainer = new Hashtable<String, TimeSeries>();
    }
    
    JPanel getPlot(String[] variableNames) throws ParseException{
        Date[] dates=dataContainer.getDates();
        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
                
        for(int i = 0; i <variableNames.length; i++){
           timeSeriesContainer.put(variableNames[i], new TimeSeries(variableNames[i]));
           Double[] value = dataContainer.getData(variableNames[i]);
           
           for (int j = 0; j < dataContainer.getNumberOfSamples(); j++)
               timeSeriesContainer.get(variableNames[i]).add(new Hour(dates[j]), value[j]);
           
           timeSeriesCollection.addSeries(timeSeriesContainer.get(variableNames[i]));
       }
        
        JPanel chartPanel = new ChartPanel(ChartFactory.createTimeSeriesChart("Occupancy Project", "Date", "", timeSeriesCollection, true, true, false));
        return chartPanel;
    } 
}