package com.vgg.controller;

import com.vgg.config.JwtTokenUtil;
import com.vgg.exceptions.impl.AccessDeniedException;
import com.vgg.exceptions.impl.DuplicateException;
import com.vgg.model.*;
import com.vgg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/auth")
    public ApiResponse<AuthToken> login(@RequestBody @Valid LoginUser loginUser) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final User user = userService.findOne(loginUser.getUsername());
        //if (user == null) return new ApiResponse<>(401, "invalid username and password", null);
        if (user == null) throw new AccessDeniedException(HttpStatus.UNAUTHORIZED.toString(), "Invalid username or password");
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success",new AuthToken(token, user.getUsername()));
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody @Valid LoginUser registerUser){
         User user = userService.findOne(registerUser.getUsername());
        if (user != null) throw  new DuplicateException(HttpStatus.CONFLICT.toString(), "Username already exist");
        UserDto userDto = new UserDto();
        userDto.setPassword(registerUser.getPassword());
        userDto.setUsername(registerUser.getUsername());
        userService.save(userDto);
        return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",null);
    }

}
