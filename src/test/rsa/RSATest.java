package rsa;

import chess.core.util.RSAUtils;
import org.testng.annotations.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by pengyi on 16-7-7.
 */
public class RSATest {

    /**
     * 公钥加密私钥解密
     */
    @Test
    public void rsa() {

        String before = "java RSA加密解密";
        String privateKeyString = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALTt7BeQ9EVHF4id" +
                "UeDeLz3IBcJfguVaMYTMOgjzMO8IP+Bz20xq35BbLxZWUhxxn8VzoRydEuZeDik2" +
                "tcUroNj9w7+mmxa+ESZCF4YZTIUF9yy3iVUahI9KeiX88Ek4g7vGvjOht7wl3MDt" +
                "5GAfkqogj/LaD+tOobQE9SNy8WT9AgMBAAECgYAfOrH1n1+KVasKOZ1YYwFkeJIF" +
                "6tZ9IARMF7Qst01K9PGRnG3N0uSj1hlakau6nSImwr6cYcBRYwqusnLNhapNf60C" +
                "gmZJ8SCT+B1XaDgcNSfD2xcJCsSGGorUQ+3Tq+3SfRNbBvDw0BX1LmQ2G3YVsX58" +
                "t057axF7t4O09dkKAQJBAN0i369oUDSDRwzgbIlT+6OyQbrGDCxnq63RBVF7vEzz" +
                "AVQRX/lDHD6ooG/1G+EgcvZcEVh88QDZCX2H654OOvkCQQDRdEm10oqfxBIQ+eq5" +
                "/A6yM7IS91UzU+hXHhxaTIxWWV0ksVnC+skoltv1Nn9FxjHsjVvDOME9MpseEgzj" +
                "apclAkEAvJF5Zy0vf6gWVT+mrfAkm/FspWUuiksZFY4w18wIxSFPF639RiPZGQFY" +
                "VkAQNwghoDzgObHJHWz178qQu+XsmQJBAIgcJXo+Y08HPyZBAcPyhCpRZbrldzxv" +
                "nmBedNZJQDkswVaC2W3XQrlZr35d978D2JV8FtF6JDj8lcioui9eqPECQQCFGlI+" +
                "z0V05wfXZIcGab+JW18lZwRXt4dQaHeah+BaoE1ukNH+Kt76OcE34zrit93ygGkn" +
                "WYg5RjV1c1IskA/R";
        String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC07ewXkPRFRxeInVHg3i89yAXC" +
                "X4LlWjGEzDoI8zDvCD/gc9tMat+QWy8WVlIccZ/Fc6EcnRLmXg4pNrXFK6DY/cO/" +
                "ppsWvhEmQheGGUyFBfcst4lVGoSPSnol/PBJOIO7xr4zobe8JdzA7eRgH5KqII/y" +
                "2g/rTqG0BPUjcvFk/QIDAQAB";
        PublicKey publicKey = null;
        try {
            publicKey = RSAUtils.getPublicKey(publicKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PrivateKey privateKey = null;
        try {
            privateKey = RSAUtils.getPrivateKey(privateKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String encodeS = RSAUtils.encrypt(publicKey, before);

        String decodeS = RSAUtils.decrypt(privateKey, encodeS);
        System.out.println(decodeS);

    }
}
