package com.Synapse.ProyekPBO.Controllers;


import com.Synapse.ProyekPBO.Models.RegistrationDto;
import com.Synapse.ProyekPBO.Models.UserEntity;
import com.Synapse.ProyekPBO.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/register")
    public String getRegisterForm(Model model){
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user",user);
        return"/Admin/register";
    }
    @GetMapping("/login")
    public String getLoginForm(Model model){
        return"/Admin/Login";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user")RegistrationDto user,
            BindingResult result, Model model){
        UserEntity existingUsersEmail = userService.findByEmail(user.getEmail());
        if (existingUsersEmail != null && existingUsersEmail.getEmail() != null && !existingUsersEmail.getEmail().isEmpty()){
            return "redirect:/register?fail";
        }
        UserEntity existingUserUsername = userService.findByUsername(user.getUsername());
        if (existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()){
            return "redirect:/register?fail";
        }
        if (result.hasErrors()){
            model.addAttribute("user",user);
            return "redirect:/register";
        }
        userService.saveUser(user);
        return "redirect:/login?success";
    }


    @GetMapping("/403")
    public String accessDenied() {
        return "redirect:/login?usernotfound";
    }

}
