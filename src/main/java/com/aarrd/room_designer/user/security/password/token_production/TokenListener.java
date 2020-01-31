package com.aarrd.room_designer.user.security.password.token_production;

import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.security.password.IPasswordTokenRepository;
import com.aarrd.room_designer.user.security.password.PasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Component
public class TokenListener implements ApplicationListener<TokenEvent>
{
    private IPasswordTokenRepository tokenRepository;
    private JavaMailSender javaMailSender;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public TokenListener(IPasswordTokenRepository tokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                         JavaMailSender javaMailSender)
    {
        this.tokenRepository = tokenRepository;
        this.javaMailSender = javaMailSender;
    }

    /**
     * On event execution...
     * @param event event.
     */
    @Override
    public void onApplicationEvent(TokenEvent event)
    {
        sendToken(event);
    }

    /**
     * Send password token to user email.
     * @param event event.
     */
    private void sendToken(TokenEvent event)
    {
        User user = event.getUser();
        Random random = new Random();
        int min = (int) Math.pow(10,4);
        int max = (int) Math.pow(10,5)-1;
        int token = random.nextInt((max-min)+1);

        long expiry = calculateExpiryDate();

        PasswordToken passwordToken = new PasswordToken(token, expiry, user);

        tokenRepository.save(passwordToken);

        String email = user.getEmail();
        String subject = "Password Recovery";
        String message = String.valueOf(token);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("Tahmiduddin@hotmail.co.uk");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);
    }

    /**
     * Calculate token expiration.
     * @return Long (the expiration).
     */
    private long calculateExpiryDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, 60);
        return calendar.getTime().getTime();
    }
}
