package com.Synapse.ProyekPBO.Controllers;


import com.Synapse.ProyekPBO.Models.FasilitasModels;
import com.Synapse.ProyekPBO.Services.FasilitasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
        @GetMapping({"","/"})
        public String ShowFasilitas(Model model) {
            return "/Admin/photos-home";
        }

}
