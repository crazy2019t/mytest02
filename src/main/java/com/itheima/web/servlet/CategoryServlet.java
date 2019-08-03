package com.itheima.web.servlet;

import com.itheima.constant.Constant;
import com.itheima.domain.Category;
import com.itheima.domain.Result;
import com.itheima.factory.BeanFactory;
import com.itheima.service.ICategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet {
    private ICategoryService service= (ICategoryService) BeanFactory.getBean("categoryService");

    //头部所有分类查询
    private Result findAll(HttpServletRequest request,HttpServletResponse response){
        Result result = new Result(true);
        try {
            List<Category> list=service.findAll();
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(Constant.SERVICE_ERROR);
        }
        return result;
    }
}
