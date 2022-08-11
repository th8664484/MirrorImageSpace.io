package com.blog.controller.admin;

import com.blog.entity.Blog;
import com.blog.entity.User;
import com.blog.service.BlogService;
import com.blog.service.TagService;
import com.blog.service.TypeService;
import com.blog.vo.BlogSevaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private final String RETURN = "admin/blogs";
    private final String RETURN_INPUT = "admin/blogs-input";
    private final String RETURN_REDIRECT = "redirect:/admin/blogs";
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(Model model){
        model.addAttribute("types",typeService.listType());
        return RETURN;
    }

    @ResponseBody
    @GetMapping("/blogsPage")
    public Map<String, Object> blogPage(@RequestParam int page, @RequestParam int limit,
                                        String title, String typeId, String recommend){
        Map<String,Object> map = new HashMap<>();
        map.put("title","".equals(title)? null:title);
        map.put("typeId","".equals(typeId)? null:typeId);
        map.put("recommend", recommend);
        return blogService.pageBlog(page, limit,map);
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new BlogSevaVo());
        return RETURN_INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog,RedirectAttributes attributes , HttpSession session){
        int t;
        if(blog.getId() == null){
            User user = (User) session.getAttribute("user");
            blog.setUserId(user.getId());
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            t = blogService.saveBlog(blog);
        }else {
            blog.setUpdateTime(new Date());
            t = blogService.updateBlog(blog.getId(),blog);
        }

        if (t == 0 ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return RETURN_REDIRECT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog", blogService.getBlog(id));
        return RETURN_INPUT;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.delete(id);
        attributes.addFlashAttribute("message", "操作成功");
        return RETURN_REDIRECT;
    }
}
