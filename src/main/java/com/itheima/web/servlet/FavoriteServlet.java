package com.itheima.web.servlet;

import com.itheima.constant.Constant;
import com.itheima.domain.Favorite;
import com.itheima.domain.PageBean;
import com.itheima.domain.Result;
import com.itheima.domain.User;
import com.itheima.factory.BeanFactory;
import com.itheima.service.IFavoriteService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/favorite")
public class FavoriteServlet extends BaseServlet {
    private IFavoriteService service= (IFavoriteService) BeanFactory.getBean("favoriteService");

    //查看我的收藏
    private  Result findMyFavorite(HttpServletRequest request,HttpServletResponse response){
        Result result = new Result(true);
        Integer currentPage = Integer.valueOf(request.getParameter("currentPage"));

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_KEY);

        try {
            PageBean<Favorite> pageBean=service.findMyFavorite(user,currentPage);
            result.setData(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(Constant.SERVICE_ERROR);
        }
        return result;
    }

    //添加收藏
    private Result addFavorite(HttpServletRequest request,HttpServletResponse response){
        Result result = new Result(true);
        String rid = request.getParameter("rid");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_KEY);
        try {
            if (user==null){
            //用户未登录
                result.setData(-1);
            }else {
                //已登录
                int count=service.addFavorite(user,rid);
                result.setData(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(Constant.SERVICE_ERROR);
        }
        return result;
    }

    //判断某条路线是否已经被当前用户收藏
    private Result isFavorite(HttpServletRequest request,HttpServletResponse response){
        Result result = new Result(true);
        String rid = request.getParameter("rid");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_KEY);
        try {
            if (user==null) {
                //用户未登录
                result.setData(false);
            }else {
                //用户已登录
                Favorite favorite=service.isFavorite(user,rid);
                if (favorite==null){
                    //未收藏
                    result.setData(false);
                }else {
                    //已收藏
                    result.setData(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage(Constant.SERVICE_ERROR);
        }
        return result;
    }
}
