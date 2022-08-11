package com.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Type;
import com.blog.service.BlogService;
import com.blog.service.TypeService;
import com.blog.vo.TypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by limi on 2017/10/23.
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id , Page page, Model model) {
        List<TypeVo> types = typeService.listTypeTop(100);
        if (id == -1) {
           id = Long.valueOf(types.get(0).getId());
        }
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(page,id));
        model.addAttribute("activeTypeId", id.toString());
        return "types";
    }
}
