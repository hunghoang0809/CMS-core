package com.example.BaseCMS.module.user.repo;

import com.example.BaseCMS.module.user.dto.UserDto;
import com.example.BaseCMS.module.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long>  {
    @Query("SELECT new com.example.BaseCMS.module.user.dto.UserDto(u.id, u.username, u.password) " +
            "FROM User u WHERE u.username = :username")
    UserDto findByUsername(@Param("username") String username);;

}
