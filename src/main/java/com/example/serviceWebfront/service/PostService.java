package com.example.serviceWebfront.service;

import com.example.servicePost.*;
import com.example.serviceUser.UpdateUserPostRequest;
import com.example.serviceUser.UserRequest;
import com.example.serviceUser.UserResponse;
import com.example.serviceUser.UserServiceGrpc;
import com.example.serviceWebfront.domain.dto.PostDto;
import com.example.serviceWebfront.security.UserDetailsImpl;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    ManagedChannel getChannelPost() {
        return ManagedChannelBuilder.forAddress("localhost", 9095)
                .usePlaintext()
                .build();
    }

    ManagedChannel getChannelUser() {
        return ManagedChannelBuilder.forAddress("localhost", 9092)
                .usePlaintext()
                .build();
    }
    private String getIdUserCurrent(){
        UserDetailsImpl userCurrent = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userCurrent.getId();
    }

    public createPostResponse createPost(PostDto dto) {

        if (dto != null) {
            ManagedChannel channelPost = getChannelPost();
            createPostRequest request = createPostRequest.newBuilder()
                    .setUserId(getIdUserCurrent())
                    .setTitle(dto.getTitle())
                    .setContent(dto.getContent())
                    .build();
            PostServiceGrpc.PostServiceBlockingStub stub = PostServiceGrpc.newBlockingStub(channelPost);
            createPostResponse responseCreatePost = stub.createPost(request);


            ManagedChannel channelUser = getChannelUser();
            UpdateUserPostRequest updateUserPostRequest = UpdateUserPostRequest.newBuilder()
                    .setIdUser(getIdUserCurrent())
                    .setIdPost(responseCreatePost.getIdPost())
                    .build();
            UserServiceGrpc.UserServiceBlockingStub stubUser = UserServiceGrpc.newBlockingStub(channelUser);
            stubUser.updateUserPost(updateUserPostRequest);


            return responseCreatePost;

        }
        return createPostResponse.newBuilder()
                .setMessage("Please transmit data")
                .build();
    }

    public updatePostResponse updatePost(PostDto dto) {
        if (dto != null) {
            ManagedChannel channelPost = getChannelPost();
            updatePostRequest request = updatePostRequest.newBuilder()
                    .setIdPost(dto.getId())
                    .setContent(dto.getContent())
                    .setTitle(dto.getTitle())
                    .build();
            PostServiceGrpc.PostServiceBlockingStub stub = PostServiceGrpc.newBlockingStub(channelPost);
            updatePostResponse responseUpdatePost = stub.updatePost(request);
            return  responseUpdatePost;
        }
        return updatePostResponse.newBuilder()
                .setMessage("Please transmit data")
                .build();

    }

}
