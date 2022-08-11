package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dao.BlogMapper;
import com.blog.entity.Blog;
import com.blog.exception.NotFoundException;
import com.blog.vo.BlogListVo;;
import com.blog.vo.BlogSevaVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BlogServiceImpl  implements BlogService{

    @Autowired
    private BlogMapper blogMapper;


    @Override
    public BlogSevaVo getBlog(Long id) {
        Blog b = blogMapper.getBlog(id);
        BlogSevaVo blog = new BlogSevaVo();
        BeanUtils.copyProperties(b,blog);
        blog.setId(b.getId().toString());
        blog.setTypeId(b.getType().getId().toString());
        blog.info();
        return blog;
    }


    @Transactional
    @Override
    public BlogSevaVo getAndConvert(Long id) {
        Blog blog = blogMapper.getBlog(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        BlogSevaVo b = new BlogSevaVo();
        BeanUtils.copyProperties(blog,b);
        b.setId(blog.getId().toString());
        b.toHtml();
        return b;
    }

    @Override
    public Map<String, Object> pageBlog(int page, int limit,Map<String,Object> queryMap) {
        Page<Blog> p = new Page<>(page,limit);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        if (queryMap.get("title") != null)
            queryWrapper.like("title",queryMap.get("title"));
        if (queryMap.get("typeId")!= null)
            queryWrapper.eq("type_id",queryMap.get("typeId"));
        if (queryMap.get("recommend")!= null)
            queryWrapper.eq("recommend",1);
        p = blogMapper.listBlog(p, queryWrapper);

        List<BlogListVo> blogVos = new ArrayList<>();
        for(Blog b:p.getRecords()){
            BlogListVo vo = new BlogListVo(b.getId().toString(),b.getTitle(),b.getUpdateTime(),b.isPublished(),b.isRecommend(),b.getType().getId(),b.getType().getName());
            blogVos.add(vo);
        }

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", 0);
        map.put("msg", "数据错误");
        map.put("count", p.getTotal());
        map.put("data",blogVos);
        return map;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        Map<String,List<Blog>> archiveMap = new HashMap<>();
        List<String> years = blogMapper.listBlogYear();
        for (String year : years) {
            archiveMap.put(year,blogMapper.listBlogByYear(year));
        }
        return archiveMap;
    }

    @Override
    public Page<Blog> listBlog(Page page) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("b.published",true);
        Page<Blog> p =blogMapper.listBlog(page,queryWrapper);
        return p;
    }

    @Override
    public Page<Blog> listBlog(Page page, Long typeId) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("b.published",true);
        queryWrapper.eq("b.type_id",typeId);
        return blogMapper.listBlog(page,queryWrapper);
    }

    @Override
    public Page<Blog> listBlogByTagId(Page page, Long tagId) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("b.published",true);
        queryWrapper.eq("bt.tags_id",tagId);
        return blogMapper.listBlogByTagId(page,queryWrapper);
    }

    @Override
    public Page<Blog> listBlog(String query, Page page) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("b.published",true);
        queryWrapper.like("b.title",query);
        queryWrapper.or();
        queryWrapper.like("b.content",query);
        return blogMapper.listBlog(page,queryWrapper);
    }

    @Override
    public List<Blog> listRecommendBlogTop(int size) {
        Page<Blog> p = new Page<>(1,size);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("b.recommend",true);
        queryWrapper.eq("b.published",true);
        p = blogMapper.listBlog(p,queryWrapper);
        return p.getRecords();
    }

    @Transactional
    @Override
    public Integer saveBlog(Blog blog) {
        int t = blogMapper.insert(blog);
        if (t != 0){
            saveTag(blog.getId(),blog.getTagIds());
        }
        return t;
    }

    private void saveTag(Long b,List<Long> tagIds){
        List<Map<Long,Long>> maps = new ArrayList<>();
        for (Long l:tagIds){
            Map<Long,Long> map = new HashMap<>();
            map.put(b,l);
            maps.add(map);
        }
        if (maps.size()>0)
            blogMapper.insertBatch(maps);
    }
    @Transactional
    @Override
    public Integer updateBlog(Long id, Blog blog) {
        Blog b = blogMapper.selectById(id);
        if (b == null){
            throw new NotFoundException("博客不存在");
        }
        BeanUtils.copyProperties(blog,b);
        int t = blogMapper.updateById(b);
        if (t != 0){
            blogMapper.deleteTag(id);
            saveTag(id,b.getTagIds());
        }
        return t;
    }

    @Override
    public Integer countBlog() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published",true);
        return blogMapper.selectCount(queryWrapper);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        blogMapper.deleteById(id);
    }


}
