package com.aarrd.room_designer.user;

import lombok.Data;

@Data
public class UserDetail
{
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNum;

    public UserDetail(){}

    public UserDetail(String firstName, String lastName, String password, String phoneNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNum = phoneNum;
    }
}
