package com.aarrd.room_designer.user;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
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
     * @return HttpStatus.
     */
    public HttpStatus changeDetails(Principal principal, Map<String, Object> user)
    {
        User retrievedUser = userRepository.findByEmail(principal.getName());
        String firstName = (String) user.get("firstName");
        String lastName = (String) user.get("lastName");
        String password = (String) user.get("password");
        String phoneNum = (String) user.get("phoneNum");

        if (retrievedUser != null)
        {
            if(firstName != null && !firstName.equals(""))
                retrievedUser.setFirstName(firstName);
            if(lastName != null && !lastName.equals(""))
                retrievedUser.setLastName(lastName);
            if(password != null && !password.equals(""))
                retrievedUser.setPassword(bCryptPasswordEncoder.encode(password));
            if(phoneNum != null && phoneNum.equals(""))
                retrievedUser.setPhoneNum(phoneNum);

            userRepository.save(retrievedUser);
            return HttpStatus.OK;
        }
        return HttpStatus.CONFLICT;
    }

    /**
     * Authenticate user.
     * @return ResponseEntity containing String.
     */
    @Override
    public ResponseEntity<?> authenticateUser(Map<String,Object> login)
    {
        String email = (String) login.get("email");
        String password = (String) login.get("password");

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
    public ResponseEntity<?> retrieveUserDetails(Long userId, Principal principal)
    {
        Optional<User> user;
        if(!userId.equals((long)-1))
            user = userRepository.findById(userId);
        else
            user = Optional.of(userRepository.findByEmail(principal.getName()));
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

    @Override
    public ResponseEntity<?> deleteUser(Principal principal)
    {
        User user = userRepository.findByEmail(principal.getName());

        if(user != null)
        {
            userRepository.delete(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
