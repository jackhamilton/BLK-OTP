package otpgen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * @author Jack
 */
public class OTPgen {
    
    //todo: add scrambler for the random data
    
    public static File generateOTP(File f, String randData, String filename) {
        try {
            Scanner getFileInput = new Scanner(f);
            String text = "";
            if (getFileInput.hasNext()) {
                text = getFileInput.next();
            }
            String usedDataContainer = "";
            randData = scrubData(randData);
            PrintWriter writer = new PrintWriter(filename+".blk", "UTF-8");
            while (!text.equals("")) {
                text = scrubData(text);
                String output = "";
                //todo: output "output" into a file at the end of each of these loops
                if (randData.length() > text.length()) {
                    for(int x = 0; x < text.length(); x++) {
                        char[] textchars = text.toCharArray();
                        char[] data = randData.toCharArray();
                        int outint = -1;
                        if ((int)textchars[x] + (int)data[x] <= 255) {
                            outint = (int)textchars[x] + (int)data[x];
                        } else {
                            outint = (int)textchars[x] + (int)data[x] - 255;
                        }
                        output += (char)outint;
                    }
                    usedDataContainer += randData.substring(0, text.length() - 1);
                    randData = randData.substring(text.length() - 1);
                } else {
                    int preexisting = 0;
                    int strcounter = 0;
                    for(int x = 0; x < text.length(); x++) {
                        if (strcounter + 1 == randData.length() && !usedDataContainer.equals("")) {
                            usedDataContainer += randData;
                            randData = usedDataContainer;
                            usedDataContainer = "";
                            preexisting += strcounter;
                            strcounter = 0;
                        } else if (strcounter + 1 == randData.length() && usedDataContainer.equals("")) {
                            preexisting += strcounter;
                            strcounter = 0;
                        }
                        char[] textchars = text.toCharArray();
                        char[] data = randData.toCharArray();
                        int outint = -1;
                        if ((int)textchars[x] + (int)data[strcounter] <= 255) {
                            outint = (int)textchars[x] + (int)data[strcounter];
                        } else {
                            outint = (int)textchars[x] + (int)data[strcounter] - 255;
                        }
                        output += (char)outint;
                        strcounter++;
                    }
                    usedDataContainer += randData.substring(0, text.length() - 1 - preexisting);
                    randData = randData.substring(text.length() - 1 - preexisting);
                    if (randData.equals("")) {
                        randData = usedDataContainer;
                        usedDataContainer = "";
                    }
                }
                writer.write(output);
                if (getFileInput.hasNext()) {
                    text = getFileInput.next();
                } else {
                    text = "";
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.err.println(ex.getMessage());
        }
        File file = new File(filename+".blk");
        return file;
    }
    
    public static File generateOTP(String randData, String filename) {
        String scrubbed = scrubData(randData);
        try {
            PrintWriter writer = new PrintWriter(filename+".blk", "UTF-8");
            writer.write(scrubbed);
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        File file = new File(filename+".blk");
        return file;
    }
    
    public static String scrubData(String data) {
        char lastChar = '0';
        for(int x = 0; x < data.length(); x++) {
            char c = data.toCharArray()[x];
            if (c == lastChar) {
                char[] temparray = new char[data.toCharArray().length - 1];
                int tempcounter = 0;
                for (int i = 0; i < data.length(); i++){
                    if (i != x) {
                        temparray[tempcounter] = data.toCharArray()[i];
                        tempcounter++;
                    }
                }
                data = new String(temparray);
                x--;
            } else {
                lastChar = c;
            }
        }
        return data;
    }
    
}