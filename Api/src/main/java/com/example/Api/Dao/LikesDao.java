package com.example.Api.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.support.KeyHolder;

import com.example.Api.Models.Likes;
import com.example.Api.Models.User;

import java.util.List;
import java.util.ArrayList;

@Component
public class LikesDao {

  // ToDo : 1. If user A hits like to post A, then count of post A should be
  // increased to 1.
  // ToDo: 2. If user B also hits like to post A then count of post A should be
  // increased from 1 to 2.
  // toDo: (means total like count of Post A is 2 now)
  // ToDo: 3. If user A again hits like button then it will be counted as dislike
  // and count should get decreased from 2 to 1.
  // toDo: (Now current count should be 1 again for post A).

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Likes likePost(Likes like) throws Exception {
    try {
      // String query = "INSERT into likes(user_id,like_count,post_id)
      // VALUES(:userId,:likeCount,:postId)";

      String query = "INSERT into likes(user_id,like_count,post_id) VALUES(:userId,:likeCount,:postId)";

      KeyHolder holder = new GeneratedKeyHolder();

      MapSqlParameterSource param = new MapSqlParameterSource();

      //ToDo: does not work because new column is created for every post
      // *Likes On Same Post*/
      int count = LikeCount(like.getPostId());
      
      if(isExistsPost(like.getPostId())){
        param.addValue("likeCount", count + 1);
      } else {
        param.addValue("likeCount", 1);
      }

      //*  if (isExistsPost(like.getPostId())) {
      //    query = "SELECT post_id, like_count FROM Employee GROUP BY like_count;"
      //*         if (isExistsMail(like.getUserId())) {
      //*             param.addValue("likeCount", count - 1);
      //    throw new Exception("User Id is same!!!!");
      //*         } else if (!isExistsMail(like.getUserId())){
      //ToDO ::  Update likes SET likeCount = count WHERE userID is equal to like.getUserId */
      //*            param.addValue("likeCount", count + 1);
      //*       }
      //*   } else {
      //*       param.addValue("likeCount", 1);
      //*  }

      // * ------------------- */
      param.addValue("userId", like.getUserId());
      param.addValue("postId", like.getPostId());

      namedParameterJdbcTemplate.update(query, param, holder);

      like.setId(holder.getKey().intValue());

      return like;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  // public Likes dislike(Likes dislike) throws Exception {
  // try {
  // String query = "UPDATE likes SET like_count = ?";

  // return dislike;
  // } catch (Exception e) {
  // e.printStackTrace();
  // throw e;
  // }
  // }

  public List<Likes> getPost(int postId) {
    Object[] obj = new Object[] { postId };
    String query = "SELECT * FROM likes WHERE post_id = ?";
    List<Likes> isPost = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Likes.class), obj);
    return isPost;
  }

  public Boolean isExistsPost(int postId) {
    List<Likes> like = getPost(postId);
    if (like.size() > 0) {
      return true;
    } else {
      return false;
    }
  }

  public int LikeCount(int postId) {
    Object[] obj = new Object[] { postId };
    String query = "SELECT like_count FROM likes WHERE post_id = ?";
    List<Likes> isPost = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Likes.class), obj);
    int count = 0;
    for (int i = 0; i < isPost.size(); i++) {
      count = isPost.get(i).getLikeCount();
    }
    return count;
  }

  // ToDo: get the user
  public List<Likes> getUserByEmail(String email) {
    Object[] obj = new Object[] { email };
    String query = "SELECT * FROM likes WHERE user_id = ?";
    List<Likes> users = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Likes.class), obj);
    return users;
  }

  public Boolean isExistsMail(String email) {
    List<Likes> users = getUserByEmail(email);
    if (users.size() > 0) {
      return true;
    } else {
      return false;
    }
  }

  public List<Likes> getLikes() throws Exception {
    try {
      Object[] obj = new Object[] {};
      String query = "SELECT * FROM likes";
      List<Likes> like = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Likes.class), obj);
      return like;
    } catch (Exception e) {
      throw e;
    }
  }
}
