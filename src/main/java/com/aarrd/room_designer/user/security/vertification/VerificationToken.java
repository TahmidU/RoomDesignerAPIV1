package com.aarrd.room_designer.user.security.vertification;

import com.aarrd.room_designer.user.User;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "verification_token")
@Data
public class VerificationToken
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_id")
    private Long verification_id;

    @Column(name = "token", nullable = false, length = 5)
    private int token;

    @Column(name = "expiry", nullable = false)
    private long expiry;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public VerificationToken(){}

    public VerificationToken(int token, User user, long expiry) {
        this.token = token;
        this.user = user;
        this.expiry = expiry;
    }
}
