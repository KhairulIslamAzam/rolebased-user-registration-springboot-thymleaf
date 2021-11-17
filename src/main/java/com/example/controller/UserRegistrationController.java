package com.example.controller;

import com.example.domain.request.RoleDto;
import com.example.domain.request.UserDto;
import com.example.repository.schema.User;
import com.example.repository.specification.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.example.utils.LogInUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import java.util.*;


@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;
    private final LogInUser logInUser;

    @ModelAttribute()
    public void init(Model model) {

        List<RoleDto> roleDto = userRegistrationService.roleDtoList();
        model.addAttribute("roleDto", roleDto);
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/list")
    public String loadUsers(Model model) {
        List<UserDto> users = userRegistrationService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/update")
    public String updateUserForm(@RequestParam("userId") long id, Model model) {
        UserDto userDto = userRegistrationService.getUser(id);
        model.addAttribute("userDto", userDto);
        return "admin_update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("userDto") UserDto user, @RequestParam(value = "file", required = false) MultipartFile file) {

        userRegistrationService.update(user, file);
        return "redirect:/users/list?update";
    }

    @GetMapping("/show-single-user-data")
    public String showSingleUserData(Model model) {

        User user = logInUser.userInfo();
        UserDto userDto = userRegistrationService.getUser(user.getId());
        model.addAttribute("userDto", userDto);
        return "singleUser";
    }

    @GetMapping("/update-single-user")
    public String showSingleUserForm(@RequestParam("userId") long id, Model model) {

        UserDto userDto = userRegistrationService.getUser(id);
        model.addAttribute("userDto", userDto);
        return "single_user_update";
    }


    @PostMapping("/update-single-user")
    public String updateSingleUserForm(@ModelAttribute("userDto") UserDto user, @RequestParam(value = "file", required = false) MultipartFile file) {

        userRegistrationService.update(user, file);
        return "redirect:/users/show-single-user-data?update";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("userId") Long id){

        userRegistrationService.deleteUser(id);
        return "redirect:/users/list?delete";
    }

    @GetMapping("/403")
    public String error(){
        return "403";
    }

}
