package com.itheima.dao;

import com.itheima.domain.Favorite;
import com.itheima.domain.User;

import java.util.List;
import java.util.Map;

public interface IFavoriteDao {
    Favorite findUser(User user, String rid);

    void addFavorite(User user, String rid);

    void updateRoute(String rid);

    int findRoutCount(String rid);

    Integer findFavoriteCount(int uid);

    List<Map<String,Object>> findFavoriteList(int uid, Integer currentPage, Integer pageSize);
}
