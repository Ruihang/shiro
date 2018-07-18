package config;

import org.apache.shiro.web.servlet.IniShiroFilter;
import org.apache.shiro.web.servlet.ShiroFilter;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @author w_huangruixian
 * @date 2018/7/17 11:59
 **/
@WebFilter(urlPatterns = "/*", initParams = @WebInitParam(name = "configPath", value = "classpath:shiro.ini"))
public class ShiroFilterConfig extends IniShiroFilter {

}
