package com.blog.controller.admin;

import com.blog.entity.Tag;
import com.blog.entity.Type;
import com.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(){
        return "admin/types";
    }
    @ResponseBody
    @GetMapping("/typePage")
    public Map<String, Object> typePage(@RequestParam int page,@RequestParam int limit){
        return typeService.pageType(page, limit);
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "操作成功");
        return "redirect:/admin/types";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type type1 = typeService.getByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","分类名称重复");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        int t = typeService.saveType(type);

        if (t == 0 ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeService.getByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","分类名称重复");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        int t = typeService.updateType(id,type);
        if (t == 0 ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }

}
