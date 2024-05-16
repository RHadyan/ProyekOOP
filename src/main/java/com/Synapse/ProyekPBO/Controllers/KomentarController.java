package com.Synapse.ProyekPBO.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/komentar")
public class KomentarController {


    @GetMapping({"","/"})
    public String ShowKomentar(Model model) {
        return "/Admin/komentar";
    }

}
