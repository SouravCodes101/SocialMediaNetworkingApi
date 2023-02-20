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

import java.util.List;

@Component
public class LikesDao {
  
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public Likes likePost(Likes like) throws Exception{
    try {
      KeyHolder holder = new GeneratedKeyHolder();
      if(isPostAndUserExists(like)){
        delete(like,like.getPostId());
      } else {
        String query = "INSERT into likes(user_id,post_id,like_count) VALUES (:userId,:postId,:likeCount)";
         holder = new GeneratedKeyHolder();
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("userId", like.getUserId());
        param.addValue("postId",  like.getPostId());
        param.addValue("likeCount", 1);
        namedParameterJdbcTemplate.update(query, param, holder);
        like.setId(holder.getKey().intValue());
      }
        return like;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  // public List<Likes> likeCount() throws Exception {
  //   try {
  //     String query = "SELECT COUNT(post_id) FROM likes JOIN posts ON likes.post_id = posts.post_id";
  //   } catch (Exception e) {
  //     e.printStackTrace();
  //     throw e;
  //   }
  // }

  public void delete(Likes like,int postId) {
    String query = "DELETE FROM Likes WHERE post_id = :postId AND user_id = :userId";
    MapSqlParameterSource namedParameters = new MapSqlParameterSource("postId", like.getPostId());
    namedParameters.addValue("userId", like.getUserId());
    namedParameterJdbcTemplate.update(query, namedParameters);
  }

  public List<Likes> getLikes() throws Exception {
      try {
        Object[] obj = new Object[] {};
        String query = "SELECT * FROM likes";
        List<Likes> likes = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Likes.class), obj);
        return likes;
      } catch (Exception e) {
        throw e;
      }
    }

    public boolean isPostAndUserExists(Likes like) {
      Object[] obj = new Object[] { like.getPostId(), like.getUserId() };
        String query = "select * from likes where post_id = ? and user_id = ?";
        List<Likes> likes = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Likes.class) ,obj);
        if(likes.size() > 0) {
          return true;
       } else {
          return false;    
      }
    }
}
