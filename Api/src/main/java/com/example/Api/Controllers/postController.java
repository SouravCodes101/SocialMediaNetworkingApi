package com.example.Api.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Api.Models.Posts;

@RestController
@RequestMapping("/post")
public class postController {
  
  @PostMapping("/addPost")
  public Posts addPost (@RequestBody Posts post) {

  }
}
