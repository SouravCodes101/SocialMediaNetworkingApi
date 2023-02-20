package com.example.Api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Api.Models.Posts;
import com.example.Api.Services.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostsController {

  @Autowired
  private PostService postService;
  
  @PostMapping("/addPost")
  public Posts addPost(@RequestBody Posts post) throws Exception {
    try {
      return postService.addPost(post);
    } catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  } 

  @GetMapping("/getPosts")
  public List<Posts> getPosts() throws Exception {
    try {
      return postService.getPosts();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
