package com.authentication;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.entity.Account;
import com.reponsitory.AccountRepository;;

/**
 * @author HiepLe
 * @version 1.0 Nov 10, 2017
 */

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountReponsitory;
    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
    	Account activeUserInfo = new Account();
    	Account getUser = accountReponsitory.getActiveUser(userName);
        if(getUser != null){
            activeUserInfo = getUser;
        }
        GrantedAuthority authority = new SimpleGrantedAuthority(activeUserInfo.getRole());
        UserDetails userDetails = (UserDetails)new User(activeUserInfo.getUserName(),
                activeUserInfo.getPassword(), Arrays.asList(authority));
        return userDetails;
    }

}
