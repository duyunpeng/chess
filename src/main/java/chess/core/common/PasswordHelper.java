package chess.core.common;


import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import chess.domain.model.account.Account;

/**
 * Created by YJH on 2016/3/7.
 */
public class PasswordHelper {

    private static RandomNumberGenerator generator = new SecureRandomNumberGenerator(); //加密随机数生成器

    private final static String algorithmName = "md5";   //加密类型

    private final static int hashIterations = 2;     //迭代次数

    /**
     * create salt
     *
     * @return
     */
    public static String getSalt() {
        return generator.nextBytes().toHex();
    }

    /**
     * 加密
     *
     * @param password
     * @param salt
     * @return
     */
    public static String encryptPassword(String password, String salt) {
        String newPassword = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(salt),
                hashIterations
        ).toHex();
        return newPassword;
    }

    /**
     * 验证密码是否相等
     *
     * @param account
     * @param password
     * @return
     */
    public static boolean equalsPassword(Account account, String password) {
        String newPassword = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(account.getSalt()),
                hashIterations).toHex();

        return account.getPassword().equals(newPassword);
    }

}
