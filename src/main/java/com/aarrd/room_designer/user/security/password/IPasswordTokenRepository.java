package com.aarrd.room_designer.user.security.password;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPasswordTokenRepository extends JpaRepository<PasswordToken, Long>
{
    @Query("FROM PasswordToken WHERE user_id = ?1")
    PasswordToken findByUserId(Long userId);
}
