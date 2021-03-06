package realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import permission.BitPermission;

/**
 * @author w_huangruixian
 * @date 2018/7/13 10:53
 **/
public class MyRoleRealm extends AuthorizingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        if (!"zhang".equalsIgnoreCase(username)) throw new UnknownAccountException();
        if (!"123".equals(password)) throw new IncorrectCredentialsException();
        return new SimpleAuthenticationInfo(username, password, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRole("role1");
        authorizationInfo.addRole("role2");
        authorizationInfo.addObjectPermission(new BitPermission("+user1+10"));//1010
        authorizationInfo.addObjectPermission(new WildcardPermission("user1:*"));

        authorizationInfo.addStringPermission("+user2+10");//1010
        authorizationInfo.addStringPermission("user2:*");
        return authorizationInfo;
    }
}
