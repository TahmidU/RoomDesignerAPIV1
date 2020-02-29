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
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService)
    {
        this.userService = userService;
    }

    /**
     * Retrieve user details.
     * @param principal currently logged in user.
     * @return ResponseEntity containing user.
     */
    @GetMapping(value = "/me")
    @Override
    public ResponseEntity<?> userDetails(Principal principal)
    {
        return userService.userDetails(principal);
    }

    /**
     * Change user details.
     * @param principal (request parameter) currently logged in user.
     * @param firstName (request parameter) new first name of the user.
     * @param lastName (request parameter) new last name of the user.
     * @param password (request parameter) new password of the user.
     * @param phoneNum (request parameter) new phone number of the user.
     * @return HttpStatus.
     */
    @PutMapping(value = "/change-details", produces = "application/json")
    @Override
    public HttpStatus changeDetails(Principal principal, @RequestParam(required = false) String firstName,
                                    @RequestParam(required = false) String lastName,
                                    @RequestParam(required = false) String password,
                                    @RequestParam(required = false) String phoneNum)
    {
        return userService.changeDetails(principal, firstName, lastName, password, phoneNum);
    }

    /**
     * Authenticate user.
     * @param email (request parameter) email of the user.
     * @param password (request parameter) password of the user.
     * @return ResponseEntity containing String.
     * @throws IOException
     */
    @GetMapping(value = "/authenticate")
    @Override
    public ResponseEntity<?> authenticateUser(@RequestParam String email, @RequestParam String password) throws IOException
    {
        return userService.authenticateUser(email, password);
    }

    @GetMapping(value = "/contact-info")
    public ResponseEntity<?> retrieveContactInfo(@RequestParam Long userId)
    {
        return userService.retrieveContactInfo(userId);
    }
}
