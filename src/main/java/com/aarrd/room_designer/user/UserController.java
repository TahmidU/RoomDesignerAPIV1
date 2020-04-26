package com.aarrd.room_designer.user;

import com.aarrd.room_designer.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController
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
    public ResponseEntity<?> userDetails(Principal principal)
    {
        return userService.userDetails(principal);
    }

    /**
     * Change user details.
     * @param principal (request parameter) currently logged in user.
     * @return HttpStatus.
     */
    @PutMapping(value = "/change-details", produces = "application/json")
    public HttpStatus changeDetails(Principal principal, @RequestBody Map<String, Object> user)
    {
        return userService.changeDetails(principal, user);
    }

    /**
     * Authenticate user.
     * @return ResponseEntity containing String.
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, Object> login)
    {
        Log.printMsg(this.getClass(), "Blah!");
        return userService.authenticateUser(login);
    }

    @GetMapping(value = "/{userId}/details", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> retrieveUserDetails(@PathVariable(name = "userId") Long userId, Principal principal)
    {
        return userService.retrieveUserDetails(userId, principal);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(Principal principal)
    {
        return userService.deleteUser(principal);
    }
}
