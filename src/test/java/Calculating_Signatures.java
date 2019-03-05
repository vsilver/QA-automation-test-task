
import java.security.SignatureException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class Calculating_Signatures {

    public static String Signature(String xData, String AppKey) {
        try {
            final Base64.Encoder encoder = Base64.getEncoder();
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(AppKey.getBytes("UTF-8"),"HmacSHA1");

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(xData.getBytes("UTF-8"));
            String result = encoder.encodeToString(rawHmac);
            return result;

        } catch (Exception e) {
            return "";
//            throw new SignatureException("Failed to generate HMAC : "+ e.getMessage());
        }
    }
}
