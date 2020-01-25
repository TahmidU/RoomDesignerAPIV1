package com.aarrd.room_designer.user;

import com.aarrd.room_designer.user.security.sign_up.UserLoginDetail;
import org.springframework.http.HttpStatus;

import java.security.Principal;

public interface IUserService
{
    HttpStatus userStat(UserLoginDetail userLoginDetail);
    HttpStatus changeDetails(Principal principal, UserDetail userDetail);
    User findById(Long userId);
}
