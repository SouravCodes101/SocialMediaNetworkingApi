package com.example.Api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Api.Dao.PostsDao;
import com.example.Api.Models.Posts;
import com.example.Api.Models.User;

import java.util.List;

@Component
public class PostService {
  
  @Autowired
  private PostsDao postsDao;

  public Posts  addPost(Posts post) throws Exception {
    try{
      return postsDao.addPost(post);
    } catch (Exception e) {
      throw e;
    }
  }

  public List<Posts> getPosts() throws Exception {
    try {
       return postsDao.getPosts();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public List<Posts> sharePost(int postID) throws Exception {
    try {
      return postsDao.sharePost(postID);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
