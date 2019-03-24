
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;



public class Calculating_Signatures {

    public static String calculateHMAC_SHA(String data, String key) throws SignatureException {
        String result = null;
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";

        try {

            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);

            byte[] digest = mac.doFinal(data.getBytes());

            StringBuilder sb = new StringBuilder(digest.length * 2);
            String s;
            for (byte b : digest) {
                s = Integer.toHexString(0xFF & b);
                if (s.length() == 1) {
                    sb.append('0');
                }

                sb.append(s);
            }

            result = sb.toString();

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }

        return result;
    }
}
