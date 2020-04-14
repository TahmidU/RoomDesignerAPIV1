package com.aarrd.room_designer.user.security.vertification.registration;

import com.aarrd.room_designer.AppConfig;
import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.security.vertification.IVerificationTokenRepository;
import com.aarrd.room_designer.user.security.vertification.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Async
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationComplete>
{
    private IVerificationTokenRepository verificationTokenRepository;
    private JavaMailSender javaMailSender;

    @Autowired
    public RegistrationListener(IVerificationTokenRepository verificationTokenRepository,
                                JavaMailSender javaMailSender)
    {
        this.verificationTokenRepository = verificationTokenRepository;
        this.javaMailSender = javaMailSender;
    }

    /**
     * On event execution...
     * @param event event.
     */
    @Override
    public void onApplicationEvent(OnRegistrationComplete event) {
        this.sendToken(event);
    }

    /**
     * Send password token to user email.
     * @param event event.
     */
    private void sendToken(OnRegistrationComplete event)
    {
        User user = event.getUser();
        Random random = new Random();
        int min = (int) Math.pow(10, 4);
        int max = (int) Math.pow(10, 5) - 1;
        int token = random.nextInt((max - min) + 1);

        VerificationToken verificationToken = new VerificationToken(token, user, calculateExpiryDate());

        verificationTokenRepository.save(verificationToken);

        String email = user.getEmail();
        String subject = "Registration Confirmation";
        String message = String.valueOf(token);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(AppConfig.EMAIL);
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
