package com.yubo.controller;

import com.yubo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passport")
public class UsersController {
    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExists")
    public HttpStatus usernameIsExists(@RequestParam("username") String username) {
         if(!StringUtils.hasText(username)){
             //HttpStatus.;
         }
         Boolean aBoolean = userService.queryUserNameIsExist(username);
         if(aBoolean){
             //return 500;
         }
         return HttpStatus.CREATED;
    }
}
