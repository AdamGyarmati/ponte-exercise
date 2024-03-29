package com.gyarmati.ponteexercisebackend.controller;

import com.gyarmati.ponteexercisebackend.dto.UserDetailsDto;
import com.gyarmati.ponteexercisebackend.dto.UserLoginDto;
import com.gyarmati.ponteexercisebackend.dto.UserRegisterDto;
import com.gyarmati.ponteexercisebackend.service.AppUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class AppUserController {
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AppUserController(AppUserService appUserService, AuthenticationManager authenticationManager) {
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetailsDto> register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        log.info("Http request POST /api/users/register with body: " + userRegisterDto.toString());
        UserDetailsDto userDetailsDto = appUserService.register(userRegisterDto);
        return new ResponseEntity<>(userDetailsDto, CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDetailsDto> login(@RequestBody UserLoginDto userLoginDto) {
        log.info("Http request POST /api/users/login with body: " + userLoginDto.toString());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getName(), userLoginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        UserDetailsDto userDetailsDto = appUserService.getUserByName(loggedInUser.getUsername());
        return new ResponseEntity<>(userDetailsDto, OK);
    }
}
