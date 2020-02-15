package com.aarrd.room_designer.user.security.password;

import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.security.password.token_production.TokenEvent;
import com.aarrd.room_designer.user.security.vertification.registration.OnRegistrationComplete;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
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

    /**
     * Place user in database. Trigger password recovery event.
     * @param email user email.
     */
    @Override
    public void sendEmail(String email)
    {
        User user = userRepository.findByEmail(email);
        applicationEventPublisher.publishEvent(new TokenEvent(user));
    }

    /**
     * Change password.
     * @param email user email.
     * @param token token.
     * @param password password.
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<String> changePassword(String email, int token, String password)
    {
        User user = userRepository.findByEmail(email);
        PasswordToken passwordToken = passwordTokenRepository.findByUserId(user.getUserId());

        if(passwordToken != null)
        {
            if ((passwordToken.getExpiry() - (Calendar.getInstance()).getTime().getTime()) <= 0) {
                applicationEventPublisher.publishEvent(new OnRegistrationComplete(user));
                return new ResponseEntity<>("Token expired", HttpStatus.OK);
            }

            if (passwordToken.getToken() == token) {
                user.setPassword(bCryptPasswordEncoder.encode(password));
                userRepository.save(user);
                passwordTokenRepository.delete(passwordToken);
                return new ResponseEntity<>("", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Token not found", HttpStatus.OK);
    }
}
