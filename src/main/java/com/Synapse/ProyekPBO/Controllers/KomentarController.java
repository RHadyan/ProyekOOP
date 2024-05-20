package com.Synapse.ProyekPBO.Controllers;


import com.Synapse.ProyekPBO.Models.HomeModels;
import com.Synapse.ProyekPBO.Models.KamarModels;
import com.Synapse.ProyekPBO.Models.KomentarDto;
import com.Synapse.ProyekPBO.Models.KomentarModels;
import com.Synapse.ProyekPBO.Repository.KomentarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/komentar")
public class KomentarController {

    @Autowired
    private KomentarRepository repoKomentar;
    @GetMapping({"","/"})
    public String ShowKomentar(Model model) {

        List<KomentarModels> komentar = repoKomentar.findAll();
        model.addAttribute("komentars", komentar);

        KomentarDto komentarDto = new KomentarDto();
        model.addAttribute("komentarDto", komentarDto);

        return "/Admin/komentar";
    }

    @GetMapping("edit/{id}")
    @ResponseBody
    public ResponseEntity<KomentarModels> getHomeById(@PathVariable int id) {
        KomentarModels komentar = repoKomentar.findById(id).orElseThrow(() -> new RuntimeException("Fasilitas not found"));
        return ResponseEntity.ok(komentar);
    }




    @PostMapping("/update")
    public String updateFasilitas(
            @RequestParam int id,
            @RequestParam String statusTampil){
        try {
            KomentarModels komentar = repoKomentar.findById(id).orElseThrow(() -> new RuntimeException("Fasilitas not found"));
            // Update status
            komentar.setStatusTampil(statusTampil);
            repoKomentar.save(komentar);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/kamar";
    }


}
