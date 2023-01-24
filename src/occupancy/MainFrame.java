/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package occupancy;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author rezguiha
 */
public class MainFrame extends JFrame implements ActionListener {
 
    PlotFactory plotFactory;
    ArrayList<String> checkedBoxes;    
    DataContainer dataContainer;
    ArrayList<JCheckBox> variableCheckBoxes; // selected variables to plot
    
       
    public MainFrame(DataContainer dataContainer){
        this.dataContainer = dataContainer;
        variableCheckBoxes = new ArrayList<JCheckBox>();       
        PlotFactory plotFactory;
    }
    
    public void initComponents(){
        JFrame variables = new JFrame("Variables");
        variables.setDefaultCloseOperation(EXIT_ON_CLOSE);
        variables.setLocation(400, 100);
        // creates the variables window
      
        GridLayout grid = new GridLayout((dataContainer.getNumberOfVariables() + 1), 1);
        //BorderLayout bord = new BorderLayout((dataContainer.getNumberOfVariables() + 1), 1);
     
        JPanel jpanel = new JPanel();
        jpanel.setLayout(grid);
        //jpanel.setLayout(bord);
        
        for (int i = 0; i < dataContainer.getNumberOfVariables(); i++) {
            this.variableCheckBoxes.add(new JCheckBox(dataContainer.getAvailableVariables()[i]));
            jpanel.add(variableCheckBoxes.get(i));
        }
        
        JButton plotButton = new JButton("Plot");//adds the plot button at the end of the variables
        plotButton.addActionListener(this);
        jpanel.add(plotButton);
        
        //bord.addLayoutComponent(jpanel, BorderLayout.EAST);
        
        variables.getContentPane().add(jpanel);
        variables.pack();
        variables.setVisible(true);

  }
          
    @Override
    public void actionPerformed(ActionEvent e) {
        checkedBoxes = new ArrayList<String>();
        try {
            plotFactory = new PlotFactory(dataContainer);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < dataContainer.getNumberOfVariables(); i++){//detects which variables are selected
            if (variableCheckBoxes.get(i).isSelected()){
                checkedBoxes.add(dataContainer.getAvailableVariables()[i]);
            }
        }
         
        JDialog plot = new JDialog(this, "Plot of selected variables"); // we used JDialog to close windows seperetly
        plot.setLocation(600, 100);
        
        try {
            plot.getContentPane().add(plotFactory.getPlot(checkedBoxes.toArray(new String[checkedBoxes.size()])));//Plots the selected variables
        }
        catch (ParseException exception) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, exception);
        }
         
        plot.pack();
        plot.setVisible(true); 
        
    }

    public static void main(String[] args) {
        try {
            new MainFrame(new DataContainerWithProcessing("office.csv")).initComponents(); //Reads the file
        }
        catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
}
