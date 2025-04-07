package vn.graybee.models.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final Logger logger = LoggerFactory.getLogger(UserPrincipal.class);

    private final UserPrincipalDto user;

    private final Integer userUid;

    public UserPrincipal(UserPrincipalDto user, Integer userUid) {
        this.user = user;
        this.userUid = userUid;
    }

    public UserPrincipalDto getUser() {
        return user;
    }

    public Integer getUserUid() {
        return userUid;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.getROLE_NAME() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getROLE_NAME().toUpperCase()));
        }

        if (user.getPassword() != null && !user.getPermissions().isEmpty()) {
            user.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.toUpperCase())));
        }

        logger.info("Permission: {}", authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isActive();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

}
