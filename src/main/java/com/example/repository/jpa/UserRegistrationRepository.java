package com.example.repository.jpa;

import com.example.repository.schema.Role;
import com.example.repository.schema.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface UserRegistrationRepository extends
        JpaRepository<User, Long> {

    User findByUserName(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User SET firstName = :firstname, lastName = :lastname, contact = :contact, roles = :roles WHERE id = :id")
    public void update(@Param("firstname") String firstname, @Param("lastname") String lastname, @Param("contact") String contact, @Param("roles") Set<Role> roles, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE User SET firstName = :firstname, lastName = :lastname, contact = :contact, profileImageName = :profileImageName, image = :image , roles = :roles WHERE id = :id")
    public void update(@Param("firstname") String firstname, @Param("lastname") String lastname, @Param("contact") String contact,@Param("profileImageName") String profileImageName, @Param("image") String image, @Param("roles") Set<Role> roles, @Param("id") Long id);
}
