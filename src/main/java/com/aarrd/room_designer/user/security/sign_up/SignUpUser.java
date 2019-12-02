package com.aarrd.room_designer.user.security.sign_up;

import lombok.Data;

@Data
public class SignUpUser
{
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNum;

    public SignUpUser(){}

    public SignUpUser(String firstName, String lastName, String password, String email, String phoneNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNum = phoneNum;
    }
}
