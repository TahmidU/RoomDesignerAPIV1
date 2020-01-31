package com.aarrd.room_designer.user.security.sign_up;

import com.aarrd.room_designer.user.SignInUser;
import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.security.vertification.IVerificationTokenRepository;
import com.aarrd.room_designer.user.security.vertification.TokenDoesNotExistException;
import com.aarrd.room_designer.user.security.vertification.TokenExpiredException;
import com.aarrd.room_designer.user.security.vertification.VerificationToken;
import com.aarrd.room_designer.user.security.vertification.registration.OnRegistrationComplete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/sign-up")
public class SignUpController implements ISignUpController
{
    private final ISignUpService signUpService;

    @Autowired
    public SignUpController(ISignUpService signUpService) {
        this.signUpService = signUpService;
    }

    /**
     * User sign up. Send email for verification.
     * @param signUpUser (Request Body) SignUpUser.
     * @return HttpStatus.
     */
    @PostMapping("/v1")
    @Override
    public HttpStatus signUp(@RequestBody SignUpUser signUpUser)
    {
        signUpService.signUp(signUpUser);
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
    @Override
    public HttpStatus confirmAccount(@RequestParam int token, @RequestParam String email) throws TokenDoesNotExistException, TokenExpiredException
    {
        signUpService.confirmation(token, email);
        return HttpStatus.OK;
    }

    /**
     * Resend verification email.
     * @param signInUser (request body) SignInUser.
     * @return HttpStatus.
     */
    @PostMapping("/resend-token")
    @Override
    public HttpStatus resendVerificationToken(@RequestBody SignInUser signInUser)
    {
        signUpService.resendVerificationToken(signInUser);
        return HttpStatus.OK;
    }
}
