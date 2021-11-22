package com.project.invitation.repository;

import com.project.invitation.constant.UserRole;
import com.project.invitation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(value = "select u from User u where u.role = :role")
    List<User> findUser(UserRole role);

    @Modifying
    @Query(value = "update User u set u.password = :password where u.email = :email")
    int updateUser(String password, String email);

    @Modifying
    @Query(value = "delete from User u where u.email = :email")
    int deleteUser(String email);
}