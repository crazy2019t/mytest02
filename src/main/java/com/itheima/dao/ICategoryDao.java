package com.itheima.dao;

import com.itheima.domain.Category;

import java.util.List;

public interface ICategoryDao {
    List<Category> findAll();

}
