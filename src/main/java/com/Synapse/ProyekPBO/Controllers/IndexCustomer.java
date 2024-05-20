package com.Synapse.ProyekPBO.Controllers;

import com.Synapse.ProyekPBO.Models.*;
import com.Synapse.ProyekPBO.Repository.FasilitasRepository;
import com.Synapse.ProyekPBO.Repository.HomeRepository;
import com.Synapse.ProyekPBO.Repository.KamarRepository;
import com.Synapse.ProyekPBO.Repository.KomentarRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexCustomer {
    @Autowired
    private HomeRepository repoHome;

    @Autowired
    private KamarRepository repoKamar;

    @Autowired
    private FasilitasRepository repoFasilitas;

    @Autowired
    private KomentarRepository repoKomentar;


    @GetMapping({"","/"})
    public String ShowDashboard(Model model) {

        List<HomeModels> home = repoHome.findAll();
        model.addAttribute("homes", home);

        List<KomentarModels> komentar = repoKomentar.findAll();
        model.addAttribute("komentars", komentar);




        return "/Customer/index";
    }

    @GetMapping("/fasilitasC")
    public String ShowFasilitasCustomer(Model model){
        List<FasilitasModels> fasilitas = repoFasilitas.findAll();
        model.addAttribute("fasilitass", fasilitas);

        return "/Customer/fasilitas";
    }

    @GetMapping("/sewaC")
    public String ShowSewaCustomer( Model model){
        List<KamarModels> kamar = repoKamar.findAll();
        model.addAttribute("kamars", kamar);
//        model.addAttribute("username", userDetails.getUsername());

        KomentarDto komentarDto = new KomentarDto();
        model.addAttribute("komentarDto", komentarDto);

        return "/Customer/sewa";
    }

    @PostMapping("/seweC/Komen")
    public String createKomen(@Valid @ModelAttribute KomentarDto komentarDto,
                              BindingResult result){

        String statusTampil = "False";

        KomentarModels komentar = new KomentarModels();
        komentar.setUsername(komentarDto.getUsername());
        komentar.setKomentar(komentarDto.getKomentar());
        komentar.setRating(komentarDto.getRating());
        komentar.setStatusTampil(statusTampil);


        repoKomentar.save(komentar);

        return "redirect:/sewaC";
    }
}
