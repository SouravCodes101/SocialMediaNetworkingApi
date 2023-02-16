package com.example.Api.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.util.regex.*;

import com.example.Api.Models.User;

import java.security.MessageDigest;
import java.util.List;
import java.util.ArrayList;

@Component
public class UserDao {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public String encrypt(String pass,User user) throws Exception{
    try {
      String encryptedPassword = null;
      MessageDigest m = MessageDigest.getInstance("MD5");
      m.update(user.getPassword().getBytes());
      byte[] bytes = m.digest();

      StringBuilder s = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      return encryptedPassword = s.toString();
  } catch(Exception e) {
    throw e;
  }
 }
  public List<User> getUserByEmail(String email) {
      Object[] obj = new Object[] { email };
      String query = "SELECT * FROM users WHERE email = ?";
      Boolean flag = false;
      List<User> users = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(User.class), obj);
      return users;
  } 
  
    public Boolean isExistsMail(String email){
        List<User> users = getUserByEmail(email);
        if(users.size() > 0) {
          return true;
        } else {
          return false;
        }
    }

  public User addUser(User user) throws Exception {
    try {
      String query = "INSERT into users(first_name,last_name,email,password) VALUES(:firstName,:lastName,:email,:password) ";

      KeyHolder holder = new GeneratedKeyHolder();

      MapSqlParameterSource param = new MapSqlParameterSource();

      param.addValue("firstName", user.getFirstName());
      param.addValue("lastName", user.getLastName());
      if(isExistsMail(user.getEmail())) {
        throw new Exception("Duplicate Email");
      } else {
        param.addValue("email", user.getEmail());
      }
     
      String pass = encrypt(user.getPassword(), user);
      param.addValue("password", pass);

      namedParameterJdbcTemplate.update(query, param, holder);

      user.setId(holder.getKey().intValue());
      user.setEmail(user.getEmail());

      user.setPassword(null);
      return user;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public List<User> getUsers() throws Exception {
    try {
      Object[] obj = new Object[] {};
      String query = "SELECT * FROM users";
      List<User> user = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(User.class), obj);
      return user;
    } catch (Exception e) {
      throw e;
    }
  }

  public Boolean removeUser(String firstName) throws Exception {
    try {
      Object[] obj = new Object[] { firstName };
      String query = "DELETE FROM users WHERE first_name = ?";
      jdbcTemplate.update(query, obj);
      return true;
    } catch (Exception e) {
      throw e;
    }
  }
}
