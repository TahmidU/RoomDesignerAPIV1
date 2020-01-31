package com.aarrd.room_designer.user;

import com.aarrd.room_designer.user.security.sign_up.UserLoginDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController implements IUserController
{
    @Autowired
    private UserService userService;

    /**
     * Change user details.
     * @param principal currently logged in user.
     * @param userDetail (request body) UserDetail.
     * @return HttpStatus.
     */
    @PutMapping(value = "/change-details", produces = "application/json")
    @Override
    public HttpStatus changeDetails(Principal principal, @RequestBody UserDetail userDetail)
    {
        return userService.changeDetails(principal, userDetail);
    }

    /**
     * Authenticate user.
     * @param signInUser (request body) SignInUser.
     * @return ResponseEntity containing string.
     * @throws IOException
     */
    @GetMapping(value = "/authenticate")
    @Override
    public ResponseEntity<String> authenticateUser(@RequestBody SignInUser signInUser) throws IOException
    {
        return userService.authenticateUser(signInUser);
    }
}
