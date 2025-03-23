package com.marketplace.service;

import com.marketplace.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> signUp ( Map<String,String> requestMap);

    ResponseEntity<String> login ( Map<String,String> requestMap);

    ResponseEntity<List<UserWrapper>> getAllAdmin();

    ResponseEntity<String> updateStatus (Map<String,String> requestMap);
}
