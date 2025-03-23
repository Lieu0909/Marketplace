package com.marketplace.rest;

import com.marketplace.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {

    @PostMapping(path = "/signup")
    public ResponseEntity <String> signUp (@RequestBody(required =true)Map<String, String> requestMap);

    @PostMapping(path = "/login")
    public ResponseEntity <String> login (@RequestBody(required =true)Map<String, String> requestMap);

    @GetMapping(path = "/getAllAdmin")
    public ResponseEntity<List<UserWrapper>> getAllAdmin();

    @Transactional
    @Modifying
    @PostMapping(path = "/updateUserStatus")
    public ResponseEntity <String> updateStatus(@RequestBody(required = true)Map<String,String> requestMap);
}
