package com.example.repository.jpa;

import com.example.repository.schema.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRegistrationRepository extends
        JpaRepository<User, Long> {

    @Query("SELECT u from User u Where u.userName = :userName")
    User getUserByUserName(@Param("userName") String userName);

    @Query("SELECT u FROM User u WHERE u.active = true")
    List<User> findAllActiveUsers();

    @Modifying
    @Transactional
    @Query("UPDATE User SET active = :active WHERE id = :id")
    void update(@Param("active") boolean active, @Param("id") long id);

}
