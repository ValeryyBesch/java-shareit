package ru.practicum.shareit.user;

import java.util.List;

public interface UserDao {

    User getUser(long userid);

    List<User> getAllUsers();

    void addUser(User user);

    User updateUser(User user, long userId);

    void deleteUser(User user);
}
