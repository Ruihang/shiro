package config;

import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.env.WebEnvironment;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

/**
 * @author w_huangruixian
 * @date 2018/7/17 14:16
 **/
@WebListener
public class ShiroListenerConfig extends EnvironmentLoaderListener {


}
