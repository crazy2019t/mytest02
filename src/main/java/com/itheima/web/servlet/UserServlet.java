package com.itheima.web.servlet;

import com.itheima.constant.Constant;
import com.itheima.domain.Result;
import com.itheima.domain.User;
import com.itheima.exception.PwdException;
import com.itheima.exception.RegisterFaildError;
import com.itheima.exception.UnActiveException;
import com.itheima.exception.UsernameException;
import com.itheima.factory.BeanFactory;
import com.itheima.service.IUserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user")
public class UserServlet extends BaseServlet {
    private IUserService service = (IUserService) BeanFactory.getBean("userService");

    //退出登录
    private Result loginOut(HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.getSession().invalidate();

        Cookie cookie = new Cookie("info", null);
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);

        response.sendRedirect("/index.html");
        return null;
    }

    //页面头部数据
    private Result findUserInfo(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_KEY);
        Result result = new Result(true, user, null);

        return result;
    }

    //登录
    private Result login(HttpServletRequest request,HttpServletResponse response){
        Result result = new Result(true);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String checkCode = request.getParameter("checkCode");
        String autologin = request.getParameter("autologin");
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(Constant.CHECKCODE);

        if (code.equalsIgnoreCase(checkCode)){
            //验证码成功
            try {
                User user=service.doLogin(username,password);
                result.setData(true);
                session.setAttribute(Constant.USER_KEY,user);
                if ("on".equals(autologin)){
                    Cookie cookie = new Cookie("info",username+"#"+password);
                    cookie.setMaxAge(30*60);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                }

            }
            catch (UsernameException e) {
                result.setMessage(e.getMessage());
            }catch (PwdException e) {
                result.setMessage(e.getMessage());
            }catch (UnActiveException e) {
                result.setMessage(e.getMessage());
            }catch (Exception e) {
                e.printStackTrace();
                result.setFlag(false);
                result.setMessage(Constant.SERVICE_ERROR);
            }
        }else {
            //验证码失败
            result.setMessage(Constant.CHECK_ERRROE);
        }
        return result;
    }


    //激活
    private Result active(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        try {
           service.doActive(code);
           response.sendRedirect("/login.html");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
        }
        return null;
    }

    //注册
    private Result register(HttpServletRequest request,HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        Result result = new Result(true);
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            BeanUtils.populate(user, map);
        try {
            service.doRegister(user);
            result.setData(true);
        } catch (RegisterFaildError e) {
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(Constant.SERVICE_ERROR);
        }

        return result;
    }

    //查看用户名是否可用
    private Result checkUsername(HttpServletRequest request,HttpServletResponse response){
        Result result = new Result(true);
        String username = request.getParameter("username");

        try {
            User user=service.checkUsername(username);
            if (user==null){
                //用户名可用
                result.setData(true);
            }else {
                result.setData(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(Constant.SERVICE_ERROR);
        }
        return result;
    }

}
