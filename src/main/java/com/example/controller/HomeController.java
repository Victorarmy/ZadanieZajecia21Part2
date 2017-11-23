package com.example.controller;

import com.example.model.Option;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {


    @ModelAttribute("options")
    public List<Option> optionList() {
        return Arrays.asList(Option.values());
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

}
