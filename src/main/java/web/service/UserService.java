package web.service;

import web.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User findById(Long id);

    public List<User> findAll();
    public void saveUser(User user);

    public void deleteById(Long id);
    public void updateUser(Long id, User user);

}
