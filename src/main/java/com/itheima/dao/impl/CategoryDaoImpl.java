package com.itheima.dao.impl;

import com.itheima.dao.ICategoryDao;
import com.itheima.domain.Category;
import com.itheima.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements ICategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());
    @Override
    public List<Category> findAll() {
        String sql="select * from tab_category";
        List<Category> list = null;
        try {
            list = template.query(sql, new BeanPropertyRowMapper<>(Category.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
