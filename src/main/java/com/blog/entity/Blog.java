package com.blog.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private Long id;

    /**标题*/
    private String title;

    /**内容*/
    private String content;
    /**首图*/
    private String firstPicture;
    /**简介*/
    private String description;
    /**标记*/
    private String flag;
    /**浏览次数*/
    private Integer views = 0;
    /**赞赏开启*/
    private boolean appreciation;
    /**版权开启*/
    private boolean shareStatement;
    /**评论开启*/
    private boolean commentabled;
    /**发布开启*/
    private boolean published;
    /**推荐开启*/
    private boolean recommend;

    /**创建时间*/
    private Date createTime;
    /**更新时间*/
    private Date updateTime;

    private Long typeId;

    private Long userId;

//    @Transient
    @TableField(exist = false)
    private List<Long> tagIds;

    @TableField(exist = false)
    private Type type;
    @TableField(exist = false)
    private List<Tag> tags = new ArrayList<>();
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private List<Comment> comments = new ArrayList<>();
}
