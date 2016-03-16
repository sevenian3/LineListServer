/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package linelistserver;

/**
 *
 * @author Ian
 */
public class ToolBox {
    
 /*
 Linear interpolation to a new abscissa - mainly for interpolating flux to a specific lambda
 */   
    /**
     *
     * @param x
     * @param y
     * @param newX
     * @return
     */
    public static double interpol(double[] x, double[] y, double newX) {

        double newY;

        // Bracket newX:
        double x1, x2;
        int p1, p2;
        p1 = 0;
        p2 = 1;
        x1 = x[p1];
        x2 = x[p2];

        for (int i = 1; i < x.length; i++) {
            if (x[i] >= newX) {
                // Found upper bracket
                p2 = i;
                p1 = i - 1;
                x2 = x[p2];
                x1 = x[p1];
                break;
            }
        }

        double step = x2 - x1;

    //Interpolate
        //First order Lagrange formula
        //   newY = y[1][p2] * (newX - x1) / step
        //           + y[1][p1] * (x2 - newX) / step;
        newY = y[p2] * (newX - x1) / step
                + y[p1] * (x2 - newX) / step;

        //System.out.println("Interpol: p1, p2, x1, x2, y1, y2, newX, newY: " + 
        //        p1 + " " + p2 + " " + x1 + " " + x2 + " " + y[1][p1] + " " + y[1][p2] + " " + newX + " " + newY + " ");
        return newY;

    }

/**
 *
 * @author Ian vectorized version of simple linear 1st order interpolation
 * // Caution: Assumes new abscissae to which we're interpolating are entirey within the 
 *  range of the old abscissae
 */

    public static double[] interpolV(double[] y, double[] x, double[] newX) {

        int num = newX.length;
        double[] newY = new double[num];

        int j = 0; //initialize old abscissae index
        //outer loop over new acscissae
        for (int i = 0; i < num; i++) {

            if (x[j] < newX[i]) {
                j++;
            }
            j--; //Passed the first newX - back up one

            //1st order Lagrange method:
            newY[i] = y[j+1]*(newX[i]-x[j]) + y[j]*(x[j+1]-newX[i]);
        }

        return newY;
    }

    /**
 * Return the array index of the wavelength array (lambdas) closest to a desired
 * value of wavelength (lam)
 */
     /**
     * 
     * @param numLams
     * @param lambdas
     * @param lam
     * @return 
     */
    public static int lamPoint(int numLams, double[] lambdas, double lam) {

        int index;

        double[] help = new double[numLams];

        for (int i = 0; i < numLams; i++) {

            help[i] = lambdas[i] - lam;
            help[i] = Math.abs(help[i]);

        }
        index = 0;
        double min = help[index];

        for (int i = 1; i < numLams; i++) {

            if (help[i] < min) {
                min = help[i];
                index = i;
            }

        }

        return index;

    }
    /**
 * Return the minimum and maximum values of an input 1D array CAUTION; Will
 * return the *first* occurence if min and/or max values occur in multiple
 * places iMinMax[0] = first occurence of minimum iMinMax[1] = first occurence
 * of maximum
 */
     /**
     * 
     * @param x
     * @return 
     */
    public static int[] minMax(double[] x) {

        int[] iMinMax = new int[2];

        int num = x.length;
        //System.out.println("MinMax: num: " + num);

        int iMin = 0;
        int iMax = 0;
        double min = x[iMin];
        double max = x[iMax];

        for (int i = 1; i < num; i++) {

            //System.out.println("MinMax: i , current min, x : " + i + " " + min + " " + x[i]);
            if (x[i] < min) {
                //System.out.println("MinMax: new min: if branch triggered" );
                min = x[i];
                iMin = i;
            }
            //System.out.println("MinMax: new min: " + min);

            if (x[i] > max) {
                max = x[i];
                iMax = i;
            }

        }
        //System.out.println("MinMax: " + iMin + " " + iMax);

        iMinMax[0] = iMin;
        iMinMax[1] = iMax;

        return iMinMax;

    }

 /**
 * Version of MinMax.minMax for 2XnumDep & 2XnumLams arrays where row 0 is
 * linear and row 1 is logarithmic
 *
 * Return the minimum and maximum values of an input 1D array CAUTION; Will
 * return the *first* occurence if min and/or max values occur in multiple
 * places iMinMax[0] = first occurence of minimum iMinMax[1] = first occurence
 * of maximum
 */
    /**
     * 
     * @param x
     * @return 
     */
    public static int[] minMax2(double[][] x) {

        int[] iMinMax = new int[2];

        int num = x[0].length;

        int iMin = 0;
        int iMax = 0;

        // Search for minimum and maximum in row 0 - linear values:
        double min = x[0][iMin];
        double max = x[0][iMax];

        for (int i = 1; i < num; i++) {

            if (x[0][i] < min) {
                min = x[0][i];
                iMin = i;
            }

            if (x[0][i] > max) {
                max = x[0][i];
                iMax = i;
            }

        }

        iMinMax[0] = iMin;
        iMinMax[1] = iMax;

        return iMinMax;

    }
   
 /**
 * Return the array index of the optical depth arry (tauRos) closest to a
 * desired value of optical depth (tau) Assumes the use wants to find a *lienar*
 * tau value , NOT logarithmic
 */

    /**
     * 
     * @param numDeps
     * @param tauRos
     * @param tau
     * @return 
     */
    public static int tauPoint(int numDeps, double[][] tauRos, double tau) {

        int index;

        double[] help = new double[numDeps];

        for (int i = 0; i < numDeps; i++) {

            help[i] = tauRos[0][i] - tau;
            help[i] = Math.abs(help[i]);

        }
        index = 0;
        double min = help[index];

        for (int i = 1; i < numDeps; i++) {

            if (help[i] < min) {
                min = help[i];
                index = i;
            }

        }

        return index;

    }
  
    
}
