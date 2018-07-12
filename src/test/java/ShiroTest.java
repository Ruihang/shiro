import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author w_huangruixian
 * @date 2018/7/12 15:00
 **/
public class ShiroTest {

    @Test
    public void testIniUsernamePasswordTokenLogin() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);//全局
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");//
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            System.out.println("AuthenticationException");
        }

        Assert.assertEquals(true, subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testIniUseRealmLogin() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);//全局
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");//
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            System.out.println("AuthenticationException");
        }

        Assert.assertEquals(true, subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testIniJdbcRealmLogin() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);//全局
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");//
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            System.out.println("AuthenticationException");
        }

        Assert.assertEquals(true, subject.isAuthenticated());
        subject.logout();
    }


    //===
    private void login(String configFile) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);//全局
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);
    }

    @Test
    public void testAllSuccessfulStrategyWithSuccess() {
        login("classpath:shiro-authenticator-all-success.ini");
        Subject subject = SecurityUtils.getSubject();

        PrincipalCollection principals = subject.getPrincipals();
        principals.forEach(System.out::println);
        Assert.assertEquals(2, principals.asList().size());
    }

    @Test(expected = UnknownAccountException.class)
    public void testAllSuccessfulStrategyWithFail() {
        login("classpath:shiro-authenticator-all-fail.ini");
        Subject subject = SecurityUtils.getSubject();
    }
}
