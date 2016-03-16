/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linelistserver;

//import java.nio.file;
//import java.io.BufferedWriter;
import java.io.BufferedReader;
//import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
//import java.nio.file.Files;  //java.nio.file not available in Java 6
//import java.nio.file.Paths;  //java.nio.file not available in Java 6
//import java.nio.file.Path;  //java.nio.file not available in Java 6
import java.io.File;  
import java.nio.charset.Charset;
import java.text.*;
import java.text.DecimalFormat;

/**
 *
 * @author Ian
 */
public class LineListServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Argument 0: Effective temperature, Teff, in K:
        String asciiListStr = args[0];
        String byteListStr = args[1];

//
////
////Abundance table adapted from PHOENIX V. 15 input bash file
////Solar abundances:
//// c='abundances, Anders & Grevesse',
//

  double logE = Math.log10(Math.E); // for debug output
  double logE10 = Math.log(10.0); //natural log of 10


  int nelemAbnd = 40;
  int[] nome = new int[nelemAbnd];
  String[] cname = new String[nelemAbnd];
//nome is the Kurucz code - in case it's ever useful
  nome[0]=   100;
  nome[1]=   200;
  nome[2]=   300;
  nome[3]=   400;
  nome[4]=   500;
  nome[5]=   600;
  nome[6]=   700;
  nome[7]=   800;
  nome[8]=   900;
  nome[9]=  1000;
  nome[10]=  1100;
  nome[11]=  1200;
  nome[12]=  1300;
  nome[13]=  1400;
  nome[14]=  1500;
  nome[15]=  1600;
  nome[16]=  1700;
  nome[17]=  1800;
  nome[18]=  1900;
  nome[19]=  2000;
  nome[20]=  2100;
  nome[21]=  2200;
  nome[22]=  2300;
  nome[23]=  2400;
  nome[24]=  2500;
  nome[25]=  2600;
  nome[26]=  2700;
  nome[27]=  2800;
  nome[28]=  2900;
  nome[29]=  3000;
  nome[30]=  3100;
  nome[31]=  3600;
  nome[32]=  3700;
  nome[33]=  3800;
  nome[34]=  3900;
  nome[35]=  4000;
  nome[36]=  4100;
  nome[37]=  5600;
  nome[38]=  5700;
  nome[39]=  5500;

  cname[0]="H";
  cname[1]="He";
  cname[2]="Li";
  cname[3]="Be";
  cname[4]="B";
  cname[5]="C";
  cname[6]="N";
  cname[7]="O";
  cname[8]="F";
  cname[9]="Ne";
  cname[10]="Na";
  cname[11]="Mg";
  cname[12]="Al";
  cname[13]="Si";
  cname[14]="P";
  cname[15]="S";
  cname[16]="Cl";
  cname[17]="Ar";
  cname[18]="K";
  cname[19]="Ca";
  cname[20]="Sc";
  cname[21]="Ti";
  cname[22]="V";
  cname[23]="Cr";
  cname[24]="Mn";
  cname[25]="Fe";
  cname[26]="Co";
  cname[27]="Ni";
  cname[28]="Cu";
  cname[29]="Zn";
  cname[30]="Ga";
  cname[31]="Kr";
  cname[32]="Rb";
  cname[33]="Sr";
  cname[34]="Y";
  cname[35]="Zr";
  cname[36]="Nb";
  cname[37]="Ba";
  cname[38]="La";
  cname[39]="Cs";

  String species;


//
//     FILE I/O Section
//
//External line list input file approach:

String dataPath = "./InputData/";
String lineListFile = dataPath + asciiListStr + ".dat";
//String lineListFile = "gsLineList.dat";
//Path path = Paths.get(dataPath + lineListFile); //java.nio.file not available in Java 6
Charset charset = Charset.forName("US-ASCII");
String pattern = "0.0000000000000000";
//String pattern = "###.####";
DecimalFormat myFormatter = new DecimalFormat(pattern);

