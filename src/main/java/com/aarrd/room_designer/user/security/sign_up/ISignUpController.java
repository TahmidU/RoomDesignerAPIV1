package com.aarrd.room_designer.user.security.sign_up;

import com.aarrd.room_designer.user.SignInUser;
import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.security.vertification.TokenDoesNotExistException;
import com.aarrd.room_designer.user.security.vertification.TokenExpiredException;
import com.aarrd.room_designer.user.security.vertification.VerificationToken;
import com.aarrd.room_designer.user.security.vertification.registration.OnRegistrationComplete;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;

public interface ISignUpController
{
    public HttpStatus signUp(@RequestBody SignUpUser signUpUser);
    public HttpStatus confirmAccount(@RequestParam int token, @RequestParam String email) throws TokenDoesNotExistException, TokenExpiredException;
    public HttpStatus resendVerificationToken(@RequestBody SignInUser signInUser);
}
