package com.example.repository.specification;
//

import com.example.repository.jpa.UserRegistrationRepository;
import com.example.repository.schema.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.example.domain.request.RoleDto;
import com.example.domain.request.UserDto;
import com.example.repository.jpa.RoleRepository;
import com.example.repository.jpa.UserRegistrationRepository;
import com.example.repository.schema.Role;
import com.example.repository.schema.SchemaConstant;
import com.example.repository.schema.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserRegistrationService {


    @Autowired
    private UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private ModelMapper modelMapper = new ModelMapper();

    public boolean save(UserDto userDto, MultipartFile file) {
        User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
                userDto.getUserName(), userDto.getContact());

        List<Role> roleList = roleRepository.findAll();

//        int index = 0;
//        for (int i = 0; i < roleList.size(); i++) {
//            if (roleList.equalsIgnoreCase("ROLE_USER")) {
//                index = i;
//            }
//        }

//        int index = IntStream.range(0, roleList.size())
//                .filter(i -> roleList.get(i).getName().equals("ROLE_USER"))
//                .findFirst().orElse(-1);



        Role role = roleRepository.findByName("ROLE_USER");

        user.setPassword(userDto.getPassword());
        user.setActive(userDto.isActive());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        Role role = new Role();
//        roles.add(role.setName(SchemaConstant.DEFAULT_ROLE));

        user.setRoles((Set<Role>) role);
        user.setActive(true);
        if (file != null && !file.isEmpty()) {
            user.setProfileImageName(file.getOriginalFilename());
            try {
                user.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        userRegistrationRepository.save(user);
        return true;

    }

    public List<UserDto> getAllUsers() {

        List<User> users = userRegistrationRepository.findAll();
        return users.stream().map(this::mapUserToDto).collect(Collectors.toList());
    }

    public UserDto getUser(long id) {
        User user = userRegistrationRepository.findById(id).get();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public boolean update(UserDto userDto, MultipartFile file) {

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setContact(userDto.getContact());
//        Set<Role> roles = userDto.getRoles().stream().map(this::mapDtoToRole).collect(Collectors.toSet());
//        user.setRoles(roles);
//        Role role = new Role();
//        roles.add(role.setName(roles.forEach((item) -> {
//            item.getName();
//             return item;
//        });
        user.setActive(true);


        if (file != null && !file.isEmpty()) {
            user.setProfileImageName(file.getOriginalFilename());
            try {
                user.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
                userRegistrationRepository.update(user.getFirstName(), user.getLastName(), user.getContact(),
                        user.getProfileImageName(), user.getImage(), user.getRoles(), userDto.getId());

            } catch (IOException e) {
                e.printStackTrace();

            }
        } else {

            userRegistrationRepository.update(user.getFirstName(), user.getLastName(), user.getContact(),
                    user.getRoles(), userDto.getId());
        }
        return true;
    }

    public boolean deleteUser(long id) {

        userRegistrationRepository.deleteById(id);
        return true;
    }

    public List<RoleDto> roleDtoList() {

        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(this::mapRoleToDto).collect(Collectors.toList());
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userRegistrationRepository.findByUserName(username);
//        if(user == null) {
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUserName(),
//                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
//    }
//
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
//        return roles.stream().map(role ->
//                new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }

//    private Optional<org.springframework.security.core.userdetails.User> getCurrentUser(){
//        org.springframework.security.core.userdetails.User principle = (org.springframework.security.core.userdetails.User)
//                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return Optional.of(principle);
//    }


    private UserDto mapUserToDto(User user) {

        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    private RoleDto mapRoleToDto(Role role) {

        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        return roleDto;
    }


    private Role mapDtoToRole(RoleDto roleDto) {

        Role role = modelMapper.map(roleDto, Role.class);
        return role;
    }

//    private String getPassword(String password){
//
//        return passwordEncoder.encode(password);
//    }
}
