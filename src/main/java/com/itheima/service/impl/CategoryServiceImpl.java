package com.itheima.service.impl;

import com.alibaba.druid.sql.dialect.sqlserver.ast.SQLServerOutput;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeBase;
import com.itheima.constant.Constant;
import com.itheima.dao.ICategoryDao;
import com.itheima.domain.Category;
import com.itheima.factory.BeanFactory;
import com.itheima.service.ICategoryService;
import com.itheima.utils.JedisUtil;
import redis.clients.jedis.Jedis;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.List;

public class CategoryServiceImpl implements ICategoryService {
    private ICategoryDao dao= (ICategoryDao) BeanFactory.getBean("categoryDao");

   /* @Override
    public List<Category> findAll() throws IOException {
        Jedis jedis = new Jedis();
        String jsonStr = jedis.get(Constant.CATEGORY_LIST);
        ObjectMapper mapper = new ObjectMapper();

        if (jsonStr == null) {
            //说明redis中没有存储分类信息
            //3.从mysql获取
            List<Category> categoryList = dao.findAll();
            //4.将categoryList转换成json字符串
            jsonStr = mapper.writeValueAsString(categoryList);
            //5.将jsonStr存储到redis中
            jedis.set(Constant.CATEGORY_LIST,jsonStr);
        }
        //关闭连接
        jedis.close();

        List<Category> list = mapper.readValue(jsonStr, new TypeReference<List<Category>>() {
        });
        return list;
    }*/
    @Override
    public List<Category> findAll() throws IOException {
        //1.从redis中获取所有分类信息
        Jedis jedis = JedisUtil.getJedis();
        String jsonStr = jedis.get(Constant.CATEGORY_LIST);

        ObjectMapper mapper = new ObjectMapper();
        //2.判断redis中有没有分类信息
        if (jsonStr == null) {
            //说明redis中没有存储分类信息
            //3.从mysql获取
            List<Category> categoryList = dao.findAll();
            //4.将categoryList转换成json字符串
            jsonStr = mapper.writeValueAsString(categoryList);
            //5.将jsonStr存储到redis中
            jedis.set(Constant.CATEGORY_LIST,jsonStr);
        }
        //关闭连接
        jedis.close();

        //将jsonStr转换成List<Category>
        List<Category> list = mapper.readValue(jsonStr, new TypeReference<List<Category>>() {});
        return list;
    }
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        Category category = new Category(13, "武汉");
        String c=null;
        Category cat2=null;
        try {
             c = mapper.writeValueAsString(category);
            cat2 = mapper.readValue(c, Category.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(category);
        System.out.println(c);
        System.out.println(cat2);

    }
}

