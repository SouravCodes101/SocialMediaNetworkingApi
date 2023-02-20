package com.example.Api.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.KeyHolder;

import com.example.Api.Models.Dislikes;
import com.example.Api.Models.Likes;

import java.util.List;

@Component
public class DislikesDao {
  
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  // * DISLIKE POST --------------------------------------------
  public Dislikes dislikePost(Dislikes dislike) throws Exception{
    try {
      Likes like =  new Likes();
      KeyHolder holder = new GeneratedKeyHolder();
      if(isPostAndUserExists(dislike)){
        delete(dislike,dislike.getPostId());
      } else {
        String query = "INSERT  into dislikes(user_id,post_id,dislike_count) VALUES (:userId,:postId,:dislikeCount)";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("userId", dislike.getUserId());
        param.addValue("postId",  dislike.getPostId());
        param.addValue("dislikeCount", 1);
        namedParameterJdbcTemplate.update(query, param, holder);
        dislike.setId(holder.getKey().intValue());
      }
        return dislike;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  //* Method for Deleting dislike on posts */
  public void delete(Dislikes dislike,int postId) {
    String query = "DELETE FROM dislikes WHERE post_id = :postId AND user_id = :userId";
    MapSqlParameterSource namedParameters = new MapSqlParameterSource("postId", dislike.getPostId());
    namedParameters.addValue("userId", dislike.getUserId());
    namedParameterJdbcTemplate.update(query, namedParameters);
  }

  //* Method to check id same post_id and same user_id already exists in db */
  public boolean isPostAndUserExists(Dislikes dislike) {
    Object[] obj = new Object[] { dislike.getPostId(), dislike.getUserId() };
      String query = "select * from dislikes where post_id = ? and user_id = ?";
      List<Dislikes> dislikes = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Dislikes.class) ,obj);
      if(dislikes.size() > 0) {
        return true;
     } else {
        return false;    
    }
  }

  //*Get the dislikes from db */
  public List<Dislikes> getDislikes() throws Exception {
    try {
      Object[] obj = new Object[] {};
      String query = "SELECT * FROM dislikes";
      List<Dislikes> dislikes = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Dislikes.class), obj);
      return dislikes;
    } catch (Exception e) {
      throw e;
    }
  }

}
