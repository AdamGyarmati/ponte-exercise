package com.gyarmati.ponteexercisebackend.controller;

import com.gyarmati.ponteexercisebackend.dto.UserDetailsDto;
import com.gyarmati.ponteexercisebackend.dto.UserRegisterByAdminDto;
import com.gyarmati.ponteexercisebackend.dto.UserRegisterDto;
import com.gyarmati.ponteexercisebackend.dto.UserUpdateDto;
import com.gyarmati.ponteexercisebackend.service.AppUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class AppUserController {
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetailsDto> register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        log.info("Http request POST /api/users/register with body: " + userRegisterDto.toString());
        UserDetailsDto userDetailsDto = appUserService.register(userRegisterDto);
        return new ResponseEntity<>(userDetailsDto, CREATED);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> registerAppUserByAdmin(@RequestBody @Valid UserRegisterByAdminDto userRegisterByAdminDto) {
        log.info("Http request POST /api/users with body: " + userRegisterByAdminDto.toString());
        appUserService.registerAppUserByAdmin(userRegisterByAdminDto);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<UserDetailsDto> login() {
        log.info("Http request GET /api/users/login");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        UserDetailsDto userDetailsDto = appUserService.getUserByName(loggedInUser.getUsername());
        return new ResponseEntity<>(userDetailsDto, OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Void> delete() {
        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Http request DELETE /api/users with name: " + loggedInUser.getUsername());
        appUserService.delete(loggedInUser.getUsername());
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<UserDetailsDto> update(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        log.info("Http request PUT /api/users with body: " + userUpdateDto.toString());
        UserDetails loggedInUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsDto userDetailsDto = appUserService.update(loggedInUser.getUsername(), userUpdateDto);
        return new ResponseEntity<>(userDetailsDto, OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDetailsDto>> getAllWithPage(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        log.info("Http request, GET /api/users");
        List<UserDetailsDto> userDetailsDtoList = appUserService.listUserWithPage(pageNo, pageSize);
        return new ResponseEntity<>(userDetailsDtoList, OK);
    }
}
