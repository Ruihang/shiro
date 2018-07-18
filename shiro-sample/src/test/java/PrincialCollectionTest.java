import entity.User;
import org.apache.shiro.subject.PrincipalCollection;
import org.junit.Test;

import java.util.Collection;
import java.util.Set;

/**
 * @author w_huangruixian
 * @date 2018/7/17 9:55
 **/
public class PrincialCollectionTest extends BaseTest {

    @Test
    public void test() {
        login("classpath:shiro-multirealm.ini", "zhang", "123");
        Object primaryPrincipal1 = subject().getPrincipal();
        PrincipalCollection princialCollection = subject().getPrincipals();
        Object primaryPrincipal2 = princialCollection.getPrimaryPrincipal();

        Set<String> realmNames = princialCollection.getRealmNames();
        System.out.println(realmNames);

        Set<Object> principals = princialCollection.asSet(); //asList和asSet的结果一样
        System.out.println(principals);

        Collection<User> users = princialCollection.fromRealm("c");
        System.out.println(users);
    }
}
