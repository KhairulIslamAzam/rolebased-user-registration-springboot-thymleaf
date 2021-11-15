package com.example.repository.schema;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = SchemaConstant.ROLE_TABLE_NAME)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ROLE_ID_GEN")
    @SequenceGenerator(
            name = "ROLE_ID_GEN",
            allocationSize = 1,
            sequenceName = SchemaConstant.ROLE_SEQUENCE_NAME)
    private long id;

    private String name;
//
//    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
//    private List<User> users;
}
