package realm.realm2;

import entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.Realm;

public class MyRealm3 implements Realm {

    @Override
    public String getName() {
        return "c"; //realm name 为 “a”
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return true;
    }

    //省略supports方法，具体请见源码  
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        User user = new User("zhang", "123");
        return new SimpleAuthenticationInfo(
                user, //身份 User类型
                "123",   //凭据
                getName() //Realm Name
        );
    }
}  