// We have Java SE 6 - we don't have the java.nio package!
//From http://www.deepakgaikwad.net/index.php/2009/11/23/reading-text-file-line-by-line-in-java-6.html
//

System.out.println(" *********************************************** ");
System.out.println("  ");
System.out.println("  ");
System.out.println("BEFORE FILE READ");
System.out.println("  ");
System.out.println("  ");
System.out.println(" *********************************************** ");


        FileLineByLine fileLBL = new FileLineByLine();

        String masterLineString = fileLBL.readFileLineByLine(lineListFile);

System.out.println(" *********************************************** ");
System.out.println("  ");
System.out.println("  ");
System.out.println("AFTER FILE READ");
System.out.println("  ");
System.out.println("  ");
System.out.println(" *********************************************** ");

        String[] arrayLineString = masterLineString.split("%%"); 
//Number of lines MUST be the ONLY entry on the first line 

        int numLineList = Integer.parseInt(arrayLineString[0]);
        //System.out.println("arrayLineString[0] " + arrayLineString[0]);
        int list2Length = arrayLineString.length - 1; //useful for checking if something's wrong?
        System.out.println("numLineList " + numLineList + " list2Length " + list2Length); 
//        for (int i = 0; i < 5; i++){
//           System.out.println(arrayLineString[i]);
//        }

// In general there will be header information.  The first block of six lines (blank separartor line
//followed by five data lines) must be immediately preceded by a line whose first six columns contain 
//the string "START:", followed by the correct pipe symbol ("|") separators  
  
       String startKey = "START:";
       String testField= "";
       int startLine = 1; //initialization
       for (int i = 1; i < list2Length; i++){
            //System.out.println("i " + i + " arrayLineString[i] " + arrayLineString[i]); 
            testField = arrayLineString[i].substring(0, 6);
            if (testField.equals(startKey)){
                  break;  //We found it
                }
            startLine++;
         } 
//            startLine++; //one more
     System.out.println("list2Length " + list2Length + " numLineList " + numLineList + " startLine " + startLine); 
     //System.out.println("arrayLineString[startLine] " + arrayLineString[startLine]); 

//// Find seven field separators ("|"):
//int lastBound = 0; //initialization
//int[] bounds = new int[7];
//for (int i = 0; i < 7; i++){
//   bounds[i] = arrayLineString[startLine].indexOf("|", lastBound);
//   lastBound = bounds[i]+1;
//   //System.out.println("i " + i + " bounds[i] " + bounds[i]);
//   }


//Okay, here we go:
        System.out.println("numLineList " + numLineList);
        String list2Element; // = new String[numLineList]; //element
        String list2LogGammaCol; // = new double[numLineList];
        //Log of unitless oscillator strength, f 
        double list2Logf; // = new double[numLineList];
        //Einstein coefficinet for spontaneous de-excitation
        double list2LogAij; // = new double[numLineList];
        //Unitless statisital weight, lower E-level of b-b transition                 
        double list2GwL; // = new double[numLineList];

        //Atomic Data sources:
 

 int list2_ptr = 0; //pointer into line list2 that we're populating
 int array_ptr; //pointer into array containing line list2 data file line-by-line Strings
    
     //First line in block of six is always a blank separator line:
 int numBlocks = (list2Length - (startLine+1))/6 - 1;
