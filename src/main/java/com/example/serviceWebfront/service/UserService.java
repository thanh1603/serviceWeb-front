package com.example.serviceWebfront.service;

import com.example.serviceUser.*;

import com.example.serviceWebfront.domain.dto.UserDto;
import com.example.serviceWebfront.security.UserDetailsImpl;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    ManagedChannel getChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 9092)
                .usePlaintext()
                .build();
    }

    public UserResponse createUser(String name, String email, String password) {
        if (name != null && name.length()>0 && password != null && password.length()>0) {
            ManagedChannel channel = getChannel();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            UserRequest request = UserRequest.newBuilder()
                    .setName(name)
                    .setEmail(email)
                    .setPassword(passwordEncoder.encode(password))
                    .build();
            UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);

            return stub.createUser(request);
        }
        return UserResponse.newBuilder()
                .setMessage("Please transmit data")
                .build();
    }

    public UserResponseLogin loginUser(String name) {
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9092)
//                .usePlaintext()
//                .build();
        if (name != null && name.length() > 0) {
            ManagedChannel channel = getChannel();
            UserRequestLogin request = UserRequestLogin.newBuilder()
                    .setName(name)
                    .build();
            UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);

            return stub.loginUser(request);
        }
        return UserResponseLogin.newBuilder()
                .setMessage("Please transmit data")
                .build();

    }


    public UpdateUserResponse updateUser(UserDto dto) {
        UserDetailsImpl userCurrent = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (dto !=null) {
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            ManagedChannel channel = getChannel();
            UpdateUserRequest request = UpdateUserRequest.newBuilder()
                        .setId(userCurrent.getId())
                        .setName(dto.getName())
                        .setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()))
                        .setEmail(dto.getEmail())
                        .build();

            UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
            return stub.updateUser(request);

        }

        return UpdateUserResponse.newBuilder()
                .setMessage("Please transmit data")
                .build();

    }


}
