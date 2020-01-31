package com.aarrd.room_designer.user;

import com.aarrd.room_designer.user.security.sign_up.UserLoginDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import java.io.IOException;
import java.security.Principal;

public interface IUserController
{
    HttpStatus changeDetails(Principal principal, @RequestBody UserDetail userDetail);
    ResponseEntity<String> authenticateUser(@RequestBody SignInUser signInUser) throws IOException;
}
