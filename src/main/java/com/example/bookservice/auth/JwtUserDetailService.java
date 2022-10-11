package com.example.bookservice.auth;

import com.example.bookservice.auth.UserPrincipal;
import com.example.bookservice.common.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null){
            return null;
        }
        if(username.equals(Common.DEFAULT_USERNAME)){
            return new UserPrincipal();
        }
        return null;
    }
}
