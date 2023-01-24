package occupancy;

import java.io.IOException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rezguiha
 */
public class DataContainerWithProcessing extends DataContainer {

    public DataContainerWithProcessing(String csvFileName) throws IOException {
        super(csvFileName);
        laptopsPower();
        detectedMotions();
    }
    
    public void laptopsPower(){
        String occupancyFromLaptopsVariable = "Occupancy_from_laptops";
        ArrayList<Double> data = new ArrayList<Double>();
        double count;
        
        for (int i = 0; i < super.getNumberOfSamples(); i++){//verifies if the power consumed is greater than 15
            count = 0;
            if(getData("power_laptop1_zone1")[i]>15) 
                count = count + 1;
            if(getData("power_laptop1_zone2")[i]>15)
                count = count + 1;
            if(getData("power_laptop2_zone2")[i]>15)
                count = count + 1;
            if(getData("power_laptop3_zone2")[i]>15)
                count = count + 1;
             data.add(count);
        }
        addData(occupancyFromLaptopsVariable, data.toArray(new Double[data.size()]));//adds the estimated number of people to the file
    }
    
    public void detectedMotions(){
        String stringMotion = "Occupancy_from_motions";
        ArrayList<Double> motions = new ArrayList<Double>();
        DichotomicScaler dichotomicScaler = new DichotomicScaler(getData("Occupancy_from_laptops"), getData("detected_motions"), 0, 1, 100);
        
        for (int i = 0; i < getNumberOfSamples(); i++)
            motions.add(getData("detected_motions")[i]*dichotomicScaler.getBestScale());//uses the dichotomic algorithms to detect motions            
        
        addData(stringMotion, motions.toArray(new Double[data.size()]));
        System.out.println(dichotomicScaler);     
    }
}
