package com.example.serviceWebfront.controller;

import com.example.servicePost.createPostResponse;
import com.example.servicePost.updatePostResponse;
import com.example.serviceWebfront.common.DataResult;
import com.example.serviceWebfront.domain.dto.PostDto;
import com.example.serviceWebfront.service.PostService;
import com.example.serviceWebfront.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    private PostService postService;
    public PostController (PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/post/createPost")
    public ResponseEntity<DataResult> createPost(@RequestBody PostDto dto) {
        createPostResponse response= postService.createPost(dto);

        DataResult dataResult = new DataResult();
        dataResult.setMessage(response.getMessage());

        return new ResponseEntity<>(dataResult, HttpStatus.OK);


    }
    @PostMapping("/post/updatePost")
    public ResponseEntity<DataResult> updatePost(@RequestBody PostDto dto) {
        updatePostResponse response = postService.updatePost(dto);

        DataResult dataResult = new DataResult();
        dataResult.setPostId(response.getIdPost());
        dataResult.setPostTitle(response.getTitle());
        dataResult.setPostContent(response.getContent());
        dataResult.setMessage(response.getMessage());
        return new ResponseEntity<>(dataResult,HttpStatus.OK);
    }



}
