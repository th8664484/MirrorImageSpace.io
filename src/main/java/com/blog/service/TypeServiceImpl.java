package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dao.TypeMapper;
import com.blog.entity.Blog;
import com.blog.entity.Type;
import com.blog.exception.NotFoundException;
import com.blog.vo.TypeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public Integer saveType(Type type) {
        type.setBlogs(null);
        return typeMapper.insert(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name");
        queryWrapper.eq("id",id);
        return typeMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Type> listType() {
        return typeMapper.listType();
    }

    @Override
    public List<TypeVo> listTypeTop(int size) {
        return typeMapper.listTypeTop(size);
    }

    @Transactional
    @Override
    public Type getByName(String name) {
        return typeMapper.selectByName(name);
    }


    @Transactional
    @Override
    public Map<String, Object> pageType(int page, int limit) {
        Page<Type> p = new Page<>(page,limit);
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name");
        queryWrapper.orderByDesc("id");
        p = typeMapper.selectPage(p,queryWrapper);
        List<TypeVo> typeVos = new ArrayList<>();
        for (Type type : p.getRecords()) {
            TypeVo vo = new TypeVo(type.getId().toString(),type.getName(),0);
            typeVos.add(vo);
        }
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", 0);
        map.put("msg", "数据错误");
        map.put("count", p.getTotal());
        map.put("data", typeVos);
        return map;
    }

    @Transactional
    @Override
    public Integer updateType(Long id, Type type) {
        Type t = getType(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        type.setBlogs(null);
        BeanUtils.copyProperties(type,t);
        return typeMapper.updateById(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeMapper.deleteById(id);
    }
}
