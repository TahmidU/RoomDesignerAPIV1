package com.aarrd.room_designer.user.security.sign_up;

import com.aarrd.room_designer.user.security.vertification.TokenDoesNotExistException;
import com.aarrd.room_designer.user.security.vertification.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;

public interface ISignUpController
{
    public HttpStatus signUp(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String password,
                             @RequestParam String email, @RequestParam String phoneNum);
    public ResponseEntity<?> confirmAccount(@RequestParam int token, @RequestParam String email) throws TokenDoesNotExistException, TokenExpiredException;
    public HttpStatus resendVerificationToken(@RequestParam String email);
}
