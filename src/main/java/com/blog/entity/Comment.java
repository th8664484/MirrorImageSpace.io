package com.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long id;

    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private String blogId;
    private Long parentCommentId;
    private boolean adminComment;

    private Date createTime;

@TableField(exist = false)
    private Blog blog;

    @TableField(exist = false)
    private List<Comment> replyComments = new ArrayList<>();

    @TableField(exist = false)
    private Comment parentComment;
}
