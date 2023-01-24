package occupancy;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rezguiha
 */
public class DichotomicScaler {
    Double[] referenceData;
    Double[] scaleData;
    double l;
    double u;
    double c;
    double bestScale;
    int numberOfIterations;
    double precision;

    public DichotomicScaler(Double[] referenceData, Double[] scaleData, double minScale, double maxScale, int maxIterations) {
        this.referenceData = referenceData;
        this.scaleData = scaleData;
        l = minScale;
        u = maxScale;
        c = 0;
        for(int i=0;i<maxIterations;i++){
            numberOfIterations = numberOfIterations + 1;
            c = (l+u)/2;
            if((getError(l)<getError(u))&&(getError(c)<getError(u)))
                u=c;
            else if((getError(c)<getError(l))&&(getError(u)<getError(l)))
                l=c;            
            else
                break;
        }
        precision = (u-l)/2;
        bestScale = c;       
    }

    public int getNumberOfIterations() {
        return numberOfIterations;   
    }

    public double getBestScale() {
        return bestScale;
    }

    public double getPrecision(){
        return this.precision;
    }
      
@Override
  public String toString(){
        String sPrecision = "\nPrecision: " + getPrecision();
        String sError = "\nMinimum Error: " + getError(getBestScale());
        String sBestScale = "\nBest Scale: " + getBestScale();
        String sNumberOfIterations = "\nNumber of Interactions: " + getNumberOfIterations();
        
        return sPrecision + sError + sBestScale  + sNumberOfIterations;   
    }
  
    public double getError(double scale) {
        double error = 0;
        for (int i = 0; i < this.referenceData.length; i++) 
            error = error + Math.abs(scale * scaleData[i] - referenceData[i]);
        error = error/referenceData.length;
        return error;
    }
}
