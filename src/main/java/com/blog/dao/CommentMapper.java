package com.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.Comment;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId);
}
