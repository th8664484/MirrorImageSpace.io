package com.blog.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BlogMapper extends BaseMapper<Blog> {

    Blog getBlog(Long id);

    List<Blog> listBlogByYear(String year);

    List<String> listBlogYear();

    Page<Blog> listBlog(Page<Blog> page, @Param("ew") Wrapper<Blog> queryWrapper);

    Page<Blog> listBlogByTagId(Page<Blog> page, @Param("ew") Wrapper<Blog> queryWrapper);

    Integer insertBatch(@Param("blogIdIsTagIds") List<Map<Long,Long>> blogIdIsTagIds);

    Integer deleteTag(Long id);
}
