package com.itheima.dao.impl;

import com.itheima.dao.IFavoriteDao;
import com.itheima.domain.Favorite;
import com.itheima.domain.User;
import com.itheima.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class FavoriteDaoImpl implements IFavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public Favorite findUser(User user, String rid) {
        String sql ="select * from tab_favorite where rid=? and uid=?";
        Favorite favorite = null;
        try {
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, user.getUid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return favorite;
    }

    @Override
    public void addFavorite(User user, String rid) {
        String sql ="insert into tab_favorite values (?,?,?)";
        template.update(sql,rid,new Date(),user.getUid());
    }

    @Override
    public void updateRoute(String rid) {
        String sql="update tab_route set count=count+1 where rid=?";
        template.update(sql,rid);
    }

    @Override
    public int findRoutCount(String rid) {
        String sql="select count from tab_route where rid=?";
        Integer count = null;
        try {
            count = template.queryForObject(sql, Integer.class, rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Integer findFavoriteCount(int uid) {
        String sql="select count(*) from tab_favorite where uid=?";
        Integer count = null;
        try {
            count = template.queryForObject(sql, Integer.class, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> findFavoriteList(int uid, Integer currentPage, Integer pageSize) {
        String sql="SELECT * FROM tab_favorite f,tab_route r WHERE r.rid=f.rid AND uid=? limit ?,?";
        List<Map<String, Object>> maps = null;
        try {
            maps = template.queryForList(sql, uid, (currentPage - 1) * pageSize, pageSize);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return maps;
    }
}
