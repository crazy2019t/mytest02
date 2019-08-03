package com.itheima.service;

import com.itheima.domain.PageBean;
import com.itheima.domain.Route;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface IRouteService {
    Map<String, List<Route>> careChoose();

    PageBean<Route> findByPage(String cid, Integer currentPage, String keyword);

    Route findByRid(String rid) throws InvocationTargetException, IllegalAccessException;

    PageBean<Route> favoriteRank(Integer currentPage, String routename, String minPrice, String maxPrice);
}
