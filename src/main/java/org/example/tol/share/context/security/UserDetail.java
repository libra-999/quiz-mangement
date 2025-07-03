package org.example.tol.share.context.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.tol.infrastructure.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Data
@AllArgsConstructor
public class UserDetail implements UserDetails {

    private String id;
    private String username;
    private String name;
    private int gender;

    @JsonIgnore
    private String password;
    private String dob;
    private Date createdAt;
    private boolean isActive;

    public static UserDetail build(User user) {
        return new UserDetail(
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getGender(),
            user.getPassword(),
            user.getDob(),
            user.getCreateTime(),
            user.isActive()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
