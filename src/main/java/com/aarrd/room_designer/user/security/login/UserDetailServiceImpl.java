package com.aarrd.room_designer.user.security.login;

import com.aarrd.room_designer.user.User;
import com.aarrd.room_designer.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{
    @Autowired
    private IUserRepository IUserRepository;

    /**
     * Return user details via email.
     * @param email user email.
     * @return UserDetails.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        User user = IUserRepository.findByEmail(email);

        if(user == null)
            throw new UsernameNotFoundException("Email does not exist!");

        return new UserLoginDetail(user.getEmail(), user.getPassword(), user.getActive(), Collections.emptyList());
        //return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
