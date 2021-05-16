package com.assignment.User.controller;

import com.assignment.User.entity.User;
import com.assignment.User.exception.UserNotValidException;
import com.assignment.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult bindingResult) throws UserNotValidException {
        if(bindingResult.hasErrors())
        {
            throw new UserNotValidException(bindingResult);
        }
        User newUser=userService.addUser(user);
        return new ResponseEntity<>("User::"+newUser.getName()+" added successfully with Id::"+newUser.getId(),HttpStatus.OK);
    }

    @GetMapping("/users")
    public List<User> getAllUsers()
    {
        return userService.getAllUser();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id)
    {
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}")
    public User UpdateUser(@PathVariable Long id,@Valid @RequestBody User user,BindingResult bindingResult) throws UserNotValidException {
        if(bindingResult.hasErrors())
        {
            throw new UserNotValidException(bindingResult);
        }
     return userService.updateUser(id,user);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
        return new ResponseEntity<>("User::deleted with Id::"+id,HttpStatus.OK);

    }

}
