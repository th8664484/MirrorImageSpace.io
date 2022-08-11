package com.blog.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.blog.entity.Tag;
import com.blog.entity.Type;
import com.blog.entity.User;
import com.blog.utils.MarkdownUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogSevaVo {
    private String id;
    /**标题*/
    private String title;

    /**内容*/
    private String content;
    /**首图*/
    private String firstPicture;
    private String description;
    /**标记*/
    private String flag;
    /**浏览次数*/
    private Integer views;
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

    private String typeId;

    private String tagsId;

    private Type type;

    private User user;

    private List<Tag> tags;

    public void info(){
        if (tags.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            for(Tag t: this.tags){
                buffer.append(t.getId()+",");
            }
            String tags = buffer.toString();
            this.tagsId=tags.substring(0,tags.length()-1);
        }
    }

    public void toHtml(){
        String content = MarkdownUtil.markdownToHtmlExtensions(this.content);

        this.content = content;
    }
}
