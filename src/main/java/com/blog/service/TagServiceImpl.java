package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dao.TagMapper;
import com.blog.entity.Tag;
import com.blog.exception.NotFoundException;
import com.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public Integer saveTag(Tag tag) {
        tag.setBlogs(null);
        return tagMapper.insert(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name");
        queryWrapper.eq("id",id);
        return tagMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Tag> listTag() {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name");
        return tagMapper.selectList(queryWrapper);
    }

    @Override
    public List<Tag> listTag(List<String> tagIds) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name");
        queryWrapper.in("id",tagIds);
        return tagMapper.selectList(queryWrapper);
    }

    @Override
    public List<TagVo> listTop(int size) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name");
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        List<TagVo> voList = tagMapper.listTop(size);
        for(int i=0;i<tagList.size();i++){
            for(int j=0;j<voList.size();j++){
                if(!tagList.get(i).getId().toString().equals(voList.get(j).getId())){
                    voList.add(new TagVo(tagList.get(i).getId().toString(),tagList.get(i).getName(),0));
                    break;
                }
            }
        }
        return voList;
    }

    @Transactional
    @Override
    public Tag getByName(String name) {
        return tagMapper.selectByName(name);
    }


    @Transactional
    @Override
    public Map<String, Object> pageTag(int page, int limit) {
        Page<Tag> p = new Page<>(page,limit);
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name");
        queryWrapper.orderByDesc("id");
        p = tagMapper.selectPage(p,queryWrapper);
        List<TagVo> tagVos = new ArrayList<>();
        for (Tag tag : p.getRecords()) {
            TagVo vo = new TagVo(tag.getId().toString(),tag.getName(),0);
            tagVos.add(vo);
        }
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", 0);
        map.put("msg", "数据错误");
        map.put("count", p.getTotal());
        map.put("data", tagVos);
        return map;
    }

    @Transactional
    @Override
    public Integer updateTag(Long id, Tag tag) {
        Tag t = getTag(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        tag.setBlogs(null);
        BeanUtils.copyProperties(tag,t);
        return tagMapper.updateById(t);
    }

    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteById(id);
    }
}
