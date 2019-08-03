package com.itheima.web.filter;

import com.itheima.constant.Constant;
import com.itheima.domain.User;
import com.itheima.factory.BeanFactory;
import com.itheima.service.IUserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //强转
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response= (HttpServletResponse) resp;
        //得到user
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute(Constant.USER_KEY);
        if (user==null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("info".equals(cookie.getName())){
                    String value = cookie.getValue();
                    String[] split = value.split("#");
                    String username = split[0];
                    String password= split[1];

                    IUserService service= (IUserService) BeanFactory.getBean("userService");
                    try {
                        user = service.doLogin(username, password);
                        session.setAttribute(Constant.USER_KEY,user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
