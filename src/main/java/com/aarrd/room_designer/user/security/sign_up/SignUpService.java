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

    /**
     * User sign up. Send email and by triggering an event.
     * @param firstName first name of the user.
     * @param lastName last name of the user.
     * @param password password of the user.
     * @param email email of the user.
     * @param phoneNum phone number of the user.
     */
    @Override
    public void signUp(String firstName, String lastName, String password, String email, String phoneNum) {
        User user = new User(firstName, lastName, bCryptPasswordEncoder.encode(password), email, phoneNum, false);
        userRepository.save(user);
        applicationEventPublisher.publishEvent(new OnRegistrationComplete(user));
    }

    /**
     * Confirm the verification token.
     * @param token token.
     * @param email user email.
     * @throws TokenDoesNotExistException
     * @throws TokenExpiredException
     */
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

    /**
     * Resend verification token.
     * @param email email of the user.
     */
    @Override
    public void resendVerificationToken(String email)
    {
        User user = userRepository.findByEmail(email);
        VerificationToken token = verificationTokenRepository.findByUser(user.getUserId());
        verificationTokenRepository.delete(token);

        applicationEventPublisher.publishEvent(new OnRegistrationComplete(user));
    }
}
