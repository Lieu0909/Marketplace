package com.marketplace.JWT;

import com.marketplace.dao.UserDao;
import com.marketplace.POJO.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    private User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("{}",username);
        userDetail = userDao.findByAccountEmail(username);
        log.info("{}",userDetail);
        if (userDetail != null) {
            return new org.springframework.security.core.userdetails.User(
                    userDetail.getEmail(),
                    userDetail.getPassword(),
                    new ArrayList<>()
            );
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

    public User getUserDetail() {
        return userDetail;
    }
}
