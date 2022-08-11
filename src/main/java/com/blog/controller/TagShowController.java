package com.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Tag;
import com.blog.service.BlogService;
import com.blog.service.TagService;
import com.blog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * Created by limi on 2017/10/23.
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable Long id , Page page, Model model) {
        List<TagVo> tags = tagService.listTop(100);
        if (id == -1) {
            id = Long.valueOf(tags.get(0).getId());
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlogByTagId(page,id));
        model.addAttribute("activeTagId", id.toString());
        return "tags";
    }

}
