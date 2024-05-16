package com.Synapse.ProyekPBO.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/kamar")
public class KamarController {
    @GetMapping({"","/"})
    public String ShowKamar(Model model) {
        return "/Admin/kamar";
    }

}