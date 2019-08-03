package com.itheima.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决请求参数乱码问题
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        //1.获取参数action的值
        String action = request.getParameter("action");
        //2.判断action的值，调用不同的方法
        //目标:根据action参数值调用对应的方法，action的值其实就跟要调用的方法的方法名一样
        //已知方法名、以及方法的参数类型，获取方法并且调用，使用反射技术
        Class clazz = this.getClass();
        try {
            Method method = clazz.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            //暴力反射
            method.setAccessible(true);
            //调用方法
            Result result = (Result) method.invoke(this, request, response);
            if (result!=null){
                toJson(response, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void toJson(HttpServletResponse response, Result result) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(result);

        response.getWriter().write(jsonStr);
    }
}