// int rmndr = (list2Length - (startLine+1)) % 6;
 int rmndr = 0; //for now - something's wrong
 System.out.println("numBlocks " + numBlocks + " rmndr " + rmndr); 
 String myString, myStringUp, elName;  //useful helper
 double log10gf, Jnumer, Jdenom, Jfinal;
 int testLength, thisUpperBound;
 boolean blankFlag;

 String newField = " | "; //field separator - consistent with NIST ascii output 
 String newRecord = "%%"; //record separator
 String masterStringOut = ""; //initialize master string for output 
 int numFields = 9; //number of INPUT fields in NIST ascii dump
 // Input filds:
 // 0: element + ion stage, 1: lambda_0, 2: A_ij, 3: f, 4: log(gf), 5: "Acc." - ??, 6: E_i - E_j, 7: J_i, 8: J_j
   String[] thisRecord = new String[numFields];
   String[] subFields = new String[2];
 

     for (int iBlock = 0; iBlock < numBlocks; iBlock++){

       int offset = startLine + 6 * iBlock + 1;
       for (int i = 1; i < 6; i++){

           array_ptr = offset + i;
  //System.out.println("i " + i + " array_ptr " + array_ptr);
  //System.out.println("arrayLineString " + arrayLineString[array_ptr]); 
  //"|" turns out to mean something in regexp, so we need to escape with '\\':
           //Get the chemical element symbol - we don't know if it's one or two characters
           thisRecord = arrayLineString[array_ptr].split("\\|"); 
           testField = thisRecord[0];
          //Contains both chemical symbol and ionization stage, so have to "sub-split":
           testField = testField.trim();
           subFields = testField.split(" ");
           myString = subFields[0];
           //System.out.println("element " + myString);
           list2Element = myString.trim();
           masterStringOut = masterStringOut + list2Element + newField;
           myString = subFields[1];
           //System.out.println("ion " + myString.trim());
           //list2StageRoman[list2_ptr] = myString.trim();
           masterStringOut = masterStringOut + myString.trim() + newField;
           //wavelength in nm starts at position 23 and is in %8.3f format - we're not expecting anything greater than 9999.999 nm
           myString = thisRecord[1];
           //We need to be ready for blank fields - checking for this in Java is hard!
           //testLength = bounds[1] - bounds[0];
           blankFlag = true;
               if (myString.trim().length() > 0){
                  blankFlag = false; 
                 }
           if (blankFlag){
               myString = " "; 
             } // else { 
             //  myString.trim();
             //  list2Lam0[list2_ptr] = Double.parseDouble(myString);
            // }
           //System.out.println("lambda " + myString.trim());
           masterStringOut = masterStringOut + myString.trim() + newField;
           //Einstein A_ij coeffcient for spontaneous de-excitation:
           myString = thisRecord[2];
           //We need to be ready for blank fields - checking for this in Java is hard!
           //testLength = bounds[1] - bounds[0];
           blankFlag = true;
               if (myString.trim().length() > 0){
                  blankFlag = false; 
                 }
           if (blankFlag){
               myString = "-19.0"; 
             }  else { 
               myString.trim();
               list2LogAij = Math.log10(Double.parseDouble(myString)); //careful - base 10 log of f
               myString = Double.toString(list2LogAij);
             }
           //System.out.println("logAij " + myString.trim());
           masterStringOut = masterStringOut + myString.trim() + newField;
           //Linear oscillator strength, f, starts at position 50 and is in FORTRAN e8.2 format 
           myString = thisRecord[3]; 
           //We need to be ready for blank fields - checking for this in Java is hard!
           //testLength = bounds[3] - bounds[2];
           blankFlag = true;
               if (myString.trim().length() > 0){
                  blankFlag = false; 
                 }
           if (blankFlag == true){
               list2Logf =  -6.0; //careful - NATURAL log of f
               myString = "-6.0"; 
             } else { 
               myString.trim();
               list2Logf = Math.log10(Double.parseDouble(myString)); //careful - base 10 log of f
               myString = Double.toString(list2Logf);
             }
           //System.out.println("log(f) " + myString.trim());
           masterStringOut = masterStringOut + myString.trim() + newField;
           //Log gf starts at position 62 and is in %6.2f format 
           //process this so we can back out the statistical weight, g_l of the lower E-level (heh-heh!)
           myString = thisRecord[4]; 
           //We need to be ready for blank fields - checking for this in Java is hard!
           //testLength = bounds[4] - bounds[3];
           blankFlag = true;
               if (myString.trim().length() > 0){
                  blankFlag = false; 
                 }
           if (blankFlag){
               list2GwL = 1.0; 
               myString = "1.0"; 
             } else { 
               myString.trim();
               log10gf = Double.parseDouble(myString); // log_10 of gf
               //Lower E level statistical weight
               list2GwL = 2.0 * ( Math.exp(log10gf - list2Logf) ); 
               list2GwL = (double) ( (int) list2GwL );
               myString = Double.toString(list2GwL);
             }
           //System.out.println("g_i " + myString.trim());
           masterStringOut = masterStringOut + myString.trim() + newField;
           //Lower & Upper E-level excitation energy in eV starts at position 82 and is in %10.7f format 
           testField = thisRecord[6]; 
           //System.out.println("list2Element " + list2Element + " testField " + testField + " testField.trim().length() " + testField.trim().length());
           //testLength = bounds[6] - bounds[5];
           blankFlag = true;
           //for (int kk = 0; kk < testLength-2; kk++){
               //testChar = testField.substring(kk, kk+2);
               testField = testField.trim();
               if (testField.length() > 0){
                  blankFlag = false; 
                 }
            //   }  
           if (blankFlag){
               myString = "0.0";
               myStringUp = "0.0";
               //System.out.println("blankFlag triggered, myString = " + myString); 
             } else {
           // chi_L and chi_U separated by "-" - revise upper boundary to isolate chi_L:
               subFields = testField.split("-");
               myString = subFields[0].trim(); //lower E level 
               myStringUp = subFields[1].trim(); //upper E level 
           //Some values are in square brackets ("[ ]"):
               int sqbr1 = myString.indexOf("[");
               if (sqbr1 != -1){
                   int sqbr2 = myString.indexOf("]");
                   myString = myString.substring(sqbr1+1, sqbr2); 
                   } 
               sqbr1 = myStringUp.indexOf("[");
               if (sqbr1 != -1){
                   int sqbr2 = myStringUp.indexOf("]");
                   myStringUp = myStringUp.substring(sqbr1+1, sqbr2); 
                   } 
           //Or it could be round brackets ("( )"):
               sqbr1 = myString.indexOf("(");
               if (sqbr1 != -1){
                   int sqbr2 = myString.indexOf(")");
                   myString = myString.substring(sqbr1+1, sqbr2); 
                   } 
               sqbr1 = myStringUp.indexOf("(");
               if (sqbr1 != -1){
                   int sqbr2 = myStringUp.indexOf(")");
                   myStringUp = myStringUp.substring(sqbr1+1, sqbr2); 
                   } 
           //**Or** Some values have "+x" appended (NIST code):
               int plusX = myString.indexOf("+x");
               if (plusX != -1){
                  myString = myString.substring(0, plusX);
                  }
               plusX = myStringUp.indexOf("+x");
               if (plusX != -1){
                  myStringUp = myStringUp.substring(0, plusX);
                  }
           //**Or** Some values have "?" appended (NIST code):
               int questn = myString.indexOf("?");
               if (questn != -1){
                  myString = myString.substring(0, questn);
                  }
               questn = myStringUp.indexOf("?");
               if (questn != -1){
                  myStringUp = myStringUp.substring(0, questn);
                  }
               //myString.trim();
               //list2ChiL[list2_ptr] = Double.parseDouble(myString);
             }
               //System.out.println("final myString = " + myString); 
           //System.out.println("loggf " + myString.trim());
           //System.out.println("chi_i " + myString.trim() + " chi_j " + myStringUp.trim());
           masterStringOut = masterStringOut + myString.trim() + newField + myStringUp.trim() + newField;
           //Lower J quantum number 
           testField = thisRecord[7]; 
           //System.out.println("list2Element " + list2Element + " testField " + testField + " testField.trim().length() " + testField.trim().length());
           //testLength = bounds[6] - bounds[5];
           blankFlag = true;
           //for (int kk = 0; kk < testLength-2; kk++){
               //testChar = testField.substring(kk, kk+2);
               testField = testField.trim();
               if (testField.length() > 0){
                  blankFlag = false; 
                 }
            //   } 
           //initialize subfields so we're ready for both whole and rational number Js
           subFields[0] = "1"; 
           subFields[1] = "1"; 
           if (blankFlag){
               myString = "1";
               myStringUp = "1";
               //System.out.println("blankFlag triggered, myString = " + myString); 
             } else {
           // chi_L and chi_U separated by "-" - revise upper boundary to isolate chi_L:
               int slash = testField.indexOf("/");
               if (slash != -1){
                  subFields = testField.split("/");
                  myString = subFields[0].trim(); //numerator OR entire value, as case may be 
                  myStringUp = subFields[1].trim(); //denominator OR default value of unity as case may be
               } else {
                  myString = testField;
                  myStringUp = "1";
               } 
               Jnumer = Double.parseDouble(myString); // log_10 of gf
               Jdenom = Double.parseDouble(myStringUp); // log_10 of gf
               //Lower E level statistical weight
               Jfinal = Jnumer / Jdenom;
               myString = Double.toString(Jfinal);
             }

           //System.out.println("J_i " + myString.trim());
           masterStringOut = masterStringOut + myString.trim() + newField;
           //Upper J quantum number 
           testField = thisRecord[8]; 
           //System.out.println("list2Element " + list2Element + " testField " + testField + " testField.trim().length() " + testField.trim().length());
           //testLength = bounds[6] - bounds[5];
           blankFlag = true;
           //for (int kk = 0; kk < testLength-2; kk++){
               //testChar = testField.substring(kk, kk+2);
               testField = testField.trim();
               if (testField.length() > 0){
                  blankFlag = false; 
                 }
            //   } 
           //initialize subfields so we're ready for both whole and rational number Js
           subFields[0] = "1"; 
           subFields[1] = "1"; 
           if (blankFlag){
               myString = "1";
               myStringUp = "1";
               //System.out.println("blankFlag triggered, myString = " + myString); 
             } else {
           // chi_L and chi_U separated by "-" - revise upper boundary to isolate chi_L:
               int slash = testField.indexOf("/");
               if (slash != -1){
                  subFields = testField.split("/");
                  myString = subFields[0].trim(); //numerator OR entire value, as case may be 
                  myStringUp = subFields[1].trim(); //denominator OR default value of unity as case may be
               } else {
                  myString = testField;
                  myStringUp = "1.0";
               }
               Jnumer = Double.parseDouble(myString); // log_10 of gf
               Jdenom = Double.parseDouble(myStringUp); // log_10 of gf
               //Lower E level statistical weight
               Jfinal = Jnumer / Jdenom;
               myString = Double.toString(Jfinal);
             }

           //System.out.println("J_j " + myString.trim());
           masterStringOut = masterStringOut + myString.trim() + newRecord;
//
    //We've gotten everything we need from the NIST line list:
           list2_ptr++;
        
       } //i loop 

   } //iBlock loop


  //now get the remaining lines:
       int iBlock = numBlocks;
       int offset = startLine + 6 * iBlock + 1;
       for (int i = 1; i <= rmndr; i++){

           array_ptr = offset + i;
  //System.out.println("i " + i + " array_ptr " + array_ptr);
  //System.out.println("arrayLineString " + arrayLineString[array_ptr]); 
  //"|" turns out to mean something in regexp, so we need to escape with '\\':
           //Get the chemical element symbol - we don't know if it's one or two characters
           thisRecord = arrayLineString[array_ptr].split("\\|"); 
           testField = thisRecord[0];
          //Contains both chemical symbol and ionization stage, so have to "sub-split":
           testField = testField.trim();
           subFields = testField.split(" ");
           myString = subFields[0];
           //System.out.println("element " + myString);
           list2Element = myString.trim();
           masterStringOut = masterStringOut + list2Element + newField;
           myString = subFields[1];
           //System.out.println("ion " + myString.trim());
           //list2StageRoman[list2_ptr] = myString.trim();
           masterStringOut = masterStringOut + myString.trim() + newField;
           //wavelength in nm starts at position 23 and is in %8.3f format - we're not expecting anything greater than 9999.999 nm
           myString = thisRecord[1];
           //We need to be ready for blank fields - checking for this in Java is hard!
           //testLength = bounds[1] - bounds[0];
           blankFlag = true;
               if (myString.trim().length() > 0){
                  blankFlag = false; 
                 }
           if (blankFlag){
               myString = " "; 
             } // else { 
             //  myString.trim();
             //  list2Lam0[list2_ptr] = Double.parseDouble(myString);
            // }
           //System.out.println("lambda " + myString.trim());
           masterStringOut = masterStringOut + myString.trim() + newField;
           //Einstein A_ij coeffcient for spontaneous de-excitation:
           myString = thisRecord[2];
           //We need to be ready for blank fields - checking for this in Java is hard!
           //testLength = bounds[1] - bounds[0];
           blankFlag = true;
               if (myString.trim().length() > 0){
                  blankFlag = false; 
                 }
           if (blankFlag){
               myString = "-19.0"; 
             }  else { 
               myString.trim();
               list2LogAij = Math.log10(Double.parseDouble(myString)); //careful - base 10 log of f
               myString = Double.toString(list2LogAij);
             }
           //System.out.println("logAij " + myString.trim());
           masterStringOut = masterStringOut + myString.trim() + newField;
           //Linear oscillator strength, f, starts at position 50 and is in FORTRAN e8.2 format 
           myString = thisRecord[3]; 
           //We need to be ready for blank fields - checking for this in Java is hard!
           //testLength = bounds[3] - bounds[2];
           blankFlag = true;
               if (myString.trim().length() > 0){
                  blankFlag = false; 
                 }
           if (blankFlag == true){
               list2Logf =  -6.0; //careful - NATURAL log of f
               myString = "-6.0"; 
             } else { 
               myString.trim();
               list2Logf = Math.log10(Double.parseDouble(myString)); //careful - base 10 log of f
               myString = Double.toString(list2Logf);
             }
           //System.out.println("log(f) " + myString.trim());
           masterStringOut = masterStringOut + myString.trim() + newField;
           //Log gf starts at position 62 and is in %6.2f format 
           //process this so we can back out the statistical weight, g_l of the lower E-level (heh-heh!)
           myString = thisRecord[4]; 
           //We need to be ready for blank fields - checking for this in Java is hard!
           //testLength = bounds[4] - bounds[3];
           blankFlag = true;
               if (myString.trim().length() > 0){
                  blankFlag = false; 
                 }
           if (blankFlag){
               list2GwL = 1.0; 
               myString = "1.0"; 
             } else { 
               myString.trim();
               log10gf = Double.parseDouble(myString); // log_10 of gf
               //Lower E level statistical weight
               list2GwL = 2.0 * ( Math.exp(log10gf - list2Logf) ); 
               list2GwL = (double) ( (int) list2GwL );
               myString = Double.toString(list2GwL);
             }
           //System.out.println("g_i " + myString.trim());
           masterStringOut = masterStringOut + myString.trim() + newField;
           //Lower & Upper E-level excitation energy in eV starts at position 82 and is in %10.7f format 
           testField = thisRecord[6]; 
           //System.out.println("list2Element " + list2Element + " testField " + testField + " testField.trim().length() " + testField.trim().length());
           //testLength = bounds[6] - bounds[5];
           blankFlag = true;
           //for (int kk = 0; kk < testLength-2; kk++){
               //testChar = testField.substring(kk, kk+2);
               testField = testField.trim();
               if (testField.length() > 0){
                  blankFlag = false; 
                 }
            //   }  
           if (blankFlag){
               myString = "0.0";
               myStringUp = "0.0";
               //System.out.println("blankFlag triggered, myString = " + myString); 
             } else {
           // chi_L and chi_U separated by "-" - revise upper boundary to isolate chi_L:
               subFields = testField.split("-");
               myString = subFields[0].trim(); //lower E level 
               myStringUp = subFields[1].trim(); //upper E level 
           //Some values are in square brackets ("[ ]"):
               int sqbr1 = myString.indexOf("[");
               if (sqbr1 != -1){
                   int sqbr2 = myString.indexOf("]");
                   myString = myString.substring(sqbr1+1, sqbr2); 
                   } 
               sqbr1 = myStringUp.indexOf("[");
               if (sqbr1 != -1){
                   int sqbr2 = myStringUp.indexOf("]");
                   myStringUp = myStringUp.substring(sqbr1+1, sqbr2); 
                   } 
           //Or it could be round brackets ("( )"):
               sqbr1 = myString.indexOf("(");
               if (sqbr1 != -1){
                   int sqbr2 = myString.indexOf(")");
                   myString = myString.substring(sqbr1+1, sqbr2); 
                   } 
               sqbr1 = myStringUp.indexOf("(");
               if (sqbr1 != -1){
                   int sqbr2 = myStringUp.indexOf(")");
                   myStringUp = myStringUp.substring(sqbr1+1, sqbr2); 
                   } 
           //**Or** Some values have "+x" appended (NIST code):
               int plusX = myString.indexOf("+x");
               if (plusX != -1){
                  myString = myString.substring(0, plusX);
                  }
               plusX = myStringUp.indexOf("+x");
               if (plusX != -1){
                  myStringUp = myStringUp.substring(0, plusX);
                  }
           //**Or** Some values have "?" appended (NIST code):
               int questn = myString.indexOf("?");
               if (questn != -1){
                  myString = myString.substring(0, questn);
                  }
               questn = myStringUp.indexOf("?");
               if (questn != -1){
                  myStringUp = myStringUp.substring(0, questn);
                  }
               //myString.trim();
               //list2ChiL[list2_ptr] = Double.parseDouble(myString);
             }
               //System.out.println("final myString = " + myString); 
           //System.out.println("loggf " + myString.trim());
           //System.out.println("chi_i " + myString.trim() + " chi_j " + myStringUp.trim());
           masterStringOut = masterStringOut + myString.trim() + newField + myStringUp.trim() + newField;
           //Lower J quantum number 
           testField = thisRecord[7]; 
           //System.out.println("list2Element " + list2Element + " testField " + testField + " testField.trim().length() " + testField.trim().length());
           //testLength = bounds[6] - bounds[5];
           blankFlag = true;
           //for (int kk = 0; kk < testLength-2; kk++){
               //testChar = testField.substring(kk, kk+2);
               testField = testField.trim();
               if (testField.length() > 0){
                  blankFlag = false; 
                 }
            //   } 
           //initialize subfields so we're ready for both whole and rational number Js
           subFields[0] = "1"; 
           subFields[1] = "1"; 
           if (blankFlag){
               myString = "1";
               myStringUp = "1";
               //System.out.println("blankFlag triggered, myString = " + myString); 
             } else {
           // chi_L and chi_U separated by "-" - revise upper boundary to isolate chi_L:
               int slash = testField.indexOf("/");
               if (slash != -1){
                  subFields = testField.split("/");
                  myString = subFields[0].trim(); //numerator OR entire value, as case may be 
                  myStringUp = subFields[1].trim(); //denominator OR default value of unity as case may be
               } else {
                  myString = testField;
                  myStringUp = "1";
               } 
               Jnumer = Double.parseDouble(myString); // log_10 of gf
               Jdenom = Double.parseDouble(myStringUp); // log_10 of gf
               //Lower E level statistical weight
               Jfinal = Jnumer / Jdenom;
               myString = Double.toString(Jfinal);
             }

           //System.out.println("J_i " + myString.trim());
           masterStringOut = masterStringOut + myString.trim() + newField;
           //Upper J quantum number 
           testField = thisRecord[8]; 
           //System.out.println("list2Element " + list2Element + " testField " + testField + " testField.trim().length() " + testField.trim().length());
           //testLength = bounds[6] - bounds[5];
           blankFlag = true;
           //for (int kk = 0; kk < testLength-2; kk++){
               //testChar = testField.substring(kk, kk+2);
               testField = testField.trim();
               if (testField.length() > 0){
                  blankFlag = false; 
                 }
            //   } 
           //initialize subfields so we're ready for both whole and rational number Js
           subFields[0] = "1"; 
           subFields[1] = "1"; 
           if (blankFlag){
               myString = "1";
               myStringUp = "1";
               //System.out.println("blankFlag triggered, myString = " + myString); 
             } else {
           // chi_L and chi_U separated by "-" - revise upper boundary to isolate chi_L:
               int slash = testField.indexOf("/");
               if (slash != -1){
                  subFields = testField.split("/");
                  myString = subFields[0].trim(); //numerator OR entire value, as case may be 
                  myStringUp = subFields[1].trim(); //denominator OR default value of unity as case may be
               } else {
                  myString = testField;
                  myStringUp = "1.0";
               }
               Jnumer = Double.parseDouble(myString); // log_10 of gf
               Jdenom = Double.parseDouble(myStringUp); // log_10 of gf
               //Lower E level statistical weight
               Jfinal = Jnumer / Jdenom;
               myString = Double.toString(Jfinal);
             }

           //System.out.println("J_j " + myString.trim());
           masterStringOut = masterStringOut + myString.trim() + newRecord;
//
    //We've gotten everything we need from the NIST line list:
           list2_ptr++;
        
        
       } //i loop 

  int numLines2 = list2_ptr;


