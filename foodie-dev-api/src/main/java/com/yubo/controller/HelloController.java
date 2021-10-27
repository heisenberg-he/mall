package com.yubo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("say")
    public String hello(){
        return "Hello bobo";
    }

    @GetMapping("set")
    public String set(String key,String value){
        redisTemplate.opsForValue().set(key,value);
        return "success";
    }

    @GetMapping("get")
    public String get(String key){
        Object o = redisTemplate.opsForValue().get(key);
        return (String)o;
    }

    @GetMapping("delete")
    public String delete(String key){
        redisTemplate.delete(key);
        return "ok";
    }

}
