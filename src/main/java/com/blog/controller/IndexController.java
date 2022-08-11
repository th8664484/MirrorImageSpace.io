package com.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.service.BlogService;
import com.blog.service.TagService;
import com.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class IndexController {

    private Integer typeLimit = 6;
    private Integer tagLimit = 10;
    private Integer blogLimit = 5;

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeServices;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(Page page,Model model){
        model.addAttribute("page",blogService.listBlog(page));
        model.addAttribute("types",typeServices.listTypeTop(typeLimit));
        model.addAttribute("tags",tagService.listTop(tagLimit));
        model.addAttribute("blogs",blogService.listRecommendBlogTop(blogLimit));
        return "index";
    }

    @PostMapping("/search")
    public String search(String query,Page page,Model model){
        model.addAttribute("page",blogService.listBlog(query,page));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/about")
    public String about(){
        return "my";
    }


}
