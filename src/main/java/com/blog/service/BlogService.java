package com.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Blog;
import com.blog.vo.BlogSevaVo;

import java.util.List;
import java.util.Map;

public interface BlogService  {
    BlogSevaVo getBlog(Long id);

    BlogSevaVo getAndConvert(Long id);

    Map<String, Object> pageBlog(int page, int limit,Map<String,Object> map);

    Map<String,List<Blog>> archiveBlog();

    Page<Blog> listBlog(Page page);

    Page<Blog> listBlog(Page page,Long typeId);

    Page<Blog> listBlogByTagId(Page page,Long tagId);

    Page<Blog> listBlog(String query,Page page);

    List<Blog> listRecommendBlogTop(int size);

    Integer saveBlog(Blog blog);

    Integer updateBlog(Long id,Blog blog);

    Integer countBlog();

    void delete(Long id);

}
