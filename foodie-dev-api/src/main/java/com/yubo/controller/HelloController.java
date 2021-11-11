package com.yubo.controller;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    public String set(@RequestParam("key") String key, @RequestParam("value") String value){
        redisTemplate.opsForValue().set(key,value);
        return "success";
    }

    @GetMapping("get")
    public String get(@RequestParam("key") String key){
        Object o = redisTemplate.opsForValue().get(key);
        return (String)o;
    }

    @GetMapping("delete")
    public String delete(@RequestParam("key") String key){
        redisTemplate.delete(key);
        return "ok";
    }

}
