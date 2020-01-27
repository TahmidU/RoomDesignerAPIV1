package com.aarrd.room_designer.user.security.password;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class PasswordTokenController implements IPasswordTokenController
{
    private final IPasswordTokenService passwordTokenService;

    public PasswordTokenController(IPasswordTokenService passwordTokenService) {
        this.passwordTokenService = passwordTokenService;
    }

    @PostMapping
    @Override
    public HttpStatus sendEmail(@RequestParam String email)
    {
        passwordTokenService.sendEmail(email);
        return HttpStatus.OK;
    }

    @Override
    public ResponseEntity<String> changePassword(@RequestParam String email, @RequestParam int token,
                                                 @RequestParam String password)
    {
        return passwordTokenService.changePassword(email, token, password);
    }
}
