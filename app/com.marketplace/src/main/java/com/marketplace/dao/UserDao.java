package com.marketplace.dao;

import java.util.List;

import com.marketplace.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import com.marketplace.POJO.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends JpaRepository<User, Integer> {
    User findByAccountEmail(@Param("email") String email);

    List<UserWrapper> getAllAdmin();

    @Transactional
    @Modifying
    Integer updateUserStatus(@Param("status") String status,@Param("id") Integer id);

}
