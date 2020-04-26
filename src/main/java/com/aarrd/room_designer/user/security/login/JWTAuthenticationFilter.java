package com.aarrd.room_designer.user.security.login;

import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.util.Log;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.aarrd.room_designer.user.security.login.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private AuthenticationManager authenticationManager;

    JWTAuthenticationFilter(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    /**
     * On attempting to authenticate...
     * @param request http request.
     * @param response http response.
     * @return Authentication.
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException
    {
        try
        {
            User cred = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    cred.getEmail(),
                    cred.getPassword(),
                    new ArrayList<>()
            ));
        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * On successful authentication create JWT token,
     * @param request http request.
     * @param response http response.
     * @param chain filter.
     * @param authResult authentication result.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
    {
        UserLoginDetail user =
                (UserLoginDetail) authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET));

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.addHeader(HEADER_MSG, MSG_SUCCESS);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        Log.printMsg(this.getClass(), failed.getMessage());
        response.addHeader(HEADER_MSG, failed.getMessage());
    }
}