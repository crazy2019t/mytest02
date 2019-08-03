package com.itheima.dao.impl;

import com.itheima.dao.IRouteDao;
import com.itheima.domain.Route;
import com.itheima.domain.RouteImg;
import com.itheima.utils.JDBCUtil;
import com.itheima.utils.StringUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouteDaoImpl implements IRouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public List<Route> findpopList() {
        String sql="select * from tab_route where rflag=1 order by count desc limit 0,4";
        List<Route> popList = null;
        try {
            popList = template.query(sql, new BeanPropertyRowMapper<>(Route.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return popList;

    }

    @Override
    public List<Route> findnewestList() {
        String sql="select * from tab_route where rflag=1 order by rdate desc limit 0,4";
        List<Route> newestList = null;
        try {
            newestList = template.query(sql, new BeanPropertyRowMapper<>(Route.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return newestList;
    }

    @Override
    public List<Route> findthemeList() {
        String sql="select * from tab_route where rflag=1 and isThemeTour=1 limit 0,4";
        List<Route> themeList = null;
        try {
            themeList = template.query(sql, new BeanPropertyRowMapper<>(Route.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return themeList;
    }

    @Override
    public Integer findCount(String cid, String keyword) {
        String sql="select count(*) from tab_route where rflag=1";
        List params = new ArrayList();
        if (!StringUtil.isEmpty(cid)) {
            sql+=" and cid=?";
            params.add(cid);
        }
        if (!StringUtil.isEmpty(keyword)){
            sql+=" and rname like ?";
            params.add("%"+keyword+"%");
        }
        Integer count = null;
        try {
            count = template.queryForObject(sql, Integer.class, params.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Route> findList(String cid, Integer currentPage, Integer pageSize, String keyword) {
        String sql="select * from tab_route where rflag=1";
        List params = new ArrayList();
        if (!StringUtil.isEmpty(cid)) {
            sql+=" and cid=?";
            params.add(cid);
        }
        if (!StringUtil.isEmpty(keyword)){
            sql+=" and rname like ?";
            params.add("%"+keyword+"%");
        }
        sql+=" limit ?,?";
        params.add((currentPage-1)*pageSize);
        params.add(pageSize);
        List<Route> list = null;
        try {
            list = template.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Map<String, Object> findByRid(String rid) {
        String sql="SELECT * FROM tab_route r,tab_category c,tab_seller s WHERE r.cid=c.cid AND r.sid=s.sid AND rid=?";
        Map<String, Object> map = null;
        try {
            map = template.queryForMap(sql, rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<RouteImg> findImg(String rid) {
        String sql="SELECT * FROM tab_route_img WHERE rid=?";
        List<RouteImg> routeImgList = null;
        try {
            routeImgList = template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return routeImgList;
    }

    @Override
    public Integer favoriteRankCount(String routename, String minPrice, String maxPrice) {
        String sql="select count(*) from tab_route where rflag=1";
        List<Object> params= new ArrayList();
        if (!StringUtil.isEmpty(routename)){
            sql+=" and rname like ?";
            params.add("%"+routename+"%");
        }
        if (!StringUtil.isEmpty(minPrice)){
            sql+=" and price >=?";
            params.add(minPrice);
        }
        if (!StringUtil.isEmpty(maxPrice)){
            sql+=" and price <=?";
            params.add(maxPrice);
        }

        Integer count = null;
        try {
            count = template.queryForObject(sql, Integer.class,params.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Route> favoriteRankList(Integer currentPage, Integer pageSize, String routename, String minPrice, String maxPrice) {
        String sql="select * from tab_route where rflag=1";
        List<Object> params= new ArrayList();
        if (!StringUtil.isEmpty(routename)){
            sql+=" and rname like ?";
            params.add("%"+routename+"%");
        }
        if (!StringUtil.isEmpty(minPrice)){
            sql+=" and price >=?";
            params.add(minPrice);
        }
        if (!StringUtil.isEmpty(maxPrice)){
            sql+=" and price <=?";
            params.add(maxPrice);
        }
        sql+=" order by count desc limit ?,?";
        params.add((currentPage-1)*pageSize);
        params.add(pageSize);

        List<Route> list = null;
        try {
            list = template.query(sql, new BeanPropertyRowMapper<>(Route.class),params.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
