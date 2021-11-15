package com.example.controller;

import com.example.domain.request.RoleDto;
import com.example.domain.request.UserDto;
import com.example.repository.schema.Role;
import com.example.repository.schema.User;
//import com.example.repository.specification.UserRegistrationService;
import com.example.repository.specification.UserRegistrationService;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;


@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    private ModelMapper mapper = new ModelMapper();
    private final UserRegistrationService userRegistrationService;

    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @ModelAttribute()
    public void init(Model model) {

        List<RoleDto> roleDto = userRegistrationService.roleDtoList();
        model.addAttribute("roleDto", roleDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
//        List<RoleDto> roleDto = userRegistrationService.roleDtoList();
//        model.addAttribute("roleDto", roleDto);
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String joinGroup(@ModelAttribute("userDto") UserDto user, @RequestParam(value = "file", required = false) MultipartFile file) {

        userRegistrationService.save(user, file);

//        String encryptedPwd = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encryptedPwd);

        return "redirect:/users/?success";
    }

    @GetMapping("/list")
//    @Secured("ROLE_ADMIN")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

//        String encryptedPwd = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encryptedPwd);

        return "redirect:/users/?success";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("userId") Long id){

        userRegistrationService.deleteUser(id);

        return "redirect:/users/list";
    }

    @GetMapping("/single-user")
    public String single_user(){
        return "singleUser";
    }




//    @GetMapping("/access/{userId}/{userRole}")
////    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String giveAccessToUser(@PathVariable long userId, @PathVariable String userRole, Principal principal) {
//
////        UserRegistration user = getLoggedInUser(principal);
//        User upDateUser = userRegistrationRepository.findById(userId).get();
//
//
//
////        if (user.getRoles().get(0).getName().contains("ROLE_ADMIN")) {
//        Role role = new Role();
//        role.setName(userRole);
//        List<Role> roles = new ArrayList<>();
////            roles.addAll(upDateUser.getRoles());
////            roles.add(role);
//        upDateUser.getRoles().add(role);
////            upDateUser.setRoles(roles);
////        }

//        userRegistrationRepository.save(upDateUser);
//        return "Hi " + upDateUser.getUserName() + " New Role assing to you by " + principal.getName();
//    }

//    @RequestMapping("/test")
////    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String testUser(){
//        System.out.println("from test");
//        return "Admin not yet confirmed your registration";
//    }
//
//    @GetMapping("/moderate")
////    @PreAuthorize("hasAuthority('ROLE_MODERATOR')")
//    public User testUser(Principal principal){
//        User user = getLoggedInUser(principal);
//        return userRegistrationRepository.findById(user.getId()).get();
//    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


//    @GetMapping("/loginck")
//    public String getUserInfo(Principal principal){
//        User user = getLoggedInUser(principal);
//        if (user.getRoles().contains("ROLE_ADMIN")){
//            return "redirect:/user/list";
//        }
//
//        if (user.getRoles().contains("ROLE_MODERATOR")){
//
//            return "redirect:/user/moderate";
//        }
//
//        if (user.getRoles().contains("ROLE_USER")){
//
//            return "redirect:/user/test";
//        }
//
//        return null;
//    }


//    private Set<Role> getLoggedInUserRoles(Principal principal) {
//
//        return getLoggedInUser(principal).getRoles();
//    }
//
//    private User getLoggedInUser(Principal principal) {
//
//        return userRegistrationRepository.findByUserName(principal.getName());
//    }

}
