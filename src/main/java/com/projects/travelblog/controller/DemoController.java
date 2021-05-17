package com.projects.travelblog.controller;

import com.projects.travelblog.service.Demo.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class DemoController {

    private final DemoService demoService;

    @Autowired
    public DemoController(DemoService demoService)
    {
        this.demoService = demoService;
    }

    @GetMapping("Demo")
    public String demo(Model model)
    {
        log.info("demo method called");
        model.addAttribute("message", demoService.getMessage());
        model.addAttribute("LOGIN_ERROR", demoService.getERROR());
        return "Demo/demo";
    }
}
