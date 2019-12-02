package com.aarrd.room_designer.user.security.vertification;

import com.aarrd.room_designer.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVerificationTokenRepository extends JpaRepository<VerificationToken, Long>
{
    VerificationToken findByToken(int token);
    VerificationToken findByUser(User user);
}
