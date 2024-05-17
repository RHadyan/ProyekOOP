package com.Synapse.ProyekPBO.Controllers;
import com.Synapse.ProyekPBO.Models.FasilitasDto;
import com.Synapse.ProyekPBO.Models.FasilitasModels;
import com.Synapse.ProyekPBO.Services.FasilitasRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/fasilitas")
public class FasilitasController {

    @Autowired
    private FasilitasRepository repo;

    @GetMapping({"","/"})
    public String ShowFasilitas(Model model){
        List<FasilitasModels> fasilitas = repo.findAll();
        model.addAttribute("fasilitass", fasilitas);

        FasilitasDto fasilitasDto = new FasilitasDto();
        model.addAttribute("fasilitasDto", fasilitasDto);

        return "/Admin/photos-fasilitas";
    }
//add data
@PostMapping({"","/"})
public String CreateFasilitas(
        @Valid @ModelAttribute FasilitasDto fasilitasDto,
        BindingResult result){

    if (fasilitasDto.getImageFile().isEmpty()){
        result.addError(new FieldError("fasilitasDto","imageFile","the image file is missing"));
    }
    if (result.hasErrors()){
        return  "redirect:/fasilitas";
    }
//        add image
    MultipartFile image = fasilitasDto.getImageFile();
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

    FasilitasModels fasilitas = new FasilitasModels();
    fasilitas.setNameFasilitas(fasilitasDto.getNameFasilitas());
    fasilitas.setImageFileName(storageFileName);
    fasilitas.setCreateAt(createAt);

    repo.save(fasilitas);

    return "redirect:/fasilitas";
}

    @GetMapping("delete/{id}")
    public String deleteProduct(@PathVariable int id){

        try {
            FasilitasModels fasilitas = repo.findById(id).get();

            Path imagePath = Paths.get("public/images/" + fasilitas.getImageFileName());

            try {
                Files.delete(imagePath);
            }catch (Exception ex){
                System.out.println("Exception" + ex.getMessage());
            }

            repo.delete(fasilitas);

        }
        catch (Exception ex){
            System.out.println("Exception" + ex.getMessage());


        }
        return "redirect:/fasilitas";
    }


    @GetMapping("edit/{id}")
    @ResponseBody
    public ResponseEntity<FasilitasModels> getFasilitasById(@PathVariable int id) {
        FasilitasModels fasilitas = repo.findById(id).orElseThrow(() -> new RuntimeException("Fasilitas not found"));
        return ResponseEntity.ok(fasilitas);
    }


    @PostMapping("/update")
    public String updateFasilitas(
            @RequestParam int id,
            @RequestParam String nameFasilitas,
            @RequestParam("imageFileName") MultipartFile imageFileName) {
        System.out.println("ID: " + id);
        System.out.println("Name: " + nameFasilitas);
        System.out.println("Image File: " + imageFileName.getOriginalFilename());
        try {
            FasilitasModels fasilitas = repo.findById(id).orElseThrow(() -> new RuntimeException("Fasilitas not found"));

            // Update nama fasilitas
            fasilitas.setNameFasilitas(nameFasilitas);

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
                    Path oldImagePath = Paths.get(uploadDir + fasilitas.getImageFileName());
                    Files.deleteIfExists(oldImagePath);
                    // Set nama file baru
                    fasilitas.setImageFileName(storageFileName);
                }
            }

            repo.save(fasilitas);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/fasilitas";
    }


//    @PostMapping("/edit")
//    public String updateProduct(
//            Model model,
//            @RequestParam int id,
//            @Valid @ModelAttribute FasilitasDto fasilitasDto,
//            BindingResult result
//    ){
//        try {
//            FasilitasModels fasilitas = repo.findById(id).get();
//            model.addAttribute("fasilitass",fasilitas);
//
//            if (result.hasErrors()){
//                return "Products/EditProduct";
//            }
//
//            if (!fasilitasDto.getImageFile().isEmpty()){
////                delete image lama
//                String uploadDir = "public/images/";
//                Path oldImagePath = Paths.get(uploadDir + fasilitas.getImageFileName());
//
//
//                try {
//                    Files.delete(oldImagePath);
//                }
//                catch (Exception ex){
//                    System.out.println("Exception" + ex.getMessage());
//                }
////                save gambar baru
//                MultipartFile image = fasilitasDto.getImageFile();
//                Date createAt = new Date();
//
//                String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();
//
//                try (InputStream inputStream = image.getInputStream()){
//                    Files.copy(inputStream,  Paths.get(uploadDir+storageFileName),
//                            StandardCopyOption.REPLACE_EXISTING);
//                }
//                fasilitas.setImageFileName(storageFileName);
//            }
//
//            fasilitas.setNameFasilitas(fasilitasDto.getNameFasilitas());
//
//            repo.save(fasilitas);
//        }
//        catch (Exception ex){
//            System.out.println("Exception" + ex.getMessage());
//
//        }
//        return "redirect:/fasilitas";
//    }


}
