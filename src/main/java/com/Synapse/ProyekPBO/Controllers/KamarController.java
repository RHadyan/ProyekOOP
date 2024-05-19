package com.Synapse.ProyekPBO.Controllers;

import com.Synapse.ProyekPBO.Models.KamarDto;
import com.Synapse.ProyekPBO.Models.KamarModels;
import com.Synapse.ProyekPBO.Repository.KamarRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
@RequestMapping("/kamar")
public class KamarController {


    @Autowired
    private KamarRepository repo;
    @GetMapping({"","/"})
    public String ShowKamar(Model model) {

        List<KamarModels> kamar = repo.findAll();
        model.addAttribute("kamars", kamar);

        KamarDto kamarDto = new KamarDto();
        model.addAttribute("kamarDto", kamarDto);

        return "/Admin/kamar";

    }
    @PostMapping({"","/"})
    public String CreateKamar(
            @Valid @ModelAttribute KamarDto kamarDto,
            BindingResult result){

        if (kamarDto.getImageFile().isEmpty()){
            result.addError(new FieldError("kamarDto","imageFile","the image file is missing"));
        }
        if (result.hasErrors()){
            return  "redirect:/kamar";
        }
//        add image
        MultipartFile image = kamarDto.getImageFile();
        Date createAt = new Date();
        String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();
        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream = image.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);

            }

        }catch (Exception ex){
            System.out.println("Exception" + ex.getMessage());
        }

        KamarModels kamar = new KamarModels();
        kamar.setNamaKamar(kamarDto.getNamaKamar());
        kamar.setStatus(kamarDto.getStatus());
        kamar.setImageFileName(storageFileName);
        kamar.setCreateAt(createAt);


        repo.save(kamar);

        return "redirect:/kamar";
    }
    @GetMapping("edit/{id}")
    @ResponseBody
    public ResponseEntity<KamarModels> getKamarById(@PathVariable int id) {
        KamarModels kamar = repo.findById(id).orElseThrow(() -> new RuntimeException("Fasilitas not found"));
        return ResponseEntity.ok(kamar);
    }
    @PostMapping("/update")
    public String updateFasilitas(
            @RequestParam int id,
            @RequestParam String nameKamar,
            @RequestParam String status,
            @RequestParam("imageFileName") MultipartFile imageFileName){
        System.out.println("ID: " + id);
        System.out.println("Name: " + nameKamar);
        System.out.println("Image File: " + imageFileName.getOriginalFilename());
        try {
            KamarModels kamar = repo.findById(id).orElseThrow(() -> new RuntimeException("Fasilitas not found"));

            // Update nama fasilitas
            kamar.setNamaKamar(nameKamar);
            kamar.setStatus(status);


            // Jika ada file gambar baru, simpan file baru dan update nama file
            if (!imageFileName.isEmpty()) {
                String uploadDir = "public/images/";
                String storageFileName = new Date().getTime() + "_" + imageFileName.getOriginalFilename();
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try (InputStream inputStream = imageFileName.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                    // Hapus file lama
                    Path oldImagePath = Paths.get(uploadDir + kamar.getImageFileName());
                    Files.deleteIfExists(oldImagePath);
                    // Set nama file baru
                    kamar.setImageFileName(storageFileName);
                }
            }

            repo.save(kamar);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/kamar";
    }

    @GetMapping("delete/{id}")
    public String deleteProduct(@PathVariable int id){

        try {
            KamarModels kamar = repo.findById(id).get();

            Path imagePath = Paths.get("public/images/" + kamar.getImageFileName());

            try {
                Files.delete(imagePath);
            }catch (Exception ex){
                System.out.println("Exception" + ex.getMessage());
            }

            repo.delete(kamar);

        }
        catch (Exception ex){
            System.out.println("Exception" + ex.getMessage());


        }
        return "redirect:/kamar";
    }

}