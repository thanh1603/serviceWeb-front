package com.example.serviceWebfront.controller;

import com.example.servicePost.createPostResponse;
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


    @PostMapping("createPost")
    public ResponseEntity<DataResult> createPost(@RequestBody PostDto dto) {
        createPostResponse response= postService.createPost(dto);

        DataResult dataResult = new DataResult();
        dataResult.setMessage(response.getMessage());

        return new ResponseEntity<>(dataResult, HttpStatus.OK);


    }



}
