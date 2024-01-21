package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {
    private static long newId = 1;

    private final Map<Long, User> userMap = new HashMap<>();


    public User getUser(long userId) {
        User user = userMap.get(userId);
        if (user == null) {
            throw new NotFoundException(User.class, "Пользователь с id " + userId + " не найден.");
        }
        return user;
    }


    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }


    public void addUser(User user) {
        for (User users : getAllUsers()) {
            if (users.getEmail().equals(user.getEmail())) {
                throw new ConflictException(user.getEmail() + "Уже зарегистрирован");
            }
        }

        if (user.getId() == 0) {
            user.setId(newId++);
        }

        userMap.put(user.getId(), user);
    }

    public User updateUser(User user, long userId) {
        User newUser = userMap.get(userId);
        if (user.getEmail() != null) {
            for (User userCheckEmail : getAllUsers()) {
                if (userCheckEmail.getEmail().equals(user.getEmail()) && userCheckEmail.getId() != userId) {
                    throw new ConflictException(user.getEmail() + "Уже зарегистрирован");
                }
            }

            newUser.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            newUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            newUser.setEmail(user.getEmail());
        }

        userMap.put(userId, newUser);
        return userMap.get(user.getId());
    }

    public void deleteUser(User user) {
        userMap.remove(user.getId());
    }
}

