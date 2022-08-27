package com.order.service.controller;

import com.order.service.dto.UserDetailsDto;
import com.order.service.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/user-details")
public class UserDetailsController {
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(value = "/get-user-details/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable("id") Long userId){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(userDetailsService.getUserDetails(userId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping(value = "/create-user-details/{id}")
    public ResponseEntity<?> createUserDetails(@PathVariable("id") Long userId, @RequestBody UserDetailsDto userDetailsDto){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(userDetailsService.createUserDetails(userId, userDetailsDto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
