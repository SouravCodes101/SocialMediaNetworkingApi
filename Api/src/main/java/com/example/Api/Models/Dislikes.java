package com.example.Api.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Dislikes {
  private int id;
  private String userId;
  private int postId;
  private int dislikeCount;
}
