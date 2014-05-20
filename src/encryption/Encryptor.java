package encryption;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * @author Jack
 */
public class Encryptor {
    
    public void AESEncrypt(File f) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
            System.out.println(AES.static_byteArrayToString(secretKey.getEncoded()));
            AES aes = new AES();
            aes.setKey(secretKey.getEncoded());
            Scanner s = new Scanner(f);
            PrintWriter writer = new PrintWriter(f.getName().replaceAll(".blk", "") + "-encrypted.blk");
            while (s.hasNext()) {
                writer.write(aes.Encrypt(s.next()));
            }
            writer.close();
        } catch (Exception e) {}
    }
    
}