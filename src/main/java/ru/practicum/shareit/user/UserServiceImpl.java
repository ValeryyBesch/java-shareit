package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserDao repository;

    @Override
    public User addUser(User user) {
        return repository.addUser(user);
    }

    @Override
    public User updateUser(User user, long userId) {

        repository.getUser(userId);
        user.setId(userId);
        return repository.updateUser(user, userId);
    }

    @Override
    public void deleteUser(long userId) {
        repository.deleteUser(repository.getUser(userId));
    }

    @Override
    public User getUserById(long userId) {
    return repository.getUser(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }
}
