package com.Synapse.ProyekPBO.Controllers;


import com.Synapse.ProyekPBO.Models.FasilitasDto;
import com.Synapse.ProyekPBO.Models.FasilitasModels;
import com.Synapse.ProyekPBO.Models.HomeDto;
import com.Synapse.ProyekPBO.Models.HomeModels;
import com.Synapse.ProyekPBO.Services.FasilitasRepository;
import com.Synapse.ProyekPBO.Services.HomeRepository;
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
@RequestMapping("/home")
public class HomeController {
        @Autowired
        private HomeRepository repo;

        @GetMapping({"","/"})
        public String ShowHome(Model model) {
            List<HomeModels> home = repo.findAll();
            model.addAttribute("homes", home);

            HomeDto homeDto = new HomeDto();
            model.addAttribute("homeDto", homeDto);

            return "/Admin/photos-home";

        }
        @PostMapping({"","/"})
        public String CreateHome(
                @Valid @ModelAttribute HomeDto homeDto,
                BindingResult result){

            if (homeDto.getImageFile().isEmpty()){
                result.addError(new FieldError("homeDto","imageFile","the image file is missing"));
            }
            if (result.hasErrors()){
                return  "redirect:/home";
            }
    //        add image
            MultipartFile image = homeDto.getImageFile();
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

            HomeModels home = new HomeModels();
            home.setImageFileName(storageFileName);
            home.setCreateAt(createAt);

            repo.save(home);

            return "redirect:/home";
        }


        @GetMapping("edit/{id}")
        @ResponseBody
        public ResponseEntity<HomeModels> getHomeById(@PathVariable int id) {
            HomeModels home = repo.findById(id).orElseThrow(() -> new RuntimeException("Fasilitas not found"));
            return ResponseEntity.ok(home);
        }


        @GetMapping("delete/{id}")
        public String deleteProduct(@PathVariable int id){

            try {
                HomeModels home = repo.findById(id).get();

                Path imagePath = Paths.get("public/images/" + home.getImageFileName());

                try {
                    Files.delete(imagePath);
                }catch (Exception ex){
                    System.out.println("Exception" + ex.getMessage());
                }

                repo.delete(home);

            }
            catch (Exception ex){
                System.out.println("Exception" + ex.getMessage());


            }
            return "redirect:/home";
        }

        @PostMapping("/update")
        public String updateFasilitas(
                @RequestParam int id,
                @RequestParam("imageFileName") MultipartFile imageFileName) {
            System.out.println("ID: " + id);
            System.out.println("Image File: " + imageFileName.getOriginalFilename());
            try {
                HomeModels home = repo.findById(id).orElseThrow(() -> new RuntimeException("Fasilitas not found"));

                // Update nama fasilitas

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
                        Path oldImagePath = Paths.get(uploadDir + home.getImageFileName());
                        Files.deleteIfExists(oldImagePath);
                        // Set nama file baru
                        home.setImageFileName(storageFileName);
                    }
                }

                repo.save(home);

            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
            return "redirect:/home";
        }


}
