package com.itheima.web.servlet;

import com.itheima.constant.Constant;
import com.itheima.domain.PageBean;
import com.itheima.domain.Result;
import com.itheima.domain.Route;
import com.itheima.factory.BeanFactory;
import com.itheima.service.IRouteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/route")
public class RouteServlet extends BaseServlet {
    private IRouteService service= (IRouteService) BeanFactory.getBean("routeService");

    //收藏排行榜的分页展示
    private Result favoriteRank(HttpServletRequest request,HttpServletResponse response){
        Result result = new Result(true);
        Integer currentPage = Integer.valueOf(request.getParameter("currentPage"));
        String routename = request.getParameter("routename");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");

        try {
            PageBean<Route> pageBean=service.favoriteRank(currentPage,routename,minPrice,maxPrice);
            result.setData(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(Constant.SERVICE_ERROR);
        }
        return result;
    }

    //详细信息
    private Result findByRid(HttpServletRequest request,HttpServletResponse response){
        Result result = new Result(true);
        String rid = request.getParameter("rid");
        try {
            Route route=service.findByRid(rid);
            result.setData(route);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(Constant.SERVICE_ERROR);
        }
        return result;
    }

    //list分页信息和搜索
    private Result findByPage(HttpServletRequest request,HttpServletResponse response){
        Result result = new Result(true);
        String cid = request.getParameter("cid");
        Integer currentPage = Integer.valueOf(request.getParameter("currentPage"));
        String keyword = request.getParameter("keyword");

        try {
            PageBean<Route> pageBean=service.findByPage(cid,currentPage,keyword);
            result.setData(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(Constant.SERVICE_ERROR);
        }
        return result;
    }

    //精选信息
    private Result careChoose(HttpServletRequest request,HttpServletResponse response){
        Result result = new Result(true);
        try {
            Map<String,List<Route>> map=service.careChoose();
            result.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(Constant.SERVICE_ERROR);
        }
        return result;
    }
}
