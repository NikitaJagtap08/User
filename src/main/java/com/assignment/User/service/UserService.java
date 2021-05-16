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

    public User addUser(User user)
    {
        return userRepository.save(user);
    }
    public List<User> getAllUser()
    {
        if(userRepository.findAll().size()==0)
        {
            throw new NoUserInDatabaseException();
        }
        return userRepository.findAll();
    }
    public User getUserById(Long id){
        if(!userRepository.existsById(id))
        {
            throw new UserNotFoundException("User not found with id::"+id);
        }
        return userRepository.findById(id).get();
    }
    public void deleteUser(Long id)
    {
        if(!userRepository.existsById(id))
        {
            throw new UserNotFoundException("User not found with id::"+id);
        }
        userRepository.deleteById(id);
    }
    public User updateUser(Long id, User user)
    {
        if(!userRepository.existsById(id))
        {
            throw new UserNotFoundException("User not found with id::"+id);
        }

      else
        {
            Optional<User> byId = userRepository.findById(id);
            User userById=byId.get();
            userById.setName(user.getName());
           userById.setEmail(user.getEmail());
           userById.setPhone(user.getPhone());
           return userRepository.save(userById);
        }

    }
}
