package com.aarrd.room_designer.user.security.vertification.registration;

import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.security.vertification.IVerificationTokenRepository;
import com.aarrd.room_designer.user.security.vertification.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationComplete>
{
    @Autowired
    IVerificationTokenRepository verificationTokenRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(OnRegistrationComplete event) {
        this.sendToken(event);
    }

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
        simpleMailMessage.setFrom("Tahmiduddin@hotmail.co.uk");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);
    }

    private long calculateExpiryDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, 60);
        return calendar.getTime().getTime();
    }
}
