package chess.core.shiro;

import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 * 扩展SimplePrincipalCollection
 * Created by YJH on 2016/4/5 0005.
 */
public class ExtendSimplePrincipalCollection extends SimplePrincipalCollection {

    public ExtendSimplePrincipalCollection() {
    }

    public ExtendSimplePrincipalCollection(Object principal, String realmName) {
        super(principal, realmName);
    }

}
