package com.aarrd.room_designer.user.security.sign_up;

import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.SignInUser;
import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.security.vertification.IVerificationTokenRepository;
import com.aarrd.room_designer.user.security.vertification.TokenDoesNotExistException;
import com.aarrd.room_designer.user.security.vertification.TokenExpiredException;
import com.aarrd.room_designer.user.security.vertification.VerificationToken;
import com.aarrd.room_designer.user.security.vertification.registration.OnRegistrationComplete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
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
    public void confirmation(int token, String email) throws TokenDoesNotExistException, TokenExpiredException
    {
        User user = userRepository.findByEmail(email);
        VerificationToken verificationToken = verificationTokenRepository.findByUser(user.getUserId());
        if(verificationToken != null)
        {
            verificationTokenRepository.delete(verificationToken);
            if((verificationToken.getExpiry() - (Calendar.getInstance()).getTime().getTime()) <= 0)
            {
                applicationEventPublisher.publishEvent(new OnRegistrationComplete(user));
                throw new TokenExpiredException("Token has expired.");
            }

            user.setActive(true);
            userRepository.save(user);
        }else
            throw new TokenDoesNotExistException("Token does not exist!");
    }

    @Override
    public void resendVerificationToken(SignInUser signInUser)
    {
        User user = userRepository.findByEmail(signInUser.getEmail());
        VerificationToken token = verificationTokenRepository.findByUser(user.getUserId());
        verificationTokenRepository.delete(token);

        applicationEventPublisher.publishEvent(new OnRegistrationComplete(user));
    }
}
