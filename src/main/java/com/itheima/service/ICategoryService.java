package com.itheima.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itheima.domain.Category;

import java.io.IOException;
import java.util.List;

public interface ICategoryService {
    List<Category> findAll() throws IOException;
}
