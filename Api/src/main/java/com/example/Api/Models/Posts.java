package com.example.Api.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Posts {
  private int postId;
  private String content;
  private List<String> fileUrls;
  private Date createdOn;
  private int createdBy;
  private Date updatedOn;
  private int updatedBy;
}
