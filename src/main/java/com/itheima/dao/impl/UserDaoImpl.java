package com.itheima.dao.impl;

import com.itheima.dao.IUserDao;
import com.itheima.domain.User;
import com.itheima.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements IUserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtil.getDataSource());

    @Override
    public User checkUsername(String username) {
        String sql="select * from tab_user where username=?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean addUser(User user) {
        String sql = "insert into tab_user values (null,?,?,?,?,?,?,?,?,?)";
        int i = template.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
        if (i==1){
            return true;
        }
        return false;
    }

    @Override
    public User findUserByCode(String code) {
        String sql="select * from tab_user where code=?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void update(User user) {
        String sql = "update tab_user set username=?,password=?,name=?,telephone=?,status=? where uid=?";
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getTelephone(),user.getStatus(),user.getUid());
    }
}
