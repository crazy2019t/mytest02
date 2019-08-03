package com.itheima.service.impl;

import com.itheima.constant.Constant;
import com.itheima.dao.IRouteDao;
import com.itheima.domain.*;
import com.itheima.factory.BeanFactory;
import com.itheima.service.IRouteService;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteServiceImpl implements IRouteService {
    private IRouteDao dao= (IRouteDao) BeanFactory.getBean("routeDao");
    @Override
    public Map<String, List<Route>> careChoose() {
        Map<String, List<Route>> map = new HashMap<>();
        List<Route> popList=dao.findpopList();
        List<Route> newestList=dao.findnewestList();
        List<Route> themeList=dao.findthemeList();

        map.put("popList", popList);
        map.put("newestList", newestList);
        map.put("themeList", themeList);

        return map;
    }

    @Override
    public PageBean<Route> findByPage(String cid, Integer currentPage, String keyword) {
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);

        Integer pageSize=Constant.PAGESIZE;
        pageBean.setPageSize(pageSize);

        Integer totalSize=dao.findCount(cid,keyword);
        pageBean.setTotalSize(totalSize);

        List<Route> list=dao.findList(cid,currentPage,pageSize,keyword);
        pageBean.setList(list);

        return pageBean;
    }

    @Override
    public Route findByRid(String rid) throws InvocationTargetException, IllegalAccessException {
        Map<String,Object> map=dao.findByRid(rid);
        Route route = new Route();
        BeanUtils.populate(route,map);

        Category category = new Category();
        BeanUtils.populate(category,map );
        route.setCategory(category);

        Seller seller = new Seller();
        BeanUtils.populate(seller,map);
        route.setSeller(seller);

        List<RouteImg> routeImgList=dao.findImg(rid);
        route.setRouteImgList(routeImgList);

        return route;
    }

    @Override
    public PageBean<Route> favoriteRank(Integer currentPage, String routename, String minPrice, String maxPrice) {
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);

        Integer pageSize=Constant.PAGESIZE;
        pageBean.setPageSize(Constant.PAGESIZE);

        Integer totalSize=dao.favoriteRankCount(routename,minPrice,maxPrice);
        pageBean.setTotalSize(totalSize);

        List<Route> list=dao.favoriteRankList(currentPage,pageSize,routename,minPrice,maxPrice);

        pageBean.setList(list);
        return pageBean;
    }
}
