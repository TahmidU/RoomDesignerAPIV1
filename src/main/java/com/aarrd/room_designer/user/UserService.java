package com.aarrd.room_designer.user;

import com.aarrd.room_designer.user.security.sign_up.UserLoginDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Service
public class UserService implements IUserService
{
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Retrieve user details.
     * @param principal currently logged in user.
     * @return ResponseEntity containing user.
     */
    @Override
    public ResponseEntity<?> userDetails(Principal principal)
    {
        return new ResponseEntity<>(userRepository.findByEmail(principal.getName()), HttpStatus.OK);
    }

    /**
     * Change user details.
     * @param principal currently logged in user.
     * @param userDetail UserDetail.
     * @return HttpStatus.
     */
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

    /**
     * Authenticate user.
     * @param signInUser (request body) SignInUser.
     * @return ResponseEntity containing string.
     * @throws IOException
     */
    @Override
    public ResponseEntity<?> authenticateUser(SignInUser signInUser) throws IOException
    {
        User user = userRepository.findByEmail(signInUser.getEmail());
        if(user != null)
        {
            if(user.getActive()) {
                if (bCryptPasswordEncoder.matches(signInUser.getPassword(), user.getPassword()))
                    return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
            }else
                return new ResponseEntity<String>("NOT ACTIVE", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("NOT FOUND", HttpStatus.UNAUTHORIZED);
    }
}
