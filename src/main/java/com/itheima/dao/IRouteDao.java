package com.itheima.dao;

import com.itheima.domain.Route;
import com.itheima.domain.RouteImg;

import java.util.List;
import java.util.Map;

public interface IRouteDao {
    List<Route> findpopList();

    List<Route> findnewestList();

    List<Route> findthemeList();

    Integer findCount(String cid, String keyword);

    List<Route> findList(String cid, Integer currentPage, Integer pageSize, String keyword);

    Map<String,Object> findByRid(String rid);

    List<RouteImg> findImg(String rid);

    Integer favoriteRankCount(String routename, String minPrice, String maxPrice);

    List<Route> favoriteRankList(Integer currentPage, Integer pageSize, String routename, String minPrice, String maxPrice);
}
