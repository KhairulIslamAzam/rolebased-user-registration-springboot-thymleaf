package com.example.repository.specification;

import com.example.exception.FileException;
import com.example.exception.UserNotFoundException;
import com.example.repository.jpa.UserRegistrationRepository;
import com.example.repository.schema.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import com.example.domain.request.RoleDto;
import com.example.domain.request.UserDto;
import com.example.repository.jpa.RoleRepository;
import com.example.repository.schema.User;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRegistrationRepository userRegistrationRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper = new ModelMapper();

    public void save(UserDto userDto, MultipartFile file) {


        Role role = getRole("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);


        User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
                userDto.getUserName(),passwordEncoder.encode(userDto.getPassword()),
                userDto.getContact(),true, roles);

        if (file != null && !file.isEmpty()) {
            user.setProfileImageName(file.getOriginalFilename());
            imageByte(file, user);
        }

        getSave(user);

    }

    public List<UserDto> getAllUsers() {

        List<User> users = userRegistrationRepository.findAllActiveUsers();
        return users.stream().map(this::mapUserToDto).collect(Collectors.toList());
    }

    public UserDto getUser(long id) {

        User user = this.getUserById(id);
        return modelMapper.map(user, UserDto.class);
    }

    public void update(UserDto userDto, MultipartFile file) {

        User dbUser = this.getUserById(userDto.getId());
        Set<Role> roles = new HashSet<>(dbUser.getRoles());

        if(userDto.getRoleName() != null){

            Role role = getRole(userDto.getRoleName());
            roles.add(role);
        }

        User user = new User(userDto.getFirstName(), userDto.getLastName(),dbUser.getEmail(),
                dbUser.getUserName(),dbUser.getPassword(),userDto.getContact(),
                true, roles);

        user.setId(dbUser.getId());

        if (file != null && !file.isEmpty()) {

            user.setProfileImageName(file.getOriginalFilename());
            imageByte(file, user);

        } else {

            user.setProfileImageName(dbUser.getProfileImageName());
            user.setImageContent(dbUser.getImageContent());
        }
        getSave(user);
    }

    private void getSave(User user) {
        try {
            userRegistrationRepository.save(user);
        }catch (Exception e){
            throw new UserNotFoundException("failed to save or update "+e.getMessage());
        }
    }

    public void deleteUser(long id) {

        try {
            userRegistrationRepository.update(false,id);
        }catch (Exception e){
            throw new UserNotFoundException(id +" not found");
        }
    }

    public List<RoleDto> roleDtoList() {

        List<Role> roles = this.roles();
        return roles.stream().map(this::mapRoleToDto).collect(Collectors.toList());
    }

    private List<Role> roles() {
        return roleRepository.findAll();
    }

    private User getUserById(long id) {

        User user = userRegistrationRepository.findById(id).get();

        if(user == null){
            throw new UserNotFoundException("user not found");
        }

        return user;
    }

    private void imageByte(MultipartFile file, User user) {
        try {
            user.setImageContent(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new FileException("problem in image upload"+e.getMessage());

        }
    }

    private Role getRole(String userRole) {

        return roleRepository.findByName(userRole);
    }

    private UserDto mapUserToDto(User user) {

        return modelMapper.map(user, UserDto.class);
    }

    private RoleDto mapRoleToDto(Role role) {

        return modelMapper.map(role, RoleDto.class);
    }

}
