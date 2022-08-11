package com.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class BlogListVo {
    private String id;
    private String title;
    private Date updateTime;
    private boolean published;
    private boolean recommend;
    private Long typeId;
    private String typeName;

}
