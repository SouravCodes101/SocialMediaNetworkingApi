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

  public Boolean isValidMail(ArrayList<String> emails) {
    String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    // !Compile regular expression to get the pattern
    Pattern pattern = Pattern.compile(regex);
    Boolean flag = false;
    for (String email : emails) {
      // !Create instance of matcher
      Matcher matcher = pattern.matcher(email);
      flag = matcher.matches();
    }
    return flag;
  }

  public Boolean isValidPassword(String password, User user1) {
    String regex = "^(?=.*[0-9])"
        + "(?=.*[a-z])(?=.*[A-Z])"
        + "(?=.*[@#$%^&+=])"
        + "(?=\\S+$).{8,20}$";
    Pattern p = Pattern.compile(regex);

    Matcher m = p.matcher(user1.getPassword());

    return m.matches();
  }

  @PostMapping("/signUp")
  public User signUp(@RequestBody User user) throws Exception {
    try {
      // validating user params
      validateParams(user);

      return userService.addUser(user);
    } catch (Exception e) {
      throw e;
    }
  }

  void validateParams(User user) throws Exception {
    ArrayList<String> emails = new ArrayList<String>();
    emails.add(user.getEmail());
    if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
      throw new Exception("First name is required!");
    }
    if (user.getLastName() == null || user.getLastName().isEmpty()) {
      throw new Exception("Last name is required!");
    }
    if (user.getEmail() == null || user.getEmail().isEmpty()) {
      throw new Exception("Email is required!");
    }
    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      throw new Exception("Password is required!");
    }
    if (!(isValidMail(emails))) {
      throw new Exception("Email is invalid");
    }
    if (!(isValidPassword(user.getPassword(), user))) {
      throw new Exception(
          "Password must contain at least 8 characters and at most 20 characters.It must contain at least one digit.It must contain at least one upper case alphabet. It must contain at least one lower case alphabet.It must contain at least one special character which includes !@#$%&*()-+=^.It should'nt contain any white space.");
    }
  }

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

  @GetMapping("/signIn")
  public List<User> signIn(@RequestParam (value = "email") String email, @RequestParam (value = "password") String password,User user)throws Exception {
      try {
        String pass = encrypt(password,user);
        List<User> newUser = userDao.getUserByEmail(email);
        for(int i =0; i<newUser.size(); i++) {
          if(!(newUser.get(i).getEmail().equals(email) && newUser.get(i).getPassword().equals(pass))) {
            throw new Exception("Enter Correct Email and Password!!!");
          }
        }
        return newUser.stream().filter(userObj -> (userObj.getEmail().equals(email)) && (userObj.getPassword().equals(pass))).collect(Collectors.toList());
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
