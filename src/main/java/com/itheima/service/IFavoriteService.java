package com.itheima.service;

import com.itheima.domain.Favorite;
import com.itheima.domain.PageBean;
import com.itheima.domain.User;

import java.lang.reflect.InvocationTargetException;

public interface IFavoriteService {
    Favorite isFavorite(User user, String rid);

    int addFavorite(User user, String rid);

    PageBean<Favorite> findMyFavorite(User user, Integer currentPage) throws InvocationTargetException, IllegalAccessException;
}
