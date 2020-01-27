package com.aarrd.room_designer.user.security.password;

import org.springframework.http.ResponseEntity;

public interface IPasswordTokenService
{
    void sendEmail(String email);
    ResponseEntity<String> changePassword(String email, int token, String password);
}
