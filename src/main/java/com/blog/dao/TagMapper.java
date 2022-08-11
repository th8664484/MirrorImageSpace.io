package com.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.Tag;
import com.blog.vo.TagVo;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    Tag selectByName(String name);

    List<TagVo> listTop(int size);
}
