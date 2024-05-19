package com.Synapse.ProyekPBO.Controllers;

import com.Synapse.ProyekPBO.Models.HomeModels;
import com.Synapse.ProyekPBO.Models.KamarModels;
import com.Synapse.ProyekPBO.Repository.HomeRepository;
import com.Synapse.ProyekPBO.Repository.KamarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexCustomer {
    @Autowired
    private HomeRepository repoHome;

    @Autowired
    private KamarRepository repoKamar;

    @GetMapping({"","/"})
    public String ShowDashboard(Model model) {

        List<HomeModels> home = repoHome.findAll();
        model.addAttribute("homes", home);


        return "/Customer/index";
    }

    @GetMapping("/fasilitasC")
    public String ShowFasilitasCustomer(){
        return "/Customer/fasilitas";
    }

    @GetMapping("/sewaC")
    public String ShowSewaCustomer(Model model){
        List<KamarModels> kamar = repoKamar.findAll();
        model.addAttribute("kamars", kamar);

        return "/Customer/sewa";
    }
}
