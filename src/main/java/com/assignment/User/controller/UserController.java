package com.assignment.User.controller;

import com.assignment.User.entity.User;
import com.assignment.User.exception.NoUserInDatabaseException;
import com.assignment.User.exception.UserNotFoundException;
import com.assignment.User.exception.UserNotValidException;
import com.assignment.User.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @PostMapping("/users")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user,
                                     BindingResult bindingResult,
                                     HttpServletRequest httpServletRequest)
            throws UserNotValidException {
        if(bindingResult.hasErrors())
        {
            throw new UserNotValidException(bindingResult);
        }
        User newUser=userService.addUser(user);
        logger.debug("User::"+newUser.getName()+" added successfully with Id::"+newUser.getId(),HttpStatus.OK);
        return new ResponseEntity<>("User::"+newUser.getName()+" added successfully with Id::"+newUser.getId(),HttpStatus.OK);

    }

    @GetMapping("/users")
    public List<User> getAllUsers(
            HttpServletRequest httpServletRequest)  {
        logger.debug("Inside getAllUsers method of UserController");
        List<User> allUser = userService.getAllUser();
        if(allUser.size()==0)
        {
            throw new NoUserInDatabaseException();
        }
        logger.debug("Response::"+allUser);
        return allUser;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id, HttpServletRequest httpServletRequest)  {
        logger.debug("Inside getUserById method of UserController");
        if(!userService.checkIfUserExist(id))
        {
            throw new UserNotFoundException("User does not exist with id::"+id);
        }

        User userById = userService.getUserById(id);
        logger.debug("Response::"+userById);
        return userById;
    }

    @PutMapping("/users/{id}")
    public User UpdateUser(@PathVariable Long id,
                           @Valid @RequestBody User user,
                           BindingResult bindingResult,

                           HttpServletRequest httpServletRequest
    ) throws UserNotValidException {
        logger.debug("Inside UpdateUser method of UserController");
        if(bindingResult.hasErrors())
        {
            throw new UserNotValidException(bindingResult);
        }
        if(!userService.checkIfUserExist(id))
        {
            throw new UserNotFoundException("User does not exist with id::"+id);
        }

        User updatedUser = userService.updateUser(id, user);
        logger.debug("Response::"+updatedUser);

        return updatedUser;
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id,
                                        HttpServletRequest httpServletRequest
    )
    {
        logger.debug("Inside deleteUser method of UserController");
        if(!userService.checkIfUserExist(id))
        {
            throw new UserNotFoundException("User does not exist with id::"+id);
        }

        userService.deleteUser(id);
        logger.debug("User::deleted with Id::"+id);

        return new ResponseEntity<>("User::deleted with Id::"+id,HttpStatus.OK);

    }

}
