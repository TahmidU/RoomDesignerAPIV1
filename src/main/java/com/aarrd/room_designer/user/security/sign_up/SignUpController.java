package com.aarrd.room_designer.user.security.sign_up;

import com.aarrd.room_designer.user.SignInUser;
import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.security.vertification.IVerificationTokenRepository;
import com.aarrd.room_designer.user.security.vertification.TokenDoesNotExistException;
import com.aarrd.room_designer.user.security.vertification.TokenExpiredException;
import com.aarrd.room_designer.user.security.vertification.VerificationToken;
import com.aarrd.room_designer.user.security.vertification.registration.OnRegistrationComplete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/sign-up")
public class SignUpController implements ISignUpController
{
    private final ISignUpService signUpService;

    @Autowired
    public SignUpController(ISignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/v1")
    @Override
    public HttpStatus signUp(@RequestBody SignUpUser signUpUser)
    {
        signUpService.signUp(signUpUser);
        return HttpStatus.OK;
    }

    @PostMapping("/confirmation")
    @Override
    public HttpStatus confirmAccount(@RequestParam int token, @RequestParam String email) throws TokenDoesNotExistException, TokenExpiredException
    {
        signUpService.confirmation(token, email);
        return HttpStatus.OK;
    }

    @PostMapping("/resend-token")
    @Override
    public HttpStatus resendVerificationToken(SignInUser signInUser)
    {
        signUpService.resendVerificationToken(signInUser);
        return HttpStatus.OK;
    }
}
