package com.Synapse.ProyekPBO.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Admin")
public class DashboardController {

    @GetMapping({"","/"})
    public String ShowDashboard(Model model) {
        return "/Admin/index";
    }

}
