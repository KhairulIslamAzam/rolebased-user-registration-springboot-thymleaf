package com.example.controller;

import com.example.domain.request.UserDto;
import com.example.repository.schema.User;
import com.example.repository.specification.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRegistrationService userRegistrationService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, trimmerEditor);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/registration")
    public String showRegistrationForm(Model model) {

        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String joinGroup(@Validated(User.class) @ModelAttribute("userDto") UserDto user,BindingResult result, @RequestParam(value = "file", required = false) MultipartFile file) {

        if (result.hasErrors()) {
            return "registration";
        }
        userRegistrationService.save(user, file);
        return "redirect:/registration?success";
    }
}
