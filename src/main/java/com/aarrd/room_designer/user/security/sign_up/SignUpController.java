package com.aarrd.room_designer.user.security.sign_up;

import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.security.vertification.IVerificationTokenRepository;
import com.aarrd.room_designer.user.security.vertification.TokenDoesNotExistException;
import com.aarrd.room_designer.user.security.vertification.TokenExpiredException;
import com.aarrd.room_designer.user.security.vertification.VerificationToken;
import com.aarrd.room_designer.user.security.vertification.registration.OnRegistrationComplete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/sign-up")
public class SignUpController
{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IVerificationTokenRepository verificationTokenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/v1")
    public void signUp(@RequestBody SignUpUser signUpUser)
    {
        User user = new User(signUpUser.getFirstName(),signUpUser.getLastName(),
                bCryptPasswordEncoder.encode(signUpUser.getPassword()), signUpUser.getEmail(),
                signUpUser.getPhoneNum(), false);
        userRepository.save(user);
        applicationEventPublisher.publishEvent(new OnRegistrationComplete(user));
    }

    @GetMapping("/reject-reason")
    public void reason(){}

    @PostMapping("/confirmation")
    public void confirmAccount(@RequestParam int token) throws TokenDoesNotExistException, TokenExpiredException
    {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        User user = verificationToken.getUser();
        if(user != null)
        {
            if((verificationToken.getExpiry() - (Calendar.getInstance()).getTime().getTime()) <= 0)
            {
                throw new TokenExpiredException("Token has expired.");
            }

            user.setActive(true);
            userRepository.save(user);
        }else
            throw new TokenDoesNotExistException("Token does not exist!");

    }

}
