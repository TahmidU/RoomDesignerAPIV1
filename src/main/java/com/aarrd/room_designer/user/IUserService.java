package com.aarrd.room_designer.user;

import com.aarrd.room_designer.user.security.sign_up.UserLoginDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

public interface IUserService
{
    ResponseEntity<?> userDetails(Principal principal);
    HttpStatus changeDetails(Principal principal, UserDetail userDetail);
    ResponseEntity<?> authenticateUser(SignInUser signInUser) throws IOException;
}
