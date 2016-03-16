
package linelistserver;

//import java.nio.file;
//import java.io.BufferedWriter;
import java.io.BufferedOutputStream;
//import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.nio.file.Files;  //java.nio.file not available in Java 6
//import java.nio.file.Paths;  //java.nio.file not available in Java 6
//import java.nio.file.Path;  //java.nio.file not available in Java 6
import java.io.File;
import java.nio.charset.Charset;
import java.text.*;
import java.text.DecimalFormat;

//Amended From http://www.deepakgaikwad.net/index.php/2009/11/23/reading-text-file-line-by-line-in-java-6.html
//Kluged from http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly

public class ByteFileWrite{

    public static void writeFileBytes(String byteFile, byte[] barray){

        //String dataPath = "./InputData/";
        //String lineListFile = dataPath + "gsLineList.dat";

        int bufferSize = 8 * 1024;
        int arrSize = barray.length;

        //BufferedReader buffReader = null;
        BufferedOutputStream buffStreamer = null;
        try{
            //buffReader = new BufferedReader (new FileReader(lineListFile));
            buffStreamer = new BufferedOutputStream (new FileOutputStream(byteFile), bufferSize);
            //String line = buffReader.readLine();
            buffStreamer.write(barray, 0, arrSize); 
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            try{
                buffStreamer.flush();
                buffStreamer.close();
            }catch(IOException ioe1){
                //Leave It
            }
        }

    } //end writeFileBytes() method

} //end ByteFileWrite class
