package com.example.serviceWebfront.common;


import lombok.Data;

@Data
public class DataResult {
    private String name;
    private String email;
    private String message;
//    private String jwt;
    private String postId;
    private String postTitle;
    private String postContent;

}
