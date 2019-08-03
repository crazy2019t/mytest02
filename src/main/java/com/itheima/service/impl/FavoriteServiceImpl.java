package com.itheima.service.impl;

import com.itheima.constant.Constant;
import com.itheima.dao.IFavoriteDao;
import com.itheima.domain.Favorite;
import com.itheima.domain.PageBean;
import com.itheima.domain.Route;
import com.itheima.domain.User;
import com.itheima.factory.BeanFactory;
import com.itheima.service.IFavoriteService;
import com.itheima.utils.JDBCUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoriteServiceImpl implements IFavoriteService {
    private IFavoriteDao dao= (IFavoriteDao) BeanFactory.getBean("favoriteDao");

    @Override
    public Favorite isFavorite(User user, String rid) {

        return dao.findUser(user,rid);
    }

    @Override
    public int addFavorite(User user, String rid) {

//开启事务:以前是使用connection.setAutoCommit(false)
        //1. 开启事务同步
        TransactionSynchronizationManager.initSynchronization();
        //2.获取一个数据源
        DataSource dataSource = JDBCUtil.getDataSource();
        //3.创建一个jdbctemplate对象
        JdbcTemplate template = new JdbcTemplate(dataSource);
        //4.使用jdbc框架中的datasourceutil获取连接
        Connection conn = DataSourceUtils.getConnection(dataSource);

        int count= 0;
        try {
            //开启事物
            conn.setAutoCommit(false);
            //添加表
            dao.addFavorite(user,rid);

            dao.updateRoute(rid);

            count=dao.findRoutCount(rid);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                //回滚事物
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
//            关闭事物
            TransactionSynchronizationManager.clearSynchronization();

            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public PageBean<Favorite> findMyFavorite(User user, Integer currentPage) throws InvocationTargetException, IllegalAccessException {
        PageBean<Favorite> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);

        Integer pageSize=Constant.FAVORITE_SIZE;
        pageBean.setPageSize(Constant.FAVORITE_SIZE);

        Integer totalSize=dao.findFavoriteCount(user.getUid());
        pageBean.setTotalSize(totalSize);

        List<Map<String,Object>> maps=dao.findFavoriteList(user.getUid(),currentPage,pageSize);
        List<Favorite> list=new ArrayList<>();
        for (Map<String, Object> map : maps) {
            Favorite favorite = new Favorite();
            BeanUtils.populate(favorite,map );

            Route route = new Route();
            BeanUtils.populate(route,map);
            favorite.setRoute(route);
            list.add(favorite);

            favorite.setUser(user);
        }
        pageBean.setList(list);
        return pageBean;
    }
}
