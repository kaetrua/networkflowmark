package ss.pku.attacktraceproject.utils.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    public static final String ALGORITHM = "MD5";

    public static byte[] encode(byte[] content){
        try{
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update(content);
            return digest.digest();
        }catch(NoSuchAlgorithmException e){

        }
        return null;
    }

}
