package com.suntyra.pip.lab3.repository;

import com.suntyra.pip.lab3.model.Point;
import com.suntyra.pip.lab3.model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserRepositoryTest {
    @Test
    public void saveAndFindTest() {
        UserRepository userRepository = new UserRepository();
        User user = new User("username1", "password1");
        user.getPoints().add(new Point(1.1, 1.2, 1.3, true, user));
        Long userId = userRepository.save(user);

        User received = userRepository.findByUsername(user.getUsername());
        assertEquals(userId, received.getId());
        assertEquals(user.getUsername(), received.getUsername());
        assertEquals(user.getPassword(), received.getPassword());
        assertEquals(user.getPoints().size(), received.getPoints().size());
        assertEquals(user.getPoints().get(0).getR(), received.getPoints().get(0).getR());
    }
}
