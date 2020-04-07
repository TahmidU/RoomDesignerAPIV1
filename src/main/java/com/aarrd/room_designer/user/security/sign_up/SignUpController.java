package com.aarrd.room_designer.user.security.sign_up;

import com.aarrd.room_designer.user.security.vertification.TokenDoesNotExistException;
import com.aarrd.room_designer.user.security.vertification.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/sign-up")
public class SignUpController
{
    private final ISignUpService signUpService;

    @Autowired
    public SignUpController(ISignUpService signUpService) {
        this.signUpService = signUpService;
    }

    /**
     * User sign up. Send email and by triggering an event.
     * @param firstName (request parameter) first name of the user.
     * @param lastName (request parameter) last name of the user.
     * @param password (request parameter) password of the user.
     * @param email (request parameter) email of the user.
     * @param phoneNum (request parameter) phone number of the user.
     */
    @PostMapping("/v1")
    public HttpStatus signUp(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String password,
                             @RequestParam String email, @RequestParam String phoneNum)
    {
        signUpService.signUp(firstName, lastName, password, email, phoneNum);
        return HttpStatus.OK;
    }

    /**
     * Confirm verification token.
     * @param token (request parameter) token.
     * @param email (request parameter) user email.
     * @return HttpStatus.
     * @throws TokenDoesNotExistException
     * @throws TokenExpiredException
     */
    @PostMapping("/confirmation")
    public ResponseEntity<?> confirmAccount(@RequestParam int token, @RequestParam String email) throws TokenDoesNotExistException, TokenExpiredException
    {
        return signUpService.confirmation(token, email);
    }

    /**
     * Resend verification token.
     * @param email (request parameter) email of the user.
     */
    @PostMapping("/resend-token")
    public HttpStatus resendVerificationToken(@RequestParam String email)
    {
        signUpService.resendVerificationToken(email);
        return HttpStatus.OK;
    }
}
