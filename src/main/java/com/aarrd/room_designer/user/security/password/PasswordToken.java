package com.aarrd.room_designer.user.security.password;

import com.aarrd.room_designer.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "password_token")
@Data
public class PasswordToken
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_id")
    private Long passwordId;

    @Column(name = "token", nullable = false, length = 5)
    private int token;

    @Column(name = "expiry", nullable = false)
    private Long expiry;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public PasswordToken() {}

    public PasswordToken(int token, Long expiry) {
        this.token = token;
        this.expiry = expiry;
    }

    public PasswordToken(int token, Long expiry, User user) {
        this.token = token;
        this.expiry = expiry;
        this.user = user;
    }
}
