package com.sunhp.activiti.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sunhp.activiti.entity.User;

/**
 * @author sunhp
 * @Description
 * @Date 2022/11/28 12:43
 **/
@Controller
@RequestMapping
public class TestLayUiController {

    @GetMapping("/testLayUi")
    public String testLayUi(Model model){
        model.addAttribute("name", "123456");
        model.addAttribute("jsName", "js123456");
        Map<Object, Object> map = new HashMap<>(8);
        map.put("lay1", "111");
        map.put("lay2", "222");
        model.addAttribute("maps", map);
        User user = new User();
        user.setUsername("sunho");
        user.setRealname("真实名字");
        model.addAttribute("user", user);
        return "test";
    }

    @GetMapping("/testLayUiObject")
    public ModelAndView testLayUiObject(ModelAndView model){
        model.addObject("name", "123456");
        model.addObject("jsName", "js123456");
        Map<Object, Object> map = new HashMap<>(8);
        map.put("lay1", "111");
        map.put("lay2", "222");
        model.addObject("maps", map);
        User user = new User();
        user.setUsername("sunho");
        user.setRealname("真实名字");
        model.addObject("user", user);

        model.setViewName("test");
        return model;
    }
}
