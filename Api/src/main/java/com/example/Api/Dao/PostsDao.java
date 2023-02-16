package com.example.Api.Dao;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.example.Api.Models.Posts;

import java.util.Date;

@Component
public class PostsDao {
  
  public Boolean addPost(Posts post) throws Exception {
    try {
      String query = "INSERT into posts(content,created_on,updated_on) VALUE (:content,:createdOn,:updatedOn)";
      KeyHolder holder = new GeneratedKeyHolder();
      Date created = new Date();
      Date updated = new Date();
      MapSqlParameterSource param = new MapSqlParameterSource();
      param.addValue("content", post.getContent());
      param.addValue("createdOn", created);
      param.addValue("updatedOn", updated);

      return true;
    } catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
