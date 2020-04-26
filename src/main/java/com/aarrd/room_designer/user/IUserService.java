package com.aarrd.room_designer.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Map;

public interface IUserService
{
    ResponseEntity<?> userDetails(Principal principal);
    HttpStatus changeDetails(Principal principal, Map<String,Object> user);
    ResponseEntity<?> authenticateUser(Map<String,Object> login);
    ResponseEntity<?> retrieveUserDetails(Long userId, Principal principal);
    ResponseEntity<?> deleteUser(Principal principal);
}
