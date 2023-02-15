package com.example.Api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Api.Dao.UserDao;
import com.example.Api.Models.User;

import java.util.List;

@Component
public class UserService {
  
  @Autowired
  private UserDao userDao;

  public User addUser(User user) throws Exception {
    try {
      return userDao.addUser(user);
    } catch (Exception e) {
      throw e;
    }
  }

  public List<User> getUsers() throws Exception {
    try {
      return userDao.getUsers();
    } catch (Exception e) {
      throw e;
    }
  }

  public Boolean removeUser(String firstName) throws Exception{
    try{
      return userDao.removeUser(firstName);
    } catch(Exception e) {
      throw e;
    }
  }
}
