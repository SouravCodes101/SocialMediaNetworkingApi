package com.example.Api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Api.Dao.LikesDao;
import com.example.Api.Models.Likes;
import com.example.Api.Models.User;

import java.util.List;

@Component
public class LikeService {

  @Autowired
  private LikesDao likesDao;

  public Likes likePost(Likes like) throws Exception {
    try {
      return likesDao.likePost(like);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public List<Likes> getLikes() throws Exception {
    try {
      return likesDao.getLikes();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public List<Likes> numberOfLikes() throws Exception {
    try {
      return likesDao.numberOfLikes();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
