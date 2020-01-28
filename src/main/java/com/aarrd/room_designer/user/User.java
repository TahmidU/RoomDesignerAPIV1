package com.aarrd.room_designer.user;

import com.aarrd.room_designer.favourite.Favourite;
import com.aarrd.room_designer.item.Item;
import com.aarrd.room_designer.user.security.password.PasswordToken;
import com.aarrd.room_designer.user.security.vertification.VerificationToken;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone_num",unique = true, length = 11)
    private String phoneNum;

    @Column(name = "active", nullable = false, columnDefinition = "BIT", length = 1)
    private Boolean active;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private VerificationToken verificationTokens;

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private PasswordToken passwordToken;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private List<Item> items;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private List<Favourite> favourites;

    public User() {
    }

    public User(String firstName, String lastName, String password, String email, String phoneNum, Boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.active = active;
    }

    public User(Long userId, String firstName, String lastName, String password, String email, String phoneNum, Boolean active) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
        this.active = active;
    }
}
