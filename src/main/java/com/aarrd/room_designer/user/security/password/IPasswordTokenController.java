package com.aarrd.room_designer.user.security.password;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface IPasswordTokenController
{
    HttpStatus sendEmail(@RequestParam String email);
    ResponseEntity<String> changePassword(@RequestParam String email, @RequestParam int token, @RequestParam String password);
}
