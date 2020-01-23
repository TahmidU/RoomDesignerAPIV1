package com.aarrd.room_designer.user.security.sign_up;

import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.security.vertification.IVerificationTokenRepository;
import com.aarrd.room_designer.user.security.vertification.TokenDoesNotExistException;
import com.aarrd.room_designer.user.security.vertification.TokenExpiredException;
import com.aarrd.room_designer.user.security.vertification.VerificationToken;
import com.aarrd.room_designer.user.security.vertification.registration.OnRegistrationComplete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;

public class SignUpService implements ISignUpService
{
    private IUserRepository userRepository;
    private IVerificationTokenRepository verificationTokenRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public SignUpService(IUserRepository userRepository, IVerificationTokenRepository verificationTokenRepository,
                         BCryptPasswordEncoder bCryptPasswordEncoder,
                         ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void signUp(SignUpUser signUpUser) {
        User user = new User(signUpUser.getFirstName(),signUpUser.getLastName(),
                bCryptPasswordEncoder.encode(signUpUser.getPassword()), signUpUser.getEmail(),
                signUpUser.getPhoneNum(), false);
        userRepository.save(user);
        applicationEventPublisher.publishEvent(new OnRegistrationComplete(user));
    }

    @Override
    public void confirmation(int token) throws TokenDoesNotExistException, TokenExpiredException
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
