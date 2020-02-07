package com.aarrd.room_designer.user.security.vertification;

import com.aarrd.room_designer.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.Cascade;

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
    private Long verificationId;

    @Column(name = "token", nullable = false, length = 5)
    private int token;

    @Column(name = "expiry", nullable = false)
    private long expiry;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public VerificationToken(){}

    public VerificationToken(int token, long expiry) {
        this.token = token;
        this.expiry = expiry;
    }

    public VerificationToken(int token, User user, long expiry) {
        this.token = token;
        this.user = user;
        this.expiry = expiry;
    }
}
