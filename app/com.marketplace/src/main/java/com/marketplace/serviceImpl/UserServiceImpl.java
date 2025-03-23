package com.marketplace.serviceImpl;

import com.marketplace.JWT.CustomerUsersDetailsService;
import com.marketplace.JWT.JWTUtil;
import com.marketplace.JWT.JwtFilter;
import com.marketplace.POJO.User;
import com.marketplace.constants.Constants;
import com.marketplace.dao.UserDao;
import com.marketplace.service.UserService;
import com.marketplace.utils.ResponseUtils;
import com.marketplace.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    public ResponseEntity<String> signUp(Map<String, String> requestMap){
        log.info("Sign Up{}",requestMap);
        try{
            if (validateSignUp(requestMap)) {
                // Fetch existing users with matching email, phone, or name
                User user = userDao.findByAccountEmail(requestMap.get("email"));
                if(Objects.isNull(user)){
                    userDao.save(getUserFromMap(requestMap));
                    return ResponseUtils.getResponseEntity(Constants.ACCOUNT_REGISTERED_SUCCESS, HttpStatus.OK);
                }else{
                    return ResponseUtils.getResponseEntity(Constants.ACCOUNT_EMAIL_DUPLICATED, HttpStatus.BAD_REQUEST);
                }

                // Check for duplicate fields
//                for (User user : usersList) {
//                    if (user.getEmail().equals(requestMap.get("email"))) {
//                        return ResponseUtils.getResponseEntity(Constants.ACCOUNT_EMAIL_DUPLICATED, HttpStatus.BAD_REQUEST);
//                    }
//                    if (user.getName().equals(requestMap.get("name"))) {
//                        return ResponseUtils.getResponseEntity(Constants.ACCOUNT_NAME_DUPLICATED, HttpStatus.BAD_REQUEST);
//                    }
//                    if (user.getPhoneNumber().equals(requestMap.get("phoneNumber"))) {
//                        return ResponseUtils.getResponseEntity(Constants.ACCOUNT_PHONENUMBER_DUPLICATED, HttpStatus.BAD_REQUEST);
//                    }
//                }


            } else {
                return ResponseUtils.getResponseEntity(Constants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } 
            
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("login{}",requestMap);
        try{
            Authentication  auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
            );
            if(auth.isAuthenticated()){
                if(customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    log.info("login{}",customerUsersDetailsService.getUserDetail().getEmail());
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(),customerUsersDetailsService.getUserDetail().getRole()) + "\"}",
                    HttpStatus.OK);

                }else{
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval."+"\"}",HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception ex){
//            log.error("Login {}",ex);

        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credential."+"\"}",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllAdmin() {
        try{
            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllAdmin(),HttpStatus.OK);
            }else{
                return new ResponseEntity <>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity <>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                Optional <User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                log.info(" Optional {}", optional);
                if(!optional.isEmpty()){
                    userDao.updateUserStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    return ResponseUtils.getResponseEntity("User status update successfully", HttpStatus.BAD_REQUEST);
                }else{
                    return ResponseUtils.getResponseEntity(Constants.USERID_NOT_FOUND, HttpStatus.BAD_REQUEST);
                }

            }else{
                return ResponseUtils.getResponseEntity(Constants.UNAUTHORIZED_ACCESS, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseUtils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
    }

    private boolean validateSignUp(Map<String, String> requestMap) {
        return requestMap.containsKey("name") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("phoneNumber") &&
                requestMap.containsKey("password");
    }


    private User getUserFromMap(Map<String,String>requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setPhoneNumber(requestMap.get("phoneNumber"));
        user.setPassword(requestMap.get("password"));
        user.setRole(requestMap.get("role"));
        user.setStatus(requestMap.get("status"));
        return user;
    }
}
