package com.aarrd.room_designer.user;

import com.aarrd.room_designer.user.security.sign_up.UserLoginDetail;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

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
     * @param firstName new first name of the user.
     * @param lastName new last name of the user.
     * @param password new password of the user.
     * @param phoneNum new phone number of the user.
     * @return HttpStatus.
     */
    public HttpStatus changeDetails(Principal principal, String firstName, String lastName, String password, String phoneNum)
    {
        User user = userRepository.findByEmail(principal.getName());
        if (user != null)
        {
            if(firstName != null)
                user.setFirstName(firstName);
            if(lastName != null)
                user.setLastName(lastName);
            if(password != null)
                user.setPassword(bCryptPasswordEncoder.encode(password));
            if(password != null)
                user.setPhoneNum(phoneNum);

            userRepository.save(user);
            return HttpStatus.OK;
        }
        return HttpStatus.CONFLICT;
    }

    /**
     * Authenticate user.
     * @param email email of the user.
     * @param password password of the user.
     * @return ResponseEntity containing String.
     * @throws IOException
     */
    @Override
    public ResponseEntity<?> authenticateUser(String email, String password) throws IOException
    {
        User user = userRepository.findByEmail(email);
        if(user != null)
        {
            if(user.getActive()) {
                if (bCryptPasswordEncoder.matches(password, user.getPassword()))
                    return new ResponseEntity<>("", HttpStatus.OK);
                else
                    return new ResponseEntity<>("Email or Password incorrect.", HttpStatus.OK);
            }else
                return new ResponseEntity<>("This Account is not active.", HttpStatus.OK);
        }
        System.out.println("UserService:: user " + email + "is null.");
        return new ResponseEntity<>("Email or Password incorrect.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> retrieveUserDetails(Long userId)
    {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent())
        {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("userId", user.get().getUserId());
            jsonObject.put("firstName", user.get().getFirstName());
            jsonObject.put("lastName", user.get().getLastName());
            jsonObject.put("phoneNum", user.get().getPhoneNum());
            jsonObject.put("email", user.get().getEmail());

            return new ResponseEntity<>(jsonObject.toJSONString(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
