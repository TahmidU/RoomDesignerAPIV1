package com.aarrd.room_designer.user;

import lombok.Data;

@Data
public class SignInUser
{
    private String email;
    private String password;

    public SignInUser(){}

    public SignInUser(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
