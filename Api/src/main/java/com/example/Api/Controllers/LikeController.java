package com.example.Api.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Api.Models.Likes;
import com.example.Api.Services.LikeService;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/likes")
public class LikeController {
  
  @Autowired
  private LikeService likeService;

  @PostMapping("/likePost")
  public Likes likePost(@RequestBody Likes like) throws Exception {
    try {
      // validateLikes(like);
      // List<Likes> likes = new ArrayList<Likes>();
      // likes.stream().filter(likeObj -> (likeObj.getPostId().equals(likes)));
      return likeService.likePost(like);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  void validateLikes (Likes like) throws Exception {
    try {
      // if(like.getUserId()== null || like.getUserId().isEmpty()) {
      //   throw new Exception("User Id is not present!!!");
      // }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @GetMapping("/getLikes")
  public List<Likes> getLikes(Likes like) throws Exception {
    try {
      return likeService.getLikes(like);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
