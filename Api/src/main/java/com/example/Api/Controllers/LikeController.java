package com.example.Api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Api.Services.LikeService;
import com.example.Api.Models.Likes;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {
  
  @Autowired
  private LikeService likeService;

  @PostMapping("/likePost")
  public Likes likePost(@RequestBody Likes like) throws Exception {
    try {
      return likeService.likePost(like);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @GetMapping("/getLikes")
  public List<Likes> getLikes() throws Exception {
    try {
      return likeService.getLikes();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @GetMapping("/likesCount")
  public List<Likes> likesCount() throws Exception {
    try {
      return likeService.numberOfLikes();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
