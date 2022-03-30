package com.example.serviceWebfront.controller;

import com.example.serviceUser.UpdateUserResponse;
import com.example.serviceUser.UserResponse;

import com.example.serviceWebfront.common.DataResult;
import com.example.serviceWebfront.common.LoginRequest;
import com.example.serviceWebfront.domain.dto.UserDto;
import com.example.serviceWebfront.reponse.JwtResponse;
import com.example.serviceWebfront.security.JwtUtils;
import com.example.serviceWebfront.security.UserDetailsImpl;
import com.example.serviceWebfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }


    @PostMapping("auth/signup")
    public ResponseEntity<DataResult> createUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) {
        UserResponse result = userService.createUser(name, email, password);
        DataResult dataResult = new DataResult();
        dataResult.setName(result.getName());
        dataResult.setEmail(result.getEmail());
        dataResult.setMessage(result.getMessage());

        return new ResponseEntity<>(dataResult, HttpStatus.OK);

    }


    @PostMapping("auth/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassWord()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(
                userDetails.getId(),
                jwt,
                userDetails.getUsername()));
    }

    @PutMapping("updateUser")
    public ResponseEntity<DataResult> updateUser(@RequestBody UserDto dto) {
        UpdateUserResponse result = userService.updateUser(dto);
        DataResult dataResult = new DataResult();
        dataResult.setName(result.getName());
        dataResult.setEmail(result.getEmail());
        dataResult.setMessage(result.getMessage());

        return new ResponseEntity<>(dataResult, HttpStatus.OK);

    }

}
