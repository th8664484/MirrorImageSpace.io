package com.blog.controller.admin;

import com.blog.entity.Tag;
import com.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(){
        return "admin/tags";
    }

    @ResponseBody
    @GetMapping("/tagPage")
    public Map<String, Object> tagPage(@RequestParam int page,@RequestParam int limit){
        return tagService.pageTag(page, limit);
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "操作成功");
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","标签名称重复");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        int t = tagService.saveTag(tag);
        if (t == 0 ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Tag tag1 = tagService.getByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","标签名称重复");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        int t = tagService.updateTag(id,tag);
        if (t == 0 ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/tags";
    }
}
