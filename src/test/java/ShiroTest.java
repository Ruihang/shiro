import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

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
    private void login(String configFile, String username, String password) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);//全局
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
    }

    @Test
    public void testAllSuccessfulStrategyWithSuccess() {
        login("classpath:shiro-authenticator-all-success.ini", "zhang", "123");
        Subject subject = SecurityUtils.getSubject();

        PrincipalCollection principals = subject.getPrincipals();
        principals.forEach(System.out::println);
        Assert.assertEquals(2, principals.asList().size());
    }

    @Test(expected = UnknownAccountException.class)
    public void testAllSuccessfulStrategyWithFail() {
        login("classpath:shiro-authenticator-all-fail.ini", "zhang", "123");
        Subject subject = SecurityUtils.getSubject();
    }

    //===
    @Test
    public void testHasRole() {
        login("classpath:shiro-role.ini", "zhang", "123");
        Assert.assertTrue(subject().hasRole("role1"));
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));
        boolean[] hasRoles = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertTrue(hasRoles[0]);
        Assert.assertTrue(hasRoles[1]);
        Assert.assertFalse(hasRoles[2]);

    }

    private Subject subject() {
        return SecurityUtils.getSubject();
    }

    //===
    @Test(expected = UnauthorizedException.class)
    public void testCheckRole() {
        login("classpath:shiro-role.ini", "zhang", "123");
        subject().checkRole("roles");
        subject().checkRoles("role1", "role2");
    }


    //===
    @Test
    public void testIsPermitted() {
        login("classpath:shiro-permission.ini", "zhang", "123");
        Assert.assertTrue(subject().isPermitted("user:create"));
        Assert.assertTrue(subject().isPermittedAll("user:create", "user:delete"));
        Assert.assertFalse(subject().isPermitted("user:view"));
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckPermission() {
        login("classpath:shiro-permission.ini", "zhang", "123");
        subject().checkPermission("user:create");
        subject().checkPermissions("user:delete", "user:update");
        subject().checkPermission("user:view");
    }

    //===3.3Permission
    @Test
    public void testCheckPermission2() {
        login("classpath:shiro-permission.ini", "zhang", "123");
//        subject().checkPermissions("system:user:update");
//        subject().checkPermissions("system:user:update", "system:user:delete");
//        subject().checkPermissions("system:user:update,delete");
//        subject().checkPermissions("system:user:create,delete,update,view");
//        subject().checkPermissions("user:view");
//        subject().checkPermissions("system:user:view");
//        subject().checkPermissions("user:view:1");//role71
//        subject().checkPermissions("user:delete,update:1");//role72
//        subject().checkPermissions("user:update:1", "user:delete:1");//role72
//        subject().checkPermissions("user:update:1", "user:delete:1", "user:view:1");//role73
//        subject().checkPermissions("user:auth:1", "user:auth:2");//role74
//        subject().checkPermissions("user:view:1", "user:auth:2");//role75

    }

    @Test
    public void testWildcardPermission(){
        login("classpath:shiro-permission.ini", "zhang", "123");
        subject().checkPermission("menu:view:1");
        subject().checkPermission(new WildcardPermission("menu:view:1"));
    }


    //===



    @Test
    public void testIsPermitted2() {
        login("classpath:shiro-authorizer.ini", "zhang", "123");
        //判断拥有权限：user:create
        Assert.assertTrue(subject().isPermitted("user1:update"));
        Assert.assertTrue(subject().isPermitted("user2:update"));
        //通过二进制位的方式表示权限
        Assert.assertTrue(subject().isPermitted("+user1+2"));//新增权限 0010 add
        Assert.assertTrue(subject().isPermitted("+user1+8"));//查看权限 1000 view
        Assert.assertTrue(subject().isPermitted("+user2+10"));//新增及查看

        Assert.assertFalse(subject().isPermitted("+user1+4"));//没有删除权限

        Assert.assertTrue(subject().isPermitted("menu:view"));//通过MyRolePermissionResolver解析得到的权限
    }

}
