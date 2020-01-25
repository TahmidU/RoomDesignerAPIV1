package com.aarrd.room_designer.user;

import com.aarrd.room_designer.user.security.sign_up.UserLoginDetail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

public interface IUserController
{
    HttpStatus userStat(UserLoginDetail userLoginDetail);
    HttpStatus changeDetails(Principal principal, @RequestBody UserDetail userDetail);
}
