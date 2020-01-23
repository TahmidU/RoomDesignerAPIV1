package com.aarrd.room_designer.user.security.sign_up;

import com.aarrd.room_designer.user.security.vertification.TokenDoesNotExistException;
import com.aarrd.room_designer.user.security.vertification.TokenExpiredException;

public interface ISignUpService
{
    void signUp(SignUpUser signUpUser);
    void confirmation(int token) throws TokenDoesNotExistException, TokenExpiredException;
}
