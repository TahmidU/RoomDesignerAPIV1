package com.aarrd.room_designer.user.security.password;

import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.security.password.token_production.TokenEvent;
import com.aarrd.room_designer.user.security.vertification.registration.OnRegistrationComplete;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Calendar;

public class PasswordTokenService implements IPasswordTokenService
{
    private final IPasswordTokenRepository passwordTokenRepository;
    private final IUserRepository userRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordTokenService(IPasswordTokenRepository passwordTokenRepository, IUserRepository userRepository,
                                ApplicationEventPublisher applicationEventPublisher,
                                BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.passwordTokenRepository = passwordTokenRepository;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void sendEmail(String email)
    {
        User user = userRepository.findByEmail(email);
        applicationEventPublisher.publishEvent(new TokenEvent(user));
    }

    @Override
    public ResponseEntity<String> changePassword(String email, int token, String password)
    {
        User user = userRepository.findByEmail(email);
        PasswordToken passwordToken = passwordTokenRepository.findByUserId(user.getUserId());

        if(passwordToken != null)
        {
            if ((passwordToken.getExpiry() - (Calendar.getInstance()).getTime().getTime()) <= 0) {
                applicationEventPublisher.publishEvent(new OnRegistrationComplete(user));
                return new ResponseEntity<String>("TOKEN EXPIRED", HttpStatus.UNAUTHORIZED);
            }

            if (passwordToken.getToken() == token) {
                user.setPassword(bCryptPasswordEncoder.encode(password));
                userRepository.save(user);
                passwordTokenRepository.delete(passwordToken);
                return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
            }
        }

        return new ResponseEntity<String>("TOKEN NOT FOUND", HttpStatus.UNAUTHORIZED);
    }
}
