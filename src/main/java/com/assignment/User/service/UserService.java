package com.assignment.User.service;

import com.assignment.User.entity.User;
import com.assignment.User.exception.NoUserInDatabaseException;
import com.assignment.User.exception.UserNotFoundException;
import com.assignment.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public Boolean checkIfUserExist(Long id)
    {
        if(!userRepository.existsById(id))
        {
            return false;
        }
        return true;
    }

    public User addUser(User user)
    {
        return userRepository.save(user);
    }
    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }
    public User getUserById(Long id){

        return userRepository.findById(id).get();
    }
    public void deleteUser(Long id)
    {
        userRepository.deleteById(id);
    }
    public User updateUser(Long id, User user)
    {

        Optional<User> byId = userRepository.findById(id);
        User userById=byId.get();
        userById.setName(user.getName());
        userById.setEmail(user.getEmail());
        userById.setPhone(user.getPhone());
        return userRepository.save(userById);


    }
}
