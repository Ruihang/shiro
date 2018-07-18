package realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @author w_huangruixian
 * @date 2018/7/13 18:24
 **/
public class MySalt2Realm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = "liu"; //用户名及salt1
        String password = "a9a114054aa6758184314fbb959fbda4"; //加密后的密码
        String salt2 =    "24520ee264eab73ec09451d0e9ea6aac";
        SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(username, password, getName());
        ai.setCredentialsSalt(ByteSource.Util.bytes(username + salt2)); //盐是用户名+随机数
        return ai;
    }
}
