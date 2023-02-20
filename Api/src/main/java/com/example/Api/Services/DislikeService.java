package com.example.Api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Api.Dao.DislikesDao;
import com.example.Api.Models.Dislikes;

import java.util.List;

@Component
public class DislikeService {
  @Autowired
  private DislikesDao disLikesDao;

  public Dislikes dislikePost(Dislikes dislike) throws Exception {
    try {
      return disLikesDao.dislikePost(dislike);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public List<Dislikes> getDislikes() throws Exception {
    try {
      return disLikesDao.getDislikes();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
