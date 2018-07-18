package credentials;

import com.alibaba.druid.filter.AutoLoad;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author w_huangruixian
 * @date 2018/7/13 19:32
 **/
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    Ehcache ehcache;

    public RetryLimitHashedCredentialsMatcher(){
        CacheManager cacheManager = CacheManager.getInstance();
        ehcache = cacheManager.addCacheIfAbsent("ehcache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        Element element = ehcache.get(username);
        if (element == null) {
            element = new Element(username, new AtomicInteger(0));
            ehcache.put(element);
        }
        AtomicInteger retryCount = (AtomicInteger) element.getObjectValue();
        if (retryCount.incrementAndGet() > 5) {
            throw new ExcessiveAttemptsException();//过度尝试
        }

        boolean match = super.doCredentialsMatch(token, info);
        if (match) {
            ehcache.remove(username);
        }
        return match;
    }
}
