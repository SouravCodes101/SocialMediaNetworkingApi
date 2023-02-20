package com.example.Api.Dao;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.example.Api.Models.Posts;
import com.example.Api.Models.User;

import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;

@Component
public class PostsDao {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public User user;

  @Autowired
  private UserDao userDao;

  public List<User> getUserByEmail(String email) {
    Object[] obj = new Object[] { email };
    String query = "SELECT * FROM users WHERE email = ?";
    Boolean flag = false;
    List<User> users = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(User.class), obj);
    return users;
} 

  public Posts addPost(Posts post) throws Exception {
    try {
      String query = "INSERT into posts(content,created_on,image_url,updated_on,created_by) VALUES(:content,:createdOn,:imageUrl,:updatedOn,:createdBy)";

      KeyHolder holder = new GeneratedKeyHolder();

      MapSqlParameterSource param = new MapSqlParameterSource();

      // List<User> email = getUserByEmail(user);

      param.addValue("content", post.getContent());
      param.addValue("createdBy",post.getCreatedBy());
      param.addValue("imageUrl", post.getImageUrl());
      LocalDateTime created = LocalDateTime.now();
      LocalDateTime updated = LocalDateTime.now();
      param.addValue("createdOn", created);
      param.addValue("updatedOn", updated);

      namedParameterJdbcTemplate.update(query, param, holder);

      post.setPostId(holder.getKey().intValue());

      return post;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public List<Posts> getPosts() throws Exception {
    try {
      Object[] obj = new Object[] {};
      String query = "SELECT * FROM posts";
      List<Posts> post = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Posts.class), obj);
      return post;
    } catch (Exception e) {
      throw e;
    }
  }

  // public String sharePost(Posts post) throws Exception {
  //   try {
  //     return getPostUrl(post);
  //   } catch (Exception e) {
  //     e.printStackTrace();
  //     throw e;
  //   }
  // }

  public List<Posts> sharePost(int postId) {
    Object[] obj = new Object[] { postId };
    String query = "SELECT * FROM posts WHERE post_id= ?";
    List<Posts> post = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Posts.class), obj);
    return post;
} 

  public Boolean isExistsMail(String email){
      List<User> users = getUserByEmail(email);
      if(users.size() > 0) {
        return true;
      } else {
        return false;
      }
  }

}
