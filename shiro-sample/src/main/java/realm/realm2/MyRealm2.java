package realm.realm2;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.Realm;

public class MyRealm2 implements Realm {

    @Override
    public String getName() {
        return "b"; //realm name 为 “a”
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return true;
    }

    //省略supports方法，具体请见源码  
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return new SimpleAuthenticationInfo(
                "zhang", //身份 字符串类型  
                "123",   //凭据  
                getName() //Realm Name  
        );
    }
}  