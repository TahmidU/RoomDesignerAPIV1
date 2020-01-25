package com.aarrd.room_designer.user;

import com.aarrd.room_designer.user.security.sign_up.UserLoginDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService implements IUserService
{
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public HttpStatus userStat(UserLoginDetail userLoginDetail)
    {
        User user = userRepository.findByEmail(userLoginDetail.getUsername());
        if(user != null && !user.getActive())
            return HttpStatus.UNAUTHORIZED;
        else
            return HttpStatus.NOT_FOUND;
    }

    public HttpStatus changeDetails(Principal principal, UserDetail userDetail)
    {
        User user = userRepository.findByEmail(principal.getName());
        if (user != null)
        {
            user.setFirstName(userDetail.getFirstName());
            user.setLastName(userDetail.getLastName());
            user.setPassword(bCryptPasswordEncoder.encode(userDetail.getPassword()));
            user.setPhoneNum(userDetail.getPhoneNum());

            userRepository.save(user);
            return HttpStatus.OK;
        }
        return HttpStatus.CONFLICT;
    }

    @Override
    public User findById(Long userId)
    {
        return userRepository.getOne(userId);
    }
}
