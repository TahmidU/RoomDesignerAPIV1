package com.aarrd.room_designer.user.security.vertification;

import com.aarrd.room_designer.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IVerificationTokenRepository extends JpaRepository<VerificationToken, Long>
{
    VerificationToken findByToken(int token);

    @Query("FROM VerificationToken v WHERE v.user.userId = ?1")
    VerificationToken findByUser(Long userId);
}
