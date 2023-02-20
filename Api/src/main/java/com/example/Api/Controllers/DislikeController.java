package com.example.Api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.Api.Services.DislikeService;
import com.example.Api.Models.Dislikes;

import java.util.List;

@RestController
@RequestMapping("/dislikes")
public class DislikeController {
  
  @Autowired
  private DislikeService dislikeService;

  @PostMapping("/dislikePost")
  public Dislikes dislikePost(@RequestBody Dislikes dislike) throws Exception {
    try {
      return dislikeService.dislikePost(dislike);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @GetMapping("/getDislikes")
  public List<Dislikes> getDislikes() throws Exception {
    try {
      return dislikeService.getDislikes();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
