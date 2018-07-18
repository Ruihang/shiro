package realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * @author w_huangruixian
 * @date 2018/7/12 15:15
 **/
public class MyRealm2 implements Realm {

    public String getName() {
        return "myRealm";
    }

    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof UsernamePasswordToken;
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        if (!"wang".equalsIgnoreCase(username)) throw new UnknownAccountException();
        if (!"123".equals(password)) throw new IncorrectCredentialsException();
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
