package com.aarrd.room_designer.user.security.sign_up;

import com.aarrd.room_designer.user.security.vertification.TokenDoesNotExistException;
import com.aarrd.room_designer.user.security.vertification.TokenExpiredException;

public interface ISignUpService
{
    void signUp(String firstName, String lastName, String password, String email, String phoneNum);
    void confirmation(int token, String email) throws TokenDoesNotExistException, TokenExpiredException;
    void resendVerificationToken(String email);
}
