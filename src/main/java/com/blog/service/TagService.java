package com.blog.service;

import com.blog.entity.Tag;
import com.blog.vo.TagVo;

import java.util.List;
import java.util.Map;

public interface TagService {
    Integer saveTag(Tag tag);
    Tag getTag(Long id);
    List<Tag> listTag();
    List<Tag> listTag(List<String> tagIds);
    List<TagVo> listTop(int size);
    Tag getByName(String name);
    Map<String, Object> pageTag(int page, int limit);
    Integer updateTag(Long id,Tag tag);
    void deleteTag(Long id);
}
