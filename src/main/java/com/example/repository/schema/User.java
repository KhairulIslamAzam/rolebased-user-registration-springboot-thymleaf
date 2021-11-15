package com.example.repository.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = SchemaConstant.USER_TABLE_NAME)
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ACCOUNT_ID_GEN")
    @SequenceGenerator(
            name = "ACCOUNT_ID_GEN",
            allocationSize = 1,
            sequenceName = SchemaConstant.USER_SEQUENCE_NAME)
    private long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "CONTACT", nullable = false, length = 11, unique = true)
    private String contact;

    @Column(name = "IMAGE_NAME")
    private String profileImageName;

    @Lob
    @Column(name = "MEDIUMBLOB")
    private String image;

    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = SchemaConstant.USER_ROLE_TABLE_NAME,
            joinColumns = @JoinColumn(
                    name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE_ID", referencedColumnName = "ID")
    )
    private Set<Role> roles = new HashSet<>();


    public User(String firstName, String lastName, String email, String userName, String contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.contact = contact;
    }
}
