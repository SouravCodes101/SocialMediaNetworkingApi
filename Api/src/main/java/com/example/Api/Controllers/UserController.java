package com.example.Api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.regex.*;

import java.util.List;
import java.util.ArrayList;

import com.example.Api.Services.UserService;
import com.example.Api.Models.User;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

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
