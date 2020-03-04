package com.aarrd.room_designer.user;

import com.aarrd.room_designer.user.security.sign_up.UserLoginDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

public interface IUserService
{
    ResponseEntity<?> userDetails(Principal principal);
    HttpStatus changeDetails(Principal principal, String firstName, String lastName, String password, String phoneNum);
    ResponseEntity<?> authenticateUser(String email, String password) throws IOException;
    ResponseEntity<?> retrieveUserDetails(Long userId);
}
