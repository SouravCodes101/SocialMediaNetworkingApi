package com.example.Api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.regex.*;
import java.security.MessageDigest;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

import com.example.Api.Services.UserService;
import com.example.Api.Dao.UserDao;
import com.example.Api.Models.User;
import com.example.Api.Models.Posts;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private UserDao userDao;
  
  public List<User> users = new ArrayList<User>();

  

  @PostMapping("/signUp")
  public User signUp(@RequestBody User user) throws Exception {
    try {
      // validating user params
      user.validateParams();

      return userService.addUser(user);
    } catch (Exception e) {
      throw e;
    }
  }

  @GetMapping("/signIn")
  public User signIn(@RequestParam (value = "email") String email, @RequestParam (value = "password") String password,User user)throws Exception {
      try {
        String pass = user.encrypt(password,user);
        User newUser = userDao.getUserByEmailAndPassword(email,pass);
        return newUser;
      }catch(Exception e) {
        throw e;
      }
  }

  @GetMapping("/getUsers")
  public List<User> getUsers() throws Exception {
    try {
      return userService.getUsers();
    } catch (Exception e) {
      throw e;
    }
  }

  @GetMapping("/removeUsers")
  public Boolean removeUsers(@RequestParam(value = "firstName") String firstName) throws Exception {
    try {
      return userService.removeUser(firstName);
    } catch (Exception e) {
      throw e;
    }
  }
}
