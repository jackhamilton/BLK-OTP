package encryption;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Jack
 */
public class RSA {
    
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger t;
    private BigInteger e;
    private BigInteger d;
    public BigInteger[] privateKey;
    public BigInteger[] publicKey;
    
    public BigInteger[] generateKeyPairs(int bits) {
        p = genPrime(bits);
        q = genPrime(bits);
        n = p.multiply(q);
        t = p.subtract(new BigInteger("1")).multiply(q.subtract(new BigInteger("1")));
        Random rnd = new SecureRandom();
        e = BigInteger.probablePrime(bits - 1, rnd);
        while (!e.gcd(t).equals(new BigInteger("1"))) {
            e = BigInteger.probablePrime(bits - 1, rnd);
        }
        d = e.pow(-1).mod(p.subtract(new BigInteger("-1")).multiply(q.subtract(new BigInteger("-1"))));
        publicKey = new BigInteger[]{n, e};
        privateKey = new BigInteger[]{n, d};
        return publicKey;
    }
    
    public BigInteger genPrime(int bits) {
        Random rnd = new SecureRandom();
        while (true) {
            BigInteger prime = BigInteger.probablePrime(bits,rnd);
            if (prime.isProbablePrime(50000)) {
                return prime;
            }
        }
    }
    
}