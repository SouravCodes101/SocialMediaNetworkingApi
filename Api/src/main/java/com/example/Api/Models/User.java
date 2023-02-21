package com.example.Api.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.regex.*;
import java.security.MessageDigest;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private int id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;

  public void validateParams() throws Exception {
    ArrayList<String> emails = new ArrayList<String>();
    emails.add(this.email);
    if (this.getFirstName() == null || this.getFirstName().isEmpty()) {
      throw new Exception("First name is required!");
    }
    if (this.getLastName() == null || this.getLastName().isEmpty()) {
      throw new Exception("Last name is required!");
    }
    if (this.getEmail() == null || this.getEmail().isEmpty()) {
      throw new Exception("Email is required!");
    }
    if (this.getPassword() == null || this.getPassword().isEmpty()) {
      throw new Exception("Password is required!");
    }
    if (!(isValidMail(emails))) {
      throw new Exception("Email is invalid");
    }
    if (!(isValidPassword(this.getPassword(), this))) {
      throw new Exception(
          "Password must contain at least 8 characters and at most 20 characters.It must contain at least one digit.It must contain at least one upper case alphabet. It must contain at least one lower case alphabet.It must contain at least one special character which includes !@#$%&*()-+=^.It should'nt contain any white space.");
    }
  }

  private Boolean isValidMail(ArrayList<String> emails) {
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

  private Boolean isValidPassword(String password, User user1) {
    String regex = "^(?=.*[0-9])"
        + "(?=.*[a-z])(?=.*[A-Z])"
        + "(?=.*[@#$%^&+=])"
        + "(?=\\S+$).{8,20}$";
    Pattern p = Pattern.compile(regex);

    Matcher m = p.matcher(user1.getPassword());

    return m.matches();
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
}
