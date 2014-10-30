package org.ygc.rap.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.ygc.rap.object.User;
import org.ygc.rap.repo.UserDataLayer;
/*import org.yarlithub.dia.repo.DataLayer;
import org.yarlithub.dia.repo.object.Garden;*/

import java.util.Collection;
import java.util.List;

public class LoginUserDetails implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        final User user= UserDataLayer.getUserByName(username);
        if (user==null) {
            throw new UsernameNotFoundException(username + " not found");
        }

        //creating dummy user details, should do JDBC operations
        return new UserDetails() {

            private static final long serialVersionUID = 2059202961588104658L;

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            //To DO for authentication.
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
                auths.add(new SimpleGrantedAuthority("Admin"));
                return auths;
            }
        };
    }

}