//check:
//System.out.println("masterStringOut " + masterStringOut);

//Okay - what kind of mess did we make...
// System.out.println("We processed " +  numLines2 + " lines");
// System.out.println("list2Element  list2Stage  list2Lam0  list2Logf  list2GwL  list2ChiL  list2ChiI1  list2ChiI2  list2Mass");


// WARNING: The line list is expected to be in the format printed out by the NIST Atomic Spectra Database (ver. 5.3), [Online]. 
//Available: http://physics.nist.gov/asd [2015, November 21] * when ascii output is selected *   
// Ie. blocks of five lines sepeareted by a lineof blank fields, fields separated by '|', etc.    
// NOTE: "START:" MUST be added by hand after retrieving a NIST list
//NIST database Print-out options MUST be selected so as to produce the following header, headings and sample data lines:
//117
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//Spectrum  |               Observed  |    Aki    |    fik    | log_gf  | Acc. |            Ei           Ek            | Lower level | Upper level |Type|
//          |              Wavelength |    s^-1   |           |         |      |           (eV)         (eV)           |-------------|-------------|    |
//          |               Vac (nm)  |           |           |         |      |                                       | J           | J           |    |
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//START:
//          |                         |           |           |         |      |                                       |             |             |    |
//N I       |             600.109     | 3.64e+06  | 1.97e-02  | -1.406  | C+   |    11.6026334     -     13.6686605    | 1/2         | 1/2         |    |
//Al II     |             600.141     | 2.07e+06  | 3.35e-02  | -1.475  | C    |    15.585203      -     17.651077     | 0           | 1           |    |

//
//
// END FILE I/O SECTION


  byte[] barray = masterStringOut.getBytes();
  //byte[] barray = masterStringOut.getBytes("UTF-8")
// what do I do with this??   throws UnsupportedEncodingException; 
  System.out.println(" ");
  System.out.println("*************************");
  System.out.println(" ");
  System.out.println("This needs to be detected by GrayStar3Server.java: ");
  System.out.println(" ");
  System.out.println("size of barray " + barray.length);
  System.out.println(" ");
  System.out.println("*************************");
  System.out.println(" ");
 // System.out.println("barray " + barray);
 //
 ByteFileWrite.writeFileBytes(byteListStr, barray); 

//
    } // end main()

        //

} //end class LineListServer
