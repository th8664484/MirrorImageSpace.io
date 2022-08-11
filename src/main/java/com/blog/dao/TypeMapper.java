package com.blog.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Blog;
import com.blog.entity.Type;
import com.blog.vo.TypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

public interface TypeMapper extends BaseMapper<Type> {

    Type selectByName(String name);

    List<Type> listType();

    List<TypeVo> listTypeTop(int size);
}
