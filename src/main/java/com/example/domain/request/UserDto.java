package com.example.domain.request;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String contact;
    private String profileImageName;
    private String image;
    private boolean active;
    private long roleId;
    private Set<RoleDto> roles;

//    private long roleId;
}
