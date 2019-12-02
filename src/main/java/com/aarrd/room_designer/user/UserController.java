package com.aarrd.room_designer.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private IUserRepository IUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PutMapping(value = "/change-details", produces = "application/json")
    public HttpStatus changeDetails(Principal principal, @RequestBody UserDetail userDetail)
    {
        User user = IUserRepository.findByEmail(principal.getName());
        if (user != null)
        {
            user.setFirstName(userDetail.getFirstName());
            user.setLastName(userDetail.getLastName());
            user.setPassword(bCryptPasswordEncoder.encode(userDetail.getPassword()));
            user.setPhoneNum(userDetail.getPhoneNum());

            IUserRepository.save(user);
            return HttpStatus.OK;
        }

        return HttpStatus.UNAUTHORIZED;
    }
}
