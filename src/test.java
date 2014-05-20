
import encryption.Encryptor;
import java.io.File;

/**
 * @author Jack
 */
public class test {
    
    public static void main(String[] args) {
        //System.out.println(new encryption.RSA().generateKeyPairs(2048));
        /*
        try {
            Path path = Paths.get("C:\\Users\\Jack\\Documents\\GitHub\\BLK-OTP\\otp1.blk");
            byte[] data = Files.readAllBytes(path);
            System.out.println(toBinary(data));
        } catch (Exception e) {}
        */
        Encryptor e = new Encryptor();
        e.AESEncrypt(new File("C:\\Users\\Jack\\Documents\\GitHub\\BLK-OTP\\otp1.blk"));
    }
    
    static String toBinary( byte[] bytes )
    {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }
    
}