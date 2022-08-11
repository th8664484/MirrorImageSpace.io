package com.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Type;
import com.blog.vo.TypeVo;

import java.util.List;
import java.util.Map;

public interface TypeService {
    Integer saveType(Type type);
    Type getType(Long id);
    List<Type> listType();

    List<TypeVo> listTypeTop(int size);

    Type getByName(String name);
    Map<String, Object> pageType(int page, int limit);
    Integer updateType(Long id,Type type);
    void deleteType(Long id);
}
