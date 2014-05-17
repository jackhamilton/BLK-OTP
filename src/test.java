
import java.io.File;
import java.util.Scanner;

/**
 * @author Jack
 */
public class test {
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String userIn = s.next();
        //BUG: Scanner not reading non notepad readable files
        otpgen.OTPgen.generateOTP(new File("C:\\Users\\Jack\\Downloads\\dictionary.txt"), userIn, "donger");
    }
    
}