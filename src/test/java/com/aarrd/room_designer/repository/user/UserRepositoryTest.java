package com.aarrd.room_designer.repository.user;

import com.aarrd.room_designer.user.IUserRepository;
import com.aarrd.room_designer.user.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IUserRepository userRepository;

    @Test
    public void givenEmailFindUser()
    {
        //given
        User user = new User("Tahmid", "Uddin", "foo234", "jimuddin@hotmail.co.uk",
                "07566754231", true);
        entityManager.persist(user);
        entityManager.flush();

        //when
        User foundUser = userRepository.findByEmail(user.getEmail());

        //then
        assertNotNull(foundUser);
    }
}
