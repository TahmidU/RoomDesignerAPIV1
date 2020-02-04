package com.aarrd.room_designer.user.security.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/password")
public class PasswordTokenController implements IPasswordTokenController
{
    private final IPasswordTokenService passwordTokenService;

    @Autowired
    public PasswordTokenController(IPasswordTokenService passwordTokenService) {
        this.passwordTokenService = passwordTokenService;
    }

    /**
     * Send email for password recovery.
     * @param email (request parameter) user email.
     * @return
     */
    @PostMapping("/recovery")
    @Override
    public HttpStatus sendEmail(@RequestParam String email)
    {
        passwordTokenService.sendEmail(email);
        return HttpStatus.OK;
    }

    /**
     * Change user password.
     * @param email (request parameter) user email.
     * @param token (request parameter) token.
     * @param password (request parameter) password.
     * @return ResponseEntity containing string (if it was a success).
     */
    @PostMapping("/recovery/change-password")
    @Override
    public ResponseEntity<String> changePassword(@RequestParam String email, @RequestParam int token,
                                                 @RequestParam String password)
    {
        return passwordTokenService.changePassword(email, token, password);
    }
}
