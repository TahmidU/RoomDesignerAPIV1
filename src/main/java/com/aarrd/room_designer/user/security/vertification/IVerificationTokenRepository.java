package com.aarrd.room_designer.user.security.vertification;

import com.aarrd.room_designer.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IVerificationTokenRepository extends JpaRepository<VerificationToken, Long>
{
    VerificationToken findByToken(int token);

    @Query("FROM VerificationToken WHERE user_id = ?1")
    VerificationToken findByUser(Long userId);
}
