package chess.core.shiro;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.util.ByteSource;

/**
 * 扩展SimpleAuthenticationInfo
 * Created by YJH on 2016/4/5 0005.
 */
public class ExtendSimpleAuthenticationInfo extends SimpleAuthenticationInfo {

    public ExtendSimpleAuthenticationInfo() {
        super();
    }

    public ExtendSimpleAuthenticationInfo(Object principal, Object hashedCredentials, ByteSource credentialsSalt, String realmName) {
        this.principals = new ExtendSimplePrincipalCollection(principal, realmName);
        this.credentials = hashedCredentials;
        this.credentialsSalt = credentialsSalt;
    }

}
