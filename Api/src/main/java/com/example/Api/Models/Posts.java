package com.example.Api.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Posts {
  private int postId;
  private String content;
  private String imageUrl; //list -> string
  private Date createdOn;
  private String createdBy;
  private Date updatedOn;
  // private int updatedBy;
}
